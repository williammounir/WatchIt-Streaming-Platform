package UI.Common.CommonControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import models.*;
import UI.Common.CommonScreensNavigator.CNavigators;
import session.CurrentUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileScreenController {

    @FXML private ImageView profileImage;
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label memberSinceLabel;

    @FXML private Label moviesWatchedLabel;
    @FXML private Label favoriteGenreLabel;
    @FXML private Label averageRatingLabel;

    @FXML private HBox watchedMoviesBox;

    @FXML private Button editProfileBtn;
    @FXML private Button backButton;

    public void initialize() {

        loadUserInfo();
        loadStats();
        loadWatchedMovies();
        setupActions();
    }


    private Movie_Genere.genres GetFavoriteGenere(){
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
        return Movie_Genere.genres.valueOf(favouriteGenere);
    }

    private void loadUserInfo() {
        clsUser cuser= CurrentUser.getCurrentUser();
        clsSubscription sub = clsSubscription.findSubscription(cuser.get_UserName());
        nameLabel.setText(cuser.getFullName());
        emailLabel.setText(cuser.get_Email());
        memberSinceLabel.setText("Member since " + sub.get_StartDate());

        profileImage.setImage(
                new Image(getClass()
                        .getResource("/UI/resources/Images/william mounir.jpg")
                        .toExternalForm())
        );
    }

    private void loadStats() {

        List<clsUserWatchRecord> watchedMovies = getWatchedMovies();

        moviesWatchedLabel.setText(String.valueOf(watchedMovies.size()));


        favoriteGenreLabel.setText(GetFavoriteGenere().toString());

        double sum = 0;
        for (clsUserWatchRecord movie : watchedMovies) {
            sum += movie.getRating();
        }

        double avg = watchedMovies.isEmpty() ? 0 : sum / watchedMovies.size();
        averageRatingLabel.setText(String.format("%.1f ★", avg));
    }

    private void loadWatchedMovies() {

        watchedMoviesBox.getChildren().clear();

        List<clsUserWatchRecord> watchedMovies = getWatchedMovies();

        for (clsUserWatchRecord movie : watchedMovies) {
            try {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/UI/Common/CommonFXML/MovieCard.fxml")
                );

                Parent card = loader.load();

                UI.Common.CommonControllers.MovieCardController controller =
                        loader.getController();

                controller.setMovie(clsMovie.FindMovie(movie.getMovieId()));

                watchedMoviesBox.getChildren().add(card);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setupActions() {

        editProfileBtn.setOnAction(e -> {
            // I am so tired to do it :-<
            CNavigators.ShowEditProfileScreen();
        });

        backButton.setOnAction(e -> {
            CNavigators.ShowMoviesHome();
        });
    }


    private List<clsUserWatchRecord> getWatchedMovies() {
        return clsUserWatchRecord.GetUserWatchRecords(CurrentUser.getCurrentUser().get_UserName());
    }
}