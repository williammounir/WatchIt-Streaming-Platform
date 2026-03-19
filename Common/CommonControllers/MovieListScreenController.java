package UI.Common.CommonControllers;

import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import models.clsMovie;

import java.util.List;

public class MovieListScreenController {

    @FXML
    private FlowPane moviesFlowPane;

    @FXML
    private Label screenTitle;

    // Call this when opening screen
    public void loadMovies(String title, List<clsMovie> movies) {
        screenTitle.setText(title);
        moviesFlowPane.getChildren().clear();

        try {
            for (clsMovie movie : movies) {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("UI/Common/CommonFXML/MovieCard.fxml")
                );

                VBox card = loader.load();

                MovieCardController controller = loader.getController();
                controller.setMovie(movie);

                moviesFlowPane.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBack() {
        CNavigators.ShowMoviesHome();
    }
}


