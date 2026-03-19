package Main;
import UI.Admin.MainAdmin.AdminMainMenuScreen;
import UI.Common.clsMainScreen;
import UI.Common.clsMainScreen.ScreenFlow;
import Util.Pause;
import models.clsSuspention;

public class Main {
public static void main(String[] args) {
  
boolean running = true;
clsSuspention.AutomaticDeletionOfSuspentionsWhenPeriodPass();
while(running) {
    ScreenFlow flow = clsMainScreen.show();

    switch(flow) {
        case GO_TO_ADMIN_MENU:          
            System.out.println("Admin login succeded\n");
            Pause.pause("Press any key to go to Admin Main menu...");
            AdminMainMenuScreen.ShowAdminsMainMenu(); 
            break;
        case GO_TO_USER_MENU:   
            System.out.println("user login succeded\n");
            Pause.pause("Press any key to go to User Main menu...");
            break;
        case EXIT_APP:
            running = false;
            break;
        case STAY_IN_LOOP:
            break;
    }

}
}



}
