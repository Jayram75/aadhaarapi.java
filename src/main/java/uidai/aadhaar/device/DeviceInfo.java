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

package uidai.aadhaar.device;

import uidai.aadhaar.internal.ErrorMessages;

import java.util.Map;

/**
 * Represents information of device used to capture and encrypt resident data. Host device code is
 * mandatory in all type of API transactions. Registered device information is required when using
 * registered device.
 */
public class DeviceInfo {
    private String hostDeviceCode;
    private String providerCode;
    private String serviceCode;
    private String serviceVersion;
    private String modelCode;
    private String deviceCode;
    private String publicKey;
    private Map<String, String> additionalInfo;

    /**
     * Gets a unique host device code (udc).
     *
     * @return A host device code.
     */
    public String getHostDeviceCode() {
        return hostDeviceCode;
    }

    /**
     * Sets a unique host device code (udc).
     *
     * @param hostDeviceCode A host device code.
     * @return This object.
     * @throws IllegalArgumentException If host device code is greater than 20 characters.
     */
    public DeviceInfo setHostDeviceCode(String hostDeviceCode) {
        if (hostDeviceCode != null && hostDeviceCode.length() > 20)
            throw new IllegalArgumentException(ErrorMessages.INVALID_HOST_DEVICE_CODE);

        this.hostDeviceCode = hostDeviceCode;
        return this;
    }

    /**
     * Gets the unique code (dpId) assigned to registered device provider.
     *
     * @return A device provider code.
     */
    public String getProviderCode() {
        return providerCode;
    }

    /**
     * Sets the unique code (dpId) assigned to registered device provider.
     *
     * @param providerCode A device provider code.
     * @return This object.
     */
    public DeviceInfo setProviderCode(String providerCode) {
        this.providerCode = providerCode;
        return this;
    }

    /**
     * Gets the unique code (rdsId) of the certified registered device service.
     *
     * @return A registered service code.
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * Sets the unique code (rdsId) of the certified registered device service.
     *
     * @param serviceCode A registered service code.
     * @return This object.
     */
    public DeviceInfo setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    /**
     * Gets the service version (rdsVer) of the registered device.
     *
     * @return A registered service version.
     */
    public String getServiceVersion() {
        return serviceVersion;
    }

    /**
     * Sets the service version (rdsVer) of the registered device.
     *
     * @param serviceVersion A registered service version.
     * @return This object.
     */
    public DeviceInfo setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
        return this;
    }

    /**
     * Gets the model code (mi) of the registered device.
     *
     * @return A device model code.
     */
    public String getModelCode() {
        return modelCode;
    }

    /**
     * Sets the model code (mi) of the registered device.
     *
     * @param modelCode A device model code.
     * @return This object.
     */
    public DeviceInfo setModelCode(String modelCode) {
        this.modelCode = modelCode;
        return this;
    }

    /**
     * Gets the device code (dc) of the registered device.
     *
     * @return A device code.
     */
    public String getDeviceCode() {
        return deviceCode;
    }

    /**
     * Sets the device code (dc) of the registered device.
     *
     * @param deviceCode A device code.
     * @return This object.
     */
    public DeviceInfo setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
        return this;
    }

    /**
     * Gets the public key certificate (mi) of the registered device signed with device provider
     * key.
     *
     * @return A public key.
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Sets the public key certificate (mi) of the registered device signed with device provider
     * key.
     *
     * @param publicKey A public key.
     * @return This object.
     */
    public DeviceInfo setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    /**
     * Gets additional device info required to pass back to host application.
     *
     * @return A map of additional device information.
     */
    public Map<String, String> getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets additional device info required to pass back to host application. The data passed
     * through this MUST NOT violate UIDAI policies and device providers must take care in defining
     * this to ensure no sensitive data is passed.
     *
     * @param additionalInfo A map of additional device information.
     * @return This object.
     */
    public DeviceInfo setAdditionalInfo(Map<String, String> additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }
}