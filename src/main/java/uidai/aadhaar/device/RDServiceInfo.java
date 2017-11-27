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

import java.util.Collection;

public class RDServiceInfo {
    private String serviceCode;
    private String providerCode;
    private Collection<RDServiceVersionInfo> versions;

    public String getServiceCode() {
        return serviceCode;
    }

    public RDServiceInfo setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public RDServiceInfo setProviderCode(String providerCode) {
        this.providerCode = providerCode;
        return this;
    }

    public Collection<RDServiceVersionInfo> getVersions() {
        return versions;
    }

    public RDServiceInfo setVersions(Collection<RDServiceVersionInfo> versions) {
        this.versions = versions;
        return this;
    }
}