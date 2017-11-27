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
import uidai.aadhaar.device.DeviceInfo;
import uidai.aadhaar.resident.IdentityType;
import uidai.aadhaar.security.EncryptedData;
import uidai.aadhaar.security.SessionKey;
import uidai.aadhaar.security.XmlSignature;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

import static uidai.aadhaar.serialization.ApiConstants.*;

public class AuthRequestXmlEncoder extends ApiXmlEncoder<AuthRequest> {
    public AuthRequestXmlEncoder(AuthRequest api) {
        super(api);
    }

    public AuthRequestXmlEncoder(AuthRequest api, XmlSignature signer) {
        super(api, signer);
    }

    private static void writeAuth(XmlWriter x, AuthRequest a) throws XMLStreamException {
        if (a == null)
            return;

        x.document();
        x.element("Auth", String.format("%s%s", AUTH_NAMESPACE, a.getVersion()));

        x.attribute("rc", "Y");
        x.attribute("ver", a.getVersion());
        x.attribute("uid", a.getAadhaarNumber());
        x.attribute("txn", a.getTransactionId());
        x.requiredAttribute("tid", a.getTerminal());

        if (a.getAgencyInfo() != null) {
            x.attribute("ac", a.getAgencyInfo().getAuaCode());
            x.attribute("sa", a.getAgencyInfo().getSubAuaCode());
            x.attribute("lk", a.getAgencyInfo().getAuaLicenseKey());
        }
        writeUses(x, a.getUses());
        writeMeta(x, a.getDeviceInfo());
        writeSkey(x, a.getSessionKey());
        writeData(x, a.getEncryptedData());

        x.endElement();
        x.endDocument();
    }

    private static void writeUses(XmlWriter x, Collection<IdentityType> a) throws XMLStreamException {
        if (a == null)
            return;

        x.emptyElement("Uses");
        for (final IdentityType i : IDENTITY_TYPES)
            x.attribute(i.value().toLowerCase(Locale.ENGLISH), a.contains(i) ? "y" : "n");
        for (final IdentityType i : BIOMETRIC_TYPES)
            x.attribute("bt", a.stream()
                .filter(BIOMETRIC_TYPES::contains)
                .map(IdentityType::value)
                .collect(Collectors.joining(",")));
    }

    private static void writeData(XmlWriter x, EncryptedData a) throws XMLStreamException {
        if (a == null)
            return;

        x.element("Data");
        x.attribute("type", a.getDataFormat());
        x.text(a.getData());
        x.endElement();

        x.element("Hmac");
        x.text(a.getHmac());
        x.endElement();
    }

    private static void writeSkey(XmlWriter x, SessionKey a) throws XMLStreamException {
        if (a == null)
            return;

        x.element("Skey");
        x.attribute("ci", a.getCertificateId());
        x.text(a.getKey());
        x.endElement();
    }

    private static void writeMeta(XmlWriter x, DeviceInfo a) throws XMLStreamException {
        if (a == null)
            return;

        x.emptyElement("Meta");
        x.attribute("udc", a.getHostDeviceCode());
        x.requiredAttribute("rdsId", a.getServiceCode());
        x.requiredAttribute("rdsVer", a.getServiceVersion());
        x.requiredAttribute("dpId", a.getProviderCode());
        x.requiredAttribute("dc", a.getDeviceCode());
        x.requiredAttribute("mi", a.getModelCode());
        x.requiredAttribute("mc", a.getPublicKey());
    }

    @Override
    public void write(XMLStreamWriter xml) {
        try {
            super.write(xml);
            writeAuth(new XmlWriter(xml), api);
        } catch (final XMLStreamException e) {
            e.printStackTrace();
        }
    }
}