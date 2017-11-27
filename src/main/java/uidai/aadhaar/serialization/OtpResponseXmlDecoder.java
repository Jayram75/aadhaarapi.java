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

import uidai.aadhaar.agency.OtpResponse;
import uidai.aadhaar.generic.ApiException;
import uidai.aadhaar.security.XmlSignature;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.time.format.DateTimeFormatter;

public class OtpResponseXmlDecoder extends ApiXmlDecoder<OtpResponse> {
    public OtpResponseXmlDecoder() {
    }

    public OtpResponseXmlDecoder(XmlSignature verifier) {
        super(verifier);
    }

    private static OtpResponse readOtpRes(XmlReader x) throws XMLStreamException {
        final OtpResponse a = new OtpResponse();

        x.document();
        x.element();

        a
            .setOtpSent(x.attributeBoolean("ret"))
            .setTransactionId(x.attribute("txn"))
            .setTimestamp(x.attributeDateTime("ts", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
            .setResponseCode(x.attribute("code"))
            .setInfo(x.attribute("info"))
            .setErrorCode(x.attribute("err"));

        x.endElement();
        x.endDocument();
        return a;
    }

    @Override
    public OtpResponse read(XMLStreamReader xml) throws ApiException {
        OtpResponse api = null;
        try {
            super.read(xml);
            api = readOtpRes(new XmlReader(xml));
        } catch (final XMLStreamException e) {
            e.printStackTrace();
        }
        return api;
    }
}