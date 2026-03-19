package UI.Common.CommonControllers;

import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import models.clsAdmin;
import session.CurrentAdmin;


public class Admin_Login_Controller {

    @FXML private Label LoginMessage;
    @FXML private TextField UserNameField;
    @FXML private TextField PasswordField;


    public void SignIn(ActionEvent event){
        String UserName = UserNameField.getText();
        String Password = PasswordField.getText();
        CurrentAdmin.setCurrentAdmin(clsAdmin.FindAdmin(UserName,Password));
        if(CurrentAdmin.getCurrentAdmin() == null){
            LoginMessage.setText("Invalid UserName or Password");
            LoginMessage.setTextFill(Paint.valueOf("red"));
        }
        else{

            LoginMessage.setText("Successful Sign In :)");
            LoginMessage.setTextFill(Paint.valueOf("green"));
            PauseTransition pause = new PauseTransition(Duration.seconds(2));

            pause.setOnFinished(e -> {
                CurrentAdmin.setCurrentAdmin(clsAdmin.FindAdmin(UserName,Password));
                CNavigators.ShowAdminMainMenu();
            });

            pause.play();
        }
    }

    public void ReturnToMainMenu(ActionEvent e){
        CNavigators.ShowMainScene();
    }


}
