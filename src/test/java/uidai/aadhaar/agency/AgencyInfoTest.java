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

package uidai.aadhaar.agency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Agency info")
class AgencyInfoTest {
    private AgencyInfo agencyInfo;

    @BeforeEach
    void setUp() {
        agencyInfo = new AgencyInfo();
    }

    @Test
    @DisplayName("can set AUA code with length from 0-10 characters or null")
    void setAuaCode() {
        assertAll(
            () -> {
                agencyInfo.setAuaCode("A");
                assertEquals("A", agencyInfo.getAuaCode());
            },
            () -> {
                agencyInfo.setAuaCode("");
                assertEquals("", agencyInfo.getAuaCode());
            },
            () -> {
                agencyInfo.setAuaCode(null);
                assertEquals(null, agencyInfo.getAuaCode());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> agencyInfo.setAuaCode(new String(new char[11])))
        );
    }

    @Test
    @DisplayName("can get AUA code when Sub-AUA code is empty or null")
    void getSubAuaCode() {
        assertAll(
            () -> {
                agencyInfo.setAuaCode("A");
                assertEquals("A", agencyInfo.getSubAuaCode());
            },
            () -> {
                agencyInfo.setAuaCode("");
                assertEquals("", agencyInfo.getSubAuaCode());
            },
            () -> {
                agencyInfo.setAuaCode(null);
                assertEquals(null, agencyInfo.getSubAuaCode());
            }
        );
    }

    @Test
    @DisplayName("can set Sub-AUA code with length from 0-10 characters or null")
    void setSubAuaCode() {
        assertAll(
            () -> {
                agencyInfo.setSubAuaCode("A");
                assertEquals("A", agencyInfo.getSubAuaCode());
            },
            () -> {
                agencyInfo.setSubAuaCode("");
                assertEquals(null, agencyInfo.getSubAuaCode());
            },
            () -> {
                agencyInfo.setSubAuaCode(null);
                assertEquals(null, agencyInfo.getSubAuaCode());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> agencyInfo.setSubAuaCode(new String(new char[11])))
        );
    }

    @Test
    @DisplayName("can set AUA license key with length from 0-10 characters or null")
    void setAuaLicenseKey() {
        assertAll(
            () -> {
                agencyInfo.setAuaLicenseKey("A");
                assertEquals("A", agencyInfo.getAuaLicenseKey());
            },
            () -> {
                agencyInfo.setAuaLicenseKey("");
                assertEquals("", agencyInfo.getAuaLicenseKey());
            },
            () -> {
                agencyInfo.setAuaLicenseKey(null);
                assertEquals(null, agencyInfo.getAuaLicenseKey());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> agencyInfo.setAuaLicenseKey(new String(new char[65])))
        );
    }
}