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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@DisplayName("Pin value")
class PinValueTest {
    private PinValue pinValue;

    @BeforeEach
    void setUp() {
        pinValue = new PinValue();
    }

    @Test
    @DisplayName("can set valid OTP_NAMESPACE, empty or null")
    void setOtp() {
        assertAll(
            () -> {
                pinValue.setOtp("123456");
                assertEquals("123456", pinValue.getOtp());
            },
            () -> {
                pinValue.setOtp("");
                assertEquals("", pinValue.getOtp());
            },
            () -> {
                pinValue.setOtp(null);
                assertEquals(null, pinValue.getOtp());
            }
        );
    }

    @Test
    @DisplayName("can set valid PIN, empty or null")
    void setPin() {
        assertAll(
            () -> {
                pinValue.setPin("123456");
                assertEquals("123456", pinValue.getPin());
            },
            () -> {
                pinValue.setPin("");
                assertEquals("", pinValue.getPin());
            },
            () -> {
                pinValue.setPin(null);
                assertEquals(null, pinValue.getPin());
            }
        );
    }

    @Test
    @DisplayName("has value if atleast one property is set")
    void hasValue() {
        assertAll(
            () -> assertIterableEquals(EnumSet.noneOf(IdentityType.class), pinValue.getUses()),
            () -> {
                pinValue.setOtp("123456");
                assertIterableEquals(EnumSet.of(IdentityType.OTP), pinValue.getUses());
            }
        );
    }
}