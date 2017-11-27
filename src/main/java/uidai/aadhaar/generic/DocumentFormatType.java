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

import java.util.Arrays;

public enum DocumentFormatType implements CustomEnumValue<String> {
    PDF("pdf");

    private final String value;

    DocumentFormatType(String value) {
        this.value = value;
    }

    public static DocumentFormatType fromValue(String value) {
        return Arrays.stream(DocumentFormatType.values())
            .filter(d -> d.value.equalsIgnoreCase(value))
            .findFirst()
            .orElse(null);
    }

    public String value() {
        return value;
    }
}