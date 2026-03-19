package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DB.DBConnection;

import static Util.clsString.split;

public class clsDirector extends clsPerson {

    private final static String FileName = "D:\\Watch It - Copy\\Watch It\\src\\models\\Directors.txt";

    private enum enMode {
        EmptyMode, UpdateMode, NewMode
    };

    private enMode _mode;

    private String _ID;
    // private List<String> _MovieIDs; // Store movie IDs instead of movie objects
    private String _Nationality;
    private String _SocialMediaLink;
    private boolean _Marked_For_Delete = false;

    private static clsDirector _ConvertLineToDirectorRecord(String Line, String Delim) {
        List<String> DirectorData = new ArrayList<>();
        DirectorData = split(Line, Delim);
        // List<String> Movies = split(DirectorData.get(5),"-");
        return new clsDirector(
                enMode.UpdateMode,
                DirectorData.get(0), // ID
                DirectorData.get(1), // firstName
                DirectorData.get(2), // lastName
                LocalDate.parse(DirectorData.get(3)), // BirthDate
                DirectorData.get(4), // Gender
                // Movies, // Movies
                DirectorData.get(5), // Country
                DirectorData.get(6)); // Social media

    }

    private static String _ConvertRecordToDirectorLine(clsDirector Director) {
        String Line = "";
        Line += Director.get_ID() + "#//#";
        Line += Director.get_FirstName() + "#//#";
        Line += Director.get_LastName() + "#//#";
        Line += Director.get_BirthDate().toString() + "#//#";
        Line += Director.get_Gender() + "#//#";
        Line += Director.getNationality() + "#//#";
        Line += Director.getSocialMediaLink();
        return Line;
    }

    private static void _AppendDataToFile(String Line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName, true))) {
            writer.newLine(); // VERY important
            writer.write(Line);
        } catch (IOException e) {
            System.out.println("Could not write to file: " + FileName);
        }
    }

    private void _ADD() {
        _AppendDataToFile(_ConvertRecordToDirectorLine(this));
    }

    private static void _SaveDirectorsDataToFile(List<clsDirector> Directors) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {

            boolean firstLine = true;

            for (clsDirector Director : Directors) {
                if (!Director._Marked_For_Delete) {

                    if (!firstLine) {
                        writer.newLine();
                    }

                    writer.write(_ConvertRecordToDirectorLine(Director));
                    firstLine = false;
                }
            }

        } catch (IOException e) {
            System.out.println("Could not write to file: " + FileName);
        }
    }

    private static clsDirector _GetEmptyObject() {
        return new clsDirector(enMode.EmptyMode, null, null, null, null, null, null, null);
    }

    private static List<clsDirector> _LoadDirectorsDataFromFile() {

        List<clsDirector> Directors = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(FileName));

            // Loop from bottom to top
            for (int i = lines.size() - 1; i >= 0; i--) {
                String line = lines.get(i);
                Directors.add(_ConvertLineToDirectorRecord(line, "#//#"));
            }

        } catch (IOException e) {
            System.out.println("something went wrong\n");
        }

        return Directors;

    }

    private boolean _Update() {

        String sql = "UPDATE director " +
                "SET FirstName = ?, LasName = ?, BirthDate = ?, Gender = ?, Nationality = ?, Email = ? " +
                "WHERE idDirector = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.get_FirstName());
            ps.setString(2, this.get_LastName());
            ps.setDate(3, java.sql.Date.valueOf(this.get_BirthDate()));
            ps.setString(4, this.get_Gender());
            ps.setString(5, this.getNationality());
            ps.setString(6, this.getSocialMediaLink());
            ps.setString(7, this.get_ID());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static List<clsDirector> GetAllDirectors() {
        String SqlScript = "SELECT * FROM director";
        List<clsDirector> Directors = new ArrayList<>();
        try (
                Connection cn = DBConnection.getConnection();

        ) {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SqlScript);
            while (rs.next()) {
                Directors.add(new clsDirector(enMode.UpdateMode, rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getDate(4).toLocalDate(),
                        rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
        return Directors;

    }

    // Constructor
    public clsDirector(enMode mode, String ID, String firstName, String lastName, LocalDate birthDate,
            String gender, String nationality, String socialMediaLink) {

        super(firstName, lastName, birthDate, gender);
        this._mode = mode;
        this._ID = ID;
        this._Nationality = nationality;
        this._SocialMediaLink = socialMediaLink;
    }

    public static clsDirector FindDirector(String ID) {

        List<clsDirector> Directors = GetAllDirectors();
        for (clsDirector d : Directors) {
            if (d.get_ID().equals(ID)) {
                return d;
            }
        }
        return null;
    }

    public static boolean IsDirectorExists(String ID) {
        if (FindDirector(ID) != null)
            return true;
        else
            return false;
    }

    public static clsDirector GetAddNewObjectDirector(String ID) {
        return new clsDirector(enMode.NewMode, ID, null, null, null, null, null, null);
    }

    public boolean insertDirector() {
        String sql = "INSERT INTO director (idDirector, FirstName, LasName, BirthDate, Gender, Nationality, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.get_ID());
            ps.setString(2, this.get_FirstName());
            ps.setString(3, this.get_LastName());
            ps.setDate(4, java.sql.Date.valueOf(this.get_BirthDate()));
            ps.setString(5, this.get_Gender());
            ps.setString(6, this.getNationality());
            ps.setString(7, this.getSocialMediaLink());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean DELETED() {
        String SqlScript = "delete from director where idDirector = ? ";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(SqlScript);) {
            ps.setString(1, this.get_ID());
            int r = ps.executeUpdate();
            return r > 0;

        } catch (SQLException e) {
            e.getStackTrace();
            return false;
        }

    }

    public enum enSaveResult {
        SVSUCCEDED,
        SvFailedEmptyObject,
        SvFailedObjectAlreadyExists,
        SvFailedInvalidInsertation,
        SvFailedInvalidUpdate
    }

    public enSaveResult SaveD() {
        switch (_mode) {
            case EmptyMode:
                return enSaveResult.SvFailedEmptyObject;

            case NewMode:
                if (IsDirectorExists(_ID))
                    return enSaveResult.SvFailedObjectAlreadyExists;
                else {
                    // _ADD();

                    if (insertDirector()) {
                        return enSaveResult.SVSUCCEDED;
                    } else
                        return enSaveResult.SvFailedInvalidInsertation;

                }
            case UpdateMode:
                if (_Update()) {
                    return enSaveResult.SVSUCCEDED;
                } else {
                    return enSaveResult.SvFailedInvalidUpdate;
                }

            default:
                return enSaveResult.SvFailedEmptyObject;

        }
    }
    public void RetriveDeletedDirector(){
        clsDirector RetrevedDirector = new clsDirector(_mode.NewMode, _ID, get_FirstName(),
        get_LastName(), get_BirthDate(), get_Gender(), getNationality(), getSocialMediaLink());
        RetrevedDirector._ADD();
    }

    // Getters and Setters (NO getter/setter for ID)

    public String get_ID() {
        return _ID;
    }

    public enMode get_mode() {
        return _mode;
    }

    public String getNationality() {
        return _Nationality;
    }

    public void setNationality(String nationality) {
        this._Nationality = nationality;
    }

    public String getSocialMediaLink() {
        return _SocialMediaLink;
    }

    public void setSocialMediaLink(String socialMediaLink) {
        this._SocialMediaLink = socialMediaLink;
    }
}
