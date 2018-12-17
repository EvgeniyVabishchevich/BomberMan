package sample.game_objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.utils.Vector2;

import java.util.ArrayList;

public class Rock extends GameObject
{
    public Rock(Vector2 coords)
    {
        super(new ImageView(new Image("assets/rock.png")), coords);
    }

    @Override
    public void update(ArrayList<GameObject> mapObjects)
    {

    }
}
