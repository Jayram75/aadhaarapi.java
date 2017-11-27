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

import uidai.aadhaar.security.XmlSignature;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static uidai.aadhaar.serialization.ApiConstants.YES;

public class XmlReader {
    private final XMLStreamReader xml;
    private final XmlSignature verifier;

    public XmlReader(XMLStreamReader xml) {
        this(xml, null);
    }

    public XmlReader(XMLStreamReader xml, XmlSignature verifier) {
        this.xml = xml;
        this.verifier = verifier;
    }

    public void document() {
        // Do nothing.
    }

    public void endDocument() throws XMLStreamException {
        xml.close();
    }

    public String element() throws XMLStreamException {
        next(XMLStreamConstants.START_ELEMENT);
        return xml.getLocalName();
    }

    public String endElement() throws XMLStreamException {
        next(XMLStreamConstants.END_ELEMENT);
        return xml.getLocalName();
    }

    public String attribute(String localName) {
        return xml.getAttributeValue("", localName);
    }

    public boolean attributeBoolean(String localName) {
        final String a = attribute(localName);
        return a != null && YES.equals(attribute(localName));
    }

    public int attributeInt(String localName) {
        final String a = attribute(localName);
        return a != null ? Integer.parseInt(a) : 0;
    }

    public OffsetDateTime attributeDateTime(String localName, DateTimeFormatter formatter) {
        final String a = attribute(localName);
        return a != null ? OffsetDateTime.parse(a, formatter) : null;
    }

    public String text() {
        return xml.getText();
    }

    public void next(int event) throws XMLStreamException {
        while (xml.hasNext())
            if (xml.next() == event)
                return;
    }

    public XmlSignature getVerifier() {
        return verifier;
    }
}