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

import java.time.OffsetDateTime;
import java.util.Collection;

public class RDServiceRegistry {
    private OffsetDateTime updatedOn;
    private OffsetDateTime expiresOn;
    private Collection<RDServiceInfo> services;

    public OffsetDateTime getUpdatedOn() {
        return updatedOn;
    }

    public RDServiceRegistry setUpdatedOn(OffsetDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public OffsetDateTime getExpiresOn() {
        return expiresOn;
    }

    public RDServiceRegistry setExpiresOn(OffsetDateTime expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public Collection<RDServiceInfo> getServices() {
        return services;
    }

    public RDServiceRegistry setServices(Collection<RDServiceInfo> services) {
        this.services = services;
        return this;
    }
}