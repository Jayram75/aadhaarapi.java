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

import uidai.aadhaar.generic.CustomEnumValue;
import uidai.aadhaar.security.XmlSignature;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public final class XmlWriter {
    private final XMLStreamWriter xml;
    private final XmlSignature signer;

    public XmlWriter(XMLStreamWriter xml) {
        this(xml, null);
    }

    public XmlWriter(XMLStreamWriter xml, XmlSignature signer) {
        this.xml = xml;
        this.signer = signer;
    }

    public void document() throws XMLStreamException {
        xml.writeStartDocument();
    }

    public void endDocument() throws XMLStreamException {
        xml.writeEndDocument();
        xml.close();
    }

    public void element(String localName) throws XMLStreamException {
        xml.writeStartElement(localName);
    }

    public void element(String localName, String defaultNamespaceURI) throws XMLStreamException {
        xml.writeStartElement(localName);
        xml.writeDefaultNamespace(defaultNamespaceURI);
    }

    public void endElement() throws XMLStreamException {
        xml.writeEndElement();
    }

    public void emptyElement(String localName) throws XMLStreamException {
        xml.writeEmptyElement(localName);
    }

    public void text(String value) throws XMLStreamException {
        xml.writeCharacters(value);
    }

    public <T> void attribute(String localName, T value) throws XMLStreamException {
        if (value != null) {
            final String s = String.valueOf(value);
            if (!s.isEmpty())
                xml.writeAttribute(localName, s);
        }
    }

    public <V, E extends Enum<E> & CustomEnumValue<V>> void attribute(String localName, E value) throws XMLStreamException {
        if (value != null) {
            final String s = String.valueOf(value.value());
            if (!s.isEmpty())
                xml.writeAttribute(localName, s);
        }
    }

    public <T> void requiredAttribute(String localName, T value) throws XMLStreamException {
        xml.writeAttribute(localName, value == null ? "" : String.valueOf(value));
    }

    public XmlSignature getSigner() {
        return signer;
    }
}