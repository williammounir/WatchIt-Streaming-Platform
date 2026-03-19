package UI.Common.CommonControllers;
import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;

public class ManageDirectorsController {

    @FXML
    private void handleAddDirector() {
        AdminMainMenuController.loadIntoContentArea(
                "/UI/Common/CommonFXML/AddDirector.fxml"
        );
    }

    @FXML
    private void handleDeleteDirector() {
        AdminMainMenuController.loadIntoContentArea(
                "/UI/Common/CommonFXML/DeleteDirector.fxml"
        );
    }

    @FXML
    private void handleUpdateDirector() {
        AdminMainMenuController.loadIntoContentArea(
                "/UI/Common/CommonFXML/UpdateDirector.fxml"
        );
    }

    @FXML
    private void handleBack() {
        CNavigators.ShowAdminMainMenu();
    }
}