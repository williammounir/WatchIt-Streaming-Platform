package UI.Admin.ManageMovies;
import Util.InputVerfication;
import Util.Pause;
import UI.Common.clsScreen;
public class ManageMoviesMenuScreen extends clsScreen {


    enum enManageMoviesOptions{
        ADD,
        DELETE,
        UPDATE,
        ShowMoviesList,
        FindMovie,
        RETURN
    }


    private static void _ADD(){
        ADD_Movie_Screen.ShowAddScreen();
        //ADDfunction

    }
    private static void _DELETE(){
        //DELETEfunction

    }
    private static void _UPDATE(){

        //UPDATEfunction
    }

     private static void _MoviesList(){
        //showmovieslist
    }
     private static void _FindMovie(){
        //FindMoviefunction
    }
    private static void _RETURN(){

    }

    private static void _ReturnToManageMMenu(){
        Pause.pause("PRESS ANY KEY TO GO BACK TO MANAGE MOVIES SCREEN\n");
        //call ManageAdminsScreen
        ShowManageMoviesScreen();
    }

    private static enManageMoviesOptions _MakingaManageChoice(int min, int max){
        System.out.printf("Make a Choice? [%d to %d]?",min,max);
        int choice = InputVerfication.GetNumberBetween(min, max);
        return enManageMoviesOptions.values()[choice-1];
    }

    private static void _PreformManageChoice(enManageMoviesOptions choice){
        switch(choice){
            case ADD:

                _ADD();
                _ReturnToManageMMenu();
                break;
            case DELETE:

                _DELETE();
                _ReturnToManageMMenu();
                break;
            case UPDATE:

                _UPDATE();
                _ReturnToManageMMenu();
                break;
            case ShowMoviesList:

                _MoviesList();
                _ReturnToManageMMenu();
                break;
            case FindMovie:

                _FindMovie();
                _ReturnToManageMMenu();
                break;    
            case RETURN:

                _RETURN();
                break;

        }
    }


    public static void ShowManageMoviesScreen(){

        clsScreen.DrawScreenHeader("\t\tManage Movies", null);
        String indent = String.format("%-37s", ""); 
    
        System.out.println(indent + "\t[1] ADD Movie");
        System.out.println(indent + "\t[2] DELETE Movie");
        System.out.println(indent + "\t[3] UPDATE Movie");
        System.out.println(indent + "\t[4] Show Movies List");
        System.out.println(indent + "\t[5] Find Movie");
        System.out.println(indent + "\t[6] RETURN to Admin Main Menu");
        System.out.println(indent + "===========================================");
        _PreformManageChoice(_MakingaManageChoice(1, 6));

    }

}
