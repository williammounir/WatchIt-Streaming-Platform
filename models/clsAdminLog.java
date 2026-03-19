package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import DB.DBConnection;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import session.CurrentAdmin;

public class clsAdminLog {

    private int _Log_ID;
    private int _Session_ID;
    private LocalDateTime _Time;
    private String _idAdmin;
    private enAction _Action;
    private enTargetModel _Target_Type;
    private String _TargetID;
    private String _Description;

    public clsAdminLog(int log_id, int session_id, LocalDateTime time, String idadmin, enAction action,
            enTargetModel target_type, String target_id, String description) {
        _Log_ID = log_id;
        _Session_ID = session_id;
        _Time = time;
        _idAdmin = idadmin;
        _Action = action;
        _Target_Type = target_type;
        _TargetID = target_id;
        _Description = description;
    }

    public static int CurrentSessionID() {

        String sqlScript = "SELECT MAX(Session_ID) FROM adminlog";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sqlScript);
                ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                int maxSessionId = rs.getInt(1);

                // if table is empty, MAX returns NULL
                if (rs.wasNull()) {
                    return 1;
                }

                return maxSessionId + 1;
            }

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean InsertingAdminLog(enAction action, enTargetModel type, String TID, String Description) {
        String InsertScript = "INSERT INTO adminlog (Session_ID, Admin, Action, Target_Type, Target_ID, Description) VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(InsertScript)) {
            ps.setInt(1, CurrentAdmin.Session_id);
            ps.setString(2, CurrentAdmin.getCurrentAdmin().get_UserName());
            ps.setString(3, action.name());
            ps.setString(4, type.name());
            ps.setString(5, TID);
            ps.setString(6, Description);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<clsAdminLog> GetAllLogs() {
        String SqlScript = "SELECT * FROM adminlog";
        List<clsAdminLog> Logs = new ArrayList<>();
        try (
                Connection cn = DBConnection.getConnection();

        ) {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SqlScript);
            while (rs.next()) {
                LocalDateTime time = null;
                Timestamp ts = rs.getTimestamp(3);
                if (ts != null) {
                    time = ts.toLocalDateTime();
                }

                clsAdminLog log = new clsAdminLog(
                        rs.getInt(1), // Log_ID
                        rs.getInt(2), // Session_ID
                        time, // Time
                        rs.getString(4), // Admin ID
                        enAction.valueOf(rs.getString(5)), // Action
                        enTargetModel.valueOf(rs.getString(6)), //Target_Type
                        rs.getString(7), // Target_ID
                        rs.getString(8) // Description
                );

                Logs.add(log);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
        return Logs;

    }

    public int get_Log_ID() {
        return _Log_ID;
    }

    public void set_Log_ID(int _Log_ID) {
        this._Log_ID = _Log_ID;
    }

    public int get_Session_ID() {
        return _Session_ID;
    }

    public void set_Session_ID(int _Session_ID) {
        this._Session_ID = _Session_ID;
    }

    public LocalDateTime get_Time() {
        return _Time;
    }

    public void set_Time(LocalDateTime _Time) {
        this._Time = _Time;
    }

    public String get_idAdmin() {
        return _idAdmin;
    }

    public void set_idAdmin(String _idAdmin) {
        this._idAdmin = _idAdmin;
    }

    public enAction get_Action() {
        return _Action;
    }

    public void set_Action(enAction _Action) {
        this._Action = _Action;
    }

    public enTargetModel get_Target_Type() {
        return _Target_Type;
    }

    public void set_Target_Type(enTargetModel _Target_Type) {
        this._Target_Type = _Target_Type;
    }

    public String get_TargetID() {
        return _TargetID;
    }

    public void set_TargetID(String _TargetID) {
        this._TargetID = _TargetID;
    }

    public String get_Description() {
        return _Description;
    }

    public void set_Description(String _Description) {
        this._Description = _Description;
    }

}
