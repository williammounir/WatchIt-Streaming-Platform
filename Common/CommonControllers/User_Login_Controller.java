package UI.Common.CommonControllers;

import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import models.clsSubscription;
import models.clsSuspention;
import models.clsUser;
import session.CurrentUser;


public class User_Login_Controller {

    @FXML private Label LoginMessage;
    @FXML private TextField UserNameField;
    @FXML private TextField PasswordField;
    @FXML private Label SuspensionEndDateLabel;
    @FXML private Label SuspensionReasonLabel;

    @FXML private StackPane rootPane;
    @FXML private ImageView backgroundImageView;

    @FXML
    private void initialize() {

        backgroundImageView.fitWidthProperty().bind(rootPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(rootPane.heightProperty());
    }


    public void SignIn(ActionEvent event){
        String UserName = UserNameField.getText();
        String Password = PasswordField.getText();
        SuspensionReasonLabel.setText("");
        SuspensionEndDateLabel.setText("");
        if(clsUser.FindUser(UserName,Password) == null){

            LoginMessage.setText("Invalid UserName or Password");
            LoginMessage.setTextFill(Paint.valueOf("red"));
            return;
        }

        if(clsSuspention.IsUserSusbended(UserName)){
            clsSuspention s = clsSuspention.FindSuspenstion(UserName);
            String message = "ACCOUNT IS SUSPENDED";
            String Edate = s.get_EndDate().toString();
            String Reason = s.getReason();
            LoginMessage.setText(message);
            SuspensionEndDateLabel.setText("End Date: "+Edate);
            SuspensionReasonLabel.setText("Reason: "+Reason);
            LoginMessage.setTextFill(Paint.valueOf("red"));
            return;
        }

        if(clsSubscription.DidSubscriptionEnded(UserName)){
            clsSubscription s = clsSubscription.findSubscription(UserName);
            String message = "SUBSCRIPTION EXPIRED";
            String Edate = s.get_EndDate().toString();
            LoginMessage.setText(message);
            SuspensionEndDateLabel.setText("Expiration Date: "+Edate);
            SuspensionReasonLabel.setText("You Can Renew Your Subscription by Going to RENEW SUBSCRIPTION Menu");
            LoginMessage.setTextFill(Paint.valueOf("red"));
            return;
        }

            LoginMessage.setText("Successful Sign In :)");
            LoginMessage.setTextFill(Paint.valueOf("green"));
            PauseTransition pause = new PauseTransition(Duration.seconds(2));

            pause.setOnFinished(e -> {
                CurrentUser.setCurrentUser(clsUser.FindUser(UserName,Password));
                CNavigators.ShowMoviesHome();
            });

        pause.play();

    }

    public void ReturnToUserAuthMenu(ActionEvent e){
        CNavigators.ShowUserAuth();
    }


}
