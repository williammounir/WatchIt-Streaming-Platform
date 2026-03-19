package UI.Admin.ManageMovies;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import UI.Admin.ManageCast.ADD_Cast_Screen;
import UI.Admin.ManageDirectors.ADD_Director_Screen;
import UI.Common.clsScreen;
import Util.InputVerfication;
import Util.S;
import models.Movie_Genere;
import models.Movie_Language;
import models.clsAdminLog;
import models.clsCast;
import models.clsDirector;
import models.clsMovie;
import models.clsMovie_Cast;
import models.Movie_Genere.genres;

public class ADD_Movie_Screen extends clsScreen {
    /*public clsMovie(enMode mode,String id,String title, LocalDate releaseDate, int durationMinutes, 
        List<String>cast, List<String>genres, String director, List<String> languages, String country,
        double budget, double revenue,double rating, String PosterPath*/
    private static void _ReadMovieData(clsMovie NewMovie,List<Movie_Language>Languages,List<Movie_Genere>Genres,List<clsMovie_Cast>mCasts) {

        System.out.print("Enter Movie Title: ");
        NewMovie.setTitle(S.scanner.nextLine());
        System.out.print("Enter ReleaseDate of The Movie\n");
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
        NewMovie.setReleaseDate(date);

        System.out.print("Enter Duration In Minutes: ");
        NewMovie.setDurationMinutes(Integer.parseInt(S.scanner.nextLine()));

        char choice = 'n';
        
        System.out.println("Enter Cast Members\n");
        do{
            clsMovie_Cast mc =new clsMovie_Cast(null, null);
            System.out.print("Enter Member Id: ");
            String CastID = S.scanner.nextLine();
            if(clsCast.IsCastExists(CastID)){
                mc = new clsMovie_Cast(NewMovie.getId(), CastID);
                mCasts.add(mc);
            }else{
                System.out.println("Member ID DOESN't EXISTS IN THE SYSTEM");
                System.out.println("Do you want to add the information of this cast or to discard it, if you said yes the system will convert you to the add cast screen? y->add/n->discard");
                char addordiscard = InputVerfication.readYesNo();
                if(addordiscard == 'y'){
                    //edge case what if he added another id?
                    ADD_Cast_Screen.ShowAddCastScreen();
                    mc = new clsMovie_Cast(NewMovie.getId(), CastID);
                    mCasts.add(mc);
                }
                
            }
            
            System.out.print("Do you want to add more members? ");
            choice = InputVerfication.readYesNo();

        }while(choice == 'y');
        //NewMovie.setCast(Cast);
        //here we have to work on the movie_cast entity
        


        System.out.println("Enter Genres\n");
        for(genres genere : genres.values()){
            System.out.printf("Is %s Considered %s? (y/n)?",NewMovie.getId(),genere.name());
            char choice2 = InputVerfication.readYesNo();
            if(choice2 == 'y')
                Genres.add(new Movie_Genere(NewMovie.getId(), genere.name()));
        }
        //NewMovie.setGenres(Genres);
        

        String Id;
        System.out.print("Enter Movie Director ID: ");
        Id = S.scanner.nextLine();
        while(!clsDirector.IsDirectorExists(Id)){
            System.out.println("Director Doesn't exists in the system");
            System.out.println("Do you want to add the info of the director or discard this id, y->add/n->Discard");
            char choice2 = InputVerfication.readYesNo();
            if(choice2 == 'y'){
                ADD_Director_Screen.ShowAddDirectorScreen(Id);
                break;
            }
            System.out.print("Enter Movie Director ID: ");
            Id = S.scanner.nextLine();
        }
        
        NewMovie.setDirector(Id);

        choice = 'n';
        
        System.out.println("Enter Movie Supported Languages\n");
        do{
            System.out.print("Enter Language: ");
            Languages.add(new Movie_Language(NewMovie.getId(),S.scanner.nextLine()));
            
            System.out.print("Do you want to add more Languages? ");
            choice = InputVerfication.readYesNo();

        }while(choice == 'y');
        //NewMovie.setLanguages(Langueages);
        

        System.out.print("Enter Movie Production Company Countary: ");
        NewMovie.setCountry(S.scanner.nextLine());

        System.out.print("Enter Movie Budget: ");
        NewMovie.setBudget(Double.parseDouble(S.scanner.nextLine()));

        System.out.print("Enter Movie Revenue: ");
        NewMovie.setRevenue(Double.parseDouble(S.scanner.nextLine()));

        System.out.print("Enter Movie Rating: ");
        NewMovie.setRating(Double.parseDouble(S.scanner.nextLine()));

        System.out.print("Enter Movie Poster Path: ");
        NewMovie.setPosterPath(S.scanner.nextLine());
    }

