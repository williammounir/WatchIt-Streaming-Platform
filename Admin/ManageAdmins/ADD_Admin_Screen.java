package UI.Admin.ManageAdmins;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import UI.Common.clsScreen;
import Util.InputVerfication;
import Util.S;
import models.clsAdmin;
import models.clsAdminLog;
import models.clsAdmin.enSaveResult;

public class ADD_Admin_Screen extends clsScreen {

    private static void _ReadAdminData(clsAdmin NewAdmin) {

        // public clsAdmin(enMode mode,String UserName,String FirstName,String lastName
        // ,LocalDate BirthDate,String Gender,String Password,String Email, int
        // Permissions)

        System.out.print("Enter FirstName: ");
        String FirsName = S.scanner.nextLine();
        NewAdmin.set_FirstName(FirsName);

        System.out.print("Enter LastName: ");
        String LastName = S.scanner.nextLine();
        NewAdmin.set_LastName(LastName);

        System.out.print("Please Enter your BirthData\n");
        System.out.print("Enter year: ");
        int year = Integer.parseInt(S.scanner.nextLine().trim());
        System.out.print("Enter month ");
        int month = InputVerfication.GetNumberBetween(1, 12);
        System.out.print("Enter Day: ");
        List<Integer> thirtyonedays = Arrays.asList(1, 4, 5, 7, 8, 10, 12);
        List<Integer> thirtydays = Arrays.asList(4, 6, 9, 11);
        int day;
        if (thirtydays.contains(month)) {
            day = InputVerfication.GetNumberBetween(1, 31);
        } else if (thirtyonedays.contains(month)) {
            day = InputVerfication.GetNumberBetween(1, 30);
        } else {
            boolean isLeap = Year.isLeap(year);
            if (isLeap) {
                day = InputVerfication.GetNumberBetween(1, 29);
            } else {
                day = InputVerfication.GetNumberBetween(1, 28);
            }
        }
        LocalDate date = LocalDate.of(year, month, day);
        NewAdmin.set_BirthDate(date);

        System.out.print("Enter Gender: ");
        String Gender = S.scanner.nextLine();
        NewAdmin.set_Gender(Gender);
      
        System.out.print("Enter Password: ");
        String pass = S.scanner.nextLine();
        NewAdmin.set_Password(pass);
        System.out.print("Enter Email: ");
        String Email = S.scanner.nextLine();
        NewAdmin.set_Email(Email);

    }

    // in the future add a function to Read permissions

    private static void _PrintData(clsAdmin Admin) {
        System.out.println("The Information of The New Admin Added is As Follows: \n");
        System.out.printf("UserName: %s\n", Admin.get_UserName());
        System.out.printf("FullName: %s\n", Admin.getFullName());
        System.out.printf("Email: %s\n", Admin.get_Email());
        System.out.printf("BirthDate: %s%n", Admin.get_BirthDate());
        System.out.printf("Gender: %s\n", Admin.get_Gender());
    }

    public static void ShowAddScreen() {
        clsScreen.DrawScreenHeader("ADD ADMIN SCREEN", null);
        System.out.print("Enter UserName of New Admin: ");
        String UserName = S.scanner.nextLine();
        while (clsAdmin.IsAdminExists(UserName)) {
            System.out.println("USERNAME ALREADY EXISTS!");
            System.out.print("Enter UserName: ");
            UserName = S.scanner.nextLine();
        }

        clsAdmin NewAdmin = clsAdmin.GetAddNewObjectAdmin(UserName);
        _ReadAdminData(NewAdmin);
        System.out.print("Please give a Description for the last operation you have made: ");
        String Description = S.scanner.nextLine();
        clsAdmin.enSaveResult result = NewAdmin.SaveA();
        

        switch(result){
            case SVSUCCEDED:
                
                if(clsAdminLog.InsertingAdminLog(enAction.ADD_ADMIN, enTargetModel.ADMIN, NewAdmin.get_UserName(), Description)){
                    _PrintData(NewAdmin);    
                    System.out.printf("Admin (%s) Has Been Created Successfully :)\n",NewAdmin.get_UserName());
                    System.out.println("THE LAST OPERATION HAS BEEN STORED SUCCESSFULLY!");
                    
                }else{
                    NewAdmin.DELETEA();
                    System.out.printf("Admin (%s) has not been added to the system!",NewAdmin.get_UserName());
                    System.out.println("REASON: Saving process was successfull, but the Process of storing the Log failed");
                    
                }          
                break;
            case SvFailedEmptyObject:
                System.out.println("SAVE HAS BEEN FAILED EMPTY OBJECT!");
                break;
            case SvFailedObjectAlreadyExists:
                System.out.println("SAVE FAILED ADMIN USERNAME ALREADY EXISTS!");
                break;        
            case SvLogStoringFailed:
                System.out.println("SAVE LOG WASN'T SUCCESSFULL, YOU CAN'T STORE THE NEW ADMIN!");        
        }
    }
}
