package sample.game_objects;


import javafx.scene.image.ImageView;
import sample.Config;
import sample.utils.Vector2;

import java.util.ArrayList;

public abstract class GameObject
{
    protected ImageView imageView;

    public GameObject(ImageView imageView, Vector2 coords)
    {
        this.imageView = imageView;
        if (coords != null)
        {
            imageView.setTranslateX(coords.getX() * Config.object_size);
            imageView.setTranslateY(coords.getY() * Config.object_size);
        }
    }

    public abstract void update(ArrayList<GameObject> mapObjects);

    public ImageView getImageView()
    {
        return imageView;
    }

    public void setImageView(ImageView imageView)
    {
        this.imageView = imageView;
    }
}
