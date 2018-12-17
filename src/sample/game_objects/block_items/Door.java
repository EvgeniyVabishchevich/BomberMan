package sample.game_objects.block_items;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.game_objects.BomberMan;
import sample.game_objects.Enemy;
import sample.game_objects.GameObject;
import sample.mainClasses.LevelPane;
import sample.utils.CorrectBounds;
import sample.utils.Vector2;

import java.util.ArrayList;

public class Door extends BlockItem
{
    LevelPane levelPane;

    public Door(Vector2 coords, LevelPane levelPane)
    {
        super(new ImageView(new Image("assets/door.png")), coords);

        this.levelPane = levelPane;
    }

    @Override
    public void update(ArrayList<GameObject> mapObjects)
    {

        for (int object_number = 0; object_number < mapObjects.size(); object_number++)
        {
            if (mapObjects.get(object_number) instanceof BomberMan)
            {
                CorrectBounds bomberBounds = new CorrectBounds(mapObjects.get(object_number).getImageView().getBoundsInParent());
                CorrectBounds doorBounds = new CorrectBounds(imageView.getBoundsInParent());

                if (bomberBounds.intersects(doorBounds))
                {
                    mapObjects.forEach(gameObject ->
                    {
                        if(gameObject instanceof Enemy) return;
                    });
                    levelPane.loadNextLevel();
                }
            }
        }
    }
}
