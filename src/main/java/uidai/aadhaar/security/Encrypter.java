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
import uidai.aadhaar.security.implementation.EcbEncrypter;
import uidai.aadhaar.security.implementation.GcmEncrypter;

import java.security.InvalidKeyException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class Encrypter {
    protected static final int UNINITIALIZED = 0;
    protected static final int ENCRYPT = 1;
    protected static final int WRAP = 2;
    protected static final SimpleDateFormat CI_FORMATTER = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
    private static final String DEFAULT_VESION = "2.0";

    protected int state = UNINITIALIZED;

    public static Encrypter getInstance() {
        return getInstance(DEFAULT_VESION);
    }

    public static Encrypter getInstance(String version) {
        ExceptionHelper.validateEmptyString(version, "version");

        final Encrypter encrypter;
        switch (version.charAt(0)) {
            case '1':
                encrypter = new EcbEncrypter();
                break;
            case '2':
                encrypter = new GcmEncrypter();
                break;
            default:
                throw new IllegalArgumentException(ErrorMessages.INVALID_VERSION);
        }
        return encrypter;
    }

    public abstract void init(X509Certificate uidaiKey) throws InvalidKeyException;

    public abstract EncryptedData encrypt(byte[] dataToEncrypt, byte[] iv);

    public abstract SessionKey getSessionKey();
}