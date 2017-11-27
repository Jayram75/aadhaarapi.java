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

import uidai.aadhaar.internal.ErrorMessages;

import java.time.OffsetDateTime;
import java.util.regex.Pattern;

import static uidai.aadhaar.generic.AadhaarHelper.TRANSACTION_ID_PATTERN;
import static uidai.aadhaar.generic.AadhaarHelper.isNullOrEmpty;

/**
 * Represents an authentication response.
 */
public class AuthResponse extends ApiResponse {
    private boolean authentic;
    private String transactionId;
    private OffsetDateTime timestamp;
    private String responseCode;
    private String actionCode;
    private String info;

    public boolean isAuthentic() {
        return authentic;
    }

    public AuthResponse setAuthentic(boolean authentic) {
        this.authentic = authentic;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public AuthResponse setTransactionId(String transactionId) {
        if (!isNullOrEmpty(transactionId) && (transactionId.length() > 50 || !Pattern.matches(TRANSACTION_ID_PATTERN, transactionId)))
            throw new IllegalArgumentException(ErrorMessages.INVALID_TRASACTION_ID);

        this.transactionId = transactionId;
        return this;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public AuthResponse setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public AuthResponse setResponseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getActionCode() {
        return actionCode;
    }

    public AuthResponse setActionCode(String actionCode) {
        this.actionCode = actionCode;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public AuthResponse setInfo(String info) {
        this.info = info;
        return this;
    }
}