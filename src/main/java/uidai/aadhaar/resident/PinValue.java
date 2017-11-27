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
 * Represents any temporary pin information of a resident.
 */
public class PinValue implements HasIdentityValue {
    private String otp;
    private String pin;

    /**
     * Gets the OTP sent to the resident.
     *
     * @return The OTP.
     */
    public String getOtp() {
        return otp;
    }

    /**
     * Sets the OTP sent to the resident using OTP Request API
     *
     * @param otp The OTP.
     * @return This object.
     */
    public PinValue setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    /**
     * Gets the PIN used by the resident.
     *
     * @return The PIN.
     */
    public String getPin() {
        return pin;
    }

    /**
     * Sets the PIN used by the resident. This property is only used by UIDAI internally.
     *
     * @param otp The PIN.
     * @return This object.
     */
    public PinValue setPin(String pin) {
        this.pin = pin;
        return this;
    }

    /**
     * Gets {@code EnumSet.of(IdentityType.PIN_VALUE)} if at least one property is set.
     *
     * @return {@inheritDoc}
     */
    @Override
    public EnumSet<IdentityType> getUses() {
        final EnumSet<IdentityType> uses = EnumSet.noneOf(IdentityType.class);

        if (!isNullOrEmpty(otp))
            uses.add(IdentityType.OTP);
        if (!isNullOrEmpty(pin))
            uses.add(IdentityType.PIN);

        return uses;
    }
}