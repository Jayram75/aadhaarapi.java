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

import uidai.aadhaar.internal.ErrorMessages;

import java.util.Map;

import static uidai.aadhaar.generic.AadhaarHelper.isNullOrEmpty;

/**
 * Represents agency information assigned by UIDAI. All properties are mandatory.
 */
public class AgencyInfo {
    private String auaCode;
    private String subAuaCode;
    private String auaLicenseKey;
    private String asaLicenseKey;
    private Map<String, String> hosts;

    public String getAuaCode() {
        return auaCode;
    }

    public AgencyInfo setAuaCode(String auaCode) {
        if (auaCode != null && auaCode.length() > 10)
            throw new IllegalArgumentException(ErrorMessages.INVALID_AUA_CODE);

        this.auaCode = auaCode;
        return this;
    }

    public String getSubAuaCode() {
        return isNullOrEmpty(subAuaCode) ? auaCode : subAuaCode;
    }

    public AgencyInfo setSubAuaCode(String subAuaCode) {
        if (subAuaCode != null && subAuaCode.length() > 10)
            throw new IllegalArgumentException(ErrorMessages.INVALID_AUA_CODE);

        this.subAuaCode = subAuaCode;
        return this;
    }

    public String getAuaLicenseKey() {
        return auaLicenseKey;
    }

    public AgencyInfo setAuaLicenseKey(String auaLicenseKey) {
        if (auaLicenseKey != null && auaLicenseKey.length() > 64)
            throw new IllegalArgumentException(ErrorMessages.INVALID_AUA_LICENSE_KEY);

        this.auaLicenseKey = auaLicenseKey;
        return this;
    }

    public String getAsaLicenseKey() {
        return asaLicenseKey;
    }

    public AgencyInfo setAsaLicenseKey(String asaLicenseKey) {
        this.asaLicenseKey = asaLicenseKey;
        return this;
    }

    public Map<String, String> getHosts() {
        return hosts;
    }

    public AgencyInfo setHosts(Map<String, String> hosts) {
        this.hosts = hosts;
        return this;
    }
}