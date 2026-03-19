package UI.Common.CommonControllers;


import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import models.clsMovie;

import java.util.List;

public class MovieDetailsController {

    private clsMovie movie;

    @FXML
    private Label titleLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private Label castLabel;

    @FXML
    private Label directorLabel;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ImageView posterImage;

    @FXML private StackPane rootPane;
    @FXML private ImageView backgroundImage;

    @FXML
    private void initialize() {
        if (backgroundImage != null && rootPane != null) {
            backgroundImage.fitWidthProperty().bind(rootPane.widthProperty());
            backgroundImage.fitHeightProperty().bind(rootPane.heightProperty());
        }
    }

    public void setMovie(clsMovie movie) {
        if (movie == null) return;

        this.movie = movie;


        titleLabel.setText(movie.getTitle());

        descriptionArea.setText(movie.get_Description());
        String genres = joinList(movie.getGenres());
        String languages = joinList(movie.getLanguages());
        String casts = joinList(movie.getCasts());

        // Info block
        infoLabel.setText(
                "⭐ Rating: " + movie.getRating() +
                        "   |   ⏱ Duration: " + movie.getDurationMinutes() + " min\n" +
                        "🌍 Languages: " + languages + "\n" +
                        "🎬 Genres: " + genres
        );


        castLabel.setText("Cast: " + casts);
        directorLabel.setText("Director: " + movie.getDirectorName());


        loadPoster(movie.getPosterPath());
    }


    private String joinList(List<String> list) {
        if (list == null || list.isEmpty()) return "N/A";
        return String.join(", ", list);
    }



    private void loadPoster(String path) {
        try {
            if (path == null || path.isBlank()) {
                posterImage.setImage(null);
                return;
            }

            Image img = new Image(path, true);
            posterImage.setImage(img);

        } catch (Exception e) {
            posterImage.setImage(null);
        }
    }

    @FXML
    private void onWatch(){
        CNavigators.ShowWatchScreen(movie);
    }

    @FXML
    private void onBack() {
        CNavigators.ShowMoviesHome();

    }
}

