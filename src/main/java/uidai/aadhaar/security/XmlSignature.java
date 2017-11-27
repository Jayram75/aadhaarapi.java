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

import org.xml.sax.SAXException;
import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.security.implementation.DomXmlSignature;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.PublicKey;

public abstract class XmlSignature {
    protected static final int UNINITIALIZED = 0;
    protected static final int SIGN = 1;
    protected static final int VERIFY = 2;
    private static final String DEFAULT_VERSION = "DOM";

    protected int state = UNINITIALIZED;

    public static XmlSignature getInstance() {
        return getInstance(DEFAULT_VERSION);
    }

    public static XmlSignature getInstance(String version) {
        ExceptionHelper.validateEmptyString(version, "version");

        final XmlSignature signature;
        switch (version) {
            case DEFAULT_VERSION:
                signature = new DomXmlSignature();
                break;
            default:
                throw new IllegalArgumentException(ErrorMessages.INVALID_VERSION);
        }

        return signature;
    }

    public abstract void initSign(PrivateKeyEntry entry);

    public abstract void initVerify(PublicKey publicKey);

    public abstract void sign(InputStream in, OutputStream out) throws IOException, SAXException, XMLStreamException;

    public abstract boolean verify(InputStream in) throws IOException, SAXException, XMLStreamException;
}