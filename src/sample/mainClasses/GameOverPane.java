package sample.mainClasses;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameOverPane extends Pane
{
    private PaneManager paneManager;

    public GameOverPane(PaneManager paneManager)
    {
        this.paneManager = paneManager;

        setBackground(new Background(new BackgroundImage(new Image("assets/game_over.png"), null, null,
                null, null)));

        addUi();
    }

    public void addUi()
    {
        VBox vBox = new VBox();

        Button playAgain = new Button("Play again");
        playAgain.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        playAgain.setOnAction(actionEvent ->
        {
            paneManager.getLevelPane().startNewGame();
            paneManager.setLevelPane();
        });

        Button menu = new Button("Menu");
        menu.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        menu.setOnAction(actionEvent -> paneManager.setMenuPane());

        Button quit = new Button("Quit");
        quit.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        quit.setOnAction(actionEvent -> paneManager.getPrimaryStage().close());

        vBox.getChildren().addAll(playAgain, menu, quit);

        vBox.setLayoutX(350);
        vBox.setLayoutY(250);

        getChildren().add(vBox);
    }
}
