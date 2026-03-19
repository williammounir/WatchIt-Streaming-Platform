package UI.Common.CommonControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.clsCast;
import models.clsAdminLog;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import java.util.regex.Pattern;

public class AddCastController {

    @FXML private TextField txtID;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private DatePicker dateBirth;
    @FXML private ComboBox<String> comboGender;
    @FXML private TextField txtNationality;
    @FXML private TextField txtSocialLink;
    @FXML private TextArea txtDescription;


    @FXML
    public void initialize() {
        comboGender.getItems().addAll("Male", "Female");
    }

    private boolean isValidURL(String url) {
        String regex = "^(https?://)?(www\\.)?[A-Za-z0-9.-]+\\.[A-Za-z]{2,}.*$";
        return Pattern.matches(regex, url);
    }

    @FXML
    private void handleSave() {

        String id = txtID.getText().trim();

        if (clsCast.IsCastExists(id)) {
            showAlert("Error", "Cast ID already exists.");
            return;
        }

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

        clsCast newCast = clsCast.GetAddNewObjectCast(id);

        newCast.set_FirstName(txtFirstName.getText());
        newCast.set_LastName(txtLastName.getText());
        newCast.set_BirthDate(dateBirth.getValue());
        newCast.set_Gender(comboGender.getValue());
        newCast.setNationality(txtNationality.getText());
        newCast.setSocialMediaLink(txtSocialLink.getText());

        clsCast.enSaveResult result = newCast.SaveC();

        switch (result) {

            case SVSUCCEDED:

                boolean logStored = clsAdminLog.InsertingAdminLog(
                        enAction.ADD_CAST,
                        enTargetModel.CAST,
                        id,
                        txtDescription.getText()
                );

                if (!logStored) {
                    newCast.DELETEC();
                    showAlert("Error", "Cast saved but log failed. Operation rolled back.");
                    return;
                }

                showAlert("Success", "Cast created successfully.");
                clearFields();
                break;

            case SvFailedEmptyObject:
                showAlert("Error", "Save failed: empty object.");
                break;

            case SvFailedObjectAlreadyExists:
                showAlert("Error", "Cast already exists.");
                break;
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    @FXML


    private void clearFields() {
        txtID.clear();
        txtFirstName.clear();
        txtLastName.clear();
        dateBirth.setValue(null);
        comboGender.setValue(null);
        txtNationality.clear();
        txtSocialLink.clear();
        txtDescription.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
