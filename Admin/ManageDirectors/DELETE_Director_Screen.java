package UI.Admin.ManageDirectors;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import UI.Common.clsScreen;
import Util.InputVerfication;
import Util.S;
import models.clsAdminLog;
import models.clsDirector;
public class DELETE_Director_Screen extends clsScreen {

    private static void _PrintData(clsDirector Director) {
        System.out.println("Director Information: \n");
        System.out.printf("UserName: %s\n", Director.get_ID());
        System.out.printf("FullName: %s\n", Director.getFullName());
        System.out.printf("BirthDate: %s%n", Director.get_BirthDate());
        System.out.printf("Gender: %s\n", Director.get_Gender());
        System.out.printf("Nationality: %s\n", Director.getNationality());
        System.out.printf("Social Media Link: %s\n", Director.getSocialMediaLink());
    }

    public static void ShowDeleteScreen(){
        clsScreen.DrawScreenHeader("DELETE Director SCREEN", null);
        System.out.print("Enter ID of The Desired Removed Director: ");
        String ID = S.scanner.nextLine();
        while(!clsDirector.IsDirectorExists(ID)){
            System.out.printf("Director WITH ID %s DOESN'T EXISTS!!\n",ID);
            System.out.print("Enter ID: ");
            ID = S.scanner.nextLine();
        }

        clsDirector TargetDirector = clsDirector.FindDirector(ID);
        _PrintData(TargetDirector);

        System.out.printf("Are You Sure You Want To Delete Director %s ?\n",ID);
        char YesorNo = InputVerfication.readYesNo();
        System.out.print("Please give a Description for the last operation you have made: ");
        String Description = S.scanner.nextLine();
        if(YesorNo == 'y'){
            if(TargetDirector.DELETED()){
                if(clsAdminLog.InsertingAdminLog(enAction.DELETE_DIRECTOR, enTargetModel.DIRECTOR, ID, Description)){
                System.out.println("Director Deleted Successfully :-)");
                }else{
                    TargetDirector.RetriveDeletedDirector();
                    System.out.println("DELATION PROCESS FAILED :-)");
                    System.out.println("REASON: Delation process on the system was successful, although the Admin log Failed");
                }
                return;
            }
        }
        System.out.println("Proccess Failed :-(");


    }
}


