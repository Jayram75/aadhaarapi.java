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

package uidai.aadhaar.resident;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class ResidentInfoTest {
    private ResidentInfo residentInfo;

    @BeforeEach
    void setUp() {
        residentInfo = new ResidentInfo()
            .setDemographic(new Demographic()
                .setIdentity(new Identity())
                .setAddress(new Address())
                .setFullAddress(new FullAddress()))
            .setBiometric(new Biometric()
                .setBiometrics(new ArrayList<>(10)))
            .setPinValue(new PinValue());
    }

    @Test
    @DisplayName("can set only non-null version")
    void setVersion() {
        assertAll(
            () -> {
                residentInfo.setVersion("2.0");
                assertEquals("2.0", residentInfo.getVersion());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> residentInfo.setVersion("")),
            () -> assertThrows(IllegalArgumentException.class, () -> residentInfo.setVersion(null))
        );
    }

    @Test
    @DisplayName("can set only valid Aadhaar number, empty or null")
    void setAadhaarNumber() {
        assertAll(
            () -> {
                residentInfo.setAadhaarNumber("999999999999");
                assertEquals("999999999999", residentInfo.getAadhaarNumber());
            },
            () -> {
                residentInfo.setAadhaarNumber("");
                assertEquals("", residentInfo.getAadhaarNumber());
            },
            () -> {
                residentInfo.setAadhaarNumber(null);
                assertEquals(null, residentInfo.getAadhaarNumber());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> residentInfo.setAadhaarNumber("123456789"))
        );
    }

    @Test
    @DisplayName("can set only non-null captured time")
    void setCapturedOn() {
        assertAll(
            () -> {
                final OffsetDateTime now = OffsetDateTime.now();
                residentInfo.setCapturedOn(now);
                assertEquals(now, residentInfo.getCapturedOn());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> residentInfo.setCapturedOn(null))
        );
    }

    @Test
    @DisplayName("can get only identity types which have values set")
    void getUses() {
        assertAll(
            () -> assertIterableEquals(residentInfo.getUses(), EnumSet.noneOf(IdentityType.class)),
            () -> {
                residentInfo.getDemographic().getIdentity().setName("A");
                residentInfo.getDemographic().getAddress().setCareOf("A");
                residentInfo.getDemographic().getFullAddress().setAddress("A");
                residentInfo.getBiometric().getBiometrics().add(new BiometricRecord()
                    .setType(BiometricType.MINUTIAE)
                    .setPosition(BiometricPosition.LEFT_INDEX)
                    .setData("A")
                    .setSignature("A"));
                residentInfo.getPinValue().setOtp("123456");

                assertIterableEquals(EnumSet.of(
                    IdentityType.DEMOGRAPHIC,
                    IdentityType.IDENTITY,
                    IdentityType.ADDRESS,
                    IdentityType.FULL_ADDRESS,
                    IdentityType.BIOMETRIC,
                    IdentityType.MINUTIAE,
                    IdentityType.OTP), residentInfo.getUses()
                );
            }
        );
    }
}