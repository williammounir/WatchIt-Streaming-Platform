package UI.Common.CommonControllers;

import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import models.clsUser;
import session.CurrentUser;

import java.util.regex.Pattern;

public class EditProfileController {

    @FXML private TextField usernameField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private ImageView backgroundImageView;
    @FXML private StackPane rootPane;
    public void initialize() {
        backgroundImageView.fitWidthProperty().bind(rootPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(rootPane.heightProperty());
        loadUserData();
        setupActions();
    }

    private void loadUserData() {

        clsUser user = CurrentUser.getCurrentUser();

        usernameField.setText(user.get_UserName());
        firstNameField.setText(user.get_FirstName());
        lastNameField.setText(user.get_LastName());
        emailField.setText(user.get_Email());
    }

    private void setupActions() {

        saveButton.setOnAction(e -> saveChanges());
        cancelButton.setOnAction(e -> CNavigators.ShowProfileScreen());
    }

    private void saveChanges() {

        clsUser user = CurrentUser.getCurrentUser();

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // ===== VALIDATIONS =====

        if (firstName.isEmpty()) {
            showError("First name cannot be empty.");
            return;
        }

        if (lastName.isEmpty()) {
            showError("Last name cannot be empty.");
            return;
        }

        if (!isValidEmail(email)) {
            showError("Invalid email format.");
            return;
        }

        if (!password.isEmpty()) {

            if (password.length() < 6) {
                showError("Password must be at least 6 characters.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match.");
                return;
            }

            user.set_Password(password);
        }

        // ===== APPLY CHANGES =====

        user.set_FirstName(firstName);
        user.set_LastName(lastName);
        user.set_Email(email);

        user.Save();

        CNavigators.ShowProfileScreen();
    }

    private void showError(String message) {
        messageLabel.setText(message);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }
}