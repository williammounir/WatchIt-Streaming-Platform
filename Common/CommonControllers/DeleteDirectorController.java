package UI.Common.CommonControllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.clsDirector;
import models.clsAdminLog;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;

public class DeleteDirectorController {

    @FXML private TextField txtSearchID;
    @FXML private GridPane infoPane;
    @FXML private Label lblFullName;
    @FXML private Label lblBirthDate;
    @FXML private Label lblGender;
    @FXML private Label lblNationality;
    @FXML private Label lblSocialMedia;
    @FXML private TextArea txtDescription;
    @FXML private Button btnDelete;
    @FXML private Label lblDescription;

    private clsDirector targetDirector;

    @FXML
    private void handleSearch() {

        String id = txtSearchID.getText().trim();

        if (!clsDirector.IsDirectorExists(id)) {
            showAlert("Error", "Director does not exist.");
            return;
        }

        targetDirector = clsDirector.FindDirector(id);

        lblFullName.setText(targetDirector.getFullName());
        lblBirthDate.setText(targetDirector.get_BirthDate().toString());
        lblGender.setText(targetDirector.get_Gender());
        lblNationality.setText(targetDirector.getNationality());
        lblSocialMedia.setText(targetDirector.getSocialMediaLink());

        infoPane.setVisible(true);
        txtDescription.setVisible(true);
        lblDescription.setVisible(true);
        btnDelete.setVisible(true);
    }

    @FXML
    private void handleDelete() {

        if (targetDirector.DELETED()) {

            boolean logSaved = clsAdminLog.InsertingAdminLog(
                    enAction.DELETE_DIRECTOR,
                    enTargetModel.DIRECTOR,
                    targetDirector.get_ID(),
                    txtDescription.getText()
            );

            if (!logSaved) {
                targetDirector.RetriveDeletedDirector();
                showAlert("Error", "Delete succeeded but log failed. Rollback executed.");
                return;
            }

            showAlert("Success", "Director deleted successfully.");
            clearFields();

        } else {
            showAlert("Error", "Delete process failed.");
        }
    }

    private void clearFields() {
        txtSearchID.clear();
        txtDescription.clear();
        infoPane.setVisible(false);
        txtDescription.setVisible(false);
        lblDescription.setVisible(false);
        btnDelete.setVisible(false);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
