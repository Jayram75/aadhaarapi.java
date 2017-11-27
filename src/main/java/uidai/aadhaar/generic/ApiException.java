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

package uidai.aadhaar.generic;

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The base exception for API related exception. This {@link Exception} class is used to report
 * error API responses as well as invalid API request data.
 */
public class ApiException extends Exception {
    private static final long serialVersionUID = 8408340511370793154L;

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    protected ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private void readObject(ObjectInputStream in) throws NotSerializableException {
        throw new NotSerializableException();
    }

    private void writeObject(ObjectOutputStream out) throws NotSerializableException {
        throw new NotSerializableException();
    }
}