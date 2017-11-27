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

package uidai.aadhaar.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uidai.aadhaar.agency.*;
import uidai.aadhaar.device.DeviceInfo;
import uidai.aadhaar.device.Terminal;
import uidai.aadhaar.generic.AadhaarHelper;
import uidai.aadhaar.generic.ApiException;
import uidai.aadhaar.resident.PinValue;
import uidai.aadhaar.resident.ResidentInfo;
import uidai.aadhaar.security.Encrypter;
import uidai.aadhaar.security.XmlSignature;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uidai.aadhaar.Configuration.init;
import static uidai.aadhaar.Configuration.publicDeviceInfo;

@Disabled
public class KnowYourCustomerTest {
    private DeviceInfo deviceInfo;
    private AgencyInfo agencyInfo;
    private Encrypter encrypter;
    private XmlSignature xmlSigner;
    private XmlSignature xmlVerifier;

    @BeforeEach
    void setUp() {
        init();
    }

    @Test
    @DisplayName("can access customer name successfully.")
    void kyc() throws ApiException, IOException {
        final OtpContext otpContext = new OtpContext(xmlSigner);
        otpContext.setRequest(new OtpRequest()
            .setAadhaarNumber("999999999999")
            .setTransactionId(AadhaarHelper.newTransactionId())
            .setTerminal(Terminal.PUBLIC)
            .setAgencyInfo(agencyInfo));

        otpContext.send();

        /*--------------------------------------------------*/

        final ResidentInfo residentInfo = new ResidentInfo()
            .setAadhaarNumber("999999999999")
            .setPinValue(new PinValue()
                .setOtp("012345"));

        final KycContext kycContext = new KycContext(encrypter, xmlSigner, xmlVerifier);
        kycContext
            .setRequest(new KycRequest()
                .setAuthRequest(new AuthRequest()
                    .setResidentConsent(true)
                    .setTransactionId(AadhaarHelper.newTransactionId("UKC"))
                    .setTerminal(Terminal.PUBLIC)
                    .setDeviceInfo(publicDeviceInfo)
                    .setAgencyInfo(agencyInfo)));

        kycContext.encrypt(residentInfo);
        kycContext.send();

        assertEquals("Souvik Dey Chowdhury", kycContext
            .getResponse()
            .getResidentInfo()
            .getDemographic()
            .getIdentity()
            .getName(), "Authentication failed.");
    }
}