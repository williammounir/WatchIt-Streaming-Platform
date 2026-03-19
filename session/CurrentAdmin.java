package session;

import models.clsAdmin;
import models.clsAdminLog;

public class CurrentAdmin {

    private static clsAdmin currentAdmin = null;
    public static int Session_id = clsAdminLog.CurrentSessionID();
    protected static final  String Mode="Admin";

    public static String getMode() {
        return Mode;
    }

    public static clsAdmin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setCurrentAdmin(clsAdmin user) {
        currentAdmin = user;
    }

    public static void clear() {
        currentAdmin = null; // for logout
    }
}
