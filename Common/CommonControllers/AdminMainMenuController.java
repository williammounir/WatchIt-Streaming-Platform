package UI.Common.CommonControllers;


import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import session.CurrentAdmin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminMainMenuController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Label lblAdminName;

    @FXML
    private Label lblDate;


    private static StackPane staticContentArea;

    @FXML
    public void initialize() {
        staticContentArea = contentArea;
        lblAdminName.setText(CurrentAdmin.getCurrentAdmin().getFullName());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        lblDate.setText(LocalDate.now().format(formatter));
    }



    @FXML
    private void handleManageAdmins() {

        loadScreen("/UI/Common/CommonFXML/ManageAdmins.fxml");
    }

    @FXML
    private void handleManageCast() {

        loadScreen("/UI/Common/CommonFXML/ManageCastMenu.fxml");
    }

    @FXML
    private void handleManageDirectors() {
        loadScreen("/UI/Common/CommonFXML/ManageDirectorsMenu.fxml");
    }

    @FXML
    private void handleManageMovies() {
        loadScreen("/UI/Common/CommonFXML/ManageMoviesMenu.fxml");
    }

    @FXML
    private void handleShowRevenue() {
        loadScreen("/UI/Common/CommonFXML/MonthlyRevenue.fxml");
    }

    @FXML
    private void handleUserSuspension() {
       loadScreen("/UI/Common/CommonFXML/UserSuspension.fxml");
    }

    @FXML
    private void handleAdminsActivityLog() {
        loadScreen("/UI/Common/CommonFXML/AdminActivityLog.fxml");
    }
    @FXML
    private void handleSystemStatistics() {
        loadScreen("/UI/Common/CommonFXML/SystemStatistics.fxml");
    }

    @FXML
    private void handleLogout() {
        CurrentAdmin.clear();
        CNavigators.ShowAdminLogin();
    }


    private void loadScreen(String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            contentArea.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadIntoContentArea(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(
                    AdminMainMenuController.class.getResource(fxmlPath)
            );
            staticContentArea.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}