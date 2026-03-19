package UI.Common.CommonControllers;

import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import models.clsSubscription;
import models.clsUser;
import models.enPlan;

import java.time.LocalDate;


public class User_RenewSubscription_Controller {

    public void CreatNewSubscription(clsSubscription NewSub, String plan){
        NewSub.set_Plan(enPlan.valueOf(plan));
        switch(enPlan.valueOf(plan)){
            case BASIC:
                NewSub.set_Price(50);
                break;
            case STANDARD:
                NewSub.set_Price(100);
                break;
            case PREMIUM:
                NewSub.set_Price(150);
                break;
        }
        NewSub.set_StartDate(LocalDate.now());

    }

    @FXML private Label MessageLabel;
    @FXML private ChoiceBox PlanChoiceBox;
    @FXML private TextField UsernameField;
    @FXML private PasswordField PasswordField;

    @FXML private StackPane rootPane;
    @FXML private ImageView backgroundImageView;
    @FXML
    private void initialize() {
        backgroundImageView.fitWidthProperty().bind(rootPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(rootPane.heightProperty());
        PlanChoiceBox.getItems().addAll("BASIC", "STANDARD", "PREMIUM");
        PlanChoiceBox.setValue("BASIC");
    }

    public void RenewSubscription(ActionEvent e){
        String username = UsernameField.getText().trim();
        String password = PasswordField.getText().trim();
        String plan = PlanChoiceBox.getValue().toString();

        if(username.isEmpty() || password.isEmpty()){
            MessageLabel.setText("Please fill all fields");
            MessageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if(!clsUser.IsUserExist(username)){
            MessageLabel.setText("USERNAME DOESN'T EXIST");
            MessageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if(clsUser.FindUser(username,password) == null){
            MessageLabel.setText("PASSWORD IS INVALID");
            MessageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        MessageLabel.setText("Subscription Renewed successfully!");
        MessageLabel.setStyle("-fx-text-fill: #00ff88;");

        clsSubscription NewSub = clsSubscription.GetAddNewObjectSubscription(username);
        CreatNewSubscription(NewSub,plan);
        NewSub.Save();
    }

    public void ReturnToUserAuthMenu(ActionEvent e){
        CNavigators.ShowUserAuth();
    }
}
