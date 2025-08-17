package com.l3xxd.cos_alpha.utils;

import com.l3xxd.cos_alpha.models.OperatorModel;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static OperatorModel currentUser;

    public static void setUser(OperatorModel user) {
        currentUser = user;
    }

    public static OperatorModel getUser() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}
