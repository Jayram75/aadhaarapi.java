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
import uidai.aadhaar.resident.options.BiometricOptions;

import java.util.Collection;
import java.util.EnumSet;

/**
 * Represent biometric information of a resident.
 */
public class Biometric implements HasIdentityValue {
    private Collection<BiometricRecord> biometrics;
    private String deviceInfoHash;
    private BiometricOptions options;

    /**
     * Gets a collection of biometric records.
     *
     * @return The biometric records.
     */
    public Collection<BiometricRecord> getBiometrics() {
        return biometrics;
    }

    /**
     * Sets a collection of biometric records.
     *
     * @param biometrics The biometric records.
     * @return This object.
     */
    public Biometric setBiometrics(Collection<BiometricRecord> biometrics) {
        this.biometrics = biometrics;
        return this;
    }

    /**
     * Gets the info hash of device used to capture the biometrics.
     *
     * @return The device info hash.
     */
    public String getDeviceInfoHash() {
        return deviceInfoHash;
    }

    /**
     * Sets the info hash of device used to capture the biometrics. RD Service should use the
     * following logic to compute hash:
     * <p>
     * deviceInfoHash = SHA-256(dpId + rdsId + rdsVer + dc + mi + idHash)
     *
     * @param deviceInfoHash The device info hash.
     * @return This object.
     */
    public Biometric setDeviceInfoHash(String deviceInfoHash) {
        this.deviceInfoHash = deviceInfoHash;
        return this;
    }

    /**
     * Gets the biometric options to be used during during capturing biometrics.
     *
     * @return The biometric options.
     */
    public BiometricOptions getOptions() {
        return options;
    }

    /**
     * Sets the biometric options to be used during capturing biometrics.
     *
     * @param options The biometric options.
     * @return This object.
     */
    public Biometric setOptions(BiometricOptions options) {
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

        if (biometrics != null)
            biometrics.stream()
                .map(BiometricRecord::getUses)
                .forEach(uses::addAll);
        if (!uses.isEmpty())
            uses.add(IdentityType.BIOMETRIC);

        return uses;
    }
}