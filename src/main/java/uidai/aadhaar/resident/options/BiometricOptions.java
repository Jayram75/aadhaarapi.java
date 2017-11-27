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

package uidai.aadhaar.resident.options;

import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.resident.BiometricPosition;
import uidai.aadhaar.resident.BiometricType;

import java.util.EnumSet;

public class BiometricOptions {
    private Integer fingerRecordsCount;
    private Integer irisRecordsCount;
    private Integer faceRecordsCount;

    private EnumSet<BiometricType> biometricTypes;
    private EnumSet<BiometricPosition> biometricPositions;

    public Integer getFingerRecordsCount() {
        return fingerRecordsCount;
    }

    public BiometricOptions setFingerRecordsCount(Integer fingerRecordsCount) {
        if (fingerRecordsCount != null && (fingerRecordsCount < 1 || fingerRecordsCount > 10))
            throw new IllegalArgumentException(ErrorMessages.OUT_OF_RANGE_FINGERPRINT_RECORDS);

        this.fingerRecordsCount = fingerRecordsCount;
        return this;
    }

    public Integer getIrisRecordsCount() {
        return irisRecordsCount;
    }

    public BiometricOptions setIrisRecordsCount(Integer irisRecordsCount) {
        if (irisRecordsCount != null && (irisRecordsCount < 1 || irisRecordsCount > 2))
            throw new IllegalArgumentException(ErrorMessages.OUT_OF_RANGE_IRIS_RECORDS);

        this.irisRecordsCount = irisRecordsCount;
        return this;
    }

    public Integer getFaceRecordsCount() {
        return faceRecordsCount;
    }

    public BiometricOptions setFaceRecordsCount(Integer faceRecordsCount) {
        if (faceRecordsCount != null && faceRecordsCount != 1)
            throw new IllegalArgumentException(ErrorMessages.OUT_OF_RANGE_FACE_RECORDS);

        this.faceRecordsCount = faceRecordsCount;
        return this;
    }

    public EnumSet<BiometricType> getBiometricTypes() {
        return biometricTypes;
    }

    public BiometricOptions setBiometricTypes(EnumSet<BiometricType> biometricTypes) {
        this.biometricTypes = biometricTypes;
        return this;
    }

    public EnumSet<BiometricPosition> getBiometricPositions() {
        return biometricPositions;
    }

    public BiometricOptions setBiometricPositions(EnumSet<BiometricPosition> biometricPositions) {
        if (biometricPositions != null && biometricPositions.contains(BiometricPosition.UNKNOWN))
            throw new IllegalArgumentException(ErrorMessages.INVALID_BIOMETRIC_POSITION);

        this.biometricPositions = biometricPositions;
        return this;
    }
}