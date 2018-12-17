package sample.game_objects.block_items;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.game_objects.BomberMan;
import sample.game_objects.GameObject;
import sample.utils.CorrectBounds;
import sample.utils.Vector2;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class BombBonusItem extends BlockItem
{
    BomberMan bomberMan;

    public BombBonusItem(Vector2 coords, BomberMan bomberMan)
    {
        super(new ImageView(new Image("assets/extra_bomb.png")), coords);

        this.bomberMan = bomberMan;
    }

    @Override
    public void update(ArrayList<GameObject> mapObjects)
    {
        try{
            mapObjects.stream().filter(gameObject -> gameObject instanceof BomberMan).map(gameObject -> new CorrectBounds(gameObject.getImageView().getBoundsInParent())).forEach(bomberBounds -> {
            CorrectBounds extraBombBounds = new CorrectBounds(imageView.getBoundsInParent());
            if (bomberBounds.intersects(extraBombBounds))
            {
                bomberMan.setMaxBombs(bomberMan.getMaxBombs() + 1);
                ((Pane) imageView.getParent()).getChildren().remove(imageView);
                mapObjects.remove(this);
            }
        });
        }catch (ConcurrentModificationException e)
        {

        }
    }
}
