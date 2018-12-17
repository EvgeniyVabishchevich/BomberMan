package sample.game_objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.Config;
import sample.utils.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends LivingObject
{
    public Enemy(Vector2 coords)
    {
        super(new Image("assets/enemy.png", Config.image_size, Config.image_size,
                        false, false),
                new Image("assets/enemy.png", Config.image_size, Config.image_size,
                        false, false),
                new Image("assets/enemy.png", Config.image_size, Config.image_size,
                        false, false),
                new Image("assets/enemy.png", Config.image_size, Config.image_size,
                        false, false),
                coords,
                new Vector2(0.5, 0.5));
    }

    @Override
    public void die(ArrayList<GameObject> mapObjects)
    {
        if (alive)
        {
            alive = false;

            imageView.setImage(new Image("assets/enemy_dead.png"));

            speed = new Vector2();

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
                ((Pane) imageView.getParent()).getChildren().remove(imageView);
                mapObjects.remove(Enemy.this);
            }));
            timeline.play();
        }
    }

    @Override
    public void update(ArrayList<GameObject> mapObjects)
    {
        if (alive)
        {
            super.update(mapObjects);

            if (canChooseNewDirection())
            {
                speed = new Vector2();
                chooseMovementDirection(mapObjects);
            }
        }
    }

    public boolean canChooseNewDirection()
    {
        return (imageView.getTranslateX() % Config.object_size == 0 && imageView.getTranslateY() % Config.object_size == 0) ||
                (speed.getX() == 0 && speed.getY() == 0);
    }

    public void chooseMovementDirection(ArrayList<GameObject> mapObjects)
    {
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);
        directions.add(Direction.RIGHT);

        Vector2 leftPoint = new Vector2(imageView.getTranslateX() - Config.object_size + 1, imageView.getTranslateY() + 1);
        Vector2 rightPoint = new Vector2(imageView.getTranslateX() + Config.object_size + 1, imageView.getTranslateY() + 1);
        Vector2 bottomPoint = new Vector2(imageView.getTranslateX() + 1, imageView.getTranslateY() + Config.object_size + 1);
        Vector2 topPoint = new Vector2(imageView.getTranslateX() + 1, imageView.getTranslateY() - Config.object_size + 1);

        for (int object_number = 0; object_number < mapObjects.size(); object_number++)
        {
            if (mapObjects.get(object_number) instanceof Block || mapObjects.get(object_number) instanceof Rock ||
                    mapObjects.get(object_number) instanceof Bomb)
            {
                if (mapObjects.get(object_number).getImageView().getBoundsInParent().contains(
                        leftPoint.getX(), leftPoint.getY()))
                {
                    directions.remove(Direction.LEFT);
                }
                if (mapObjects.get(object_number).getImageView().getBoundsInParent().contains(
                        rightPoint.getX(), rightPoint.getY()))
                {
                    directions.remove(Direction.RIGHT);
                }
                if (mapObjects.get(object_number).getImageView().getBoundsInParent().contains(
                        bottomPoint.getX(), bottomPoint.getY()))
                {
                    directions.remove(Direction.DOWN);
                }
                if (mapObjects.get(object_number).getImageView().getBoundsInParent().contains(
                        topPoint.getX(), topPoint.getY()))
                {
                    directions.remove(Direction.UP);
                }
            }
        }

        if (!directions.isEmpty())
        {
            Random random = new Random();
            startMoving(directions.get(random.nextInt(directions.size())));
        }
    }
}
