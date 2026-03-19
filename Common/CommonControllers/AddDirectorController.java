package UI.Common.CommonControllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.clsDirector;
import models.clsAdminLog;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;



public class AddDirectorController {

    @FXML private TextField txtID;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private DatePicker dateBirth;
    @FXML private ComboBox<String> comboGender;
    @FXML private TextField txtNationality;
    @FXML private TextField txtSocialMedia;
    @FXML private TextArea txtDescription;

    @FXML
    public void initialize() {
        comboGender.getItems().addAll("Male", "Female");
    }

    private boolean isValidUrl(String url) {
        return url.matches("^(https?://).+");
    }

    @FXML
    private void handleSave() {

        String id = txtID.getText().trim();

        if (clsDirector.IsDirectorExists(id)) {
            showAlert("Error", "Director ID already exists.");
            return;
        }

        if (dateBirth.getValue() == null) {
            showAlert("Error", "Birth date is required.");
            return;
        }

        if (comboGender.getValue() == null) {
            showAlert("Error", "Please select gender.");
            return;
        }

        if (!isValidUrl(txtSocialMedia.getText())) {
            showAlert("Error", "Social media link must start with http:// or https://");
            return;
        }

        clsDirector newDirector = clsDirector.GetAddNewObjectDirector(id);

        newDirector.set_FirstName(txtFirstName.getText());
        newDirector.set_LastName(txtLastName.getText());
        newDirector.set_BirthDate(dateBirth.getValue());
        newDirector.set_Gender(comboGender.getValue());
        newDirector.setNationality(txtNationality.getText());
        newDirector.setSocialMediaLink(txtSocialMedia.getText());

        clsDirector.enSaveResult result = newDirector.SaveD();

        switch (result) {

            case SVSUCCEDED:

                boolean logSaved = clsAdminLog.InsertingAdminLog(
                        enAction.ADD_DIRECTOR,
                        enTargetModel.DIRECTOR,
                        id,
                        txtDescription.getText()
                );

                if (!logSaved) {
                    newDirector.DELETED();
                    showAlert("Error",
                            "Director saved but log failed. Operation rolled back.");
                    return;
                }

                showAlert("Success", "Director created successfully.");
                clearFields();
                break;

            case SvFailedEmptyObject:
                showAlert("Error", "Save failed: Empty object.");
                break;

            case SvFailedObjectAlreadyExists:
                showAlert("Error", "Director already exists.");
                break;

            case SvFailedInvalidInsertation:
                showAlert("Error", "Invalid insertion process.");
                break;
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        txtID.clear();
        txtFirstName.clear();
        txtLastName.clear();
        dateBirth.setValue(null);
        comboGender.setValue(null);
        txtNationality.clear();
        txtSocialMedia.clear();
        txtDescription.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
