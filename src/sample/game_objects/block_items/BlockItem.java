package sample.game_objects.block_items;

import javafx.scene.image.ImageView;
import sample.game_objects.GameObject;
import sample.utils.Vector2;

public abstract class BlockItem extends GameObject
{
    public BlockItem(ImageView imageView, Vector2 coords)
    {
        super(imageView, coords);
    }
}
