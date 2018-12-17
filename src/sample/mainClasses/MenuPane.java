package sample.mainClasses;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MenuPane extends Pane
{
    private PaneManager paneManager;

    public MenuPane(PaneManager paneManager)
    {
        this.paneManager = paneManager;

        setBackground(new Background(new BackgroundImage(new Image("assets/main_menu.png"), null, null, null, null)));

        addUi();
    }

    public void addUi()
    {
        VBox vBox = new VBox();

        Button startGame = new Button("Play");
        startGame.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        startGame.setOnAction(actionEvent ->
        {
            paneManager.getLevelPane().startNewGame();
            paneManager.setLevelPane();
        });

        Button quit = new Button("Quit");
        quit.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        quit.setOnAction(actionEvent -> paneManager.getPrimaryStage().close());

        vBox.getChildren().addAll(startGame, quit);

        vBox.setLayoutX(350);
        vBox.setLayoutY(250);

        getChildren().add(vBox);
    }
}
