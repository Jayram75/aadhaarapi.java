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

import uidai.aadhaar.device.Terminal;
import uidai.aadhaar.generic.OtpChannel;
import uidai.aadhaar.internal.ErrorMessages;

import java.util.regex.Pattern;

import static uidai.aadhaar.generic.AadhaarHelper.*;

/**
 * Represents an OTP request. Default version of the OTP request is set to 1.6. Aadhaar number and
 * mobile number is mutually exclusive and must be set. Mobile number is only required when using
 * Mobile Update API. Properties that are mandatory must be set after creating an instance of this
 * class otherwise {@link ApiContext#validateRequest()} will throw exceptions. Properties that must
 * be set are:
 * <p>
 * 1. {@link OtpRequest#transactionId}
 * <p>
 * 2. {@link OtpRequest#terminal}
 * <p>
 * 3. {@link OtpRequest#agencyInfo}
 */
public class OtpRequest extends ApiRequest {
    private static final String DEFAULT_VERSION = "1.6";

    private String aadhaarNumber;
    private String mobileNumber;
    private String transactionId;
    private String terminal;
    private OtpChannel otpChannel;
    private AgencyInfo agencyInfo;

    public OtpRequest() {
        setVersion(DEFAULT_VERSION);
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public OtpRequest setAadhaarNumber(String aadhaarNumber) {
        if (!isNullOrEmpty(aadhaarNumber) && !isValidAadhaarNumber(aadhaarNumber))
            throw new IllegalArgumentException(ErrorMessages.INVALID_AADHAAR_NUMBER);

        this.aadhaarNumber = aadhaarNumber;
        return this;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public OtpRequest setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public OtpRequest setTransactionId(String transactionId) {
        if (!isNullOrEmpty(transactionId) && (transactionId.length() > 50 || !Pattern.matches(TRANSACTION_ID_PATTERN, transactionId)))
            throw new IllegalArgumentException(ErrorMessages.INVALID_TRASACTION_ID);

        this.transactionId = transactionId;
        return this;
    }

    public String getTerminal() {
        return terminal;
    }

    public OtpRequest setTerminal(String terminal) {
        this.terminal = terminal;
        return this;
    }

    public OtpRequest setTerminal(Terminal terminal) {
        this.terminal = terminal != null ? terminal.value() : "";
        return this;
    }

    public OtpChannel getOtpChannel() {
        return otpChannel;
    }

    public OtpRequest setOtpChannel(OtpChannel otpChannel) {
        this.otpChannel = otpChannel;
        return this;
    }

    public AgencyInfo getAgencyInfo() {
        return agencyInfo;
    }

    public OtpRequest setAgencyInfo(AgencyInfo agencyInfo) {
        this.agencyInfo = agencyInfo;
        return this;
    }
}