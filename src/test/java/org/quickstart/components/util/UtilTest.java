package org.quickstart.components.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.quickstart.components.util.Util.isEmpty;

public class UtilTest {

    @Test
    public void isEmpty_WhenEmptyStr() {
        String emptyStr = "";
        assertTrue(isEmpty(emptyStr));
    }

    @Test
    public void isEmpty_WhenEmptyStrWithSpaces() {
        String emptyStr = "   ";
        assertTrue(isEmpty(emptyStr));
    }

    @Test
    public void isEmpty_WhenNull() {
        assertTrue(isEmpty(null));
    }

    @Test
    public void fieldToGetter() {
        String fieldName = "firstName";
        String expectedGetter = "getFirstName";
        assertThat(Util.fieldToGetter(fieldName), equalTo(expectedGetter));
    }

    @Test
    public void fieldToGetter_WhenLikeBoolean() {
        String fieldName = "isManager";
        String expectedGetter = "getIsManager";
        assertThat(Util.fieldToGetter(fieldName), equalTo(expectedGetter));
    }

    @Test
    public void fieldToGetter_WhenEmpty() {
        String fieldName = "";
        String expectedGetter = "";
        assertThat(Util.fieldToGetter(fieldName), equalTo(expectedGetter));
    }
}