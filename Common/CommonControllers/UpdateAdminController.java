package UI.Common.CommonControllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.clsAdmin;
import models.clsAdminLog;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import session.CurrentAdmin;

import java.util.regex.Pattern;

public class UpdateAdminController {

    @FXML private TextField txtSearchUsername;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private DatePicker dateBirth;
    @FXML private ComboBox<String> comboGender;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private TextArea txtDescription;
    @FXML private GridPane formPane;
    @FXML private Button btnUpdate;

    private clsAdmin targetAdmin;

    @FXML
    public void initialize() {
        comboGender.getItems().addAll("Male", "Female");
    }

    @FXML
    private void handleSearch() {

        String username = txtSearchUsername.getText().trim();

        if (!clsAdmin.IsAdminExists(username)) {
            showAlert("Error", "Admin does not exist.");
            return;
        }

        targetAdmin = clsAdmin.FindAdmin(username);

        txtFirstName.setText(targetAdmin.get_FirstName());
        txtLastName.setText(targetAdmin.get_LastName());
        dateBirth.setValue(targetAdmin.get_BirthDate());
        comboGender.setValue(targetAdmin.get_Gender());
        txtEmail.setText(targetAdmin.get_Email());
        txtPassword.setText(targetAdmin.get_Password());

        formPane.setVisible(true);
        btnUpdate.setVisible(true);
    }

    @FXML
    private void handleUpdate() {

        if (!isValidEmail(txtEmail.getText())) {
            showAlert("Error", "Invalid email format.");
            return;
        }

        if (!isValidPassword(txtPassword.getText())) {
            showAlert("Error", "Weak password.");
            return;
        }

        targetAdmin.set_FirstName(txtFirstName.getText());
        targetAdmin.set_LastName(txtLastName.getText());
        targetAdmin.set_BirthDate(dateBirth.getValue());
        targetAdmin.set_Gender(comboGender.getValue());
        targetAdmin.set_Email(txtEmail.getText());
        targetAdmin.set_Password(txtPassword.getText());

        clsAdmin.enSaveResult result = targetAdmin.SaveA();

        if (result == clsAdmin.enSaveResult.SVSUCCEDED) {

            if (targetAdmin.get_UserName()
                    .equals(CurrentAdmin.getCurrentAdmin().get_UserName())) {
                CurrentAdmin.setCurrentAdmin(targetAdmin);
            }

            clsAdminLog.InsertingAdminLog(
                    enAction.UPDATE_ADMIN,
                    enTargetModel.ADMIN,
                    targetAdmin.get_UserName(),
                    txtDescription.getText()
            );

            showAlert("Success", "Admin updated successfully.");
        } else {
            showAlert("Error", "Update failed.");
        }
    }
    //https://www.facebook.com/actorname
    private boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email);
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}