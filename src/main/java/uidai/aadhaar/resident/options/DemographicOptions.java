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

import uidai.aadhaar.internal.ExceptionHelper;

import java.time.format.DateTimeFormatter;

import static uidai.aadhaar.generic.AadhaarHelper.*;

public class DemographicOptions {
    private MatchType nameMatch = MatchType.EXACT;
    private int nameMatchPercent = MAX_PERCENT;
    private int localNameMatchPercent = MAX_PERCENT;

    private MatchType fullAddressMatch = MatchType.PARTIAL;
    private int fullAddressMatchPercent = MAX_PERCENT;
    private int localFullAddressMatchPercent = MAX_PERCENT;

    private DateOfBirthType dateOfBirthType;
    private boolean shouldVerifyBirthYearOnly;

    public MatchType getNameMatch() {
        return nameMatch;
    }

    public DemographicOptions setNameMatch(MatchType nameMatch) {
        ExceptionHelper.validateNull(nameMatch, "nameMatch");

        this.nameMatch = nameMatch;
        return this;
    }

    public int getNameMatchPercent() {
        return nameMatchPercent;
    }

    public DemographicOptions setNameMatchPercent(int nameMatchPercent) {
        ExceptionHelper.validatePercent(nameMatchPercent);

        this.nameMatchPercent = nameMatchPercent;
        return this;
    }

    public int getLocalNameMatchPercent() {
        return localNameMatchPercent;
    }

    public DemographicOptions setLocalNameMatchPercent(int localNameMatchPercent) {
        ExceptionHelper.validatePercent(localNameMatchPercent);

        this.localNameMatchPercent = localNameMatchPercent;
        return this;
    }

    public MatchType getFullAddressMatch() {
        return fullAddressMatch;
    }

    public DemographicOptions setFullAddressMatch(MatchType fullAddressMatch) {
        ExceptionHelper.validateNull(fullAddressMatch, "fullAddressMatch");

        this.fullAddressMatch = fullAddressMatch;
        return this;
    }

    public int getFullAddressMatchPercent() {
        return fullAddressMatchPercent;
    }

    public DemographicOptions setFullAddressMatchPercent(int fullAddressMatchPercent) {
        ExceptionHelper.validatePercent(fullAddressMatchPercent);

        this.fullAddressMatchPercent = fullAddressMatchPercent;
        return this;
    }

    public int getLocalFullAddressMatchPercent() {
        return localFullAddressMatchPercent;
    }

    public DemographicOptions setLocalFullAddressMatchPercent(int localFullAddressMatchPercent) {
        ExceptionHelper.validatePercent(localFullAddressMatchPercent);

        this.localFullAddressMatchPercent = localFullAddressMatchPercent;
        return this;
    }

    public DateOfBirthType getDateOfBirthType() {
        return dateOfBirthType;
    }

    public DemographicOptions setDateOfBirthType(DateOfBirthType dateOfBirthType) {
        this.dateOfBirthType = dateOfBirthType;
        return this;
    }

    public boolean shouldVerifyBirthYearOnly() {
        return shouldVerifyBirthYearOnly;
    }

    public DemographicOptions setVerifyBirthYearOnly(boolean verifyBirthYearOnly) {
        shouldVerifyBirthYearOnly = verifyBirthYearOnly;
        return this;
    }

    public DateTimeFormatter getBirthDateFormat() {
        return shouldVerifyBirthYearOnly ? BIRTH_YEAR_FORMAT : BIRTH_DATE_FORMAT;
    }
}