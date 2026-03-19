package UI.Common.CommonScreensNavigator;

import Main.Mainfx;
import UI.Common.CommonControllers.RatingController;
import UI.Common.CommonControllers.WatchController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import models.clsMovie;

import java.io.IOException;

public class CNavigators {
    public static void ShowMainScene() {
        try{
            Parent root = FXMLLoader.load(CNavigators.class.getResource("/UI/Common/CommonFXML/MainScreenFXML.fxml"));
            Scene scene = new Scene(root);
            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void ShowAdminLogin() {
        try{
            Parent root = FXMLLoader.load(CNavigators.class.getResource("/UI/Common/CommonFXML/AdminLoginFXML.fxml"));
            Scene scene = new Scene(root);

            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void ShowUserLogin() {
        try{
            Parent root = FXMLLoader.load(CNavigators.class.getResource("/UI/Common/CommonFXML/UserLoginFXML.fxml"));
            Scene scene = new Scene(root);

            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void ShowUserAuth() {
        try{
            Parent root = FXMLLoader.load(CNavigators.class.getResource("/UI/Common/CommonFXML/UserAuthFXML.fxml"));
            Scene scene = new Scene(root);

            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void ShowUserSignUp() {
        try{
            Parent root = FXMLLoader.load(CNavigators.class.getResource("/UI/Common/CommonFXML/UserSignUpFXML.fxml"));
            Scene scene = new Scene(root);

            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void ShowUserRenewSubscription() {
        try{
            Parent root = FXMLLoader.load(CNavigators.class.getResource("/UI/Common/CommonFXML/UserRenewSubFXML.fxml"));
            Scene scene = new Scene(root);

            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void ShowMoviesHome() {
        try{
            Parent root = FXMLLoader.load(CNavigators.class.getResource("/UI/Common/CommonFXML/MoviesHome.fxml"));
            Scene scene = new Scene(root);

            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void ShowSearchScreen() {
        try{
            Parent root = FXMLLoader.load(CNavigators.class.getResource("/UI/Common/CommonFXML/SearchScreen.fxml"));
            Scene scene = new Scene(root);

            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void ShowWatchScreen(clsMovie m){
        try {
            FXMLLoader loader = new FXMLLoader(
                    CNavigators.class.getResource("/UI/Common/CommonFXML/WatchScreen.fxml")
            );

            Parent root = loader.load();

            WatchController controller = loader.getController();
            controller.setMovie(m);

            Mainfx.getPrimaryStage().getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowRatingScreen(clsMovie movie) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    CNavigators.class.getResource("/UI/Common/CommonFXML/Rating.fxml")
            );
            Parent root = loader.load();

            RatingController controller = loader.getController();
            controller.setMovie(movie);

            Mainfx.getPrimaryStage().getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowProfileScreen() {
        try{
            Parent root = FXMLLoader.load(CNavigators.class.getResource("/UI/Common/CommonFXML/ProfileScreen.fxml"));
            Scene scene = new Scene(root);

            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void ShowEditProfileScreen() {
        try{
            Parent root = FXMLLoader.load(
                    CNavigators.class.getResource("/UI/Common/CommonFXML/EditProfile.fxml")
            );
            Scene scene = new Scene(root);
            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void ShowAdminMainMenu() {
        try{
            Parent root = FXMLLoader.load(
                    CNavigators.class.getResource("/UI/Common/CommonFXML/AdminMainMenu.fxml")
            );
            Scene scene = new Scene(root);
            Mainfx.getPrimaryStage().setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
