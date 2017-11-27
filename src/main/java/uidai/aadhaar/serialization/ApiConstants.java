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

package uidai.aadhaar.serialization;

import uidai.aadhaar.resident.IdentityType;

import java.util.EnumSet;

final class ApiConstants {
    static final String AUTH_NAMESPACE = "http://www.uidai.gov.in/authentication/uid-auth-request/";
    static final String KYC_NAMESPACE = "http://www.uidai.gov.in/kyc/uid-kyc-request/";
    static final String OTP_NAMESPACE = "http://www.uidai.gov.in/authentication/otp/";
    static final String PID_NAMESPACE = "http://www.uidai.gov.in/authentication/uid-auth-request-data/";

    static final String YES = "y";
    static final String NO = "n";

    static final EnumSet<IdentityType> IDENTITY_TYPES = EnumSet.of(
        IdentityType.IDENTITY, IdentityType.ADDRESS, IdentityType.FULL_ADDRESS,
        IdentityType.BIOMETRIC,
        IdentityType.OTP, IdentityType.PIN);
    static final EnumSet<IdentityType> BIOMETRIC_TYPES = EnumSet.of(
        IdentityType.MINUTIAE,
        IdentityType.FINGERPRINT,
        IdentityType.IRIS,
        IdentityType.FACE
    );

    private ApiConstants() {
    }
}