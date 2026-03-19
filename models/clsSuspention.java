package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;



import DB.DBConnection;

public class clsSuspention {

    private String _UserID;
    private String _AdminID;
    
    private LocalDate _StartDate;
    private LocalDate _EndDate;
    private String _Reason;


    private static List<clsSuspention>_GetSuspentionList(){
        List<clsSuspention>SuspentionList = new ArrayList<>();
        String SqlScript = "select * from suspention";
        try(
            Connection con = DBConnection.getConnection();
        ){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SqlScript);
            while(rs.next()){
                SuspentionList.add(new clsSuspention(rs.getString(1), 
                rs.getString(2), rs.getDate(3).toLocalDate(), 
                rs.getDate(4).toLocalDate(), rs.getString(5)));
            }
            return SuspentionList;

        }catch(SQLException e){
            e.getStackTrace();
            return null;
        }

    }

   

    public clsSuspention(String UserId, String AdminId, LocalDate sd, LocalDate ed, String R){
        _UserID = UserId;
        _AdminID = AdminId;
        _StartDate = sd;
        _EndDate = ed;
        _Reason = R;
    }

    public boolean SuspendUser(){
            String sql = "INSERT INTO suspention (idUser, idAdmin, StartDate, EndDate, Reason) VALUES (?, ?, ?, ?, ?)";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {
        ps.setString(1, this.get_UserID());
        ps.setString(2, this.get_AdminID());
        ps.setDate(3, java.sql.Date.valueOf(this.get_StartDate()));
        ps.setDate(4, java.sql.Date.valueOf(this.get_EndDate()));
        ps.setString(5, this.getReason());


        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    }

    public static boolean WithdrawSusbention(String UserID){
      String WithdrawScript = "delete from suspention where idUser = ? ";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(WithdrawScript);
    ) {
        ps.setString(1, UserID);


        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }

    }

    public static clsSuspention FindSuspenstion(String UserID){
        List<clsSuspention>SuspentionList = _GetSuspentionList();
        for(clsSuspention s : SuspentionList){
            if(s.get_UserID().equals(UserID))
                return s;
        }
        return null;
    }

    public static void AutomaticDeletionOfSuspentionsWhenPeriodPass(){

        String DeleteScript = "DELETE FROM suspention WHERE EndDate <= ?";
        try(
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(DeleteScript);
        ){     
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();
        }catch(SQLException e){
            e.getStackTrace();
            return;
        }
    }

    public static boolean IsUserSusbended(String UserID){
        if(FindSuspenstion(UserID)!=null)
            return true;
        else
            return false;
    }

    public String get_UserID() {
        return _UserID;
    }
    public void set_UserID(String _UserID) {
        this._UserID = _UserID;
    }
    public String get_AdminID() {
        return _AdminID;
    }

    public void set_AdminID(String _AdminID) {
        this._AdminID = _AdminID;
    }
    public LocalDate get_StartDate() {
        return _StartDate;
    }
    public void set_StartDate(LocalDate _StartDate) {
        this._StartDate = _StartDate;
    }
    public LocalDate get_EndDate() {
        return _EndDate;
    }
    public void set_EndDate(LocalDate _EndDate) {
        this._EndDate = _EndDate;
    }
    public String getReason() {
        return _Reason;
    }
    public void setReason(String reason) {
        _Reason = reason;
    }

}
