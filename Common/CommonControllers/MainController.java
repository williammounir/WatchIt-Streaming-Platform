package UI.Common.CommonControllers;

import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class MainController {

    @FXML private StackPane rootPane;
    @FXML private ImageView backgroundImageView;

    @FXML
    private void initialize() {

        backgroundImageView.fitWidthProperty().bind(rootPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(rootPane.heightProperty());
    }

    public void AdminButton(ActionEvent event){
        CNavigators.ShowAdminLogin();
    }

    public void UserButton(ActionEvent event){
        CNavigators.ShowUserAuth();
    }

    public void ExitButton(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Close Watch It?");
        alert.setContentText("Are you sure you want to exit the application?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();   // closes JavaFX safely
        }
    }

}
