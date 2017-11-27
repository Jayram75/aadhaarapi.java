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

package uidai.aadhaar.resident.options;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uidai.aadhaar.resident.BiometricPosition;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Biometric options")
class BiometricOptionsTest {
    private BiometricOptions biometricOptions;

    @BeforeEach
    void setUp() {
        biometricOptions = new BiometricOptions();
    }

    @Test
    @DisplayName("can set finger records count from 1-10 or null")
    void setFingerRecordsCount() {
        assertAll(
            () -> {
                biometricOptions.setFingerRecordsCount(10);
                assertEquals(Integer.valueOf(10), biometricOptions.getFingerRecordsCount());
            },
            () -> {
                biometricOptions.setFingerRecordsCount(1);
                assertEquals(Integer.valueOf(1), biometricOptions.getFingerRecordsCount());
            },
            () -> {
                biometricOptions.setFingerRecordsCount(null);
                assertEquals(null, biometricOptions.getFingerRecordsCount());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> biometricOptions.setFingerRecordsCount(0)),
            () -> assertThrows(IllegalArgumentException.class, () -> biometricOptions.setFingerRecordsCount(11))
        );
    }

    @Test
    @DisplayName("can set iris records count from 1-2 or null")
    void setIrisRecordsCount() {
        assertAll(
            () -> {
                biometricOptions.setIrisRecordsCount(2);
                assertEquals(Integer.valueOf(2), biometricOptions.getIrisRecordsCount());
            },
            () -> {
                biometricOptions.setIrisRecordsCount(1);
                assertEquals(Integer.valueOf(1), biometricOptions.getIrisRecordsCount());
            },
            () -> {
                biometricOptions.setIrisRecordsCount(null);
                assertEquals(null, biometricOptions.getIrisRecordsCount());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> biometricOptions.setIrisRecordsCount(0)),
            () -> assertThrows(IllegalArgumentException.class, () -> biometricOptions.setIrisRecordsCount(3))
        );
    }

    @Test
    @DisplayName("can set face records count as 1 or null")
    void setFaceRecordsCount() {
        assertAll(
            () -> {
                biometricOptions.setFaceRecordsCount(1);
                assertEquals(Integer.valueOf(1), biometricOptions.getFaceRecordsCount());
            },
            () -> {
                biometricOptions.setFaceRecordsCount(null);
                assertEquals(null, biometricOptions.getFaceRecordsCount());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> biometricOptions.setFaceRecordsCount(0)),
            () -> assertThrows(IllegalArgumentException.class, () -> biometricOptions.setFaceRecordsCount(2))
        );
    }

    @Test
    @DisplayName("can not set unknown biometric position")
    void setBiometricPositions() {
        assertThrows(IllegalArgumentException.class, () -> biometricOptions.setBiometricPositions(EnumSet.of(BiometricPosition.UNKNOWN)));
    }
}