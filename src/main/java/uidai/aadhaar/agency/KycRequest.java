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

import uidai.aadhaar.generic.KycInfo;

/**
 * Represents an KYC request. Default version of the KYC request is set to 2.0. {@link
 * KycRequest#authRequest} is mandatory.
 */
public class KycRequest extends ApiRequest {
    private static final String DEFAULT_VERSION = "2.0";

    private AuthRequest authRequest;
    private String authType;
    private boolean accessLocalInfo;
    private boolean accessEAadhaar;
    private boolean delegateDecryptionToKsa;

    public KycRequest() {
        setVersion(DEFAULT_VERSION);
    }

    public KycRequest(KycInfo info) {
        // info.decode();
        authType = info.getAuthType();
        accessLocalInfo = info.shouldAccessLocalInfo();
        accessEAadhaar = info.shouldAccessEAadhaar();
        delegateDecryptionToKsa = info.shouldDelegateDecryptionToKsa();
    }

    public AuthRequest getAuthRequest() {
        return authRequest;
    }

    public KycRequest setAuthRequest(AuthRequest authRequest) {
        this.authRequest = authRequest;
        return this;
    }

    public String getAuthType() {
        return authType;
    }

    public KycRequest setAuthType(String authType) {
        this.authType = authType;
        return this;
    }

    public boolean shouldAccessLocalInfo() {
        return accessLocalInfo;
    }

    public KycRequest setAccessLocalInfo(boolean accessLocalInfo) {
        this.accessLocalInfo = accessLocalInfo;
        return this;
    }

    public boolean shouldAccessEAadhaar() {
        return accessEAadhaar;
    }

    public KycRequest setAccessEAadhaar(boolean accessEAadhaar) {
        this.accessEAadhaar = accessEAadhaar;
        return this;
    }

    public boolean shouldDelegateDecryptionToKsa() {
        return delegateDecryptionToKsa;
    }

    public KycRequest setDelegateDecryptionToKsa(boolean delegateDecryptionToKsa) {
        this.delegateDecryptionToKsa = delegateDecryptionToKsa;
        return this;
    }
}