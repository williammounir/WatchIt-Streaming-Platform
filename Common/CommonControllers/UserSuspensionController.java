package UI.Common.CommonControllers;


import java.time.LocalDate;

import UI.Admin.MainAdmin.enAction;
import UI.Admin.MainAdmin.enTargetModel;
import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.clsAdminLog;
import models.clsSuspention;
import models.clsUser;
import session.CurrentAdmin;

public class UserSuspensionController {

    @FXML
    private TextField txtUserID;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private TextArea txtReason;

    @FXML
    private RadioButton rbSuspend;

    @FXML
    private RadioButton rbWithdraw;

    @FXML
    private ToggleGroup actionGroup;


    @FXML
    private void onSuspendSelected() {

        dateEnd.setDisable(false);
        txtReason.setDisable(false);

    }



    @FXML
    private void onWithdrawSelected() {

        dateEnd.setDisable(true);
        txtReason.setDisable(true);

    }



    @FXML
    private void onApplyAction() {

        String userID = txtUserID.getText().trim();

        if (userID.isEmpty()) {
            showAlert("Error", "Please enter User ID.");
            return;
        }

        if (!clsUser.IsUserExist(userID)) {
            showAlert("Error", "User does not exist.");
            return;
        }


        if (rbSuspend.isSelected()) {

            if (clsSuspention.IsUserSusbended(userID)) {
                showAlert("Info", "User is already suspended.");
                return;
            }

            LocalDate endDate = dateEnd.getValue();

            if (endDate == null) {
                showAlert("Error", "Please select end date.");
                return;
            }

            if (endDate.isBefore(LocalDate.now())) {
                showAlert("Error", "End date cannot be before today.");
                return;
            }

            String reason = txtReason.getText().trim();

            clsSuspention suspension = new clsSuspention(
                    userID,
                    CurrentAdmin.getCurrentAdmin().get_UserName(),
                    LocalDate.now(),
                    null,
                    null
            );

            suspension.set_EndDate(endDate);
            suspension.setReason(reason);

            if (suspension.SuspendUser()) {
                showAlert("Success", "User suspended successfully.");
                clearFields();
                String description = String.format(
                        "User suspended until %s. Reason: %s",
                        endDate.toString(),
                        reason.isEmpty() ? "No reason provided" : reason
                );

                clsAdminLog.InsertingAdminLog(
                        enAction.SUSPEND_USER,
                        enTargetModel.USER,
                        userID,
                        description
                );
            } else {
                showAlert("Error", "Suspension failed.");
            }

        }


        else if (rbWithdraw.isSelected()) {

            if (!clsSuspention.IsUserSusbended(userID)) {
                showAlert("Info", "User is not suspended.");
                return;
            }

            if (clsSuspention.WithdrawSusbention(userID)) {
                showAlert("Success", "Suspension withdrawn successfully.");
                clearFields();
                String description = "Suspension withdrawn by admin.";

                clsAdminLog.InsertingAdminLog(
                        enAction.USER_WITHDRAW_SUSPENSION,
                        enTargetModel.USER,
                        userID,
                        description
                );
            } else {
                showAlert("Error", "Failed to withdraw suspension.");
            }

        }

        else {
            showAlert("Error", "Please select an action.");
        }

    }



    @FXML
    private void onBack() {


        CNavigators.ShowAdminMainMenu();

    }



    private void showAlert(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }



    private void clearFields() {

        txtUserID.clear();
        txtReason.clear();
        dateEnd.setValue(null);

    }

}
