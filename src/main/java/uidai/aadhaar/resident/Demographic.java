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
import uidai.aadhaar.resident.options.DemographicOptions;

import java.util.EnumSet;

/**
 * Represents the demographic information of a resident.
 */
public class Demographic implements HasIdentityValue {
    private Identity identity;
    private Address address;
    private FullAddress fullAddress;
    private IndianLanguage localLanguage;
    private DemographicOptions options;

    /**
     * Gets the identity of the resident.
     *
     * @return The identity.
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * Sets the identity of the resident.
     *
     * @param identity The identity.
     * @return This object.
     */
    public Demographic setIdentity(Identity identity) {
        this.identity = identity;
        return this;
    }

    /**
     * Gets the address of the resident.
     *
     * @return The address.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the resident. The {@link Address} and {@link FullAddress} are mutually
     * exclusive during authentication.
     *
     * @param address The address.
     * @return This object.
     */
    public Demographic setAddress(Address address) {
        this.address = address;
        return this;
    }

    /**
     * Gets the full address of the resident.
     *
     * @return The full address.
     */
    public FullAddress getFullAddress() {
        return fullAddress;
    }

    /**
     * Sets the full address of the resident. The {@link Address} and {@link FullAddress} are
     * mutually exclusive during authentication.
     *
     * @param fullAddress The full address.
     * @return This object.
     */
    public Demographic setFullAddress(FullAddress fullAddress) {
        this.fullAddress = fullAddress;
        return this;
    }

    /**
     * Gets the local language of the resident which is used in local name and local address value.
     *
     * @return The local language.
     */
    public IndianLanguage getLocalLanguage() {
        return localLanguage;
    }

    /**
     * Sets the local language of the resident. The local language must be set if local name and
     * local address is set.
     *
     * @param localLanguage The local language.
     * @return This object.
     */
    public Demographic setLocalLanguage(IndianLanguage localLanguage) {
        this.localLanguage = localLanguage;
        return this;
    }

    /**
     * Gets the demographic options to be used during during authentication.
     *
     * @return The demographic options.
     */
    public DemographicOptions getOptions() {
        return options;
    }

    /**
     * Sets the demographic options to be used during authentication.
     *
     * @param options The demographic options.
     * @return This object.
     */
    public Demographic setOptions(DemographicOptions options) {
        this.options = options;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public EnumSet<IdentityType> getUses() {
        final EnumSet<IdentityType> uses = EnumSet.noneOf(IdentityType.class);

        if (identity != null)
            uses.addAll(identity.getUses());
        if (address != null)
            uses.addAll(address.getUses());
        if (fullAddress != null)
            uses.addAll(fullAddress.getUses());
        if (!uses.isEmpty())
            uses.add(IdentityType.DEMOGRAPHIC);

        return uses;
    }
}