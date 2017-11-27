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

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Biometric record")
class BiometricRecordTest {
    private BiometricRecord biometricRecord;

    @BeforeEach
    void setUp() {
        biometricRecord = new BiometricRecord();
    }

    @Test
    @DisplayName("can set type with only valid position or none")
    void setType() {
        assertAll(
            () -> {
                // Set type with no position.
                biometricRecord
                    .setPosition(null)
                    .setType(BiometricType.MINUTIAE);
                assertEquals(BiometricType.MINUTIAE, biometricRecord.getType());
            },
            () -> {
                // Set type with valid position.
                biometricRecord
                    .setPosition(BiometricPosition.LEFT_INDEX)
                    .setType(BiometricType.MINUTIAE);
                assertEquals(BiometricType.MINUTIAE, biometricRecord.getType());
            },
            () -> {
                // Unset type with valid position.
                biometricRecord
                    .setPosition(BiometricPosition.LEFT_INDEX)
                    .setType(null);
                assertEquals(null, biometricRecord.getType());
            },
            () -> {
                // Set type with invalid position.
                biometricRecord.setPosition(BiometricPosition.LEFT_IRIS);
                assertThrows(IllegalArgumentException.class, () -> biometricRecord.setType(BiometricType.MINUTIAE));
            }
        );
    }

    @Test
    @DisplayName("can set position with only valid type or none")
    void setPosition() {
        assertAll(
            () -> {
                // Set position with no type.
                biometricRecord
                    .setType(null)
                    .setPosition(BiometricPosition.LEFT_INDEX);
                assertEquals(BiometricPosition.LEFT_INDEX, biometricRecord.getPosition());
            },
            () -> {
                // Set position with valid type.
                biometricRecord
                    .setType(BiometricType.MINUTIAE)
                    .setPosition(BiometricPosition.LEFT_INDEX);
                assertEquals(BiometricPosition.LEFT_INDEX, biometricRecord.getPosition());
            },
            () -> {
                // Unset position with valid type.
                biometricRecord
                    .setType(BiometricType.MINUTIAE)
                    .setPosition(null);
                assertEquals(null, biometricRecord.getPosition());
            },
            () -> {
                // Set position with invalid type.
                biometricRecord.setType(BiometricType.MINUTIAE);
                assertThrows(IllegalArgumentException.class, () -> biometricRecord.setPosition(BiometricPosition.LEFT_IRIS));
            }
        );
    }

    @Test
    @DisplayName("has value only if all properties are set")
    void getUses() {
        assertAll(
            () -> assertIterableEquals(EnumSet.noneOf(IdentityType.class), biometricRecord.getUses()),
            () -> {
                biometricRecord.setData("A");
                assertIterableEquals(EnumSet.noneOf(IdentityType.class), biometricRecord.getUses());
            },
            () -> {
                biometricRecord
                    .setType(BiometricType.MINUTIAE)
                    .setPosition(BiometricPosition.LEFT_INDEX)
                    .setData("A")
                    .setSignature("A");
                assertIterableEquals(EnumSet.of(IdentityType.MINUTIAE), biometricRecord.getUses());
            }
        );
    }
}