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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Demographic options")
class DemographicOptionsTest {
    private DemographicOptions demographicOptions;

    @BeforeEach
    void setUp() {
        demographicOptions = new DemographicOptions();
    }

    @Test
    @DisplayName("can set only non-null name match")
    void setNameMatch() {
        assertAll(
            () -> {
                demographicOptions.setNameMatch(MatchType.EXACT);
                assertEquals(demographicOptions.getNameMatch(), MatchType.EXACT);
            },
            () -> assertThrows(IllegalArgumentException.class, () -> demographicOptions.setNameMatch(null))
        );
    }

    @Test
    @DisplayName("can set name match percent from 1-100")
    void setNameMatchPercent() {
        assertAll(
            () -> {
                demographicOptions.setNameMatchPercent(1);
                assertEquals(1, demographicOptions.getNameMatchPercent());
            },
            () -> {
                demographicOptions.setNameMatchPercent(100);
                assertEquals(100, demographicOptions.getNameMatchPercent());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> demographicOptions.setNameMatchPercent(0)),
            () -> assertThrows(IllegalArgumentException.class, () -> demographicOptions.setNameMatchPercent(101))
        );
    }

    @Test
    @DisplayName("can set local name match percent from 1-100")
    void setLocalNameMatchPercent() {
        assertAll(
            () -> {
                demographicOptions.setLocalNameMatchPercent(1);
                assertEquals(1, demographicOptions.getLocalNameMatchPercent());
            },
            () -> {
                demographicOptions.setLocalNameMatchPercent(100);
                assertEquals(100, demographicOptions.getLocalNameMatchPercent());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> demographicOptions.setLocalNameMatchPercent(0)),
            () -> assertThrows(IllegalArgumentException.class, () -> demographicOptions.setLocalNameMatchPercent(101))
        );
    }


    @Test
    @DisplayName("can set only non-null full address match")
    void setFullAddressMatch() {
        assertAll(
            () -> {
                demographicOptions.setFullAddressMatch(MatchType.EXACT);
                assertEquals(demographicOptions.getFullAddressMatch(), MatchType.EXACT);
            },
            () -> assertThrows(IllegalArgumentException.class, () -> demographicOptions.setFullAddressMatch(null))
        );
    }

    @Test
    @DisplayName("can set full address match percent from 1-100")
    void setFullAddressMatchPercent() {
        assertAll(
            () -> {
                demographicOptions.setFullAddressMatchPercent(1);
                assertEquals(1, demographicOptions.getFullAddressMatchPercent());
            },
            () -> {
                demographicOptions.setFullAddressMatchPercent(100);
                assertEquals(100, demographicOptions.getFullAddressMatchPercent());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> demographicOptions.setFullAddressMatchPercent(0)),
            () -> assertThrows(IllegalArgumentException.class, () -> demographicOptions.setFullAddressMatchPercent(101))
        );
    }

    @Test
    @DisplayName("can set local full address match percent from 1-100")
    void setLocalFullAddressMatchPercent() {
        assertAll(
            () -> {
                demographicOptions.setLocalFullAddressMatchPercent(1);
                assertEquals(1, demographicOptions.getLocalFullAddressMatchPercent());
            },
            () -> {
                demographicOptions.setLocalFullAddressMatchPercent(100);
                assertEquals(100, demographicOptions.getLocalFullAddressMatchPercent());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> demographicOptions.setLocalFullAddressMatchPercent(0)),
            () -> assertThrows(IllegalArgumentException.class, () -> demographicOptions.setLocalFullAddressMatchPercent(101))
        );
    }
}