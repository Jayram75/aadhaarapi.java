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
import org.junit.jupiter.api.Test;
import uidai.aadhaar.agency.CaptureContext;
import uidai.aadhaar.generic.ApiException;
import uidai.aadhaar.resident.Biometric;
import uidai.aadhaar.resident.BiometricPosition;
import uidai.aadhaar.resident.BiometricRecord;
import uidai.aadhaar.resident.BiometricType;
import uidai.aadhaar.security.Encrypter;

import java.security.InvalidKeyException;

@Disabled
public class BiometricCapture {
    private Encrypter encrypter;

    @BeforeEach
    void setUp() {
        encrypter = Encrypter.getInstance("2.0");
    }

    @Test
    void capture() throws ApiException {
        final CaptureContext captureContext = new CaptureContext();
        // captureContext.decodeRequest(InputStream);

        final Biometric biometric = captureContext
            .getRequest()
            .getResidentInfo()
            .getBiometric();

        /*
        Read biometric options
        final BiometricOptions biometricOptions = biometric
            .getOptions();
        */

        biometric.getBiometrics().add(new BiometricRecord()
            .setType(BiometricType.MINUTIAE)
            .setPosition(BiometricPosition.LEFT_INDEX)
            .setData("")
            .setSignature(""));

        captureContext.setEncrypter(encrypter);
        captureContext.encrypt();
        // captureContext.encodeResponse(OutputStream);
    }
}