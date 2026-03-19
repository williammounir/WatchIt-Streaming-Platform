package UI.Common.CommonControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.clsAdmin;
import models.clsAdminLog;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;


import java.util.regex.Pattern;

public class AddAdminController {

    @FXML private TextField txtUsername;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private DatePicker dateBirth;
    @FXML private ComboBox<String> comboGender;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private TextArea txtDescription;

    @FXML
    public void initialize() {
        comboGender.getItems().addAll("Male", "Female");
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$");
    }

    @FXML
    private void handleSave() {

        String username = txtUsername.getText().trim();

        if (clsAdmin.IsAdminExists(username)) {
            showAlert("Error", "Username already exists.");
            return;
        }

        if (!isValidEmail(txtEmail.getText())) {
            showAlert("Error", "Invalid email format.");
            return;
        }

        if (!isValidPassword(txtPassword.getText())) {
            showAlert("Error", "Password must contain:\n"
                    + "- At least 8 characters\n"
                    + "- Uppercase letter\n"
                    + "- Lowercase letter\n"
                    + "- Number\n"
                    + "- Special character");
            return;
        }

        clsAdmin newAdmin = clsAdmin.GetAddNewObjectAdmin(username);

        newAdmin.set_FirstName(txtFirstName.getText());
        newAdmin.set_LastName(txtLastName.getText());
        newAdmin.set_BirthDate(dateBirth.getValue());
        newAdmin.set_Gender(comboGender.getValue());
        newAdmin.set_Email(txtEmail.getText());
        newAdmin.set_Password(txtPassword.getText());

        clsAdmin.enSaveResult result = newAdmin.SaveA();

        if (result == clsAdmin.enSaveResult.SVSUCCEDED) {

            clsAdminLog.InsertingAdminLog(
                    enAction.ADD_ADMIN,
                    enTargetModel.ADMIN,
                    username,
                    txtDescription.getText()
            );

            showAlert("Success", "Admin created successfully.");
            clearFields();

        } else {
            showAlert("Error", "Failed to save admin.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        txtUsername.clear();
        txtFirstName.clear();
        txtLastName.clear();
        dateBirth.setValue(null);
        comboGender.setValue(null);
        txtEmail.clear();
        txtPassword.clear();
        txtDescription.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}