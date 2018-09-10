package org.quickstart.components.util;

public class Util {
    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static String fieldToGetter(String name) {
        if (Util.isEmpty(name)) return name;
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
