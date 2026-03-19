package models;

import DB.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class clsUserWatchRecord {


    private enum enMode  {EmptyMode,UpdateMode,NewMode};
    private enMode _mode ;
    private String _userId;
    private String _movieId;
    private LocalDate _watchDate;
    private Double _rating; // Can be null (optional)


    public clsUserWatchRecord(enMode mode,String userId, String movieId, LocalDate watchDate, Double rating) {
        this._mode = mode;
        this._userId = userId;
        this._movieId = movieId;
        this._watchDate = watchDate;
        this._rating = rating;
    }

    private boolean _Update() {

        String sql = "UPDATE userwatchrecord " +
                "SET WatchDate = ?, Rating = ?" +
                "WHERE UserName = ? and MovieID = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                if(this.getRating() != 0d){
                    ps.setDouble(2, this.getRating());
                }else{
                    ps.setDouble(2,FindWatchRecord(this.getUserId(),this.getMovieId()).getRating());
                }


            ps.setString(3,this.getUserId() );
            ps.setString(4, this.getMovieId());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean insertWatchRecord() {
        String sql = "INSERT INTO userwatchrecord (UserName, MovieID, WatchDate, Rating) VALUES (?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.getUserId());
            ps.setString(2, this.getMovieId());
            ps.setDate(3, java.sql.Date.valueOf(this.getWatchDate()));
            ps.setDouble(4, this.getRating());


            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static List<clsUserWatchRecord> GetWatchRecords() {
        String SqlScript = "SELECT * FROM userwatchrecord";
        List<clsUserWatchRecord> Records = new ArrayList<>();
        try (
                Connection cn = DBConnection.getConnection();

        ) {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SqlScript);
            while (rs.next()) {
                Records.add(new clsUserWatchRecord(enMode.UpdateMode, rs.getString(1), rs.getString(2),
                        rs.getDate(3).toLocalDate(), rs.getDouble(4)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
        return Records;

    }
    public static clsUserWatchRecord GetAddNewObject(String UserName){
        return new clsUserWatchRecord(clsUserWatchRecord.enMode.NewMode,UserName,null,null,null);

    }
    public static clsUserWatchRecord FindWatchRecord(String UserName,String MovieID) {

        List<clsUserWatchRecord> Records = GetWatchRecords();
        for (clsUserWatchRecord r : Records) {
            if (r.getUserId().equals(UserName)&&r.getMovieId().equals(MovieID)) {
                return r;
            }
        }
        return null;
    }

    private boolean isRecordExists(){
        if(FindWatchRecord(this.getUserId(),this.getMovieId()) == null)
            return false;
        return true;
    }

    public static Double getMovieAvarageRating(String ID){
        Double sum = 0d;
        Double count = 0d;
        List<clsUserWatchRecord>records = clsUserWatchRecord.GetWatchRecords();
        for(clsUserWatchRecord record : records){
            if(record.getMovieId().equals(ID)&&record.getRating()!=0d){
                sum+=record.getRating();
                count++;
            }
        }
        return (sum/count);
    }

    public void Save(){
        if(this._mode == enMode.NewMode){
            if(isRecordExists()){
                _Update();
                return;
            }
            insertWatchRecord();
        }
        else if(this._mode == enMode.UpdateMode){
            if(isRecordExists()){
                _Update();
            }
        }
    }

    public static List<clsUserWatchRecord> GetUserWatchRecords(String userName) {

        String sql = "SELECT * FROM userwatchrecord WHERE UserName = ?";
        List<clsUserWatchRecord> records = new ArrayList<>();

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                records.add(
                        new clsUserWatchRecord(
                                enMode.UpdateMode,
                                rs.getString("UserName"),
                                rs.getString("MovieID"),
                                rs.getDate("WatchDate").toLocalDate(),
                                rs.getDouble("Rating")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return records;
    }

    public String getUserId() {
        return _userId;
    }

    public String getMovieId() {
        return _movieId;
    }

    public LocalDate getWatchDate() {
        return _watchDate;
    }

    public Double getRating() {
        return _rating;
    }

    // ===== Setters (no setter for userId because it's like an identity) =====
    public void setMovieId(String movieId) {
        this._movieId = movieId;
    }

    public void setWatchDate(LocalDate watchDate) {
        this._watchDate = watchDate;
    }

    public void setRating(Double rating) {
        this._rating = rating;
    }


    public boolean hasRating() {
        return _rating != null;
    }

    public boolean isValidRating() {
        return _rating == null || (_rating >= 1 && _rating <= 5);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        clsMovie other = (clsMovie) obj;
        return this.getMovieId().equals(other.getId());
    }


}