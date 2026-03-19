package UI.Common.CommonControllers;


import Main.Mainfx;
import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import models.Movie_Genere;
import models.Movie_Language;
import models.clsMovie;

import java.util.ArrayList;
import java.util.List;

public class SearchScreenController {


    @FXML private ImageView backgroundImageView;

    @FXML private TextField searchField;
    @FXML private ComboBox<String> genreBox;
    @FXML private ComboBox<String> languageBox;
    @FXML private ComboBox<String> ratingBox;

    @FXML private FlowPane resultsPane;
    @FXML private Label emptyLabel;


    @FXML
    private void initialize() {


        backgroundImageView.fitWidthProperty().bind(Mainfx.getPrimaryStage().widthProperty());
        backgroundImageView.fitHeightProperty().bind(Mainfx.getPrimaryStage().heightProperty());

        loadFilters();
    }


    private void loadFilters() {


        for (Movie_Genere.genres g : Movie_Genere.genres.values()) {
            genreBox.getItems().add(g.name());
        }

        languageBox.getItems().addAll(
                Movie_Language.getAllLanguges()
        );

        ratingBox.getItems().addAll(
                "1", "2", "3", "4", "5"
        );
    }


    @FXML
    private void onSearch() {

        String text = (searchField.getText() == null) ? "" : searchField.getText().trim();
        String genre = genreBox.getValue();
        String language = languageBox.getValue();

        double rating = 0;
        if (ratingBox.getValue() != null) {
            rating = Double.parseDouble(ratingBox.getValue());
        }

        if (text.isEmpty() && genre == null && language == null && rating == 0) {
            showResults(clsMovie.GetAllMovies());
            return;
        }
        List<clsMovie> results = searchMovies(text, genre, language, rating);

        showResults(results);
    }


    private List<clsMovie> searchMovies(String text, String genre, String language, double rating) {

        List<clsMovie> allMovies = clsMovie.GetAllMovies();
        List<clsMovie> filtered = new ArrayList<>();

        for (clsMovie movie : allMovies) {

            boolean match = true;


            if (!text.isEmpty()) {
                String lower = text.toLowerCase();

                boolean titleMatch = movie.getTitle().toLowerCase().contains(lower);
                boolean directorMatch = movie.getDirector().toLowerCase().contains(lower);

                boolean castMatch = false;
                for (String cast : movie.getCasts()) {
                    if (cast.toLowerCase().contains(lower)) {
                        castMatch = true;
                        break;
                    }
                }

                if (!(titleMatch || directorMatch || castMatch)) {
                    match = false;
                }
            }


            if (genre != null && !genre.isEmpty()) {
                if (!movie.getGenres().contains(genre)) {
                    match = false;
                }
            }

            if (language != null && !language.isEmpty()) {
                if (!movie.getLanguages().contains(language)) {
                    match = false;
                }
            }

            if (movie.getRating() < rating) {
                match = false;
            }

            if (match) filtered.add(movie);
        }

        return filtered;
    }



    private void showResults(List<clsMovie> movies) {

        resultsPane.getChildren().clear();

        if (movies == null || movies.isEmpty()) {
            emptyLabel.setVisible(true);
            return;
        }

        emptyLabel.setVisible(false);

        for (clsMovie movie : movies) {
            try {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/UI/Common/CommonFXML/MovieCard.fxml")
                );

                Parent card = loader.load();

                MovieCardController controller = loader.getController();
                controller.setMovie(movie);

                resultsPane.getChildren().add(card);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onBack() {
        CNavigators.ShowMoviesHome();
    }

    @FXML
    private void onClear() {
        searchField.clear();
        genreBox.setValue(null);
        languageBox.setValue(null);
        ratingBox.setValue(null);
        resultsPane.getChildren().clear();
        emptyLabel.setVisible(false);
    }
}