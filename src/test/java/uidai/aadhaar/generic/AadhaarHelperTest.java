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

package uidai.aadhaar.generic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uidai.aadhaar.resident.BiometricPosition;
import uidai.aadhaar.resident.BiometricType;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Aadhaar helper")
class AadhaarHelperTest {
    @Test
    @DisplayName("can validate Aadhaar number")
    void isValidAadhaarNumber() {
        assertAll(
            () -> assertTrue(AadhaarHelper.isValidAadhaarNumber("999999999999")),

            () -> assertFalse(AadhaarHelper.isValidAadhaarNumber("1")),
            () -> assertFalse(AadhaarHelper.isValidAadhaarNumber("A")),
            () -> assertFalse(AadhaarHelper.isValidAadhaarNumber(" ")),
            () -> assertFalse(AadhaarHelper.isValidAadhaarNumber("")),
            () -> assertFalse(AadhaarHelper.isValidAadhaarNumber(null))
        );
    }

    @Test
    @DisplayName("can validate 6 digits pin")
    void isValidPin() {
        assertAll(
            () -> assertTrue(AadhaarHelper.isValidPin("123456")),

            () -> assertFalse(AadhaarHelper.isValidPin("123 456")),
            () -> assertFalse(AadhaarHelper.isValidPin("12345")),
            () -> assertFalse(AadhaarHelper.isValidPin("A")),
            () -> assertFalse(AadhaarHelper.isValidPin(" ")),
            () -> assertFalse(AadhaarHelper.isValidPin("")),
            () -> assertFalse(AadhaarHelper.isValidPin(null))
        );
    }

    @Test
    @DisplayName("can validate null or empty string")
    void isNullOrEmpty() {
        assertAll(
            () -> assertTrue(AadhaarHelper.isNullOrEmpty("")),
            () -> assertTrue(AadhaarHelper.isNullOrEmpty(null)),

            () -> assertFalse(AadhaarHelper.isValidPin(" ")),
            () -> assertFalse(AadhaarHelper.isValidPin("A"))
        );
    }

    @Test
    @DisplayName("can get correct biometric types from a set of positions")
    void getBiometricTypesFromPosition() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> AadhaarHelper.getBiometricTypesFromPosition(null)),
            () -> {
                final EnumSet<BiometricPosition> positions = EnumSet
                    .complementOf(EnumSet
                        .of(BiometricPosition.LEFT_IRIS, BiometricPosition.RIGHT_IRIS, BiometricPosition.FACE));
                assertIterableEquals(
                    EnumSet.of(BiometricType.MINUTIAE, BiometricType.FINGERPRINT),
                    AadhaarHelper.getBiometricTypesFromPosition(positions)
                );
            },
            () -> {
                final EnumSet<BiometricPosition> positions = EnumSet.of(BiometricPosition.LEFT_IRIS);
                assertIterableEquals(
                    EnumSet.of(BiometricType.IRIS),
                    AadhaarHelper.getBiometricTypesFromPosition(positions)
                );
            },
            () -> {
                final EnumSet<BiometricPosition> positions = EnumSet.of(BiometricPosition.FACE);
                assertIterableEquals(
                    EnumSet.of(BiometricType.FACE),
                    AadhaarHelper.getBiometricTypesFromPosition(positions)
                );
            }
        );
    }
}