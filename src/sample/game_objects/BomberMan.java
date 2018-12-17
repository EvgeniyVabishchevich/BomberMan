package sample.game_objects;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import sample.Config;
import sample.utils.CorrectBounds;
import sample.utils.Vector2;

import java.util.ArrayList;

public class BomberMan extends LivingObject
{
    private int maxBombs = 1;

    public BomberMan(Vector2 coords)
    {
        super(new Image("assets/bomberman_up.png", Config.image_size, Config.image_size,
                        false, false),
                new Image("assets/bomberman_down.png", Config.image_size, Config.image_size,
                        true, true),
                new Image("assets/bomberman_left.png", Config.image_size, Config.image_size,
                        false, false),
                new Image("assets/bomberman_right.png", Config.image_size, Config.image_size,
                        false, false),
                coords,
                new Vector2(1, 1));
    }

    @Override
    public void die(ArrayList<GameObject> mapObjects)
    {
        if (alive)
        {
            speed = new Vector2();
            imageView.setImage(new Image("assets/bomberman_dead.png", Config.object_size, Config.object_size, false, false));
            alive = false;
        }
    }

    @Override
    public void update(ArrayList<GameObject> mapObjects)
    {
        super.update(mapObjects);

        checkBomberManIntersections(mapObjects);
    }

    public void plantBomn(ArrayList<GameObject> mapObjects, Pane gamePane)
    {
        int currentBombsNumber = 0;

        for (int object_number = 0; object_number < mapObjects.size(); object_number++)
        {
            if (mapObjects.get(object_number) instanceof Bomb) currentBombsNumber++;
        }

        if (currentBombsNumber < maxBombs)
        {
            Bomb bomb = new Bomb(new Vector2(
                    Math.floor((getImageView().getTranslateX() + (Config.object_size / 2)) / Config.object_size),
                    Math.floor((getImageView().getTranslateY() + (Config.object_size / 2)) / Config.object_size)),
                    this);
            gamePane.getChildren().add(bomb.getImageView());
            mapObjects.add(bomb);
        }
    }

    public void checkBomberManIntersections(ArrayList<GameObject> mapObjects)
    {
        CorrectBounds bomberManBounds = new CorrectBounds(getImageView().getBoundsInParent());

        ArrayList<CorrectBounds> intersectingBlocksBounds = new ArrayList<>();

        for (int object_number = 0; object_number < mapObjects.size(); object_number++)
        {
            if (this != mapObjects.get(object_number))
            {
                CorrectBounds objectBounds = new CorrectBounds(mapObjects.get(object_number).getImageView().getBoundsInParent());

                if (bomberManBounds.intersects(objectBounds))
                {
                    if (mapObjects.get(object_number) instanceof Rock)
                    {
                        intersectingBlocksBounds.add(objectBounds);
                    }

                    if (mapObjects.get(object_number) instanceof Block || (mapObjects.get(object_number) instanceof Bomb
                            && !((Bomb) mapObjects.get(object_number)).isCrossable()))
                    {
                        intersectingBlocksBounds.add(objectBounds);
                    }

                    if (mapObjects.get(object_number) instanceof Enemy && alive == true)
                    {
                        die(mapObjects);
                    }
                }
            }
        }

        if (intersectingBlocksBounds.size() > 1)
        {
            fixBomberIntersection();
        } else if (intersectingBlocksBounds.size() == 1)
        {
            CorrectBounds blockBounds = intersectingBlocksBounds.get(0);

            if (getSpeed().getX() != 0)
            {
                CorrectBounds blockCenterBounds_By_Y = new CorrectBounds(
                        blockBounds.getMinX(),
                        blockBounds.getMinY() + (blockBounds.getHeight() / 5),
                        blockBounds.getWidth(),
                        blockBounds.getHeight() - (2 * blockBounds.getHeight() / 5)
                );
                if (blockCenterBounds_By_Y.intersects(bomberManBounds))
                {
                    fixBomberIntersection();
                    return;
                }
            }

            if (getSpeed().getY() != 0)
            {
                CorrectBounds blockCenterBounds_By_X = new CorrectBounds(
                        blockBounds.getMinX() + (blockBounds.getWidth() / 5),
                        blockBounds.getMinY(),
                        blockBounds.getWidth() - (2 * blockBounds.getWidth() / 5),
                        blockBounds.getHeight()
                );
                if (blockCenterBounds_By_X.intersects(bomberManBounds))
                {
                    fixBomberIntersection();
                    return;
                }
            }

            if (blockBounds.contains(bomberManBounds.getMinX(), bomberManBounds.getMinY()))
            {
                fixBomberPosition(blockBounds.getMaxY(), blockBounds.getMaxX());
                return;
            }

            if (blockBounds.contains(bomberManBounds.getMinX(), bomberManBounds.getMaxY()))
            {
                fixBomberPosition(blockBounds.getMinY() - Config.object_size, blockBounds.getMaxX());
                return;
            }

            if (blockBounds.contains(bomberManBounds.getMaxX(), bomberManBounds.getMinY()))
            {
                fixBomberPosition(blockBounds.getMaxY(), blockBounds.getMinX() - Config.object_size);
                return;
            }

            if (blockBounds.contains(bomberManBounds.getMaxX(), bomberManBounds.getMaxY()))
            {
                fixBomberPosition(blockBounds.getMinY() - Config.object_size,
                        blockBounds.getMinX() - Config.object_size);
                return;
            }
        }
    }

    public void fixBomberPosition(double newY, double newX)
    {
        if (getSpeed().getX() != 0)
            getImageView().setTranslateY(newY);
        else
            getImageView().setTranslateX(newX);
    }

    public void fixBomberIntersection()
    {
        getImageView().setTranslateX(getImageView().getTranslateX() -
                getSpeed().getX());
        getImageView().setTranslateY(getImageView().getTranslateY() -
                getSpeed().getY());
    }

    public int getMaxBombs()
    {
        return maxBombs;
    }

    public void setMaxBombs(int maxBombs)
    {
        this.maxBombs = maxBombs;
    }
}

