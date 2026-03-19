package UI.Common.CommonControllers;

import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import models.clsSubscription;
import models.clsUser;
import models.enPlan;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class User_Signup_Controller {

    @FXML
    private StackPane rootPane;

    @FXML
    private ImageView backgroundImageView;

    @FXML private TextField UsernameField;
    @FXML private TextField FirstNameField;
    @FXML private TextField LastNameField;
    @FXML private DatePicker BirthDatePicker;
    @FXML private ComboBox<String> GenderBox;
    @FXML private TextField EmailField;
    @FXML private PasswordField PasswordField;
    @FXML private PasswordField ConfirmPasswordField;
    @FXML private Label MessageLabel;
    @FXML private ChoiceBox PlanChoiceBox;

    @FXML
    public void initialize() {
        GenderBox.getItems().addAll("Male", "Female");
        backgroundImageView.fitWidthProperty().bind(rootPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(rootPane.heightProperty());
        PlanChoiceBox.getItems().addAll("BASIC", "STANDARD", "PREMIUM");
        PlanChoiceBox.setValue("BASIC");
    }

    public void CreatNewUser(clsUser NewUser, String first, String last, String email, String password,
    LocalDate birth, String gender){

        NewUser.set_FirstName(first);
        NewUser.set_LastName(last);
        NewUser.set_Email(email);
        NewUser.set_Password(password);
        NewUser.set_BirthDate(birth);
        NewUser.set_Gender(gender);
    }

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

    @FXML
    private void CreateAccount() {

        String username = UsernameField.getText().trim();
        String first = FirstNameField.getText().trim();
        String last = LastNameField.getText().trim();
        String email = EmailField.getText().trim();
        String password = PasswordField.getText();
        String confirm = ConfirmPasswordField.getText();
        LocalDate birth = BirthDatePicker.getValue();
        String gender = GenderBox.getValue();
        String Plan = PlanChoiceBox.getValue().toString();


        if (username.isEmpty() || first.isEmpty() || last.isEmpty()
                || email.isEmpty() || password.isEmpty() || confirm.isEmpty()
                || birth == null || gender == null) {

            MessageLabel.setText("Please fill all fields");
            MessageLabel.setStyle("-fx-text-fill: red;");
            return;
        }


        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            MessageLabel.setText("Invalid email format");
            MessageLabel.setStyle("-fx-text-fill: red;");
            return;
        }


        if (!password.equals(confirm)) {
            MessageLabel.setText("Passwords do not match");
            MessageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (password.length() < 6) {
            MessageLabel.setText("Password must be at least 6 characters");
            MessageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        int age = Period.between(birth, LocalDate.now()).getYears();
        if (age < 13) {
            MessageLabel.setText("You must be at least 13 years old");
            MessageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if(clsUser.IsUserExist(username)){
            MessageLabel.setText("UserName Already Exists!");
            MessageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        MessageLabel.setText("Account created successfully!");
        MessageLabel.setStyle("-fx-text-fill: #00ff88;");

        clsUser NewUser = clsUser.GetAddNewObject(username);
        CreatNewUser(NewUser,first,last,email,password,birth,gender);
        NewUser.Save();
        clsSubscription NewSub = clsSubscription.GetAddNewObjectSubscription(username);
        CreatNewSubscription(NewSub,Plan);
        NewSub.Save();

    }

    @FXML
    private void ReturnToUserAuth() {
         CNavigators.ShowUserAuth();
    }
}
