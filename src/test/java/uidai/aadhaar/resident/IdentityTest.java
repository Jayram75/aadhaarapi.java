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

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Identity")
class IdentityTest {
    private Identity identity;

    @BeforeEach
    void setUp() {
        identity = new Identity();
    }

    @Test
    @DisplayName("can set name with length from 0-60 characters or null")
    void setName() {
        assertAll(
            () -> {
                identity.setName("A");
                assertEquals("A", identity.getName());
            },
            () -> {
                identity.setName("");
                assertEquals("", identity.getName());
            },
            () -> {
                identity.setName(null);
                assertEquals(null, identity.getName());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> identity.setName(new String(new char[61])))
        );
    }

    @Test
    @DisplayName("can set age from 1-150 or null")
    void setAge() {
        assertAll(
            () -> {
                identity.setAge(1);
                assertEquals(1, (int) identity.getAge());
            },
            () -> {
                identity.setAge(150);
                assertEquals(150, (int) identity.getAge());
            },
            () -> assertThrows(IllegalArgumentException.class, () -> identity.setAge(0)),
            () -> assertThrows(IllegalArgumentException.class, () -> identity.setAge(151))
        );
    }

    @Test
    @DisplayName("has value if atleast one property is set")
    void getUses() {
        assertAll(
            () -> assertIterableEquals(EnumSet.noneOf(IdentityType.class), identity.getUses()),
            () -> {
                identity.setName("A");
                assertIterableEquals(EnumSet.of(IdentityType.IDENTITY), identity.getUses());
            }
        );
    }
}