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

import uidai.aadhaar.agency.AuthResponse;
import uidai.aadhaar.agency.KycResponse;
import uidai.aadhaar.generic.ApiException;
import uidai.aadhaar.resident.ResidentInfo;
import uidai.aadhaar.security.XmlSignature;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class KycResponseXmlDecoder extends ApiXmlDecoder<KycResponse> {
    public KycResponseXmlDecoder() {
    }

    public KycResponseXmlDecoder(XmlSignature verifier) {
        super(verifier);
    }

    private static KycResponse readKycRes(XmlReader x) throws XMLStreamException, ApiException {
        final KycResponse a = new KycResponse();

        x.document();
        x.element();

        a
            .setAuthentic(x.attributeBoolean("ret"))
            .setResponseCode(x.attribute("code"))
            .setTransactionId(x.attribute("txn"))
            .setTimestamp(x.attributeDateTime("ts", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
            .setActionCode(x.attribute("actn"))
            .setErrorCode(x.attribute("err"));

        a.setAuthResponse(readRar(x));
        a.setResidentInfo(readUidData(x));

        x.endElement();
        x.endDocument();
        return a;
    }

    private static AuthResponse readRar(XmlReader x) throws XMLStreamException, ApiException {
        x.element();

        final AuthResponse a = new AuthResponseXmlDecoder(x.getVerifier())
            .read(new ByteArrayInputStream(Base64.getDecoder().decode(x.text())));

        x.endElement();
        return a;
    }

    private static ResidentInfo readUidData(XmlReader x) throws XMLStreamException {
        final ResidentInfo a = new ResidentInfo();

        x.element();
        // TODO: Parse UidData.
        x.endElement();
        return a;
    }

    @Override
    public KycResponse read(XMLStreamReader xml) throws ApiException {
        KycResponse api = null;
        try {
            super.read(xml);
            api = readKycRes(new XmlReader(xml));
        } catch (final XMLStreamException e) {
            e.printStackTrace();
        }
        return api;
    }
}