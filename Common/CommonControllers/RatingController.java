package UI.Common.CommonControllers;

import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import models.clsMovie;
import models.clsUserWatchRecord;
import session.CurrentUser;

import java.time.LocalDate;

public class RatingController {

    @FXML private StackPane rootPane;
    @FXML private ImageView backgroundImage;

    @FXML private ImageView posterImage;
    @FXML private Label movieTitle;
    @FXML private Label ratingLabel;

    @FXML private Button star1;
    @FXML private Button star2;
    @FXML private Button star3;
    @FXML private Button star4;
    @FXML private Button star5;

    private clsMovie movie;
    private Double selectedRating = 0d;


    @FXML
    private void initialize() {


        if (backgroundImage != null && rootPane != null) {
            backgroundImage.fitWidthProperty().bind(rootPane.widthProperty());
            backgroundImage.fitHeightProperty().bind(rootPane.heightProperty());
        }
    }


    public void setMovie(clsMovie movie) {
        this.movie = movie;

        movieTitle.setText(movie.getTitle());

        try {

            String posterPath = getClass()
                    .getResource(movie.getPosterPath())
                    .toExternalForm();

            posterImage.setImage(new Image(posterPath));

        } catch (Exception e) {
            System.out.println("Poster load failed");
        }
    }


    @FXML private void rate1() { setRating(1d); }
    @FXML private void rate2() { setRating(2d); }
    @FXML private void rate3() { setRating(3d); }
    @FXML private void rate4() { setRating(4d); }
    @FXML private void rate5() { setRating(5d); }

    private void setRating(Double rating) {
        selectedRating = rating;
        ratingLabel.setText("Your rating: " + rating + " / 5");

        updateStars();
    }

    private void updateStars() {
        Button[] stars = {star1, star2, star3, star4, star5};

        for (int i = 0; i < stars.length; i++) {
            if (i < selectedRating) {
                stars[i].setStyle(
                        "-fx-font-size: 24px;" +
                                "-fx-text-fill: gold;" +
                                "-fx-background-color: transparent;");
            } else {
                stars[i].setStyle(
                        "-fx-font-size: 24px;" +
                                "-fx-text-fill: gray;" +
                                "-fx-background-color: transparent;");
            }
        }
    }

    private void SaveWatchRecord(){
        clsUserWatchRecord record = clsUserWatchRecord.GetAddNewObject(CurrentUser.getCurrentUser().get_UserName());
        record.setMovieId(movie.getId());
        record.setWatchDate(LocalDate.now());
        record.setRating(selectedRating);
        record.Save();
    }


    @FXML
    private void onSubmit() {

        if (selectedRating == null) {
            ratingLabel.setText("Please select a rating first");
            return;
        }

        try {
            SaveWatchRecord();
            movie.setRating(clsUserWatchRecord.getMovieAvarageRating(movie.getId()));
            movie.SaveM();
        } catch (Exception e) {
            System.out.println("Failed to save rating");
        }

        CNavigators.ShowMoviesHome();
    }

    @FXML
    private void onSkip() {
        selectedRating = 0d;
        SaveWatchRecord();
        CNavigators.ShowMoviesHome();
    }
}
