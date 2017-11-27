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
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public final class EcbEncrypter extends Encrypter {
    private static final String AES_ALGORITHM = "AES";
    private static final String SHA_ALGORITHM = "SHA-256";
    private static final String AES_ECB_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String RSA_ECB_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    private Cipher aesCipher;
    private Cipher rsaCipher;
    private KeyGenerator keyGenerator;
    private MessageDigest digest;
    private SecretKey key;
    private X509Certificate certificate;

    @Override
    public void init(X509Certificate uidaiKey) throws InvalidKeyException {
        ExceptionHelper.validateNull(uidaiKey, "uidaiKey");

        try {
            aesCipher = Cipher.getInstance(AES_ECB_TRANSFORMATION);
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
        if (state != ENCRYPT)
            throw new IllegalStateException(ErrorMessages.INVALID_STATE);

        EncryptedData encryptedData = null;
        try {
            aesCipher.init(Cipher.ENCRYPT_MODE, key);
            encryptedData = new EncryptedData()
                .setData(aesCipher.doFinal(dataToEncrypt))
                .setHmac(aesCipher.doFinal(digest.digest(dataToEncrypt)));
            state = WRAP;
        } catch (final InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
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