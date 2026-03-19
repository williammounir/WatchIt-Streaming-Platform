package UI.Admin.Admin_Functions;

import java.time.LocalDate;
import java.util.List;

import UI.Common.clsScreen;
import Util.Pause;
import models.clsAdminLog;

public class AdminsActivityLog extends clsScreen {

    // Fixed-width format for rows
    private static final String LOG_FORMAT =
        "%-6d | %-7d | %-19s | %-6s | %-14s | %-10s | %-8s | %s%n";

    public static void ShowAdminsActivityLogScreen() {


        DrawScreenHeader("ADMINS ACTIVITY LOG", null);

        System.out.println("Mode : Admin");
        System.out.println("Date : " + LocalDate.now());
        System.out.println();

        PrintHeader();

        List<clsAdminLog> logs = clsAdminLog.GetAllLogs();

        if (logs == null || logs.isEmpty()) {
            System.out.println("No activity logs found.");
        } else {
            for (clsAdminLog log : logs) {
                PrintLog(log);
            }
        }

        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("Total Logs : " + (logs == null ? 0 : logs.size()));
        System.out.println();

        Pause.pause("Press any key to return...");
    }

    private static void PrintHeader() {
        System.out.printf(
            "%-6s | %-7s | %-19s | %-6s | %-14s | %-10s | %-8s | %s%n",
            "LogID", "Session", "Time", "Admin", "Action", "Target", "TargetID", "Description"
        );
        System.out.println(
            "-----------------------------------------------------------------------------------------------");
    }

    private static void PrintLog(clsAdminLog log) {
        System.out.printf(
            LOG_FORMAT,
            log.get_Log_ID(),
            log.get_Session_ID(),
            log.get_Time(),
            log.get_idAdmin(),
            log.get_Action(),
            log.get_Target_Type(),
            log.get_TargetID(),
            log.get_Description()
        );
    }
}
