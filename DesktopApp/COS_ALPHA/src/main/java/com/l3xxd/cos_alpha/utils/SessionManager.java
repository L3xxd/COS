package com.l3xxd.cos_alpha.utils;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    private static final Map<String, Object> sessionData = new HashMap<>();

    private SessionManager() {
        // Evita instanciación
    }

    public static void set(String key, Object value) {
        sessionData.put(key, value);
    }

    public static Object get(String key) {
        return sessionData.get(key);
    }

    public static <T> T get(String key, Class<T> type) {
        Object value = sessionData.get(key);
        return type.isInstance(value) ? type.cast(value) : null;
    }

    public static void clear() {
        sessionData.clear();
    }

    public static boolean contains(String key) {
        return sessionData.containsKey(key);
    }
}
