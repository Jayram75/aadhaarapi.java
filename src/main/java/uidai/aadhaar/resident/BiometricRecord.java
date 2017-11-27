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
import uidai.aadhaar.internal.ErrorMessages;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.EnumSet;

import static uidai.aadhaar.generic.AadhaarHelper.getBiometricTypesFromPosition;
import static uidai.aadhaar.generic.AadhaarHelper.isNullOrEmpty;

/**
 * Represents a biometric record of a resident. Biometric record should not be stored anywhere in
 * any format. It should be constructed only by a UIDAI registered biometric device supporting
 * Registered Device Specification 2.0 or later.
 */
public class BiometricRecord implements HasIdentityValue {
    private BiometricType type;
    private BiometricPosition position;
    private String data;
    private String signature;

    /**
     * Gets the type of the biometric record.
     *
     * @return The biometric type.
     */
    public BiometricType getType() {
        return type;
    }

    /**
     * Sets the type of the biometric record.
     *
     * @param type The biometric type.
     * @return This object.
     * @throws IllegalArgumentException If the type doesn't match with the current position set.
     */
    public BiometricRecord setType(BiometricType type) {
        // Make sure biometric type matches with its position.
        if (position != null && type != null && !getBiometricTypesFromPosition(EnumSet.of(position)).contains(type))
            throw new IllegalArgumentException(ErrorMessages.INVALID_BIOMETRIC_TYPE);

        this.type = type;
        return this;
    }

    /**
     * Gets the position of the biometric record.
     *
     * @return The biometric position.
     */
    public BiometricPosition getPosition() {
        return position;
    }

    /**
     * Sets the position of the biometric record.
     *
     * @param position The biometric position.
     * @return This object.
     * @throws IllegalArgumentException If the position doesn't match with current type set.
     */
    public BiometricRecord setPosition(BiometricPosition position) {
        // Make sure biometric position matches with its type.
        if (type != null && position != null && !getBiometricTypesFromPosition(EnumSet.of(position)).contains(type))
            throw new IllegalArgumentException(ErrorMessages.INVALID_BIOMETRIC_POSITION);

        this.position = position;
        return this;
    }

    /**
     * Gets the actual data of the biometric record in base64 format.
     *
     * @return The data.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the actual data of the biometric record in base64 format
     *
     * @param data The data.
     * @return This object.
     */
    public BiometricRecord setData(String data) {
        this.data = data;
        return this;
    }

    /**
     * Gets the signature of the biometric record.
     *
     * @return The signature.
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the signature of the biometric record. RD Service should use the following logic to sign
     * the biometric record:
     * <p>
     * hash         = SHA-256(data)
     * <p>
     * signature    = DSA(hash + ResidentInfo::timestamp + DeviceInfo::deviceCode, key)
     *
     * @return The signature.
     */
    public BiometricRecord setSignature(byte[] signature) {
        return setSignature(signature != null ? new String(Base64.getEncoder().encode(signature), StandardCharsets.UTF_8) : null);
    }

    /**
     * Sets the signature of the biometric record in base64 format. RD Service should use the
     * following logic to sign the biometric record:
     * <p>
     * hash         = SHA-256(data)
     * <p>
     * signature    = Base64(DSA(hash + ResidentInfo::timestamp + DeviceInfo::deviceCode, key))
     *
     * @param signature The signature.
     * @return This object.
     */
    public BiometricRecord setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    /**
     * Gets {@code EnumSet.of(IdentityType.BIOMETRIC)} if all the properties are set. It also
     * returns the biometric type as a identity type.
     *
     * @return {@inheritDoc}
     */
    @Override
    public EnumSet<IdentityType> getUses() {
        final EnumSet<IdentityType> uses = EnumSet.noneOf(IdentityType.class);

        // Biometric record has value only if all properties are set.
        if (!(isNullOrEmpty(data) || isNullOrEmpty(signature) || type == null || position == null))
            uses.add(IdentityType.valueOf(type.name()));

        return uses;
    }
}