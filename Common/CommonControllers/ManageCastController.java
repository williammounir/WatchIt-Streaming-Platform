package UI.Common.CommonControllers;
import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;

public class ManageCastController {


    @FXML
    private void handleAddCast() {
        AdminMainMenuController.loadIntoContentArea("/UI/Common/CommonFXML/AddCast.fxml");
    }

    @FXML
    private void handleDeleteCast() {
        AdminMainMenuController.loadIntoContentArea("/UI/Common/CommonFXML/DeleteCast.fxml");
    }

    @FXML
    private void handleUpdateCast() {
        AdminMainMenuController.loadIntoContentArea("/UI/Common/CommonFXML/UpdateCast.fxml");
    }

    @FXML
    private void handleBack() {

        CNavigators.ShowAdminMainMenu();
    }
}