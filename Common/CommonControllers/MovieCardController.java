package UI.Common.CommonControllers;

import Main.Mainfx;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.clsMovie;

public class MovieCardController {

    @FXML
    private VBox cardRoot;

    @FXML
    private ImageView posterImage;

    @FXML
    private Label movieTitle;

    @FXML
    private Label ratingLabel;

    @FXML
    private Label durationLabel;

    private clsMovie movie;


    public void setMovie(clsMovie movie) {
        if (movie == null) return;

        this.movie = movie;

        movieTitle.setText(movie.getTitle());
        ratingLabel.setText("⭐ " + movie.getRating());
        durationLabel.setText(movie.getDurationMinutes() + "m");

        try {
            if (movie.getPosterPath() != null && !movie.getPosterPath().isBlank()) {
                posterImage.setImage(new Image(movie.getPosterPath(), true));
            }
        } catch (Exception e) {
            posterImage.setImage(null);
        }
    }

    @FXML
    private void initialize() {

        // Hover effect
        cardRoot.setOnMouseEntered(e -> {
            cardRoot.setStyle("-fx-background-color: #2a2a2a; -fx-background-radius: 10;");
            cardRoot.setScaleX(1.05);
            cardRoot.setScaleY(1.05);
        });

        cardRoot.setOnMouseExited(e -> {
            cardRoot.setStyle("-fx-background-color: #1e1e1e; -fx-background-radius: 10;");
            cardRoot.setScaleX(1);
            cardRoot.setScaleY(1);
        });


        cardRoot.setOnMouseClicked(e -> openMovieDetails());
    }


    private void openMovieDetails() {
        if (movie == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/UI/Common/CommonFXML/MovieDetails.fxml")
            );

            Parent root = loader.load();

            MovieDetailsController controller = loader.getController();
            controller.setMovie(movie);


            Stage stage = Mainfx.getPrimaryStage();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
