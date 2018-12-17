package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.mainClasses.PaneManager;

public class BomberManGame extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage)
    {

        Scene scene = new Scene(new Pane(), Config.object_size * Config.mapSize.getX(),
                Config.object_size * Config.mapSize.getY());

        primaryStage.setScene(scene);

        PaneManager paneManager = new PaneManager(primaryStage);
        paneManager.setMenuPane();

        primaryStage.show();
    }
}
