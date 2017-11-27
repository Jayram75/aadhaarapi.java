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

import uidai.aadhaar.agency.CaptureRequest;
import uidai.aadhaar.resident.Biometric;
import uidai.aadhaar.resident.BiometricPosition;
import uidai.aadhaar.resident.BiometricType;
import uidai.aadhaar.resident.ResidentInfo;
import uidai.aadhaar.resident.options.BiometricOptions;
import uidai.aadhaar.security.XmlSignature;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

import static uidai.aadhaar.serialization.ResidentInfoXmlEncoder.writeDemo;

public class CaptureRequestXmlEncoder extends ApiXmlEncoder<CaptureRequest> {
    public CaptureRequestXmlEncoder(CaptureRequest api) {
        super(api);
    }

    public CaptureRequestXmlEncoder(CaptureRequest api, XmlSignature signer) {
        super(api, signer);
    }

    private static void writePidOptions(XmlWriter x, CaptureRequest a) throws XMLStreamException {
        if (a == null)
            return;

        x.document();
        x.element("PidOptions");

        x.attribute("ver", a.getVersion());
        writeOpts(x, a);
        writeDemo(x, a.getResidentInfo().getDemographic());

        x.endElement();
        x.endDocument();
    }

    private static void writeOpts(XmlWriter x, CaptureRequest a) throws XMLStreamException {
        if (a == null)
            return;

        x.emptyElement("Opts");
        x.attribute("otp", a.getOtp());
        x.attribute("timeout", a.getTimeout());
        x.attribute("env", a.getEnvironment());
        x.attribute("format", a.getDataFormat());

        if (a.getResidentInfo() != null)
            x.attribute("pidVer", a.getResidentInfo().getVersion());

        Optional<BiometricOptions> optionalOptions = Optional.of(a)
            .map(CaptureRequest::getResidentInfo)
            .map(ResidentInfo::getBiometric)
            .map(Biometric::getOptions);
        if (optionalOptions.isPresent()) {
            final BiometricOptions o = optionalOptions.get();

            x.attribute("fCount", o.getFingerRecordsCount());
            x.attribute("iCount", o.getIrisRecordsCount());
            x.attribute("pCount", o.getFaceRecordsCount());

            if (o.getBiometricTypes() != null && o.getBiometricTypes().contains(BiometricType.FINGERPRINT))
                x.attribute("fType", 1);
            if (o.getBiometricPositions() != null && !o.getBiometricPositions().isEmpty()) {
                final StringJoiner joiner = new StringJoiner(",");
                for (BiometricPosition position : o.getBiometricPositions())
                    joiner.add(position.value());
                x.attribute("posh", joiner.toString());
            }
        }
    }

    private static void writeCustOpts(XmlWriter x, CaptureRequest a) throws XMLStreamException {
        if (a == null || a.getCustomOptions() == null)
            return;

        x.element("CustOpts");

        for (Map.Entry<String, String> entry : a.getCustomOptions().entrySet()) {
            x.emptyElement("Param");
            x.attribute("name", entry.getKey());
            x.requiredAttribute("value", entry.getValue());
        }

        x.endElement();
    }

    @Override
    public void write(XMLStreamWriter xml) {
        try {
            super.write(xml);
            writePidOptions(new XmlWriter(xml), api);
        } catch (final XMLStreamException e) {
            e.printStackTrace();
        }
    }
}