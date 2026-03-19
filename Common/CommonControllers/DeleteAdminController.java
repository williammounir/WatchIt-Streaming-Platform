package UI.Common.CommonControllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.clsAdmin;
import models.clsAdminLog;
import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;

public class DeleteAdminController {

    @FXML private TextField txtUsername;
    @FXML private Label lblFullName;
    @FXML private Label lblEmail;
    @FXML private Label lblBirthDate;
    @FXML private Label lblGender;
    @FXML private GridPane adminInfoPane;
    @FXML private TextArea txtDescription;
    @FXML private Button btnDelete;

    private clsAdmin targetAdmin;

    @FXML
    private void handleSearch() {

        String username = txtUsername.getText().trim();

        if (!clsAdmin.IsAdminExists(username)) {
            showAlert("Error", "Admin does not exist.");
            return;
        }

        targetAdmin = clsAdmin.FindAdmin(username);

        lblFullName.setText(targetAdmin.getFullName());
        lblEmail.setText(targetAdmin.get_Email());
        lblBirthDate.setText(targetAdmin.get_BirthDate().toString());
        lblGender.setText(targetAdmin.get_Gender());

        adminInfoPane.setVisible(true);
        txtDescription.setVisible(true);
        btnDelete.setVisible(true);
    }

    @FXML
    private void handleDelete() {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setContentText("Are you sure you want to delete this admin?");
        confirm.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                if (targetAdmin.DELETEA()) {

                    boolean logStored = clsAdminLog.InsertingAdminLog(
                            enAction.DELETE_ADMIN,
                            enTargetModel.ADMIN,
                            targetAdmin.get_UserName(),
                            txtDescription.getText()
                    );

                    if (!logStored) {
                        targetAdmin.RetriveDeletedAdmin();
                        showAlert("Error", "Delete succeeded but log failed. Operation rolled back.");
                        return;
                    }

                    showAlert("Success", "Admin deleted successfully.");
                    resetScreen();

                } else {
                    showAlert("Error", "Delete process failed.");
                }
            }
        });
    }

    private void resetScreen() {
        txtUsername.clear();
        txtDescription.clear();
        adminInfoPane.setVisible(false);
        txtDescription.setVisible(false);
        btnDelete.setVisible(false);
        targetAdmin = null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}