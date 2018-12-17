package sample.game_objects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.Config;
import sample.utils.CorrectBounds;
import sample.utils.Vector2;

import java.util.ArrayList;

public class Bomb extends GameObject
{
    private boolean crossable = true;
    private BomberMan bomberMan;
    private Timeline timer;

    public Bomb(Vector2 coords, BomberMan bomberMan)
    {
        super(new ImageView(new Image("assets/bomb.png")), coords);
        this.bomberMan = bomberMan;

        timer = new Timeline(new KeyFrame(Duration.seconds(2)));
        timer.play();
    }

    @Override
    public void update(ArrayList<GameObject> mapObjects)
    {
        if (crossable)
        {
            if (!(new CorrectBounds(bomberMan.getImageView().getBoundsInParent()).intersects(imageView.getBoundsInParent())))
            {
                crossable = false;
            }
        }
        if (timer.getStatus() == Animation.Status.STOPPED)
        {
            ArrayList<LivingObject.Direction> directions = new ArrayList<>();
            directions.add(LivingObject.Direction.UP);
            directions.add(LivingObject.Direction.DOWN);
            directions.add(LivingObject.Direction.LEFT);
            directions.add(LivingObject.Direction.RIGHT);

            Vector2 coords = new Vector2(
                    Math.floor(imageView.getTranslateX() / Config.object_size),
                    Math.floor(imageView.getTranslateY() / Config.object_size)
            );

            Vector2 leftPoint = new Vector2(imageView.getTranslateX() - Config.object_size + 1, imageView.getTranslateY() + 1);
            Vector2 rightPoint = new Vector2(imageView.getTranslateX() + Config.object_size + 1, imageView.getTranslateY() + 1);
            Vector2 bottomPoint = new Vector2(imageView.getTranslateX() + 1, imageView.getTranslateY() + Config.object_size + 1);
            Vector2 topPoint = new Vector2(imageView.getTranslateX() + 1, imageView.getTranslateY() - Config.object_size + 1);

            for (int object_number = 0; object_number < mapObjects.size(); object_number++)
            {
                if (mapObjects.get(object_number) instanceof Block || mapObjects.get(object_number) instanceof Rock)
                {
                    GameObject mapObject = mapObjects.get(object_number);

                    Bounds barrierBounds = mapObjects.get(object_number).getImageView().getBoundsInParent();

                    if (barrierBounds.contains(leftPoint.getX(), leftPoint.getY()))
                    {
                        directions.remove(LivingObject.Direction.LEFT);
                        if (mapObject instanceof Block) ((Block) mapObjects.get(object_number)).destroy(mapObjects);
                    }
                    if (barrierBounds.contains(rightPoint.getX(), rightPoint.getY()))
                    {
                        directions.remove(LivingObject.Direction.RIGHT);
                        if (mapObject instanceof Block) ((Block) mapObjects.get(object_number)).destroy(mapObjects);
                    }
                    if (barrierBounds.contains(topPoint.getX(), topPoint.getY()))
                    {
                        directions.remove(LivingObject.Direction.UP);
                        if (mapObject instanceof Block) ((Block) mapObjects.get(object_number)).destroy(mapObjects);
                    }
                    if (barrierBounds.contains(bottomPoint.getX(), bottomPoint.getY()))
                    {
                        directions.remove(LivingObject.Direction.DOWN);
                        if (mapObject instanceof Block) ((Block) mapObjects.get(object_number)).destroy(mapObjects);
                    }
                }
            }

            for (int direction_number = 0; direction_number < directions.size(); direction_number++)
            {
                if (directions.get(direction_number) == LivingObject.Direction.LEFT)
                {
                    Flame flame = new Flame(new Vector2(coords.getX() - 1, coords.getY()), mapObjects);
                    ((Pane) imageView.getParent()).getChildren().add(flame.getImageView());
                    mapObjects.add(flame);
                }
                if (directions.get(direction_number) == LivingObject.Direction.RIGHT)
                {
                    Flame flame = new Flame(new Vector2(coords.getX() + 1, coords.getY()), mapObjects);
                    ((Pane) imageView.getParent()).getChildren().add(flame.getImageView());
                    mapObjects.add(flame);
                }
                if (directions.get(direction_number) == LivingObject.Direction.DOWN)
                {
                    Flame flame = new Flame(new Vector2(coords.getX(), coords.getY() + 1), mapObjects);
                    ((Pane) imageView.getParent()).getChildren().add(flame.getImageView());
                    mapObjects.add(flame);
                }
                if (directions.get(direction_number) == LivingObject.Direction.UP)
                {
                    Flame flame = new Flame(new Vector2(coords.getX(), coords.getY() - 1), mapObjects);
                    ((Pane) imageView.getParent()).getChildren().add(flame.getImageView());
                    mapObjects.add(flame);
                }
            }

            Flame flame = new Flame(coords, mapObjects);
            ((Pane) imageView.getParent()).getChildren().add(flame.getImageView());
            mapObjects.add(flame);

            ((Pane) imageView.getParent()).getChildren().remove(imageView);
            mapObjects.remove(this);
        }
    }

    public boolean isCrossable()
    {
        return crossable;
    }
}
