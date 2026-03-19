package UI.Admin.ManageDirectors;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import UI.Common.clsScreen;
import Util.InputVerfication;
import Util.S;
import models.clsAdminLog;
import models.clsDirector;

public class UPDATE_Director_Screen extends clsScreen {
        private static void _ReadDirectorData(clsDirector NewDirector) {

        System.out.print("Enter FirstName: ");
        String FirsName = S.scanner.nextLine();
        NewDirector.set_FirstName(FirsName);

        System.out.print("Enter LastName: ");
        String LastName = S.scanner.nextLine();
        NewDirector.set_LastName(LastName);

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
        NewDirector.set_BirthDate(date);

        System.out.print("Enter Gender: ");
        String Gender = S.scanner.nextLine();
        NewDirector.set_Gender(Gender);        
        System.out.print("Enter Nationality: ");
        String Nationality = S.scanner.nextLine();
        NewDirector.setNationality(Nationality);
        System.out.print("Enter Social Media Link: ");
        String SocialMediaLink = S.scanner.nextLine();
        NewDirector.setSocialMediaLink(SocialMediaLink);

    }


    private static void _PrintData(clsDirector Director) {
        System.out.println("The Information of The New Director Added is As Follows: \n");
        System.out.printf("UserName: %s\n", Director.get_ID());
        System.out.printf("FullName: %s\n", Director.getFullName());
        System.out.printf("BirthDate: %s%n", Director.get_BirthDate());
        System.out.printf("Gender: %s\n", Director.get_Gender());
        System.out.printf("Nationality: %s\n", Director.getNationality());
        System.out.printf("Social Media Link: %s\n", Director.getSocialMediaLink());
    }

    public static void ShowUpdateDirectorScreen(){
        clsScreen.DrawScreenHeader("Update Director Screen", null);
        System.out.print("Enter ID: ");
        String ID = S.scanner.nextLine();
        while(!clsDirector.IsDirectorExists(ID)){
            System.out.println("Director DOESN'T EXISTS, TRY AGAIN");
            System.out.print("Enter ID: ");
            ID = S.scanner.nextLine();
        }

        clsDirector UpdatedDirector = clsDirector.FindDirector(ID);
        _PrintData(UpdatedDirector);

        System.out.println("Are You Sure You Want to Update This Director?");
        char YesorNo = InputVerfication.readYesNo();
        if(YesorNo == 'y'){

            System.out.print("Update Director Information\n");
            System.out.println("=======================\n");
            _ReadDirectorData(UpdatedDirector);

            clsDirector.enSaveResult SaveResult = UpdatedDirector.SaveD();
            System.out.print("Please give a Description for the last operation you have made: ");
            String Description = S.scanner.nextLine();
            switch(SaveResult){
                case SVSUCCEDED:
                    System.out.printf("Director %s UPDATED SUCCESSFULLY :-)\n",ID);
                    _PrintData(UpdatedDirector);
                    clsAdminLog.InsertingAdminLog(enAction.UPDATE_DIRECTOR, enTargetModel.DIRECTOR, UpdatedDirector.get_ID(), Description);
                    break;
                case SvFailedEmptyObject:
                    System.out.println("SAVE FAILED EMPTY OBJECT :-<");
                    break;
                case SvFailedInvalidUpdate:
                    System.out.println("Invalid Update in sql");
                    break;        
            } 
        }

    }


}
