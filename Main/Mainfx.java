package Main;

import UI.Common.CommonScreensNavigator.CNavigators;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.clsSuspention;

public class Mainfx extends Application {

    private static Stage primaryStage;
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Watch It");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/logo2.jpg")));
        clsSuspention.AutomaticDeletionOfSuspentionsWhenPeriodPass();
        CNavigators.ShowMainScene();
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args){launch(args);}
}