    // in the future add a function to Read permissions

    private static void _PrintData(clsMovie Movie,List<clsMovie_Cast>MC, List<Movie_Genere> generes, List<Movie_Language> languages) {
        /*public clsMovie(enMode mode,String id,String title, LocalDate releaseDate, int durationMinutes,
          String director, String country,
        double budget, double revenue,double rating, String PosterPath*/
        System.out.println("The Information of The New Movie Added is As Follows: \n");
        System.out.printf("ID: %s\n", Movie.get_id());
        System.out.printf("Title: %s\n", Movie.getTitle());
        System.out.printf("release Date: %s\n", Movie.getReleaseDate());
        System.out.printf("Duration In Minutes: %s%n", Movie.getDurationMinutes());
        System.out.println("Movie Cast Ids");
        for(int i = 0;i<MC.size();i++){
            System.out.printf("%s",MC.get(i).get_Cast_ID());
            if(i!=MC.size()-1)
                System.out.print("-");   
        }
        System.out.println();
        
        System.out.println("Movie Genres ");
        for(int i = 0;i<generes.size();i++){
            System.out.printf("%s",generes.get(i).get_Genere());
            if(i!=generes.size()-1)
                System.out.print("-");   
        }
        System.out.println();

        System.out.printf("Director: %s\n", Movie.getDirector());

        System.out.println("Movie Supported Languages");
        for(int i = 0;i<languages.size();i++){
            System.out.printf("%s",languages.get(i).get_Language());
            if(i!=languages.size()-1)
                System.out.print("-");   
        }
        System.out.println();

        System.out.printf("Country: %s\n", Movie.getCountry());
        System.out.printf("Budget: %f\n", Movie.getBudget());

    }

    public static void ShowAddScreen() {
        clsScreen.DrawScreenHeader("ADD MOVIE SCREEN", null);
        System.out.print("Enter ID of The New Movie: ");
        String ID = S.scanner.nextLine();
        while (clsMovie.IsMovieExists(ID)) {
            System.out.println("ID ALREADY EXISTS!");
            System.out.print("Enter Id: ");
            ID = S.scanner.nextLine();
        }

        clsMovie NewMovie = clsMovie.GetAddNewObjectMovie(ID);

        List<Movie_Language>Languages = new ArrayList<>();
        List<clsMovie_Cast>mCasts = new ArrayList<>();
        List<Movie_Genere>Genres = new ArrayList<>();
        _ReadMovieData(NewMovie,Languages,Genres,mCasts);
        System.out.print("Please give a Description for the last operation you have made: ");
        String Description = S.scanner.nextLine();
        clsMovie.enSaveResult result = NewMovie.SaveM();
        
        switch(result){
            case SVSUCCEDED:
                if(clsAdminLog.InsertingAdminLog(enAction.ADD_MOVIE, enTargetModel.MOVIE, NewMovie.get_id(), Description)){
                    _PrintData(NewMovie, mCasts, Genres, Languages);    
                    System.out.printf("Movie (%s) Has Been Created Successfully :)\n",NewMovie.get_id());
                    System.out.println("THE LAST OPERATION HAS BEEN STORED SUCCESSFULLY!");
                    Movie_Language.SaveRelations(Languages);
                    clsMovie_Cast.SaveRelations(mCasts);
                    Movie_Genere.SaveRelations(Genres);                  
                }else{
                    NewMovie.DELETEM();
                    System.out.printf("Movie (%s) has not been added to the system!",NewMovie.get_id());
                    System.out.println("REASON: Saving process was successfull, but the Process of storing the Log failed");
                    
                }          
                break;
            case SvFailedEmptyObject:
                System.out.println("SAVE HAS BEEN FAILED EMPTY OBJECT!");
                break;
            case SvFailedObjectAlreadyExists:
                System.out.println("SAVE FAILED ADMIN USERNAME ALREADY EXISTS!");
                break;            
        }
    }


}
