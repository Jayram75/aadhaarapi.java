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
import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.resident.IdentityType;
import uidai.aadhaar.resident.options.BiometricOptions;
import uidai.aadhaar.security.EncryptedData;
import uidai.aadhaar.security.SessionKey;

import java.util.Collection;
import java.util.EnumSet;

public class CaptureResponse extends ApiResponse {
    private EncryptedData encryptedData;
    private SessionKey sessionKey;
    private DeviceInfo deviceInfo;
    private BiometricOptions biometricOptions;
    private Collection<Integer> minutiaePoints;
    private int qualityScore;
    private String errorInfo;

    public EncryptedData getEncryptedData() {
        return encryptedData;
    }

    public CaptureResponse setEncryptedData(EncryptedData encryptedData) {
        this.encryptedData = encryptedData;
        return this;
    }

    public SessionKey getSessionKey() {
        return sessionKey;
    }

    public CaptureResponse setSessionKey(SessionKey sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public CaptureResponse setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public BiometricOptions getBiometricOptions() {
        return biometricOptions;
    }

    public CaptureResponse setBiometricOptions(BiometricOptions biometricOptions) {
        this.biometricOptions = biometricOptions;
        return this;
    }

    public Collection<Integer> getMinutiaePoints() {
        return minutiaePoints;
    }

    public CaptureResponse setMinutiaePoints(Collection<Integer> minutiaePoints) {
        this.minutiaePoints = minutiaePoints;
        return this;
    }

    public int getQualityScore() {
        return qualityScore;
    }

    public CaptureResponse setQualityScore(int qualityScore) {
        ExceptionHelper.validatePercent(qualityScore);

        this.qualityScore = qualityScore;
        return this;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public CaptureResponse setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
        return this;
    }

    public EnumSet<IdentityType> getUses(EnumSet<IdentityType> demographicUses) {
        throw new UnsupportedOperationException(ErrorMessages.NOT_SUPPORTED);
    }
}