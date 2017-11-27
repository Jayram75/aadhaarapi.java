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

import uidai.aadhaar.resident.ResidentInfo;

import java.time.OffsetDateTime;

/**
 * Represents an KYC response.
 */
public class KycResponse extends ApiResponse {
    private boolean authentic;
    private String transactionId;
    private OffsetDateTime timestamp;
    private String responseCode;
    private String actionCode;
    private AuthResponse authResponse;
    private ResidentInfo residentInfo;

    public boolean isAuthentic() {
        return authentic;
    }

    public KycResponse setAuthentic(boolean authentic) {
        this.authentic = authentic;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public KycResponse setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public KycResponse setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public KycResponse setResponseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getActionCode() {
        return actionCode;
    }

    public KycResponse setActionCode(String actionCode) {
        this.actionCode = actionCode;
        return this;
    }

    public AuthResponse getAuthResponse() {
        return authResponse;
    }

    public KycResponse setAuthResponse(AuthResponse authResponse) {
        this.authResponse = authResponse;
        return this;
    }

    public ResidentInfo getResidentInfo() {
        return residentInfo;
    }

    public KycResponse setResidentInfo(ResidentInfo residentInfo) {
        this.residentInfo = residentInfo;
        return this;
    }
}