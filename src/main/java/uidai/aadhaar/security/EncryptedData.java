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

package uidai.aadhaar.security;

import uidai.aadhaar.generic.DataFormat;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptedData {
    private DataFormat dataFormat;
    private String data;
    private String hmac;

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public EncryptedData setDataFormat(DataFormat dataFrmat) {
        dataFormat = dataFrmat;
        return this;
    }

    public String getData() {
        return data;
    }

    public EncryptedData setData(byte[] data) {
        return setData(data != null ? new String(Base64.getEncoder().encode(data), StandardCharsets.UTF_8) : null);
    }

    public EncryptedData setData(String data) {
        this.data = data;
        return this;
    }

    public String getHmac() {
        return hmac;
    }

    public EncryptedData setHmac(byte[] hmac) {
        return setHmac(hmac != null ? new String(Base64.getEncoder().encode(hmac), StandardCharsets.UTF_8) : null);
    }

    public EncryptedData setHmac(String hmac) {
        this.hmac = hmac;
        return this;
    }
}