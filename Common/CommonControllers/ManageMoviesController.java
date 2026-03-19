package UI.Common.CommonControllers;


import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ManageMoviesController {

    @FXML
    private void handleBack(ActionEvent event) {

        CNavigators.ShowAdminMainMenu();
    }

    @FXML
    private void handleAddMovie(ActionEvent event) {
        AdminMainMenuController.loadIntoContentArea(
                "/UI/Common/CommonFXML/AddMovie.fxml"
        );
    }

    @FXML
    private void handleDeleteMovie(ActionEvent event) {
        AdminMainMenuController.loadIntoContentArea(
                "/UI/Common/CommonFXML/DeleteMovie.fxml"
        );
    }

    @FXML
    private void handleUpdateMovie(ActionEvent event) {
        AdminMainMenuController.loadIntoContentArea(
                "/UI/Common/CommonFXML/UpdateMovie.fxml"
        );
    }
}
