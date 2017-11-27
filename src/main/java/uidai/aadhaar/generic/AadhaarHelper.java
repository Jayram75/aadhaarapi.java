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

import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.resident.BiometricPosition;
import uidai.aadhaar.resident.BiometricType;
import uidai.aadhaar.resident.IdentityType;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Provides helper methods required throughout the API pipeline.
 */
public final class AadhaarHelper {
    public static final int MAX_PERCENT = 100;

    public static final String TRANSACTION_ID_PATTERN = "[a-zA-Z0-9.,-\\\\/():]*";

    public static final DateTimeFormatter BIRTH_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter BIRTH_YEAR_FORMAT = DateTimeFormatter.ofPattern("yyyy");
    public static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateTimeFormatter TRANSACTION_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");

    private static final int[][] MULTIPLICATION = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 2, 3, 4, 0, 6, 7, 8, 9, 5},
        {2, 3, 4, 0, 1, 7, 8, 9, 5, 6},
        {3, 4, 0, 1, 2, 8, 9, 5, 6, 7},
        {4, 0, 1, 2, 3, 9, 5, 6, 7, 8},
        {5, 9, 8, 7, 6, 0, 4, 3, 2, 1},
        {6, 5, 9, 8, 7, 1, 0, 4, 3, 2},
        {7, 6, 5, 9, 8, 2, 1, 0, 4, 3},
        {8, 7, 6, 5, 9, 3, 2, 1, 0, 4},
        {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
    };

    private static final int[][] PERMUTATION = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 5, 7, 6, 2, 8, 3, 0, 9, 4},
        {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
        {8, 9, 1, 6, 0, 4, 3, 5, 2, 7},
        {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
        {4, 2, 8, 6, 5, 7, 3, 9, 0, 1},
        {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
        {7, 0, 4, 6, 9, 1, 3, 2, 5, 8}
    };

    private AadhaarHelper() {
    }

    /**
     * Checks if an Aadhaar number is 12 digits and Verhoeff compliant or not.
     *
     * @param aadhaarNumber An Aadhaar number.
     * @return True if the number is valid; otherwise, false.
     */
    public static boolean isValidAadhaarNumber(CharSequence aadhaarNumber) {
        if (aadhaarNumber == null || !Pattern.matches("\\d{12}", aadhaarNumber))
            return false;

        final List<Integer> digits = new ArrayList<>(12);
        for (int i = aadhaarNumber.length() - 1; i >= 0; i--)
            digits.add(aadhaarNumber.charAt(i) - '0');

        int checksum = 0;
        for (int i = 0; i < digits.size(); i++)
            checksum = MULTIPLICATION[checksum][PERMUTATION[i % 8][digits.get(i)]];

        return checksum == 0;
    }

    /**
     * Checks if PIN number is 6 digits long or not.
     *
     * @param pin A PIN number
     * @return True if the number is valid; otherwise, false.
     */
    public static boolean isValidPin(CharSequence pin) {
        return pin != null && Pattern.matches("\\d{6}", pin);
    }

    /**
     * Checks if a string is null or empty.
     *
     * @param value A string.
     * @return True if the string is null or empty; otherwise, false.
     */
    public static boolean isNullOrEmpty(CharSequence value) {
        return value == null || value.length() == 0;
    }

    /**
     * Gets a collection of corresponding biometric types from a collection of biometric positions.
     *
     * @param positions A collection of biometric positions.
     * @return A collection of biometric types.
     */
    public static Collection<BiometricType> getBiometricTypesFromPosition(EnumSet<BiometricPosition> positions) {
        ExceptionHelper.validateNull(positions, "positions");

        final EnumSet<BiometricType> types = EnumSet.noneOf(BiometricType.class);
        for (final BiometricPosition position : positions) {
            switch (position) {
                case LEFT_INDEX:
                case LEFT_LITTLE:
                case LEFT_MIDDLE:
                case LEFT_RING:
                case LEFT_THUMB:
                case RIGHT_INDEX:
                case RIGHT_LITTLE:
                case RIGHT_MIDDLE:
                case RIGHT_RING:
                case RIGHT_THUMB:
                    Collections.addAll(types, BiometricType.FINGERPRINT, BiometricType.MINUTIAE);
                    break;

                case LEFT_IRIS:
                case RIGHT_IRIS:
                    types.add(BiometricType.IRIS);
                    break;

                case FACE:
                    types.add(BiometricType.FACE);
                    break;
                case UNKNOWN:
                    break;
            }
        }
        return types;
    }

    /**
     * Gets yes/no character representation in upper case from a boolean value
     *
     * @param value A boolean value.
     * @return A yes/no character representation.
     */
    public static char yesNo(boolean value) {
        return value ? 'Y' : 'N';
    }

    /**
     * Gets boolean value from a yes/no character representation.
     *
     * @param value A yes/no character representation.
     * @return A boolean value.
     */
    public static boolean yesNo(char value) {
        return value == 'Y';
    }

    /**
     * Gets a string representation of the specified authentication types. The string contains
     * "FIOP" characters or any combination of these. For example, if resident authentication uses
     * fingerprints, then this should be "F", if both fingerprint and OTP are used this should be
     * "FO", and so on. The values represents:
     * <p>
     * F = Fingerprint / Minutiae
     * <p>
     * I = Iris
     * <p>
     * O = OTP
     * <p>
     * P = Photo
     *
     * @return A string representation of the authentication types.
     */
    public static String getAuthType(EnumSet<IdentityType> uses) {
        ExceptionHelper.validateNull(uses, "uses");

        final StringBuilder builder = new StringBuilder(4);
        if (uses.contains(IdentityType.FINGERPRINT) || uses.contains(IdentityType.MINUTIAE))
            builder.append('F');
        if (uses.contains(IdentityType.IRIS))
            builder.append('I');
        if (uses.contains(IdentityType.OTP))
            builder.append('O');
        if (uses.contains(IdentityType.FACE))
            builder.append('P');
        return builder.toString();
    }

    /**
     * Generates a new transaction identifier without a namespace from the current system
     * date-time.
     *
     * @return A string that represents a transaction identifier.
     */
    public static String newTransactionId() {
        return newTransactionId("");
    }

    /**
     * Generates a new transaction identifier with a namespace from the current system date-time.
     *
     * @param namespace A namespace to prefix to the generated transaction identifier.
     * @return A string that represents a transaction identifier.
     */
    public static String newTransactionId(CharSequence namespace) {
        return String.format("%s%s", isNullOrEmpty(namespace) ? "" : namespace, OffsetDateTime.now().format(TRANSACTION_FORMAT));
    }
}