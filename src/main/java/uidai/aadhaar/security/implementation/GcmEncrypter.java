/***************************************************************************************************
 * Aadhaar API for Java
 * Copyright © 2017 Souvik Dey Chowdhury
 *
 * This file is part of Aadhaar API for Java.
 *
 * Aadhaar API for Java is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * Aadhaar API for Java is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with Aadhaar API
 * for Java. If not, see http://www.gnu.org/licenses.
 **************************************************************************************************/

package uidai.aadhaar.security.implementation;

import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.security.EncryptedData;
import uidai.aadhaar.security.Encrypter;
import uidai.aadhaar.security.SessionKey;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public final class GcmEncrypter extends Encrypter {
    private static final String AES_ALGORITHM = "AES";
    private static final String SHA_ALGORITHM = "SHA-256";
    private static final String AES_GCM_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String RSA_ECB_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final int GCM_TAG_LENGTH = 128;

    private Cipher dataCipher;
    private Cipher hmacCipher;
    private Cipher rsaCipher;
    private KeyGenerator keyGenerator;
    private MessageDigest digest;
    private SecretKey key;
    private X509Certificate certificate;

    @Override
    public void init(X509Certificate uidaiKey) throws InvalidKeyException {
        ExceptionHelper.validateNull(uidaiKey, "uidaiKey");

        try {
            dataCipher = Cipher.getInstance(AES_GCM_TRANSFORMATION);
            hmacCipher = Cipher.getInstance(AES_GCM_TRANSFORMATION);
            rsaCipher = Cipher.getInstance(RSA_ECB_TRANSFORMATION);
            keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
            digest = MessageDigest.getInstance(SHA_ALGORITHM);
            key = keyGenerator.generateKey();
            certificate = uidaiKey;

            rsaCipher.init(Cipher.WRAP_MODE, certificate);
            state = ENCRYPT;
        } catch (final NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EncryptedData encrypt(byte[] dataToEncrypt, byte[] iv) {
        ExceptionHelper.validateNull(dataToEncrypt, "dataToEncrypt");
        ExceptionHelper.validateNull(iv, "iv");
        if (state != ENCRYPT)
            throw new IllegalStateException(ErrorMessages.INVALID_STATE);
        if (iv.length < 16)
            throw new IllegalArgumentException(ErrorMessages.INVALID_GCM_SPEC);

        /*
        *           1
        * 0123456789012345678 -> I
        * yyyy-MM-ddThh:mm:ss -> TS
        *        -ddThh:mm:ss -> IV
        *    y-MM-ddThh:mm:ss -> AAD
        * 0123456789012345678 -> I
        */

        EncryptedData encryptedData = null;
        try {
            dataCipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, iv, 7, 12));
            hmacCipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, iv, 7, 12));
            dataCipher.updateAAD(iv, 3, 16);
            hmacCipher.updateAAD(iv, 3, 16);

            final byte[] output = Arrays.copyOf(iv, iv.length + dataCipher.getOutputSize(dataToEncrypt.length));
            dataCipher.doFinal(dataToEncrypt, 0, dataToEncrypt.length, output, iv.length);

            encryptedData = new EncryptedData()
                .setData(output)
                .setHmac(hmacCipher.doFinal(digest.digest(dataToEncrypt)));

            state = WRAP;
        } catch (final InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException | ShortBufferException e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    @Override
    public SessionKey getSessionKey() {
        if (state != WRAP)
            throw new IllegalStateException(ErrorMessages.INVALID_STATE);

        SessionKey sessionKey = null;
        try {
            sessionKey = new SessionKey()
                .setKey(rsaCipher.wrap(key))
                .setCertificateId(CI_FORMATTER.format(certificate.getNotAfter()));
        } catch (final IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        } finally {
            key = keyGenerator.generateKey();
            state = ENCRYPT;
        }
        return sessionKey;
    }
}