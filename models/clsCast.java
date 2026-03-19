package models;

import static Util.clsString.split;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Util.clsString;

public class clsCast extends clsPerson {

    private final static String FileName = "D:\\Watch It - Copy\\Watch It\\src\\models\\Casts.txt";

    private enum enMode {
        EmptyMode, UpdateMode, NewMode
    };

    private enMode _mode;
    private String _ID;
    //CIRCULAR DEPENDANCY
    //private List<String> _MovieIDs; // Store movie IDs instead of objects
    private String _Nationality;
    private String _SocialMediaLink;
    private boolean _Marked_For_Delete = false;

    private static clsCast _ConvertLineToCastRecord(String Line, String Delim) {
        List<String> CastData = new ArrayList<>();
        CastData = clsString.split(Line, Delim);
        //CIRCULAR DEPENDANCY
        //List<String> MoviesIds = split(CastData.get(5), "-");
        return new clsCast(
                enMode.UpdateMode,
                CastData.get(0), // ID
                CastData.get(1), // firstName
                CastData.get(2), // lastName
                LocalDate.parse(CastData.get(3)), // BirthDate
                CastData.get(4), // Gender
                //CIRCULAR DEPENDANCY
                //MoviesIds, // MovieIds
                CastData.get(5), // Nationality
                CastData.get(6)// SocialMediaLink
        );
        // Integer.parseInt(CastData.get(7)));

    }

    private static String _ConvertRecordToCastLine(clsCast Cast) {
        String Line = "";
        Line += Cast.get_ID() + "#//#";
        Line += Cast.get_FirstName() + "#//#";
        Line += Cast.get_LastName() + "#//#";
        Line += Cast.get_BirthDate().toString() + "#//#";
        Line += Cast.get_Gender() + "#//#";
        //CIRCULAR DEPENDANCY
       /* * for(int i = 0 ;i<Cast.getMovieIDs().size();i++){
            Line+=Cast.getMovieIDs().get(i);
            if(i<Cast.getMovieIDs().size()-1)
                Line+='-';
        }
        Line += "#//#";*/
        Line += Cast.getNationality() + "#//#";
        Line += Cast.getSocialMediaLink();
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
        _AppendDataToFile(_ConvertRecordToCastLine(this));
    }

    private static void _SaveCastsDataToFile(List<clsCast> Casts) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {

            boolean firstLine = true;

            for (clsCast cast : Casts) {
                if (!cast._Marked_For_Delete) {

                    if (!firstLine) {
                        writer.newLine();
                    }

                    writer.write(_ConvertRecordToCastLine(cast));
                    firstLine = false;
                }
            }

        } catch (IOException e) {
            System.out.println("Could not write to file: " + FileName);
        }
    }

    private static clsCast _GetEmptyObject() {
        return new clsCast(enMode.EmptyMode, null, null, null, null, null, null, null);
    }

    private void _Update() {

        List<clsCast> Casts = _LoadCastsDataFromFile();

        for (int i = 0; i < Casts.size(); i++) {
            if (this.get_ID().equals(Casts.get(i).get_ID())) {
                Casts.set(i, this);
                break;
            }
        }
        _SaveCastsDataToFile(Casts);
    }

    private static List<clsCast> _LoadCastsDataFromFile() {

        List<clsCast> Casts = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(FileName));

            // Loop from bottom to top
            for (int i = lines.size() - 1; i >= 0; i--) {
                String line = lines.get(i);
                Casts.add(_ConvertLineToCastRecord(line, "#//#"));
            }

        } catch (IOException e) {
            System.out.println("something went wrong\n");
        }

        return Casts;

    }

    // Constructor
    public clsCast(enMode mode, String ID, String firstName, String lastName, LocalDate dateOfBirth,
            String gender, String nationality,
            String socialMediaLink) {
        super(firstName, lastName, dateOfBirth, gender);
        this._mode = mode;
        this._ID = ID;
        //CIRCULAR DEPENDANCY
        /*if (movieIDs != null) {
            this._MovieIDs = movieIDs;
        } else {
            this._MovieIDs = new ArrayList<>();
        }*/
        this._Nationality = nationality;
        this._SocialMediaLink = socialMediaLink;
    }

    public static clsCast FindCast(String Id) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FileName))) {
            String line;
            while ((line = reader.readLine()) != null) {

                clsCast cast = _ConvertLineToCastRecord(line, "#//#");
                if (cast.get_ID().equals(Id))
                    return cast;

            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not locate file\n");

        } catch (IOException e) {
            System.out.println("something went wrong\n");

        }

        return null;

    }

    public static boolean IsCastExists(String Id) {
        if (FindCast(Id) != null)
            return true;
        else
            return false;
    }

    public static clsCast GetAddNewObjectCast(String ID) {
        return new clsCast(enMode.NewMode, ID, null, null, null, null,  null, null);
    }

    public enum enSaveResult {
        SVSUCCEDED,
        SvFailedEmptyObject,
        SvFailedObjectAlreadyExists
    }

    public enSaveResult SaveC() {
        switch (_mode) {
            case EmptyMode:
                return enSaveResult.SvFailedEmptyObject;

            case NewMode:
                if (IsCastExists(_ID))
                    return enSaveResult.SvFailedObjectAlreadyExists;
                else {
                    _ADD();
                    return enSaveResult.SVSUCCEDED;
                }
            case UpdateMode:
                _Update();
                return enSaveResult.SVSUCCEDED;

            default:
                return enSaveResult.SvFailedEmptyObject;

        }
    }

    public boolean DELETEC(){
        List<clsCast> Casts = new ArrayList<>();
        Casts = _LoadCastsDataFromFile();
        for(clsCast cast : Casts){
            if(cast.get_ID().equals(this.get_ID())){
                cast._Marked_For_Delete = true;
                break;

            }
        }
        _SaveCastsDataToFile(Casts);
        return true;
    }

    public void RetriveDeletedCast(){
        clsCast RetrevidCast = new clsCast(_mode.NewMode, _ID, get_FirstName(),
        get_LastName(), get_BirthDate(), get_Gender(), getNationality(), getSocialMediaLink());
        RetrevidCast._ADD();
    }

    // Getters and Setters (NO getter/setter for ID)

    public String get_ID() {
        return _ID;
    }

    //CIRCULAR DEPENDANCY
    /*public List<String> getMovieIDs() {
        return _MovieIDs;
    }

    public void setMovieIDs(List<String> movieIDs) {
        this._MovieIDs = movieIDs;
    }*/

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
