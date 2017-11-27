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

package uidai.aadhaar.agency;

import uidai.aadhaar.device.DeviceInfo;
import uidai.aadhaar.device.Terminal;
import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.resident.IdentityType;
import uidai.aadhaar.resident.ResidentInfo;
import uidai.aadhaar.security.EncryptedData;
import uidai.aadhaar.security.SessionKey;

import java.util.EnumSet;
import java.util.regex.Pattern;

import static uidai.aadhaar.generic.AadhaarHelper.*;

/**
 * Represents an authentication request. All the properties of the authentication request are
 * mandatory. Default version of the authentication request is set to 2.0. Properties related to
 * resident data are automatically set through {@link AuthContext#encrypt(ResidentInfo)}. Properties
 * that are not set must be explicitly set after creating an instance of this class, otherwise
 * {@link ApiContext#validateRequest()} will throw exception. Properties that need to be set are:
 * <p>
 * 1. {@link AuthRequest#hasResidentConsent}
 * <p>
 * 2. {@link AuthRequest#transactionId}
 * <p>
 * 3. {@link AuthRequest#terminal}
 * <p>
 * 4. {@link AuthRequest#deviceInfo}
 * <p>
 * 5. {@link AuthRequest#agencyInfo}
 */
public class AuthRequest extends ApiRequest {
    private static final String DEFAULT_VERSION = "2.0";

    private boolean hasResidentConsent;
    private String aadhaarNumber;
    private String transactionId;
    private String terminal;
    private EncryptedData encryptedData;
    private SessionKey sessionKey;
    private EnumSet<IdentityType> uses;
    private DeviceInfo deviceInfo;
    private AgencyInfo agencyInfo;

    public AuthRequest() {
        setVersion(DEFAULT_VERSION);
    }

    public boolean hasResidentConsent() {
        return hasResidentConsent;
    }

    public AuthRequest setResidentConsent(boolean hasResidentConsent) {
        this.hasResidentConsent = hasResidentConsent;
        return this;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public AuthRequest setAadhaarNumber(String aadhaarNumber) {
        if (!isNullOrEmpty(aadhaarNumber) && !isValidAadhaarNumber(aadhaarNumber))
            throw new IllegalArgumentException(ErrorMessages.INVALID_AADHAAR_NUMBER);

        this.aadhaarNumber = aadhaarNumber;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public AuthRequest setTransactionId(String transactionId) {
        if (!isNullOrEmpty(transactionId) && (transactionId.length() > 50 || !Pattern.matches(TRANSACTION_ID_PATTERN, transactionId)))
            throw new IllegalArgumentException(ErrorMessages.INVALID_TRASACTION_ID);

        this.transactionId = transactionId;
        return this;
    }

    public String getTerminal() {
        return terminal;
    }

    public AuthRequest setTerminal(String terminal) {
        this.terminal = terminal;
        return this;
    }

    public AuthRequest setTerminal(Terminal terminal) {
        this.terminal = terminal != null ? terminal.value() : "";
        return this;
    }

    public EncryptedData getEncryptedData() {
        return encryptedData;
    }

    public AuthRequest setEncryptedData(EncryptedData encryptedData) {
        this.encryptedData = encryptedData;
        return this;
    }

    public SessionKey getSessionKey() {
        return sessionKey;
    }

    public AuthRequest setSessionKey(SessionKey sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public EnumSet<IdentityType> getUses() {
        return uses;
    }

    public AuthRequest setUses(EnumSet<IdentityType> uses) {
        this.uses = uses;
        return this;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public AuthRequest setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public AgencyInfo getAgencyInfo() {
        return agencyInfo;
    }

    public AuthRequest setAgencyInfo(AgencyInfo agencyInfo) {
        this.agencyInfo = agencyInfo;
        return this;
    }
}