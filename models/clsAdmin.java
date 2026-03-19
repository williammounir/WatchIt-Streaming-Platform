package models;

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
import static Util.clsString.split;

public class clsAdmin extends clsPerson {

    private final static String FileName = "D:\\Watch It - Copy\\Watch It\\src\\models\\Admins.txt";

    private enum enMode {
        EmptyMode, UpdateMode, NewMode
    };

    private enMode _mode;
    private String _UserName;
    private String _Password;
    private String _Email;
    private int _Permissions;
    private boolean _Marked_For_Delete = false;

    private static clsAdmin _ConvertLineToAdminRecord(String Line, String Delim) {
        List<String> AdminData = new ArrayList<>();
        AdminData = split(Line, Delim);
        return new clsAdmin(
                enMode.UpdateMode,
                AdminData.get(0), // ID
                AdminData.get(1), // firstName
                AdminData.get(2), // lastName
                LocalDate.parse(AdminData.get(3)), // BirthDate
                AdminData.get(4), // Gender
                AdminData.get(5), // password
                AdminData.get(6), // Email
                Integer.parseInt(AdminData.get(7)));

    }

    private static List<clsAdmin> _LoadAdminssDataFromFile() {

        List<clsAdmin> Admins = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(FileName));

            // Loop from bottom to top
            for (int i = lines.size() - 1; i >= 0; i--) {
                String line = lines.get(i);
                Admins.add(_ConvertLineToAdminRecord(line, "#//#"));
            }

        } catch (IOException e) {
            System.out.println("something went wrong\n");
        }

        return Admins;

    }

    private static String _ConvertRecordToAdminLine(clsAdmin Admin) {
        String Line = "";
        Line += Admin.get_UserName() + "#//#";
        Line += Admin.get_FirstName() + "#//#";
        Line += Admin.get_LastName() + "#//#";
        Line += Admin.get_BirthDate().toString() + "#//#";
        Line += Admin.get_Gender() + "#//#";
        Line += Admin.get_Password() + "#//#";
        Line += Admin.get_Email() + "#//#";
        Line += Admin.get_Permissions();
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
        _AppendDataToFile(_ConvertRecordToAdminLine(this));
    }

 private static void _SaveAdminDataToFile(List<clsAdmin> admins) {

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {

        boolean firstLine = true;

        for (clsAdmin admin : admins) {
            if (!admin._Marked_For_Delete) {

                if (!firstLine) {
                    writer.newLine();
                }

                writer.write(_ConvertRecordToAdminLine(admin));
                firstLine = false;
            }
        }

    } catch (IOException e) {
        System.out.println("Could not write to file: " + FileName);
    }
}

    private static clsAdmin _GetEmptyObject(){
        return new clsAdmin(enMode.EmptyMode, null, null, null, null, null, null, null, 0);
    }

  private void _Update() {

    List<clsAdmin> admins = _LoadAdminssDataFromFile();

    for (int i = 0; i < admins.size(); i++) {
        if (this.get_UserName().equals(admins.get(i).get_UserName())) {
            admins.set(i, this);
            break;
        }
    }

    _SaveAdminDataToFile(admins);
    }


    public clsAdmin(enMode mode, String UserName, String FirstName, String lastName, LocalDate BirthDate, String Gender,
            String Password, String Email, int Permissions) {
        super(FirstName, lastName, BirthDate, Gender);
        _mode = mode;
        _UserName = UserName;
        _Password = Password;
        _Email = Email;
        _Permissions = Permissions;
    }

    public static clsAdmin FindAdmin(String UserName, String Password) {

        // when you add bufferreader to try ->try(bufferredreader...)
        // it closes once it finishs you don't have to close manualy it is called TRY
        // WITH RESORCES (it closes even
        // when hitting a exception or a return)
        try (BufferedReader reader = new BufferedReader(new FileReader(FileName))) {
            String line;
            while ((line = reader.readLine()) != null) {

                clsAdmin admin = _ConvertLineToAdminRecord(line, "#//#");
                if (admin.get_UserName().equals(UserName) && admin.get_Password().equals(Password))
                    return admin;

            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not locate file\n");

        } catch (IOException e) {
            System.out.println("something went wrong\n");

        }

        return null;

    }

    public static clsAdmin FindAdmin(String UserName) {

        // when you add bufferreader to try ->try(bufferredreader...)
        // it closes once it finishs you don't have to close manualy it is called TRY
        // WITH RESORCES (it closes even
        // when hitting a exception or a return)
        try (BufferedReader reader = new BufferedReader(new FileReader(FileName))) {
            String line;
            while ((line = reader.readLine()) != null) {

                clsAdmin admin = _ConvertLineToAdminRecord(line, "#//#");
                if (admin.get_UserName().equals(UserName))
                    return admin;

            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not locate file\n");

        } catch (IOException e) {
            System.out.println("something went wrong\n");

        }

        return null;

    }

    public static boolean IsAdminExists(String UserName) {
        if (FindAdmin(UserName) != null)
            return true;
        else
            return false;
    }
    // public clsAdmin(enMode mode,String UserName,String FirstName,String lastName
    // ,LocalDate BirthDate,String Gender,String Password,String Email, int
    // Permissions)

    public static clsAdmin GetAddNewObjectAdmin(String UserName) {
        return new clsAdmin(enMode.NewMode, UserName, null, null, null, null, null, null, -1);
    }

    public enum enSaveResult {
        SVSUCCEDED,
        SvFailedEmptyObject,
        SvFailedObjectAlreadyExists,
        SvLogStoringFailed
    }

    public enSaveResult SaveA() {
        switch (_mode) {
            case EmptyMode:
                return enSaveResult.SvFailedEmptyObject;

            case NewMode:
                if (IsAdminExists(_UserName))
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



    public boolean DELETEA(){
        List<clsAdmin> Admins = new ArrayList<>();
        Admins = _LoadAdminssDataFromFile();
        for(clsAdmin admin : Admins){
            if(admin._UserName.equals(this.get_UserName())){
                admin._Marked_For_Delete = true;
                break;

            }
        }
        _SaveAdminDataToFile(Admins);
        return true;
    }

    public void RetriveDeletedAdmin(){
        clsAdmin RetrevidAdmin = new clsAdmin(_mode.NewMode, _UserName, get_FirstName(),
        get_LastName(), get_BirthDate(), get_Gender(), get_Password(), get_Email(), _Permissions);
        RetrevidAdmin._ADD();
    }

    // =====Getters&Setters
    public String get_UserName() {
        return _UserName;
    }

    public int get_Permissions() {
        return _Permissions;
    }

    public void set_Permissions(int _Permissions) {
        this._Permissions = _Permissions;
    }

    public String get_Email() {
        return _Email;
    }

    public void set_Email(String _Email) {
        this._Email = _Email;
    }

    public String get_Password() {
        return _Password;
    }

    public void set_Password(String _Password) {
        this._Password = _Password;
    }

}
