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
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.security.XmlSignature;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ApiXmlEncoder<TApi> {
    private static final XMLOutputFactory OUTPUT_FACTORY = XMLOutputFactory.newInstance();

    protected TApi api;
    protected XmlSignature signer;

    protected ApiXmlEncoder() {
    }

    protected ApiXmlEncoder(TApi api) {
        this.api = api;
    }

    protected ApiXmlEncoder(TApi api, XmlSignature signer) {
        ExceptionHelper.validateNull(api, "api");

        this.api = api;
        this.signer = signer;
    }

    public void write(OutputStream out) {
        ExceptionHelper.validateNull(out, "out");

        try {
            if (signer == null)
                write(OUTPUT_FACTORY.createXMLStreamWriter(out));
            else {
                final ByteArrayOutputStream unsigned = new ByteArrayOutputStream(16000);
                write(OUTPUT_FACTORY.createXMLStreamWriter(unsigned));
                signer.sign(new ByteArrayInputStream(unsigned.toByteArray()), out);
            }
        } catch (final IOException | SAXException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public void write(XMLStreamWriter xml) {
        ExceptionHelper.validateNull(xml, "xml");
    }

    public void validate() throws ApiException {
    }
}