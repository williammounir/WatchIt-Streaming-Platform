package session;

import models.clsUser;

public class CurrentUser {

    private static clsUser currentUser = null;
    protected static final String Mode="User";

    public static String getMode() {
        return Mode;
    }

    public static clsUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(clsUser user) {
        currentUser = user;
    }

    public static void clear() {
        currentUser = null; // for logout
    }
}

