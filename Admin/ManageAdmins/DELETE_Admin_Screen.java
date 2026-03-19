package UI.Admin.ManageAdmins;

import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import UI.Common.clsScreen;
import Util.S;
import Util.InputVerfication;
import models.clsAdmin;
import models.clsAdminLog;

public class DELETE_Admin_Screen extends clsScreen {

    private static void _PrintData(clsAdmin Admin) {
        System.out.println("Admin Information: \n");
        System.out.printf("UserName: %s\n", Admin.get_UserName());
        System.out.printf("FullName: %s\n", Admin.getFullName());
        System.out.printf("Email: %s\n", Admin.get_Email());
        System.out.printf("BirthDate: %s%n", Admin.get_BirthDate());
        System.out.printf("Gender: %s\n", Admin.get_Gender());
    }

    public static void ShowDeleteScreen(){
        clsScreen.DrawScreenHeader("DELETE ADMIN SCREEN", null);
        System.out.print("Enter UserName of The Desired Removed Admin: ");
        String UserName = S.scanner.nextLine();
        while(!clsAdmin.IsAdminExists(UserName)){
            System.out.printf("ADMIN WITH USERNAME %s DOESN'T EXISTS!!\n",UserName);
            System.out.print("Enter UserName: ");
            UserName = S.scanner.nextLine();
        }

        clsAdmin TargetAdmin = clsAdmin.FindAdmin(UserName);
        _PrintData(TargetAdmin);

        System.out.printf("Are You Sure You Want To Delete Admin %s ?\n",UserName);
        char YesorNo = InputVerfication.readYesNo();
        System.out.print("Please give a Description for the last operation you have made: ");
        String Description = S.scanner.nextLine();
        if(YesorNo == 'y'){

            if(TargetAdmin.DELETEA()){
                if(clsAdminLog.InsertingAdminLog(enAction.DELETE_ADMIN, enTargetModel.ADMIN, TargetAdmin.get_UserName(), Description)){
                    System.out.println("Client Deleted Successfully :-)");
                }else{
                    TargetAdmin.RetriveDeletedAdmin();
                    System.out.println("DELATION PROCESS FAILED :-)");
                    System.out.println("REASON: Delation process on the system was successful, although the Admin log Failed");
                }
                return;
            }
        }
        System.out.println("Proccess Failed :-(");


    }



}
