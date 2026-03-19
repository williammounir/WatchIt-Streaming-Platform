package UI.Common.CommonControllers;

import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import models.clsMovie;

public class WatchController {

    @FXML private StackPane rootPane;
    @FXML private ImageView backgroundImage;

    @FXML private MediaView mediaView;
    @FXML private Slider progressSlider;
    @FXML private Slider volumeSlider;
    @FXML private Label titleLabel;

    private MediaPlayer player;
    private clsMovie movie;

    private boolean isSeeking = false;

    @FXML
    private void initialize() {


        if (backgroundImage != null && rootPane != null) {
            backgroundImage.fitWidthProperty().bind(rootPane.widthProperty());
            backgroundImage.fitHeightProperty().bind(rootPane.heightProperty());
        }


        mediaView.setPreserveRatio(true);

        mediaView.fitWidthProperty().bind(rootPane.widthProperty().multiply(0.75));
        mediaView.fitHeightProperty().bind(rootPane.heightProperty().multiply(0.65));

        StackPane.setAlignment(mediaView, javafx.geometry.Pos.CENTER);

        volumeSlider.setValue(50);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (player != null) {
                player.setVolume(newVal.doubleValue() / 100.0);
            }
        });


        progressSlider.setOnMousePressed(e -> isSeeking = true);


        progressSlider.setOnMouseReleased(e -> {
            if (player != null) {
                player.seek(Duration.seconds(progressSlider.getValue()));
            }
            isSeeking = false;
        });
    }


    public void setMovie(clsMovie movie) {
        this.movie = movie;
        titleLabel.setText(movie.getTitle());

        try {
            String videoPath = getClass()
                    .getResource(movie.getVideoPath())
                    .toExternalForm();

            Media media = new Media(videoPath);
            player = new MediaPlayer(media);
            mediaView.setMediaPlayer(player);


            player.setOnReady(() -> {
                progressSlider.setMax(player.getTotalDuration().toSeconds());
            });


            player.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                if (!isSeeking) {
                    progressSlider.setValue(newTime.toSeconds());
                }
            });


            player.play();

        } catch (Exception e) {
            System.out.println("Video load failed:");
            e.printStackTrace();
        }
    }



    @FXML
    private void onPlay() {
        if (player != null) player.play();
    }

    @FXML
    private void onPause() {
        if (player != null) player.pause();
    }

    @FXML
    private void onStop() {
        if (player != null) {
            player.stop();
            progressSlider.setValue(0);
        }
    }


    @FXML
    private void onForward() {
        if (player != null) {
            player.seek(player.getCurrentTime().add(Duration.seconds(10)));
        }
    }


    @FXML
    private void onBackward() {
        if (player != null) {
            player.seek(player.getCurrentTime().subtract(Duration.seconds(10)));
        }
    }


    @FXML
    private void onFinish() {
        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
        }
        CNavigators.ShowRatingScreen(movie);
    }
}
