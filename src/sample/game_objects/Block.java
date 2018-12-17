package sample.game_objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.game_objects.block_items.BlockItem;
import sample.utils.Vector2;

import java.util.ArrayList;

public class Block extends GameObject
{
    private boolean broken = false;

    private BlockItem item;

    public Block(Vector2 coords)
    {
        super(new ImageView(new Image("assets/block.png")), coords);
    }

    public Block(Vector2 coords, BlockItem item)
    {
        super(new ImageView(new Image("assets/block.png")), coords);

        this.item = item;
    }

    @Override
    public void update(ArrayList<GameObject> mapObjects)
    {

    }

    public void destroy(ArrayList<GameObject> mapObjects)
    {
        if (!broken)
        {
            broken = true;

            imageView.setImage(new Image("assets/broken_block.png"));

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), actionEvent -> {
                if (item != null)
                {
                    ((Pane) imageView.getParent()).getChildren().add(item.getImageView());
                    mapObjects.add(item);
                }
                ((Pane) imageView.getParent()).getChildren().remove(imageView);
                mapObjects.remove(Block.this);
            }));
            timeline.play();
        }
    }
}
