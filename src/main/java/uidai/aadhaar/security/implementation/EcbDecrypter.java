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
import uidai.aadhaar.security.Decrypter;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource.PSpecified;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.util.Arrays;

import static uidai.aadhaar.security.implementation.EcbDecrypter.DataLengths.*;

public final class EcbDecrypter extends Decrypter {
    private static final String AES_ALGORITHM = "AES";
    private static final String SHA_ALGORITHM = "SHA-256";
    private static final String MGF = "MGF1";
    private static final String AES_CFB_TRANSFORMATION = "AES/CFB/NoPadding";
    private static final String RSA_ECB_TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String HEADER = "VERSION_1.0";

    private Cipher aesCipher;
    private Cipher rsaCipher;
    private MessageDigest digest;
    private PrivateKey privateKey;

    private static void validateHeader(byte[] encryptedData) {
        final String header = new String(encryptedData, HEADER_OFFSET, HEADER_LENGTH, StandardCharsets.UTF_8);
        if (!header.equalsIgnoreCase(HEADER))
            throw new IllegalArgumentException(ErrorMessages.INVALID_VERSION);
    }

    public void init(PrivateKey privateKey) {
        ExceptionHelper.validateNull(privateKey, "privateKey");

        try {
            aesCipher = Cipher.getInstance(AES_CFB_TRANSFORMATION);
            rsaCipher = Cipher.getInstance(RSA_ECB_TRANSFORMATION);
            digest = MessageDigest.getInstance(SHA_ALGORITHM);
            this.privateKey = privateKey;
            state = DECRYPT;
        } catch (final NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public byte[] decrypt(byte[] dataToDecrypt) throws SignatureException {
        ExceptionHelper.validateNull(dataToDecrypt, "dataToDecrypt");
        if (state != DECRYPT)
            throw new IllegalStateException(ErrorMessages.INVALID_STATE);

        byte[] data = null;
        try {
            validateHeader(dataToDecrypt);

            // Get P Source
            final byte[] pSource = Arrays.copyOfRange(dataToDecrypt, P_OFFSET, P_OFFSET + P_LENGTH);

            // Decrypt key
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey, new OAEPParameterSpec(SHA_ALGORITHM, MGF, MGF1ParameterSpec.SHA256, new PSpecified(pSource)));
            final byte[] key = rsaCipher.doFinal(dataToDecrypt, KEY_OFFSET, KEY_LENGTH);

            // Decrypt data
            aesCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, AES_ALGORITHM), new IvParameterSpec(pSource, 0, 16));
            final ByteBuffer input = ByteBuffer.wrap(dataToDecrypt, DATA_OFFSET, dataToDecrypt.length - DATA_OFFSET);
            final ByteBuffer output = ByteBuffer.allocate(aesCipher.getOutputSize(dataToDecrypt.length));
            aesCipher.doFinal(input, output);

            final byte[] hash = new byte[HASH_LENGTH];
            output.get(hash);
            data = new byte[output.remaining()];
            output.get(data);

            validateHash(data, hash);
        } catch (final InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | ShortBufferException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void validateHash(byte[] data, byte[] hash) throws SignatureException {
        if (!MessageDigest.isEqual(hash, digest.digest(data)))
            throw new SignatureException(ErrorMessages.INVALID_DATA);
    }

    static final class DataLengths {
        /*
        * Encrypted
        * ----------
        * Name         Size        From  To
        * ----------------------------------------
        * Header       11          0       11
        * Public Key   294         11      305
        * P Source     32          305     337
        * Key          256         337     593
        * Data         n - 593     593     n - 593
        *
        * Decrypted
        * ----------
        * Name         Size        From  To
        * ----------------------------------------
        * Hash         32          0       32
        * Data         n - 32      32      n - 32
        */

        static final int HEADER_OFFSET = 0;
        static final int HEADER_LENGTH = 11;

        static final int P_OFFSET = 305;
        static final int P_LENGTH = 32;

        static final int KEY_OFFSET = 337;
        static final int KEY_LENGTH = 256;

        // static final int PUBLIC_KEY_OFFSET = 11;
        // static final int PUBLIC_KEY_LENGTH = 294;

        static final int DATA_OFFSET = 593;

        static final int HASH_OFFSET = 0;
        static final int HASH_LENGTH = 32;

        static final int DECRYPTED_DATA_OFFSET = 32;

        private DataLengths() {
        }
    }
}