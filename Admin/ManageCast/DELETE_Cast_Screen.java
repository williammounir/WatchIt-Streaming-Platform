package UI.Admin.ManageCast;

import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import UI.Common.clsScreen;
import Util.InputVerfication;
import Util.S;
import models.clsAdminLog;
import models.clsCast;
public class DELETE_Cast_Screen extends clsScreen {

    private static void _PrintData(clsCast cast) {
        System.out.println("Cast Information: \n");
        System.out.printf("UserName: %s\n", cast.get_ID());
        System.out.printf("FullName: %s\n", cast.getFullName());
        System.out.printf("BirthDate: %s%n", cast.get_BirthDate());
        System.out.printf("Gender: %s\n", cast.get_Gender());
        System.out.printf("Nationality: %s\n", cast.getNationality());
        System.out.printf("Social Media Link: %s\n", cast.getSocialMediaLink());
    }

    public static void ShowDeleteScreen(){
        clsScreen.DrawScreenHeader("DELETE Cast SCREEN", null);
        System.out.print("Enter ID of The Desired Removed Cast: ");
        String ID = S.scanner.nextLine();
        while(!clsCast.IsCastExists(ID)){
            System.out.printf("CAST WITH ID %s DOESN'T EXISTS!!\n",ID);
            System.out.print("Enter ID: ");
            ID = S.scanner.nextLine();
        }

        clsCast TargetCast = clsCast.FindCast(ID);
        _PrintData(TargetCast);
        
        System.out.printf("Are You Sure You Want To Delete Cast %s ?\n",ID);
        char YesorNo = InputVerfication.readYesNo();
        System.out.print("Please give a Description for the last operation you have made: ");
        String Description = S.scanner.nextLine();
        if(YesorNo == 'y'){
            if(TargetCast.DELETEC()){
                if(clsAdminLog.InsertingAdminLog(enAction.DELETE_CAST, enTargetModel.CAST, ID, Description)){
                System.out.println("Cast Deleted Successfully :-)");
                }else{
                    TargetCast.RetriveDeletedCast();
                    System.out.println("DELATION PROCESS FAILED :-)");
                    System.out.println("REASON: Delation process on the system was successful, although the Admin log Failed");
                }
                return;
            }
        }
        System.out.println("Proccess Failed :-(");


    }



}

