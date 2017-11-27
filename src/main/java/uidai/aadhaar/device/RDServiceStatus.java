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

import uidai.aadhaar.generic.CustomEnumValue;

import java.util.Arrays;

public enum RDServiceStatus implements CustomEnumValue<String> {
    ACTIVE("ACTIVE"),
    SUSPENDED("SUSPENDED"),
    ONBOARDED("ONBOARDED"),
    DEPRECATED("DEPRECATED");

    private final String value;

    RDServiceStatus(String value) {
        this.value = value;
    }

    public static RDServiceStatus fromValue(String value) {
        return Arrays.stream(RDServiceStatus.values())
            .filter(r -> r.name().equalsIgnoreCase(value))
            .findFirst()
            .orElse(null);
    }

    public String value() {
        return value;
    }
}