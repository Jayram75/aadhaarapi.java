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

import uidai.aadhaar.agency.OtpRequest;
import uidai.aadhaar.generic.OtpChannel;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static uidai.aadhaar.generic.AadhaarHelper.isNullOrEmpty;
import static uidai.aadhaar.serialization.ApiConstants.OTP_NAMESPACE;

public class OtpRequestXmlEncoder extends ApiXmlEncoder<OtpRequest> {
    public OtpRequestXmlEncoder(OtpRequest api) {
        super(api);
    }

    private static void writeOtp(XmlWriter x, OtpRequest a) throws XMLStreamException {
        if (a == null)
            return;

        x.document();
        x.element("Otp", String.format("%s%s", OTP_NAMESPACE, a.getVersion()));

        x.attribute("ver", a.getVersion());
        x.attribute("txn", a.getTransactionId());
        x.attribute("tid", a.getTerminal());

        if (isNullOrEmpty(a.getAadhaarNumber())) {
            x.attribute("uid", a.getMobileNumber());
            x.attribute("type", "A");
        } else
            x.attribute("uid", a.getAadhaarNumber());

        if (a.getAgencyInfo() != null) {
            x.attribute("ac", a.getAgencyInfo().getAuaCode());
            x.attribute("sa", a.getAgencyInfo().getSubAuaCode());
            x.attribute("lk", a.getAgencyInfo().getAuaLicenseKey());
        }
        if (a.getOtpChannel() != OtpChannel.SMS_AND_EMAIL) {
            x.element("Opts");
            x.attribute("ch", a.getOtpChannel());
            x.endElement();
        }

        x.endElement();
        x.endDocument();
    }

    @Override
    public void write(XMLStreamWriter xml) {
        try {
            super.write(xml);
            writeOtp(new XmlWriter(xml), api);
        } catch (final XMLStreamException e) {
            e.printStackTrace();
        }
    }
}