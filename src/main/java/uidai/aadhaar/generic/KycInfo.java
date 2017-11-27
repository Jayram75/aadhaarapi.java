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

package uidai.aadhaar.generic;

import uidai.aadhaar.internal.ExceptionHelper;

import static uidai.aadhaar.generic.AadhaarHelper.yesNo;

public class KycInfo extends ApiInfo {
    private static final String DEFAULT_VERSION = "2.0";

    private String version = DEFAULT_VERSION;
    private boolean residentConsent;
    private boolean accessLocalInfo;
    private boolean accessEAadhaar;
    private boolean delegateDecryptionToKsa;
    private String authType;

    public String getVersion() {
        return version;
    }

    public KycInfo setVersion(String version) {
        this.version = version;
        return this;
    }

    public boolean hasResidentConsent() {
        return residentConsent;
    }

    public KycInfo setResidentConsent(boolean residentConsent) {
        this.residentConsent = residentConsent;
        return this;
    }

    public boolean shouldAccessLocalInfo() {
        return accessLocalInfo;
    }

    public KycInfo setAccessLocalInfo(boolean accessLocalInfo) {
        this.accessLocalInfo = accessLocalInfo;
        return this;
    }

    public boolean shouldAccessEAadhaar() {
        return accessEAadhaar;
    }

    public KycInfo setAccessEAadhaar(boolean accessEAadhaar) {
        this.accessEAadhaar = accessEAadhaar;
        return this;
    }

    public boolean shouldDelegateDecryptionToKsa() {
        return delegateDecryptionToKsa;
    }

    public KycInfo setDelegateDecryptionToKsa(boolean delegateDecryptionToKsa) {
        this.delegateDecryptionToKsa = delegateDecryptionToKsa;
        return this;
    }

    public String getAuthType() {
        return authType;
    }

    public KycInfo setAuthType(String authType) {
        this.authType = authType;
        return this;
    }

    @Override
    public void encode() {
        final StringBuilder builder = new StringBuilder(11);
        builder.append(version);
        builder.append(authType);
        builder.append(yesNo(residentConsent));
        builder.append(yesNo(accessLocalInfo));
        builder.append(yesNo(delegateDecryptionToKsa));
        builder.append(yesNo(accessEAadhaar));
        setInfo(builder.toString());
    }

    @Override
    public void decode() {
        ExceptionHelper.NotSupported();
    }
}