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

import uidai.aadhaar.resident.*;
import uidai.aadhaar.resident.options.DemographicOptions;
import uidai.aadhaar.resident.options.MatchType;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static uidai.aadhaar.generic.AadhaarHelper.TIMESTAMP_FORMAT;
import static uidai.aadhaar.serialization.ApiConstants.PID_NAMESPACE;

public class ResidentInfoXmlEncoder extends ApiXmlEncoder<ResidentInfo> {
    public ResidentInfoXmlEncoder(ResidentInfo api) {
        super(api);
    }

    private static void writePid(XmlWriter x, ResidentInfo a) throws XMLStreamException {
        // ResidentInfo::getUses traverses the whole object graph, avoid using it.
        if (a == null)
            return;

        x.document();
        x.element("Pid", String.format("%s%s", PID_NAMESPACE, a.getVersion()));

        x.attribute("ts", a.getCapturedOn().format(TIMESTAMP_FORMAT));
        x.attribute("ver", a.getVersion());
        x.attribute("wadh", a.getApiInfo());
        writeDemo(x, a.getDemographic());
        writeBios(x, a.getBiometric());
        writePv(x, a.getPinValue());

        x.endElement();
        x.endDocument();
    }

    public static void writeDemo(XmlWriter x, Demographic a) throws XMLStreamException {
        // Demographic::getUses traverses the whole object graph, avoid using it.
        if (a == null || x == null)
            return;

        DemographicOptions o = a.getOptions();
        if (o == null)
            o = new DemographicOptions();

        x.element("Demo");

        x.attribute("lang", a.getLocalLanguage());
        writePi(x, a.getIdentity(), o);
        writePa(x, a.getAddress());
        writePfa(x, a.getFullAddress(), o);

        x.endElement();
    }

    private static void writePi(XmlWriter x, Identity a, DemographicOptions o) throws XMLStreamException {
        if (a == null || a.getUses().isEmpty())
            return;

        x.emptyElement("Pi");
        x.attribute("name", a.getName());
        x.attribute("lname", a.getLocalName());
        x.attribute("email", a.getEmail());
        x.attribute("phone", a.getPhoneNumber());
        x.attribute("gender", a.getGender());
        x.attribute("age", a.getAge());

        if (a.getDateOfBirth() != null) {
            x.attribute("dob", a.getDateOfBirth().format(o.getBirthDateFormat()));
            x.attribute("dobt", o.getDateOfBirthType());
        }
        if (o.getNameMatch() != MatchType.EXACT) {
            x.attribute("ms", o.getNameMatch());
            x.attribute("mv", o.getNameMatchPercent());
            x.attribute("lmv", o.getLocalNameMatchPercent());
        }
    }

    private static void writePa(XmlWriter x, Address a) throws XMLStreamException {
        if (a == null || a.getUses().isEmpty())
            return;

        x.emptyElement("Pa");
        x.attribute("co", a.getCareOf());
        x.attribute("house", a.getHouse());
        x.attribute("street", a.getStreet());
        x.attribute("lm", a.getLandmark());
        x.attribute("loc", a.getLocality());
        x.attribute("vtc", a.getVillageOrCity());
        x.attribute("subdist", a.getSubDistrict());
        x.attribute("dist", a.getDistrict());
        x.attribute("state", a.getState());
        x.attribute("country", a.getCountry());
        x.attribute("pc", a.getPincode());
        x.attribute("po", a.getPostOffice());
    }

    private static void writePfa(XmlWriter x, FullAddress a, DemographicOptions o) throws XMLStreamException {
        if (a == null || a.getUses().isEmpty())
            return;

        x.emptyElement("Pfa");
        x.attribute("av", a.getAddress());
        x.attribute("lav", a.getLocalAddress());

        if (o.getFullAddressMatch() != MatchType.EXACT) {
            x.attribute("ms", o.getFullAddressMatch());
            x.attribute("mv", o.getFullAddressMatchPercent());
            x.attribute("lmv", o.getLocalFullAddressMatchPercent());
        }
    }

    private static void writeBios(XmlWriter x, Biometric a) throws XMLStreamException {
        if (a == null || a.getUses().isEmpty())
            return;

        x.element("Bios");

        x.attribute("dih", a.getDeviceInfoHash());
        if (a.getBiometrics() != null)
            for (final BiometricRecord r : a.getBiometrics()) {
                if (r.getUses().isEmpty())
                    continue;

                x.element("Bio");
                x.attribute("type", r.getType().value());
                x.attribute("posh", r.getPosition().value());
                x.attribute("bs", r.getSignature());
                x.text(r.getData());
                x.endElement();
            }

        x.endElement();
    }

    private static void writePv(XmlWriter x, PinValue a) throws XMLStreamException {
        if (a == null || a.getUses().isEmpty())
            return;

        x.emptyElement("Pv");
        x.attribute("otp", a.getOtp());
        x.attribute("pin", a.getPin());
    }

    @Override
    public void write(XMLStreamWriter xml) {
        try {
            super.write(xml);
            writePid(new XmlWriter(xml), api);
        } catch (final XMLStreamException e) {
            e.printStackTrace();
        }
    }
}