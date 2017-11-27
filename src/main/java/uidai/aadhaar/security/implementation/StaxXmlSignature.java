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
import uidai.aadhaar.security.XmlSignature;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.PublicKey;

public final class StaxXmlSignature extends XmlSignature {
    @Override
    public void initSign(PrivateKeyEntry entry) {
        throw new UnsupportedOperationException(ErrorMessages.NOT_SUPPORTED);
    }

    @Override
    public void initVerify(PublicKey publicKey) {
        throw new UnsupportedOperationException(ErrorMessages.NOT_SUPPORTED);
    }

    @Override
    public void sign(InputStream in, OutputStream out) {
        throw new UnsupportedOperationException(ErrorMessages.NOT_SUPPORTED);
    }

    @Override
    public boolean verify(InputStream in) {
        throw new UnsupportedOperationException(ErrorMessages.NOT_SUPPORTED);
    }
}