package UI.Admin.ManageCast;

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
import models.clsCast;



public class UPDATE_Cast_Screen extends clsScreen {

     private static void _ReadCastData(clsCast NewCast) {
      //  public clsCast(enMode mode,String ID, String firstName, String lastName, LocalDate dateOfBirth,
       //            String gender, List<String> movieIDs, String nationality,
       //            String socialMediaLink) {

        System.out.print("Enter FirstName: ");
        String FirsName = S.scanner.nextLine();
        NewCast.set_FirstName(FirsName);

        System.out.print("Enter LastName: ");
        String LastName = S.scanner.nextLine();
        NewCast.set_LastName(LastName);

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
        NewCast.set_BirthDate(date);

        System.out.print("Enter Gender: ");
        String Gender = S.scanner.nextLine();
        NewCast.set_Gender(Gender);

        //CIRCULAR DEPENDANCY
        /*List<String> listmovies = new ArrayList<>(); 
        char choice = 'n';
        do{
            System.out.print("Enter Movie Id");
            //what if movie id doesn't exists... I think we have to start working with movies class and add movie if it doesn't exists
            String MID = S.scanner.nextLine();
            if(!clsMovie.IsMovieExists(MID))
                System.out.printf("Movie With ID: %s Doesn't Exists in the System, Enter Only Existed Movies\n",MID);
            else{
                listmovies.add(MID);
            }
            System.out.println("Do you Still Want to add more Movies? (y/n)? ");
            choice = InputVerfication.readYesNo();
        }while(choice == 'y');
        NewCast.setMovieIDs(listmovies);*/
        
        System.out.print("Enter Nationality: ");
        String Nationality = S.scanner.nextLine();
        NewCast.setNationality(Nationality);
        System.out.print("Enter Social Media Link: ");
        String SocialMediaLink = S.scanner.nextLine();
        NewCast.setSocialMediaLink(SocialMediaLink);

    }


    private static void _PrintData(clsCast Cast) {
        System.out.println("The Information of The New Cast Added is As Follows: \n");
        System.out.printf("UserName: %s\n", Cast.get_ID());
        System.out.printf("FullName: %s\n", Cast.getFullName());
        System.out.printf("BirthDate: %s%n", Cast.get_BirthDate());
        System.out.printf("Gender: %s\n", Cast.get_Gender());
        /*System.out.print("Movies That He Had Particptated at: ");
        for(int i = 0 ;i<Cast.getMovieIDs().size();i++){
            clsMovie movie = clsMovie.FindMovie(Cast.getMovieIDs().get(i));
            System.out.printf("%d: %s\n",i+1,movie.getTitle());
            if(i<Cast.getMovieIDs().size()-1)
                System.out.print(", ");
        }*/
        System.out.printf("Nationality: %s\n", Cast.getNationality());
        System.out.printf("Social Media Link: %s\n", Cast.getSocialMediaLink());

    }

    public static void ShowUpdateCastScreen(){
        clsScreen.DrawScreenHeader("Update Cast Screen", null);
        System.out.print("Enter ID: ");
        String ID = S.scanner.nextLine();
        while(!clsCast.IsCastExists(ID)){
            System.out.println("Cast DOESN'T EXISTS, TRY AGAIN");
            System.out.print("Enter ID: ");
            ID = S.scanner.nextLine();
        }

        clsCast UpdatedCast = clsCast.FindCast(ID);
        _PrintData(UpdatedCast);

        System.out.println("Are You Sure You Want to Update This Cast?");
        char YesorNo = InputVerfication.readYesNo();
        if(YesorNo == 'y'){

            System.out.print("Update Cast Information\n");
            System.out.println("=======================\n");
            _ReadCastData(UpdatedCast);

            clsCast.enSaveResult SaveResult = UpdatedCast.SaveC();
            System.out.print("Please give a Description for the last operation you have made: ");
            String Description = S.scanner.nextLine();
            switch(SaveResult){
                case SVSUCCEDED:
                    System.out.printf("Cast %s UPDATED SUCCESSFULLY :-)\n",ID);
                    _PrintData(UpdatedCast);
                    clsAdminLog.InsertingAdminLog(enAction.UPDATE_CAST, enTargetModel.CAST, UpdatedCast.get_ID(), Description);
                    break;
                case SvFailedEmptyObject:
                    System.out.println("SAVE FAILED EMPTY OBJECT :-<");
                    break;    
            } 
        }

    }

}
