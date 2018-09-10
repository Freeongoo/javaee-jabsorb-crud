package org.quickstart.components.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void isEmpty_WhenEmptyStr() {
        String emptyStr = "";
        assertTrue(Util.isEmpty(emptyStr));
    }

    @Test
    public void isEmpty_WhenEmptyStrWithSpaces() {
        String emptyStr = "   ";
        assertTrue(Util.isEmpty(emptyStr));
    }

    @Test
    public void isEmpty_WhenNull() {
        String emptyStr = null;
        assertTrue(Util.isEmpty(emptyStr));
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