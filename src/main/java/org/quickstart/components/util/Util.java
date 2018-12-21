package org.quickstart.components.util;

public class Util {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String fieldToGetter(String fieldName) {
        if (Util.isEmpty(fieldName)) return fieldName;
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
