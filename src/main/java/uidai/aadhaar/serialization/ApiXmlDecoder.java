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

package uidai.aadhaar.serialization;

import org.xml.sax.SAXException;
import uidai.aadhaar.generic.ApiException;
import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.security.XmlSignature;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ApiXmlDecoder<TApi> {
    private static final XMLInputFactory INPUT_FACTORY = XMLInputFactory.newInstance();

    protected XmlSignature verifier;

    protected ApiXmlDecoder() {
    }

    protected ApiXmlDecoder(XmlSignature verifier) {
        this.verifier = verifier;
    }

    public TApi read(InputStream in) throws ApiException {
        ExceptionHelper.validateNull(in, "in");

        TApi api = null;
        try {
            if (verifier == null)
                api = read(INPUT_FACTORY.createXMLStreamReader(in));
            else {
                final ByteArrayInputStream byteIn = new ByteArrayInputStream(in.readAllBytes());
                if (!verifier.verify(byteIn))
                    throw new ApiException(ErrorMessages.INVALID_SIGNATURE);
                api = read(INPUT_FACTORY.createXMLStreamReader(byteIn));
            }
        } catch (final IOException | SAXException | XMLStreamException e) {
            e.printStackTrace();
        }
        return api;
    }

    public TApi read(XMLStreamReader xml) throws ApiException {
        ExceptionHelper.validateNull(xml, "xml");
        return null;
    }

    public void validate() throws ApiException {
    }
}