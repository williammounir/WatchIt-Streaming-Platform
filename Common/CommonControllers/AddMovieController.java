package UI.Common.CommonControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import models.clsMovie;

import java.io.File;
import java.time.LocalDate;

public class AddMovieController {

    @FXML private TextField idField;
    @FXML private TextField titleField;
    @FXML private DatePicker releaseDatePicker;
    @FXML private TextField durationField;
    @FXML private TextField directorField;
    @FXML private TextField countryField;

    @FXML private TextField budgetField;
    @FXML private TextField revenueField;
    @FXML private TextField ratingField;

    @FXML private TextArea descriptionArea;

    @FXML private TextField posterPathField;
    @FXML private TextField videoPathField;

    @FXML private ImageView posterPreview;

    @FXML private TextField castField;
    @FXML private TextField languagesField;

    @FXML private CheckBox genreAction;
    @FXML private CheckBox genreDrama;
    @FXML private CheckBox genreComedy;
    @FXML private CheckBox genreSciFi;
    @FXML private CheckBox genreHorror;



    @FXML
    private void handleBrowsePoster(){

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Poster Image");

        File file = chooser.showOpenDialog(null);

        if(file != null){
            posterPathField.setText(file.getAbsolutePath());
            posterPreview.setImage(new Image(file.toURI().toString()));
        }
    }


    @FXML
    private void handleBrowseVideo(){

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Movie Video");

        File file = chooser.showOpenDialog(null);

        if(file != null){
            videoPathField.setText(file.getAbsolutePath());
        }
    }


    @FXML
    private void handleSaveMovie(){

        try{

            String id = idField.getText().trim();
            String title = titleField.getText().trim();


            if(id.isEmpty() || title.isEmpty() || releaseDatePicker.getValue() == null){

                new Alert(Alert.AlertType.WARNING,
                        "Please fill all required fields (ID, Title, Release Date)").showAndWait();
                return;
            }


            clsMovie existingMovie = clsMovie.FindMovie(id);

            if(existingMovie != null){

                new Alert(Alert.AlertType.WARNING,
                        "Movie with this ID already exists!").showAndWait();
                return;
            }


            LocalDate date = releaseDatePicker.getValue();
            int duration = Integer.parseInt(durationField.getText());

            String director = directorField.getText();
            String country = countryField.getText();

            double budget = Double.parseDouble(budgetField.getText());
            double revenue = Double.parseDouble(revenueField.getText());
            double rating = Double.parseDouble(ratingField.getText());

            String description = descriptionArea.getText();
            String poster = posterPathField.getText();
            String video = videoPathField.getText();


            clsMovie movie = clsMovie.GetAddNewObjectMovie(id);

            movie.setTitle(title);
            movie.setReleaseDate(date);
            movie.setDurationMinutes(duration);
            movie.setDirector(director);
            movie.setCountry(country);

            movie.setBudget(budget);
            movie.setRevenue(revenue);
            movie.setRating(rating);

            movie.set_Description(description);
            movie.setPosterPath(poster);
            movie.setVideoPath(video);


            clsMovie.enSaveResult result = movie.SaveM();

            if(result == clsMovie.enSaveResult.SVSUCCEDED){

                new Alert(Alert.AlertType.INFORMATION,
                        "Movie Added Successfully").showAndWait();

                clearForm();

            }else{

                new Alert(Alert.AlertType.ERROR,
                        "Failed To Add Movie").showAndWait();
            }

        }catch(NumberFormatException e){

            new Alert(Alert.AlertType.ERROR,
                    "Please enter valid numeric values (Duration, Budget, Revenue, Rating)").showAndWait();

        }catch(Exception e){

            new Alert(Alert.AlertType.ERROR,
                    "Unexpected error occurred").showAndWait();
        }
    }


    private void clearForm(){

        idField.clear();
        titleField.clear();
        releaseDatePicker.setValue(null);
        durationField.clear();
        directorField.clear();
        countryField.clear();

        budgetField.clear();
        revenueField.clear();
        ratingField.clear();

        descriptionArea.clear();

        posterPathField.clear();
        videoPathField.clear();

        castField.clear();
        languagesField.clear();

        posterPreview.setImage(null);

        genreAction.setSelected(false);
        genreDrama.setSelected(false);
        genreComedy.setSelected(false);
        genreSciFi.setSelected(false);
        genreHorror.setSelected(false);
    }


    @FXML
    private void handleBack(){

        System.out.println("Back To Manage Movies");
    }
}