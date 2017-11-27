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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uidai.aadhaar.agency.AuthContext;
import uidai.aadhaar.agency.AuthRequest;
import uidai.aadhaar.generic.AadhaarHelper;
import uidai.aadhaar.generic.ApiException;
import uidai.aadhaar.resident.Demographic;
import uidai.aadhaar.resident.Gender;
import uidai.aadhaar.resident.Identity;
import uidai.aadhaar.resident.ResidentInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static uidai.aadhaar.Configuration.*;

@Disabled
@DisplayName("Simple authentication test")
class SimpleAuthenticationTest {
    @BeforeAll
    static void setUpAll() {
        init();
    }

    @Test
    @DisplayName("can authenticate correct resident data successfully.")
    void authenticate() throws ApiException, IOException {
        final ResidentInfo residentInfo = new ResidentInfo()
            .setAadhaarNumber("999991609177")
            .setDemographic(new Demographic()
                .setIdentity(new Identity()
                    .setName("Souvik Dey Chowdhury")
                    .setGender(Gender.MALE)));

        final AuthContext authContext = new AuthContext(encrypter, xmlSigner, xmlVerifier);
        authContext
            .setRequest(new AuthRequest()
                .setResidentConsent(true)
                .setTransactionId(AadhaarHelper.newTransactionId())
                .setDeviceInfo(publicDeviceInfo)
                .setAgencyInfo(agencyInfo));

        authContext.encrypt(residentInfo);
        authContext.send();

        assertTrue(authContext.getResponse().isAuthentic(), "Resident data is authentic.");
    }
}