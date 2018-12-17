package sample.game_objects.block_items;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.game_objects.BomberMan;
import sample.game_objects.GameObject;
import sample.utils.CorrectBounds;
import sample.utils.Vector2;

import java.util.ArrayList;

public class SpeedBonusItem extends BlockItem
{
    BomberMan bomberMan;

    public SpeedBonusItem(Vector2 coords, BomberMan bomberMan)
    {
        super(new ImageView(new Image("assets/speed_bonus.png")), coords);

        this.bomberMan = bomberMan;
    }

    @Override
    public void update(ArrayList<GameObject> mapObjects)
    {
        for (int object_number = 0; object_number < mapObjects.size(); object_number++)
        {
            if (mapObjects.get(object_number) instanceof BomberMan)
            {
                CorrectBounds bomberBounds = new CorrectBounds(mapObjects.get(object_number).getImageView().getBoundsInParent());
                CorrectBounds speedBonusBounds = new CorrectBounds(imageView.getBoundsInParent());

                if (bomberBounds.intersects(speedBonusBounds))
                {
                    bomberMan.setMaxSpeed(new Vector2(bomberMan.getMaxSpeed().getX() + 0.5,
                            bomberMan.getMaxSpeed().getY() + 0.5));

                    bomberMan.setSpeed(new Vector2(bomberMan.getSpeed().getX() > 0 ? bomberMan.getSpeed().getX() + 0.5
                            : bomberMan.getSpeed().getX() < 0 ? bomberMan.getSpeed().getX() - 0.5 : 0,

                            bomberMan.getSpeed().getY() > 0 ? bomberMan.getSpeed().getY() + 0.5 :
                                    bomberMan.getSpeed().getY() < 0 ? bomberMan.getSpeed().getY() - 0.5 : 0));

                    ((Pane) imageView.getParent()).getChildren().remove(imageView);
                    mapObjects.remove(this);
                }
            }
        }
    }
}
