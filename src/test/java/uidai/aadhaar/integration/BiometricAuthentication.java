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
import uidai.aadhaar.agency.AgencyInfo;
import uidai.aadhaar.agency.AuthContext;
import uidai.aadhaar.agency.CaptureContext;
import uidai.aadhaar.agency.CaptureResponse;
import uidai.aadhaar.device.DeviceInfo;
import uidai.aadhaar.generic.AadhaarHelper;
import uidai.aadhaar.generic.ApiException;
import uidai.aadhaar.resident.*;
import uidai.aadhaar.resident.options.BiometricOptions;
import uidai.aadhaar.security.XmlSignature;

import java.io.IOException;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static uidai.aadhaar.Configuration.init;

@Disabled
public class BiometricAuthentication {
    private DeviceInfo deviceInfo;
    private AgencyInfo agencyInfo;
    private XmlSignature xmlSigner;
    private XmlSignature xmlVerifier;

    @BeforeEach
    void setUp() {
        init();
    }

    @Test
    @DisplayName("can authenticate correct resident data with biometrics successfully.")
    void authenticate() throws ApiException, IOException {
        final ResidentInfo residentInfo = new ResidentInfo()
            .setAadhaarNumber("999999999999")
            .setDemographic(new Demographic()
                .setIdentity(new Identity()
                    .setName("Souvik Dey Chowdhury")
                    .setGender(Gender.MALE)))
            .setBiometric(new Biometric()
                .setOptions(new BiometricOptions()
                    .setBiometricTypes(EnumSet.of(BiometricType.MINUTIAE))));

        final CaptureContext captureContext = new CaptureContext();
        captureContext.getRequest()
            .setResidentInfo(residentInfo);

        captureContext.validateRequest();
        captureContext.send();

        /*--------------------------------------------------*/

        final CaptureResponse captureResponse = captureContext.getResponse();

        final AuthContext authContext = new AuthContext();
        authContext
            .setXmlSigner(xmlSigner)
            .setXmlVerifier(xmlVerifier);

        authContext
            .getRequest()
            .setEncryptedData(captureResponse.getEncryptedData())
            .setSessionKey(captureResponse.getSessionKey())
            .setUses(captureResponse.getUses(residentInfo.getUses()))
            .setAgencyInfo(agencyInfo)
            .setDeviceInfo(deviceInfo)
            .setResidentConsent(true)
            .setTransactionId(AadhaarHelper.newTransactionId());

        authContext.send();

        assertTrue(authContext.getResponse().isAuthentic());
    }
}