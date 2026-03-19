package models;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import static  Util.clsString.split;

public class clsUser extends clsPerson {

    private final static String FileName = "D:\\Watch It - Copy\\Watch It\\src\\models\\Users.txt";

    private enum enMode {EmptyMode,UpdateMode,NewMode}
    private enMode _Mode;
    
    private String _UserName;
    private String _Password;
    private String _Email;
    //multivalued attribute
    //private List<String> _watchLaterMovies; 
    //for my current understanding , we will store the reference to the subscription here but it is not going to be saved in the file
    // we don't have to store it when we need it you can get it from the subscription file by the userid
    //private clsSubscription _subscription;
 
    private boolean _Marked_For_Delete = false;


    private static clsUser _ConvertLineToUserRecord(String Line, String Delim ){
        List<String> UserData = new ArrayList<>();
        //multivalued attribute
        //List<String> WatchedLaterMovies = new ArrayList<>();
        UserData = split(Line, Delim);
        //WatchedLaterMovies = split(UserData.get(7),"-");
         
        clsUser user = new clsUser(
    enMode.UpdateMode,
    UserData.get(0),                 // UserName
    UserData.get(1),                 // firstName
    UserData.get(2),                 // lastName
    LocalDate.parse(UserData.get(3)),// BirthDate 
    UserData.get(4),                 // Gender
    UserData.get(5),                 // email
    UserData.get(6)             // Password
    //multivalued attribute
    //WatchedLaterMovies
);
//user.set_subscription(clsSubscription.findSubscription(UserData.get(0)));

return user;
    } 
    
    private static List<clsUser> _LoadUsersDataFromFile(){

     /*another way of reading from file but from top to bottom
       List<clsUser> users = new ArrayList<>();
    try(BufferedReader reader = new BufferedReader(new FileReader(FileName))){
        String line;
        while((line = reader.readLine())!=null){



        }

    }catch(FileNotFoundException e){
        System.out.println("Could not locate file\n");

    }catch(IOException e){
        System.out.println("something went wrong\n");

    }


    return users;*/
    List<clsUser> users = new ArrayList<>();

try {
    List<String> lines = Files.readAllLines(Paths.get(FileName));

    // Loop from bottom to top
    for (int i = lines.size() - 1; i >= 0; i--) {
        String line = lines.get(i);
        users.add(_ConvertLineToUserRecord(line, "#//#"));
    }

} catch (IOException e) {
    System.out.println("something went wrong\n");
}

return users;

}

private static String _ConvertRecordToUserLine(clsUser User){
    String Line="";
    Line += User.get_UserName()+"#//#";
    Line += User.get_FirstName()+"#//#";
    Line += User.get_LastName()+"#//#";
    Line += User.get_BirthDate().toString()+"#//#";
    Line += User.get_Gender()+"#//#";
    Line += User.get_Email()+"#//#";
    Line += User.get_Password();
    //multivalued attribute
   // for(String m : User._watchLaterMovies){
   //     Line+=m+"-";        
    //}
    //if(Line.charAt(Line.length()-1) == '-')
    //    Line = Line.substring(0,Line.length()-1);

    return Line;

}

private static void _AppendDataToFile(String Line){
     try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName, true))) {
        writer.newLine(); // VERY important
        writer.write(Line);       
    } catch (IOException e) {
        System.out.println("Could not write to file: " + FileName);
    }
}

private void _addNew(){
    _AppendDataToFile(_ConvertRecordToUserLine(this));
}



    private boolean _Update() {

        List<clsUser> users = _LoadUsersDataFromFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {

            for (clsUser user : users) {

                if (user.get_UserName().equals(this._UserName)) {
                    writer.write(_ConvertRecordToUserLine(this));
                } else {
                    writer.write(_ConvertRecordToUserLine(user));
                }

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Update failed.");
            return false;
        }

        return true;
    }


    public clsUser(enMode mode,String UserName,String firstName, String lastName,LocalDate BirthDate, String Gender, String email, 
        String Password
        ){
            super(firstName,lastName,BirthDate,Gender);
            _Mode = mode;
            _UserName = UserName;
            _Password = Password;
            _Email = email;  
            // multivalued attribute
           /* if(WatchLaterMovies != null){    
            _watchLaterMovies = WatchLaterMovies;
            }else{
                _watchLaterMovies = new ArrayList<>();
            }*/
           

        }

    

    public static clsUser FindUser(String UserName,String Password){
        
        
    // when you add bufferreader to try ->try(bufferredreader...) 
    // it closes once it finishs you don't have to close manualy it is called TRY WITH RESORCES (it closes even 
    // when hitting a exception or a return)
    try(BufferedReader reader = new BufferedReader(new FileReader(FileName))){
        String line;
        while((line = reader.readLine())!=null){

            clsUser user = _ConvertLineToUserRecord(line, "#//#");
            if(user.get_UserName().equals(UserName)&&user.get_Password().equals(Password))
                return user;

        }

    }catch(FileNotFoundException e){
        System.out.println("Could not locate file\n");

    }catch(IOException e){
        System.out.println("something went wrong\n");

    }


    return null;
    

    }

    public static clsUser FindUser(String UserName){
        
        
    // when you add bufferreader to try ->try(bufferredreader...) 
    // it closes once it finishs you don't have to close manualy it is called TRY WITH RESORCES (it closes even 
    // when hitting a exception or a return)
    try(BufferedReader reader = new BufferedReader(new FileReader(FileName))){
        String line;
        while((line = reader.readLine())!=null){

            clsUser user = _ConvertLineToUserRecord(line, "#//#");
            if(user.get_UserName().equals(UserName))
                return user;

        }

    }catch(FileNotFoundException e){
        System.out.println("Could not locate file\n");

    }catch(IOException e){
        System.out.println("something went wrong\n");

    }


    return null;
    

    }
    
    public static boolean IsUserExist(String UserName){
        clsUser User = FindUser(UserName);
        if(User == null)
            return false;
        else
            return true;
    }

    public static enum enSaveResult {SvFailedEmptyObject,SvSucceded,SvFailedObjectAlreadyExists};

    public enSaveResult Save(){
        switch(_Mode){
            case EmptyMode:
                return enSaveResult.SvFailedEmptyObject;
            case UpdateMode:
                if (_Update())
                    return enSaveResult.SvSucceded;
                else
                    return enSaveResult.SvFailedEmptyObject;
            case NewMode:
                if(IsUserExist(_UserName))
                    return enSaveResult.SvFailedObjectAlreadyExists;
                else{
                    _addNew();
                    _Mode = enMode.UpdateMode;
                    return enSaveResult.SvSucceded;
                }
                   
        }
        return enSaveResult.SvFailedEmptyObject;
    }
    public static clsUser GetAddNewObject(String UserName){
        return new clsUser(enMode.NewMode,UserName,null,null,null,null,null,null);

    }

    public static int CountUsers(){
        return _LoadUsersDataFromFile().size();
    }


    //======Getters&Setters
    public String get_UserName() {
        return _UserName;
    }
    public String get_Password() {
        return _Password;
    }
    public void set_Password(String _Password) {
        this._Password = _Password;
    }
    public String get_Email() {
        return _Email;
    }
    public void set_Email(String _Email) {
        this._Email = _Email;
    }

}