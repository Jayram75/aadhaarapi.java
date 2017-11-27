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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class ApiInfo {
    private static final MessageDigest digest;

    static {
        MessageDigest d = null;
        try {
            d = MessageDigest.getInstance("SHA-256");
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            digest = d;
        }
    }

    private String info;

    public String getInfo() {
        return info;
    }

    public ApiInfo setInfo(String info) {
        this.info = info;
        return this;
    }

    public abstract void encode();

    public abstract void decode();

    public byte[] infoDigest() {
        return digest.digest(info.getBytes(StandardCharsets.UTF_8));
    }
}