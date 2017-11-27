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

import java.time.OffsetDateTime;
import java.util.EnumSet;

import static uidai.aadhaar.generic.AadhaarHelper.isNullOrEmpty;

/**
 * Represents identity information of a resident.
 */
public class Identity implements HasIdentityValue {
    private String name;
    private String localName;
    private Gender gender;
    private OffsetDateTime dateOfBirth;
    private Integer age;
    private String email;
    private String phoneNumber;

    /**
     * Gets the name of the resident.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the resident.
     *
     * @param name The name.
     * @return This object.
     * @throws IllegalArgumentException If length is greater than 60 characters
     */
    public Identity setName(String name) {
        if (name != null && name.length() > 60)
            throw new IllegalArgumentException(ErrorMessages.OUT_OF_RANGE_NAME);

        this.name = name;
        return this;
    }

    /**
     * Gets the name of the resident in local language.
     *
     * @return The name in local language.
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Sets the name of the resident in local language.
     *
     * @param localName The name in local language.
     * @return This object.
     */
    public Identity setLocalName(String localName) {
        this.localName = localName;
        return this;
    }

    /**
     * Gets the gender of the resident.
     *
     * @return The gender.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of the resident.
     *
     * @param gender The gender.
     * @return This object.
     */
    public Identity setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    /**
     * Gets the date of birth of the resident.
     *
     * @return The date of birth.
     */
    public OffsetDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the resident.
     *
     * @param dateOfBirth The date of birth.
     * @return This object.
     */
    public Identity setDateOfBirth(OffsetDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    /**
     * Gets the age of the resident.
     *
     * @return The age.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age of the resident. When used during authentication if resident's age is equal to
     * or greater than the specified age, the authentication will pass.
     *
     * @param age The age.
     * @return This object.
     * @throws IllegalArgumentException If age is less than 1 or greater than 150.
     */
    public Identity setAge(Integer age) {
        if (age != null && (age < 1 || age > 150))
            throw new IllegalArgumentException(ErrorMessages.OUT_OF_RANGE_AGE);

        this.age = age;
        return this;
    }

    /**
     * Gets the email of the resident.
     *
     * @return The email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the resident.
     *
     * @param email The email.
     * @return This object.
     */
    public Identity setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets the phone number of the resident.
     *
     * @return The phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the resident.
     *
     * @return This object.
     */
    public Identity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Gets {@code EnumSet.of(IdentityType.IDENTITY)} if at least one property is set.
     *
     * @return {@inheritDoc}
     */
    @Override
    public EnumSet<IdentityType> getUses() {
        final EnumSet<IdentityType> uses = EnumSet.noneOf(IdentityType.class);

        if (!(isNullOrEmpty(name)
            && isNullOrEmpty(localName)
            && isNullOrEmpty(email)
            && isNullOrEmpty(phoneNumber)
            && gender == null
            && dateOfBirth == null
            && age == null))
            uses.add(IdentityType.IDENTITY);

        return uses;
    }
}