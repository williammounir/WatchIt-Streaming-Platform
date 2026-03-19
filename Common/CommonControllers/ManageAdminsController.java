package UI.Common.CommonControllers;


import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;

public class ManageAdminsController {

    @FXML
    private void handleAddAdmin() {
        AdminMainMenuController.loadIntoContentArea("/UI/Common/CommonFXML/AddAdmin.fxml");
    }

    @FXML
    private void handleDeleteAdmin() {
        AdminMainMenuController.loadIntoContentArea("/UI/Common/CommonFXML/DeleteAdmin.fxml");
    }

    @FXML
    private void handleUpdateAdmin() {
        AdminMainMenuController.loadIntoContentArea("/UI/Common/CommonFXML/UpdateAdmin.fxml");
    }

    @FXML
    private void handleBack() {
        CNavigators.ShowAdminMainMenu();
    }


}