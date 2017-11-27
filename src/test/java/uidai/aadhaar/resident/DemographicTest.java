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

package uidai.aadhaar.resident;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@DisplayName("Demographic")
class DemographicTest {
    private Demographic demographic;

    @BeforeEach
    void setUp() {
        demographic = new Demographic()
            .setIdentity(new Identity())
            .setAddress(new Address())
            .setFullAddress(new FullAddress());
    }

    @Test
    @DisplayName("has value if atleast one property is set")
    void getUses() {
        assertAll(
            () -> assertIterableEquals(EnumSet.noneOf(IdentityType.class), demographic.getUses()),
            () -> {
                demographic.getIdentity().setName("A");
                assertIterableEquals(EnumSet.of(IdentityType.DEMOGRAPHIC, IdentityType.IDENTITY), demographic.getUses());
            }
        );
    }
}