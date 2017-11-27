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

import uidai.aadhaar.generic.DataFormat;
import uidai.aadhaar.generic.Environment;
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.resident.ResidentInfo;

import java.util.Map;

public class CaptureRequest extends ApiRequest {
    public static final String DEFAULT_VERSION = "1.0";

    private ResidentInfo residentInfo;
    private String otp;
    private int timeout;
    private Environment environment;
    private DataFormat dataFormat;
    private Map<String, String> customOptions;

    public CaptureRequest() {
        setVersion(DEFAULT_VERSION);
    }

    public ResidentInfo getResidentInfo() {
        return residentInfo;
    }

    public CaptureRequest setResidentInfo(ResidentInfo residentInfo) {
        this.residentInfo = residentInfo;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public CaptureRequest setOtp(String otp) {
        ExceptionHelper.validatePin(otp);

        this.otp = otp;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public CaptureRequest setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public CaptureRequest setEnvironment(Environment environment) {
        this.environment = environment;
        return this;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public CaptureRequest setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
        return this;
    }

    public Map<String, String> getCustomOptions() {
        return customOptions;
    }

    public CaptureRequest setCustomOptions(Map<String, String> customOptions) {
        this.customOptions = customOptions;
        return this;
    }
}