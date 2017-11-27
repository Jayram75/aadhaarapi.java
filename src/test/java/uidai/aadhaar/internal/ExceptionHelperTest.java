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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Exception helper")
class ExceptionHelperTest {
    @Test
    @DisplayName("validates percent within 1-100")
    void validatePercent() {
        assertAll(
            () -> ExceptionHelper.validatePercent(1),
            () -> ExceptionHelper.validatePercent(100),
            () -> assertThrows(IllegalArgumentException.class, () -> ExceptionHelper.validatePercent(0)),
            () -> assertThrows(IllegalArgumentException.class, () -> ExceptionHelper.validatePercent(101))
        );
    }

    @Test
    @DisplayName("validates pin with only 6 digits, empty or null")
    void validatePin() {
        assertAll(
            () -> ExceptionHelper.validatePin("123456"),
            () -> ExceptionHelper.validatePin(""),
            () -> ExceptionHelper.validatePin(null),
            () -> assertThrows(IllegalArgumentException.class, () -> ExceptionHelper.validatePin(" ")),
            () -> assertThrows(IllegalArgumentException.class, () -> ExceptionHelper.validatePin("12345")),
            () -> assertThrows(IllegalArgumentException.class, () -> ExceptionHelper.validatePin("123 456"))
        );
    }

    @Test
    @DisplayName("validates empty or null string")
    void validateEmptyString() {
        assertAll(
            () -> ExceptionHelper.validateEmptyString("A", ""),
            () -> assertThrows(IllegalArgumentException.class, () -> ExceptionHelper.validateEmptyString("", "")),
            () -> assertThrows(IllegalArgumentException.class, () -> ExceptionHelper.validateEmptyString(null, ""))
        );
    }

    @Test
    @DisplayName("validates null")
    void validateNull() {
        assertAll(
            () -> ExceptionHelper.validateNull("A", ""),
            () -> assertThrows(IllegalArgumentException.class, () -> ExceptionHelper.validateNull(null, ""))
        );
    }
}