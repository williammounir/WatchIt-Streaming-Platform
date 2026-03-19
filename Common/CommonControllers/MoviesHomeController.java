package UI.Common.CommonControllers;

import Main.Mainfx;
import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import models.Movie_Genere;
import models.clsMovie;

import java.util.*;

import models.clsUserWatchRecord;
import session.CurrentUser;



public class MoviesHomeController {

    @FXML private ImageView backgroundImageView;
    @FXML private StackPane rootPane;
    @FXML private HBox topRatedBox;
    @FXML private HBox recentBox;
    @FXML private HBox recommendedBox;
    @FXML private HBox actionBox;
    @FXML private HBox comedyBox;
    @FXML private Label welcomeLabel;
    @FXML
    private void initialize() {

        backgroundImageView.fitWidthProperty().bind(rootPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(rootPane.heightProperty());
        welcomeLabel.setText("Welcome, " + CurrentUser.getCurrentUser().get_UserName());
        loadSections();
    }

    private List<clsMovie> GetRecommenededMovies(){
        List<clsMovie>RecommendedMovies = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for(Movie_Genere.genres g : Movie_Genere.genres.values()){
            map.put(g.toString(),0);
        }
        List<clsUserWatchRecord> WatchedMovies = clsUserWatchRecord.GetUserWatchRecords(CurrentUser.getCurrentUser().get_UserName());
        for(clsUserWatchRecord watchrecord : WatchedMovies){
            for(String genere:Movie_Genere.GetGenresPerMovie(watchrecord.getMovieId())){
                map.put(genere,map.get(genere)+1);
            }
        }
        String favouriteGenere = "ACTION";
        Integer maxvalue = -1;
        for(Movie_Genere.genres g : Movie_Genere.genres.values()){
            if(map.get(g.toString())>map.get(favouriteGenere)){
                favouriteGenere = g.toString();
                maxvalue = map.get(g.toString());
            }
        }
        List<clsMovie>Movies = clsMovie.GetAllMovies();
        Set<String> watchedMovieIds = new HashSet<>();

        for(clsUserWatchRecord record : WatchedMovies){
            watchedMovieIds.add(record.getMovieId());
        }
        for(clsMovie m : Movies){
            if(!watchedMovieIds.contains(m.getId())
                    && m.getGenres().contains(favouriteGenere)
                    && m.getRating() > 4)
            {
                RecommendedMovies.add(m);
            }
        }
        return RecommendedMovies;
    }

    private void loadSections() {
        List<clsMovie> movies = clsMovie.GetAllMovies();

        List<clsMovie> topRated = new ArrayList<>(movies);
        topRated.sort(
                Comparator.comparing((clsMovie m) -> m.getRating())
                        .reversed()
        );

        List<clsMovie> recent = new ArrayList<>(movies);
        recent.sort(Comparator.comparing(clsMovie::getReleaseDate).reversed());

        List<clsMovie> recommended = GetRecommenededMovies();
        List<clsMovie> action = clsMovie.GetAllMoviesPerGenere(Movie_Genere.genres.ACTION);
        List<clsMovie> comedy = clsMovie.GetAllMoviesPerGenere(Movie_Genere.genres.COMEDY);

        populateRow(topRatedBox, topRated);
        populateRow(recentBox, recent);
        populateRow(recommendedBox, recommended);
        populateRow(actionBox, action);
        populateRow(comedyBox, comedy);
    }

    private void populateRow(HBox container, List<clsMovie> movies) {

        if (movies == null) return;

        for (clsMovie movie : movies) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/UI/Common/CommonFXML/MovieCard.fxml")
                );

                Parent card = loader.load();
                MovieCardController controller = loader.getController();
                controller.setMovie(movie);

                container.getChildren().add(card);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onLogout() {
        session.CurrentUser.clear();
        CNavigators.ShowUserLogin();
    }

    @FXML
    private void onProfile() {
        CNavigators.ShowProfileScreen();
    }
    @FXML
    private void onOpenSearch() {
        CNavigators.ShowSearchScreen();
    }
}