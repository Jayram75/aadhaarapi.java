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

import uidai.aadhaar.agency.AuthRequest;
import uidai.aadhaar.agency.KycRequest;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static uidai.aadhaar.serialization.ApiConstants.KYC_NAMESPACE;

public class KycRequestXmlWriter extends ApiXmlEncoder<KycRequest> {
    protected KycRequestXmlWriter(KycRequest api) {
        super(api);
    }

    private static void writeKyc(XmlWriter x, KycRequest a) throws XMLStreamException {
        if (a == null)
            return;

        x.document();
        x.element("Kyc", String.format("%s%s", KYC_NAMESPACE, a.getVersion()));

        x.attribute("rc", "Y");
        x.attribute("ver", a.getVersion());
        x.attribute("ra", a.getAuthType());
        x.attribute("lr", a.shouldAccessLocalInfo() ? "Y" : "N");
        x.attribute("pfr", a.shouldAccessEAadhaar() ? "Y" : "N");
        x.attribute("de", a.shouldDelegateDecryptionToKsa() ? "Y" : "N");
        writeRad(x, a.getAuthRequest());

        x.endElement();
        x.endDocument();
    }

    private static void writeRad(XmlWriter x, AuthRequest a) throws XMLStreamException {
        if (a == null)
            return;

        final ByteArrayOutputStream signedAuth = new ByteArrayOutputStream();
        new AuthRequestXmlEncoder(a, x.getSigner()).write(signedAuth);

        x.element("Rad");
        x.text(new String(Base64.getEncoder().encode(signedAuth.toByteArray()), StandardCharsets.UTF_8));
        x.endElement();
    }

    @Override
    public void write(XMLStreamWriter xml) {
        try {
            super.write(xml);
            writeKyc(new XmlWriter(xml, signer), api);
        } catch (final XMLStreamException e) {
            e.printStackTrace();
        }
    }
}