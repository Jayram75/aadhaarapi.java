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

package uidai.aadhaar.device;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Device info")
class DeviceInfoTest {
    private DeviceInfo deviceInfo;

    @BeforeEach
    void setUp() {
        deviceInfo = new DeviceInfo();
    }

    @Test
    @DisplayName("can set host device code with length from 0-20 characters or null")
    void setHostDeviceCode() {
        assertAll(
            () -> {
                deviceInfo.setHostDeviceCode("A");
                assertEquals("A", deviceInfo.getHostDeviceCode());
            },
            () -> {
                deviceInfo.setHostDeviceCode("");
                assertEquals("", deviceInfo.getHostDeviceCode());
            },
            () -> {
                deviceInfo.setHostDeviceCode(null);
                assertEquals(null, deviceInfo.getHostDeviceCode());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> deviceInfo.setHostDeviceCode(new String(new char[21])))
        );
    }
}