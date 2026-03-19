package UI.Common.CommonControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.clsCast;
import models.clsAdminLog;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;

import java.util.regex.Pattern;

public class UpdateCastController {

    @FXML private TextField txtSearchID;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private DatePicker dateBirth;
    @FXML private ComboBox<String> comboGender;
    @FXML private TextField txtNationality;
    @FXML private TextField txtSocialLink;
    @FXML private TextArea txtDescription;
    @FXML private GridPane formPane;
    @FXML private Button btnUpdate;

    private clsCast targetCast;

    @FXML
    public void initialize() {
        comboGender.getItems().addAll("Male", "Female");
    }

    @FXML
    private void handleSearch() {

        String id = txtSearchID.getText().trim();

        if (!clsCast.IsCastExists(id)) {
            showAlert("Error", "Cast does not exist.");
            return;
        }

        targetCast = clsCast.FindCast(id);

        txtFirstName.setText(targetCast.get_FirstName());
        txtLastName.setText(targetCast.get_LastName());
        dateBirth.setValue(targetCast.get_BirthDate());
        comboGender.setValue(targetCast.get_Gender());
        txtNationality.setText(targetCast.getNationality());
        txtSocialLink.setText(targetCast.getSocialMediaLink());

        formPane.setVisible(true);
        btnUpdate.setVisible(true);
    }

    @FXML
    private void handleUpdate() {

        if (dateBirth.getValue() == null) {
            showAlert("Error", "Please select birth date.");
            return;
        }

        if (comboGender.getValue() == null) {
            showAlert("Error", "Please select gender.");
            return;
        }

        if (!isValidURL(txtSocialLink.getText())) {
            showAlert("Error", "Invalid social media link.");
            return;
        }

        targetCast.set_FirstName(txtFirstName.getText());
        targetCast.set_LastName(txtLastName.getText());
        targetCast.set_BirthDate(dateBirth.getValue());
        targetCast.set_Gender(comboGender.getValue());
        targetCast.setNationality(txtNationality.getText());
        targetCast.setSocialMediaLink(txtSocialLink.getText());

        clsCast.enSaveResult result = targetCast.SaveC();

        if (result == clsCast.enSaveResult.SVSUCCEDED) {

            clsAdminLog.InsertingAdminLog(
                    enAction.UPDATE_CAST,
                    enTargetModel.CAST,
                    targetCast.get_ID(),
                    txtDescription.getText()
            );

            showAlert("Success", "Cast updated successfully.");
            resetScreen();

        } else {
            showAlert("Error", "Update failed.");
        }
    }

    private boolean isValidURL(String url) {
        String regex = "^(https?://)?(www\\.)?[A-Za-z0-9.-]+\\.[A-Za-z]{2,}.*$";
        return Pattern.matches(regex, url);
    }

    private void resetScreen() {
        txtSearchID.clear();
        txtFirstName.clear();
        txtLastName.clear();
        dateBirth.setValue(null);
        comboGender.setValue(null);
        txtNationality.clear();
        txtSocialLink.clear();
        txtDescription.clear();
        formPane.setVisible(false);
        btnUpdate.setVisible(false);
        targetCast = null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
