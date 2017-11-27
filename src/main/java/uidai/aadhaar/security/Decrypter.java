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

package uidai.aadhaar.security;

import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.security.implementation.EcbDecrypter;

import java.security.PrivateKey;
import java.security.SignatureException;

public abstract class Decrypter {
    protected static final int UNINITIALIZED = 0;
    protected static final int DECRYPT = 1;
    private static final String DEFAULT_VESION = "2.0";

    protected int state = UNINITIALIZED;

    public static Decrypter getInstance() {
        return getInstance(DEFAULT_VESION);
    }

    public static Decrypter getInstance(String version) {
        ExceptionHelper.validateEmptyString(version, "version");

        final Decrypter decrypter;
        switch (version.charAt(0)) {
            case '1':
            case '2':
                decrypter = new EcbDecrypter();
                break;
            default:
                throw new IllegalArgumentException(ErrorMessages.INVALID_VERSION);
        }
        return decrypter;
    }

    public abstract void init(PrivateKey privateKey);

    public abstract byte[] decrypt(byte[] dataToDecrypt) throws SignatureException;
}