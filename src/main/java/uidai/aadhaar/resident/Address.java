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
import uidai.aadhaar.internal.ExceptionHelper;

import java.util.EnumSet;

import static uidai.aadhaar.generic.AadhaarHelper.isNullOrEmpty;

/**
 * Represent address information of a resident.
 */
public class Address implements HasIdentityValue {
    private String careOf;
    private String house;
    private String street;
    private String landmark;
    private String locality;
    private String villageOrCity;
    private String subDistrict;
    private String district;
    private String state;
    private String country;
    private String pincode;
    private String postOffice;
    private Address localAddress;

    /**
     * Gets the care of the address.
     *
     * @return The care of.
     */
    public String getCareOf() {
        return careOf;
    }

    /**
     * Sets the care of the address.
     *
     * @param careOf the care of
     * @return This object.
     */
    public Address setCareOf(String careOf) {
        this.careOf = careOf;
        return this;
    }

    /**
     * Gets the house number of the address.
     *
     * @return The house number.
     */
    public String getHouse() {
        return house;
    }

    /**
     * Sets the house number of the address.
     *
     * @param house The house number.
     * @return This object.
     */
    public Address setHouse(String house) {
        this.house = house;
        return this;
    }

    /**
     * Gets the street of the address.
     *
     * @return The street.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street of the address.
     *
     * @param street The street.
     * @return This object.
     */
    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    /**
     * Gets the landmark of the address.
     *
     * @return The landmark.
     */
    public String getLandmark() {
        return landmark;
    }

    /**
     * Sets the landmark of the address.
     *
     * @param landmark The landmark.
     * @return This object.
     */
    public Address setLandmark(String landmark) {
        this.landmark = landmark;
        return this;
    }

    /**
     * Gets the locality of the address.
     *
     * @return The locality.
     */
    public String getLocality() {
        return locality;
    }

    /**
     * Sets the locality of the address.
     *
     * @param locality the locality
     * @return This object.
     */
    public Address setLocality(String locality) {
        this.locality = locality;
        return this;
    }

    /**
     * Gets the village or city of the address.
     *
     * @return The village or city.
     */
    public String getVillageOrCity() {
        return villageOrCity;
    }

    /**
     * Sets the village or city of the address.
     *
     * @param villageOrCity The village or city.
     * @return This object.
     */
    public Address setVillageOrCity(String villageOrCity) {
        this.villageOrCity = villageOrCity;
        return this;
    }

    /**
     * Gets the sub-district of the address.
     *
     * @return The sub-district.
     */
    public String getSubDistrict() {
        return subDistrict;
    }

    /**
     * Sets the sub-district of the address.
     *
     * @param subDistrict The sub-district.
     * @return This object.
     */
    public Address setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
        return this;
    }

    /**
     * Gets the district of the address.
     *
     * @return The district.
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the district of the address.
     *
     * @param district The district.
     * @return This object.
     */
    public Address setDistrict(String district) {
        this.district = district;
        return this;
    }

    /**
     * Gets the state of the address.
     *
     * @return The state.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the address.
     *
     * @param state The state.
     * @return This object.
     */
    public Address setState(String state) {
        this.state = state;
        return this;
    }

    /**
     * Gets the country of the address.
     *
     * @return The country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the address.
     *
     * @param country The country.
     * @return This object.
     */
    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    /**
     * Gets the pincode of the address.
     *
     * @return The pincode.
     */
    public String getPincode() {
        return pincode;
    }

    /**
     * Sets the pincode of the address.
     *
     * @param pincode The pincode.
     * @return This object.
     */
    public Address setPincode(String pincode) {
        ExceptionHelper.validatePin(pincode);

        this.pincode = pincode;
        return this;
    }

    /**
     * Gets the post office of the address.
     *
     * @return The post office
     */
    public String getPostOffice() {
        return postOffice;
    }

    /**
     * Sets the post office of the address.
     *
     * @param postOffice The post office.
     * @return This object.
     */
    public Address setPostOffice(String postOffice) {
        this.postOffice = postOffice;
        return this;
    }

    /**
     * Gets the address of the resident in local language.
     *
     * @return The address.
     */
    public Address getLocalAddress() {
        return localAddress;
    }

    /**
     * Sets the address of the resident in local language. This property is not used during
     * authentication and gets populated as a part of e-KYC response. {@link Address#getUses()} is
     * not affected from this property.
     *
     * @param localAddress The address
     * @return This object.
     */
    public Address setLocalAddress(Address localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    /**
     * Gets {@code EnumSet.of(IdentityType.ADDRESS)} if at least one property is set.
     *
     * @return {@inheritDoc}
     */
    @Override
    public EnumSet<IdentityType> getUses() {
        final EnumSet<IdentityType> uses = EnumSet.noneOf(IdentityType.class);

        if (!(isNullOrEmpty(careOf)
            && isNullOrEmpty(house)
            && isNullOrEmpty(street)
            && isNullOrEmpty(landmark)
            && isNullOrEmpty(locality)
            && isNullOrEmpty(villageOrCity)
            && isNullOrEmpty(subDistrict)
            && isNullOrEmpty(district)
            && isNullOrEmpty(state)
            && isNullOrEmpty(country)
            && isNullOrEmpty(pincode)
            && isNullOrEmpty(postOffice)))
            uses.add(IdentityType.ADDRESS);

        return uses;
    }
}