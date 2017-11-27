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

import uidai.aadhaar.generic.HasIdentityValue;

import java.util.EnumSet;

import static uidai.aadhaar.generic.AadhaarHelper.isNullOrEmpty;

/**
 * Represents full address of a resident.
 */
public class FullAddress implements HasIdentityValue {
    private String address;
    private String localAddress;

    /**
     * Gets the address value of the resident.
     *
     * @return The address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the resident.
     *
     * @param address The address.
     * @return This object.
     */
    public FullAddress setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Gets the address of the resident in local language.
     *
     * @return The address.
     */
    public String getLocalAddress() {
        return localAddress;
    }

    /**
     * Sets the address of the resident in local language.
     *
     * @param localAddress The address.
     * @return This object.
     */
    public FullAddress setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    /**
     * Gets {@code EnumSet.of(IdentityType.FULL_ADDRESS)} if at least one property is set.
     *
     * @return {@inheritDoc}
     */
    @Override
    public EnumSet<IdentityType> getUses() {
        final EnumSet<IdentityType> uses = EnumSet.noneOf(IdentityType.class);

        if (!(isNullOrEmpty(address) && isNullOrEmpty(localAddress)))
            uses.add(IdentityType.FULL_ADDRESS);

        return uses;
    }
}