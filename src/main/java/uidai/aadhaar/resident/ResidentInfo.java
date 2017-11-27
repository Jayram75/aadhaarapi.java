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

import uidai.aadhaar.agency.ApiContext;
import uidai.aadhaar.generic.EAadhaar;
import uidai.aadhaar.generic.HasIdentityValue;
import uidai.aadhaar.generic.KycInfo;
import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.internal.ExceptionHelper;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.EnumSet;

import static uidai.aadhaar.generic.AadhaarHelper.isNullOrEmpty;
import static uidai.aadhaar.generic.AadhaarHelper.isValidAadhaarNumber;

/**
 * Represents personal information of a resident. This class models information which can be used
 * especially in two places:
 * <p>
 * 1. To create PID block sent during authentication and;
 * <p>
 * 2. To read UIDData block received during e-KYC response.
 * <p>
 * Aadhaar number should be considered mandatory. When set, this value will be automatically
 * propagated to the higher level of API through {@link ApiContext}. Default version of the resident
 * info is set to 2.0.
 */
public class ResidentInfo implements HasIdentityValue {
    public static final String DEFAULT_VERSION = "2.0";

    private String version = DEFAULT_VERSION;
    private String aadhaarNumber;
    private Demographic demographic;
    private Biometric biometric;
    private PinValue pinValue;
    private String photo;
    private EAadhaar eAadhaar;
    private OffsetDateTime capturedOn = OffsetDateTime.now();
    private OffsetDateTime expiresOn;
    private String apiInfo;

    /**
     * Gets a version information for the resident information.
     *
     * @return The version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets a version information for the resident information.
     *
     * @param version A version.
     * @return This object.
     * @throws IllegalArgumentException If the version is null or empty string.
     */
    public ResidentInfo setVersion(String version) {
        ExceptionHelper.validateEmptyString(version, "version");

        this.version = version;
        return this;
    }

    /**
     * Gets the Aadhaar number of the resident.
     *
     * @return The Aadhaar number.
     */
    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    /**
     * Sets the Aadhaar number of the resident. The Aadhaar number must be 12 digits and Verhoeff
     * compliant as specified in UIDAI specifications.
     *
     * @param aadhaarNumber An Aadhaar number.
     * @return This object.
     * @throws IllegalArgumentException If the Aadhaar number is invalid.
     */
    public ResidentInfo setAadhaarNumber(String aadhaarNumber) {
        if (!isNullOrEmpty(aadhaarNumber) && !isValidAadhaarNumber(aadhaarNumber))
            throw new IllegalArgumentException(ErrorMessages.INVALID_AADHAAR_NUMBER);

        this.aadhaarNumber = aadhaarNumber;
        return this;
    }

    /**
     * Gets the demographic information of the resident.
     *
     * @return The demographic information.
     */
    public Demographic getDemographic() {
        return demographic;
    }

    /**
     * Sets the demographic information of the resident.
     *
     * @param demographic The demographic information.
     * @return This object.
     */
    public ResidentInfo setDemographic(Demographic demographic) {
        this.demographic = demographic;
        return this;
    }

    /**
     * Gets the biometric information of the resident.
     *
     * @return The biometric information.
     */
    public Biometric getBiometric() {
        return biometric;
    }

    /**
     * Sets the biometric information of the resident.
     *
     * @param biometric The biometric information.
     * @return This object.
     */
    public ResidentInfo setBiometric(Biometric biometric) {
        this.biometric = biometric;
        return this;
    }

    /**
     * Gets the pin value information of the resident.
     *
     * @return The pin value information.
     */
    public PinValue getPinValue() {
        return pinValue;
    }

    /**
     * Sets the pin value information of the resident.
     *
     * @param pinValue The pin value information.
     * @return This object.
     */
    public ResidentInfo setPinValue(PinValue pinValue) {
        this.pinValue = pinValue;
        return this;
    }

