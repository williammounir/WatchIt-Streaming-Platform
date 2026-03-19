package UI.Common.CommonControllers;


import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.event.ActionEvent;

public class User_Authentication_Controller {

    public void UserLogin(ActionEvent e){
        CNavigators.ShowUserLogin();
    }

    public void UserSignUp(ActionEvent e){
        CNavigators.ShowUserSignUp();
    }

    public void UserRenewSubscription(ActionEvent e){
        CNavigators.ShowUserRenewSubscription();
    }

    public void ReturnToMainMenu(ActionEvent e){
        CNavigators.ShowMainScene();
    }
}
