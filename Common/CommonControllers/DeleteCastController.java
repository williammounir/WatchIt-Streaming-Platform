package UI.Common.CommonControllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.clsCast;
import models.clsAdminLog;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;

public class DeleteCastController {

    @FXML private TextField txtID;
    @FXML private Label lblFullName;
    @FXML private Label lblBirthDate;
    @FXML private Label lblGender;
    @FXML private Label lblNationality;
    @FXML private Label lblSocialLink;
    @FXML private GridPane castInfoPane;
    @FXML private TextArea txtDescription;
    @FXML private Button btnDelete;

    private clsCast targetCast;

    @FXML
    private void handleSearch() {

        String id = txtID.getText().trim();

        if (!clsCast.IsCastExists(id)) {
            showAlert("Error", "Cast does not exist.");
            return;
        }

        targetCast = clsCast.FindCast(id);

        lblFullName.setText(targetCast.getFullName());
        lblBirthDate.setText(targetCast.get_BirthDate().toString());
        lblGender.setText(targetCast.get_Gender());
        lblNationality.setText(targetCast.getNationality());
        lblSocialLink.setText(targetCast.getSocialMediaLink());

        castInfoPane.setVisible(true);
        txtDescription.setVisible(true);
        btnDelete.setVisible(true);
    }

    @FXML
    private void handleDelete() {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setContentText("Are you sure you want to delete this cast?");
        confirm.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                if (targetCast.DELETEC()) {

                    boolean logStored = clsAdminLog.InsertingAdminLog(
                            enAction.DELETE_CAST,
                            enTargetModel.CAST,
                            targetCast.get_ID(),
                            txtDescription.getText()
                    );

                    if (!logStored) {
                        targetCast.RetriveDeletedCast();
                        showAlert("Error", "Delete succeeded but log failed. Operation rolled back.");
                        return;
                    }

                    showAlert("Success", "Cast deleted successfully.");
                    resetScreen();

                } else {
                    showAlert("Error", "Delete process failed.");
                }
            }
        });
    }

    private void resetScreen() {
        txtID.clear();
        txtDescription.clear();
        castInfoPane.setVisible(false);
        txtDescription.setVisible(false);
        btnDelete.setVisible(false);
        targetCast = null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
