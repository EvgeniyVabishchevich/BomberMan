package sample.mainClasses;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.Config;
import sample.game_objects.*;
import sample.game_objects.block_items.BombBonusItem;
import sample.game_objects.block_items.Door;
import sample.game_objects.block_items.SpeedBonusItem;
import sample.utils.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class LevelPane extends VBox
{
    private PaneManager paneManager;
    private Scene scene;
    private Pane gamePane;
    private BomberMan bomberMan;
    private ArrayList<GameObject> gameObjects;
    private HashMap<String, Boolean> currentlyActiveKeys = new HashMap<>();
    private Map map;

    public LevelPane(PaneManager paneManager, Scene scene)
    {
        this.paneManager = paneManager;
        this.scene = scene;

        setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        gamePane = new Pane();
        getChildren().addAll(gamePane);
        gameObjects = new ArrayList<>();

        map = new Map();
        bomberMan = new BomberMan(null);
        gamePane.getChildren().add(bomberMan.getImageView());

        loadLevel();

        scene.setOnKeyPressed(pressEvent);
        scene.setOnKeyReleased(releaseEvent);
    }

    public void update()
    {
        if(!bomberMan.isAlive())
        {
            Timeline timerBeforGameOver = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent ->
                    paneManager.setGameOverPane()));
            timerBeforGameOver.play();
        }

        for(int object_number = 0; object_number < gameObjects.size(); object_number++)
        {
            gameObjects.get(object_number).update(gameObjects);
        }
    }

    private EventHandler<KeyEvent> pressEvent = new EventHandler<KeyEvent>()
    {
        @Override
        public void handle(KeyEvent keyEvent)
        {
            if(bomberMan.isAlive())
            {
                if(keyEvent.getCode() == KeyCode.DOWN)
                {
                    if(!currentlyActiveKeys.containsKey(keyEvent.getCode().toString()))
                    {
                        bomberMan.startMoving(LivingObject.Direction.DOWN);
                    }
                    currentlyActiveKeys.put(keyEvent.getCode().toString(), true);
                }
                if(keyEvent.getCode() == KeyCode.UP)
                {
                    if(!currentlyActiveKeys.containsKey(keyEvent.getCode().toString()))
                    {
                        bomberMan.startMoving(LivingObject.Direction.UP);
                    }
                    currentlyActiveKeys.put(keyEvent.getCode().toString(), true);
                }
                if(keyEvent.getCode() == KeyCode.LEFT)
                {
                    if(!currentlyActiveKeys.containsKey(keyEvent.getCode().toString()))
                    {
                        bomberMan.startMoving(LivingObject.Direction.LEFT);
                    }
                    currentlyActiveKeys.put(keyEvent.getCode().toString(), true);
                }
                if(keyEvent.getCode() == KeyCode.RIGHT)
                {
                    if (!currentlyActiveKeys.containsKey(keyEvent.getCode().toString()))
                    {
                        bomberMan.startMoving(LivingObject.Direction.RIGHT);
                    }
                    currentlyActiveKeys.put(keyEvent.getCode().toString(), true);
                }
                if(keyEvent.getCode() == KeyCode.SPACE)
                {
                    bomberMan.plantBomn(gameObjects, gamePane);
                }
            }
        }
    };

    private EventHandler<KeyEvent> releaseEvent = new EventHandler<KeyEvent>()
    {
        @Override
        public void handle(KeyEvent keyEvent)
        {
            if(bomberMan.isAlive())
            {
                if(keyEvent.getCode() == KeyCode.RIGHT)
                {
                    bomberMan.stopMoving(LivingObject.Direction.RIGHT);
                    currentlyActiveKeys.remove(keyEvent.getCode().toString());
                }
                if(keyEvent.getCode() == KeyCode.UP)
                {
                    bomberMan.stopMoving(LivingObject.Direction.UP);
                    currentlyActiveKeys.remove(keyEvent.getCode().toString());
                }
                if(keyEvent.getCode() == KeyCode.LEFT)
                {
                    bomberMan.stopMoving(LivingObject.Direction.LEFT);
                    currentlyActiveKeys.remove(keyEvent.getCode().toString());
                }
                if(keyEvent.getCode() == KeyCode.DOWN)
                {
                    bomberMan.stopMoving(LivingObject.Direction.DOWN);
                    currentlyActiveKeys.remove(keyEvent.getCode().toString());
                }
            }
        }
    };

    public void loadNextLevel()
    {
        if(map.getCurrentLevel() == map.getLastLevel())
        {
            paneManager.setMenuPane();
            gameObjects.clear();
        }

        map.setCurrentLevel(map.getCurrentLevel() + 1);

        loadLevel();
    }

    private final char WALL = 'x', BOMBERMAN = 'h', BLOCK = 'b', MONSTER = 'm', DOOR = 'd', EXTRA_BOMB = 'e',
            SPEED_BONUS = 's', EMPTY = '-';

    public void loadLevel()
    {
        gameObjects.clear();
        gamePane.getChildren().clear();

        char [][] map_matrix = map.getCurrentLevelMatrix();

        for(int map_y = 0; map_y < map_matrix.length; map_y++)
        {
            for(int map_x = 0; map_x < map_matrix[map_y].length; map_x++)
            {
                GameObject mapObject = null;

                if(map_matrix[map_y][map_x] == WALL)
                {
                    mapObject = new Rock(new Vector2(map_x, map_y));
                }
                if(map_matrix[map_y][map_x] == BLOCK)
                {
                    mapObject = new Block(new Vector2(map_x, map_y));
                }
                if(map_matrix[map_y][map_x] == DOOR)
                {
                    mapObject = new Block(new Vector2(map_x, map_y), new Door(new Vector2(map_x, map_y),
                            this));
                }
                if(map_matrix[map_y][map_x] == EXTRA_BOMB)
                {
                    mapObject = new Block(new Vector2(map_x, map_y), new BombBonusItem(new Vector2(map_x, map_y),
                            bomberMan));
                }
                if(map_matrix[map_y][map_x] == SPEED_BONUS)
                {
                    mapObject = new Block(new Vector2(map_x, map_y), new SpeedBonusItem(new Vector2(map_x, map_y),
                            bomberMan));
                }
                if(map_matrix[map_y][map_x] == MONSTER)
                {
                    mapObject = new Enemy(new Vector2(map_x, map_y));
                }
                if(map_matrix[map_y][map_x] == BOMBERMAN)
                {
                    bomberMan.getImageView().setTranslateX(map_x * Config.object_size);
                    bomberMan.getImageView().setTranslateY(map_y * Config.object_size);
                    gameObjects.add(bomberMan);
                    gamePane.getChildren().add(bomberMan.getImageView());
                }
                if(mapObject != null)
                {
                    gameObjects.add(mapObject);
                    gamePane.getChildren().add(mapObject.getImageView());
                }
            }
        }
    }

    public void startNewGame()
    {
        gameObjects.clear();
        gamePane.getChildren().clear();
        currentlyActiveKeys.clear();

        map.setCurrentLevel(0);
        bomberMan = new BomberMan(null);
        gamePane.getChildren().add(bomberMan.getImageView());
        loadLevel();
    }
}
