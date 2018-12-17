package sample.mainClasses;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PaneManager
{
    private Stage primaryStage;
    private Scene mainScene;
    private LevelPane levelPane;
    private MenuPane menuPane;
    private GameOverPane gameOverPane;

    Timeline updateTimeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
        if (mainScene.getRoot() instanceof LevelPane) ((LevelPane) mainScene.getRoot()).update();
    }));

    public PaneManager(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.mainScene = primaryStage.getScene();


        updateTimeline.setCycleCount(Timeline.INDEFINITE);

        levelPane = new LevelPane(this, mainScene);
        menuPane = new MenuPane(this);
        gameOverPane = new GameOverPane(this);
    }

    public void setMenuPane()
    {
        updateTimeline.pause();
        mainScene.setRoot(menuPane);
    }

    public void setLevelPane()
    {
        mainScene.setRoot(levelPane);

        updateTimeline.play();
    }

    public void setGameOverPane()
    {
        updateTimeline.pause();
        mainScene.setRoot(gameOverPane);
    }

    public LevelPane getLevelPane()
    {
        return levelPane;
    }

    public Stage getPrimaryStage()
    {
        return primaryStage;
    }
}
