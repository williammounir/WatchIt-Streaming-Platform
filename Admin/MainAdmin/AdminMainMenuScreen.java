package UI.Admin.MainAdmin;
import UI.Admin.Admin_Functions.AdminsActivityLog;
import UI.Admin.Admin_Functions.GivingAndWithdrawaBan;
import UI.Admin.Admin_Functions.RevenuPerMonth;
import UI.Admin.Admin_Functions.SystemStatistics;
import UI.Admin.ManageAdmins.ManageAdminsMenuScreen;
import UI.Admin.ManageCast.ManageCastsScreen;
import UI.Admin.ManageDirectors.ManageDirectorsMenuScreen;
import UI.Admin.ManageMovies.ManageMoviesMenuScreen;
import UI.Common.clsScreen;
import Util.InputVerfication;
import Util.Pause;
import session.CurrentAdmin;

public class AdminMainMenuScreen extends clsScreen {

   private enum enMainManuOptions  {
        MANAGE_ADMINS_MENU,
        MANAGE_CAST_MENU,
        MANAGE_DIRECTORS_MENU,
        MANAGE_MOVIE_MENU,
        SHOW_MONTHS_REVENUE,
        USER_ACCOUNT_SUSBENTION,
        ADMINS_ACTIVITY_LOG,
        SYSTEM_STATISTICS,
        LOG_OUT
    }

private static void _GoBackToMainMenu(){
    Pause.pause("\nPRESS ANY KEY TO GO BACK TO ADMIN MAIN MENU.....");
    ShowAdminsMainMenu();
}

private static void _ShowManageAdminsMenu(){
    ManageAdminsMenuScreen.ShowManageAdminsScreen();
    //call the showmanageadmins function
    
}
private static void _ShowManageCastMenu(){
    ManageCastsScreen.ShowManageCastsScreen();
    //call showmanagecast MENU function

}
private static void _ShowManageDirectorsMenu(){
    ManageDirectorsMenuScreen.ShowManageDirectorsScreen();
    //call showmangaedirectormenu function

}
private static void _ShowManageMovieMenu(){
    ManageMoviesMenuScreen.ShowManageMoviesScreen();
    //call showmanagemoviemenu function

}
private static void _ShowMonthsRevenuScreen(){
    //call showMonthsRevenuScreen function
    RevenuPerMonth.showMonthsRevenuScreen();
}
private static void _ShowUserSusbentionScreen(){
    //call showUserSusbentionScreen function
    GivingAndWithdrawaBan.ShowSuspentionScreen();
}
private static void _ShowAdminsActivityLog(){
    //call showadminactivitylog function
    AdminsActivityLog.ShowAdminsActivityLogScreen();
}
private static void _ShowSystemStatistics(){
    //call showsystemstast function
    SystemStatistics.ShowSystemStatisticsScreen();
}
private static void _Log_out(){
   CurrentAdmin.clear();
}

private static enMainManuOptions _MakingAChoice(int FirstChoice, int LastChoice){
    System.out.println("\nEnter a Choice: ");
   return enMainManuOptions.values()[InputVerfication.GetNumberBetween(FirstChoice, LastChoice)-1];
}

private static void _ImplementingChoice(enMainManuOptions Choice){

    
    switch(Choice){
        case MANAGE_ADMINS_MENU:

            _ShowManageAdminsMenu();
            _GoBackToMainMenu();
            break;
        case MANAGE_CAST_MENU: 

            _ShowManageCastMenu();
            _GoBackToMainMenu();
            break;
        case MANAGE_DIRECTORS_MENU:

            _ShowManageDirectorsMenu();
            _GoBackToMainMenu();
            break;
        case MANAGE_MOVIE_MENU:

            _ShowManageMovieMenu();
            _GoBackToMainMenu();
            break;
        case SHOW_MONTHS_REVENUE:

            _ShowMonthsRevenuScreen();
            _GoBackToMainMenu();
            break;
        case USER_ACCOUNT_SUSBENTION:

            _ShowUserSusbentionScreen();
            _GoBackToMainMenu();
            break;
        case ADMINS_ACTIVITY_LOG:

            _ShowAdminsActivityLog();
            _GoBackToMainMenu();  
            break;
        case SYSTEM_STATISTICS:

            _ShowSystemStatistics();
            _GoBackToMainMenu();
            break;
        case LOG_OUT:

            _Log_out();
            break;

    }

   
}

public static void ShowAdminsMainMenu(){
    clsScreen.DrawScreenHeader("\t\tMAIN SCREEN", null);
    String indent = String.format("%-37s", ""); 

    System.out.println(indent + "===========================================");
    System.out.println(indent + "\t\t\tAdmin Main Menu");
    System.out.println(indent + "===========================================");
    
    System.out.println(indent + "\t[1] Manage Admins");
    System.out.println(indent + "\t[2] Manage Cast");
    System.out.println(indent + "\t[3] Manage Directors");
    System.out.println(indent + "\t[4] Manage Movies");
    System.out.println(indent + "\t[5] Show Monthly Revenue");
    System.out.println(indent + "\t[6] User Account Suspension");
    System.out.println(indent + "\t[7] Admins Activity Log");
    System.out.println(indent + "\t[8] System Statistics");
    System.out.println(indent + "\t[9] Logout");
    
    System.out.println(indent + "===========================================");
    _ImplementingChoice(_MakingAChoice(1, 9));
}



}



