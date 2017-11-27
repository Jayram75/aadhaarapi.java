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

package uidai.aadhaar.internal;

import static uidai.aadhaar.generic.AadhaarHelper.isNullOrEmpty;
import static uidai.aadhaar.generic.AadhaarHelper.isValidPin;

public final class ExceptionHelper {
    private ExceptionHelper() {
    }

    public static void validatePercent(int percent) {
        if (percent < 1 || percent > 100)
            throw new IllegalArgumentException(ErrorMessages.OUT_OF_RANGE_MATCH_PERCENT);
    }

    public static void validatePin(CharSequence pin) {
        if (!isNullOrEmpty(pin) && !isValidPin(pin))
            throw new IllegalArgumentException(ErrorMessages.INVALID_PIN);
    }

    public static void validateEmptyString(CharSequence argument, String argumentName) {
        if (isNullOrEmpty(argument))
            throw new IllegalArgumentException(String.format("%s. %s", argumentName, ErrorMessages.REQUIRED_NON_EMPTY_STRING));
    }

    public static <T> void validateNull(T argument, String argumentName) {
        if (argument == null)
            throw new IllegalArgumentException(String.format("%s. %s", argumentName, ErrorMessages.REQUIRED_NON_NULL));
    }

    public static void NotSupported() {
        throw new UnsupportedOperationException(ErrorMessages.NOT_SUPPORTED);
    }
}