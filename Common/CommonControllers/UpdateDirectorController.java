package UI.Common.CommonControllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.clsDirector;
import models.clsAdminLog;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;

public class UpdateDirectorController {

    @FXML private TextField txtSearchID;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private DatePicker dateBirth;
    @FXML private ComboBox<String> comboGender;
    @FXML private TextField txtNationality;
    @FXML private TextField txtSocialMedia;
    @FXML private TextArea txtDescription;
    @FXML private GridPane formPane;
    @FXML private Button btnUpdate;

    private clsDirector targetDirector;

    @FXML
    public void initialize() {
        comboGender.getItems().addAll("Male", "Female");
    }

    @FXML
    private void handleSearch() {

        String id = txtSearchID.getText().trim();

        if (!clsDirector.IsDirectorExists(id)) {
            showAlert("Error", "Director does not exist.");
            return;
        }

        targetDirector = clsDirector.FindDirector(id);

        txtFirstName.setText(targetDirector.get_FirstName());
        txtLastName.setText(targetDirector.get_LastName());
        dateBirth.setValue(targetDirector.get_BirthDate());
        comboGender.setValue(targetDirector.get_Gender());
        txtNationality.setText(targetDirector.getNationality());
        txtSocialMedia.setText(targetDirector.getSocialMediaLink());

        formPane.setVisible(true);
        btnUpdate.setVisible(true);
    }

    @FXML
    private void handleUpdate() {

        targetDirector.set_FirstName(txtFirstName.getText());
        targetDirector.set_LastName(txtLastName.getText());
        targetDirector.set_BirthDate(dateBirth.getValue());
        targetDirector.set_Gender(comboGender.getValue());
        targetDirector.setNationality(txtNationality.getText());
        targetDirector.setSocialMediaLink(txtSocialMedia.getText());

        clsDirector.enSaveResult result = targetDirector.SaveD();

        if (result == clsDirector.enSaveResult.SVSUCCEDED) {

            clsAdminLog.InsertingAdminLog(
                    enAction.UPDATE_DIRECTOR,
                    enTargetModel.DIRECTOR,
                    targetDirector.get_ID(),
                    txtDescription.getText()
            );

            showAlert("Success", "Director updated successfully.");

        } else {
            showAlert("Error", "Update failed.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}