    /**
     * Gets the photo of the resident in Base64 format.
     *
     * @return The photo.
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Sets the photo of the resident. This property is not used during authentication and gets
     * populated as a part of e-KYC response.
     *
     * @param photo The photo.
     * @return This object.
     */
    public ResidentInfo setPhoto(byte[] photo) {
        return setPhoto(photo != null ? new String(Base64.getEncoder().encode(photo), StandardCharsets.UTF_8) : null);
    }

    /**
     * Sets the photo of the resident in Base64 format. This property is not used during
     * authentication and gets populated as a part of e-KYC response.
     *
     * @param photo The photo.
     * @return This object.
     */
    public ResidentInfo setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    /**
     * Gets e-Aadhaar document of the resident.
     *
     * @return The e-Aadhaar document.
     */
    public EAadhaar getEAadhaar() {
        return eAadhaar;
    }

    /**
     * Sets e-Aadhaar document of the resident. This property is not used during authentication and
     * gets populated as a part of e-KYC response.
     *
     * @param eAadhaar The e-Aadhaar document.
     * @return This object.
     */
    public ResidentInfo setEAadhaar(EAadhaar eAadhaar) {
        this.eAadhaar = eAadhaar;
        return this;
    }

    /**
     * Gets the date time when the resident info is captured. This property is the timestamp
     * required in many places in the API pipeline and mainly used during:
     * <p>
     * 1. Biometric record signature calculation.
     * <p>
     * 2. Resident information encryption.
     * <p>
     * 3. e-KYC API service.
     *
     * @return The date time.
     */
    public OffsetDateTime getCapturedOn() {
        return capturedOn;
    }

    /**
     * Sets the date time when the resident info is captured. Defaults to current system date time
     * when this object was created.
     *
     * @param capturedOn The date time.
     * @return This object.
     * @throws IllegalArgumentException If the date time is null.
     */
    public ResidentInfo setCapturedOn(OffsetDateTime capturedOn) {
        ExceptionHelper.validateNull(capturedOn, "capturedOn");

        this.capturedOn = capturedOn;
        return this;
    }

    /**
     * Gets the date time when the resident information expires.
     *
     * @return The date time.
     */
    public OffsetDateTime getExpiresOn() {
        return expiresOn;
    }

    /**
     * Sets the date time when the resident information expires. This property is not used during
     * authentication and gets populated as a part of e-KYC response.
     *
     * @param expiresOn The date time.
     * @return This object.
     */
    public ResidentInfo setExpiresOn(OffsetDateTime expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    /**
     * Gets any wrapper API info where this resident information is used as specified by UIDAI
     * specification.
     *
     * @return The API info.
     */
    public String getApiInfo() {
        return apiInfo;
    }

    /**
     * Gets any wrapper API info where this resident information is used as specified by UIDAI
     * specification. For example in e-KYC transaction API info must be set to wrapper API hash of
     * properties defined in {@link KycInfo}.
     *
     * @param apiInfo The API info.
     * @return This object.
     */
    public ResidentInfo setApiInfo(byte[] apiInfo) {
        return setApiInfo(apiInfo != null ? new String(Base64.getEncoder().encode(apiInfo), StandardCharsets.UTF_8) : null);
    }

    /**
     * Sets any hash value in Base64 format of the API data where this resident information is used.
     * Wrapper API data hash is mandatory during e-KYC transaction.
     *
     * @param apiInfo The wrapper API hash.
     * @return This object.
     */
    public ResidentInfo setApiInfo(String apiInfo) {
        this.apiInfo = apiInfo;
        return this;
    }

    @Override
    public EnumSet<IdentityType> getUses() {
        final EnumSet<IdentityType> uses = EnumSet.noneOf(IdentityType.class);

        if (demographic != null)
            uses.addAll(demographic.getUses());
        if (biometric != null)
            uses.addAll(biometric.getUses());
        if (pinValue != null)
            uses.addAll(pinValue.getUses());

        return uses;
    }
}