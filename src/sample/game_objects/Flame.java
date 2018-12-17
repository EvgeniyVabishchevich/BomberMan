package sample.game_objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.utils.CorrectBounds;
import sample.utils.Vector2;

import java.util.ArrayList;

public class Flame extends GameObject
{
    public Flame(Vector2 coords, ArrayList<GameObject> mapObjects)
    {
        super(new ImageView(new Image("assets/flame.png")), coords);

        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(0.5), actionEvent -> {
            ((Pane) imageView.getParent()).getChildren().remove(imageView);
            mapObjects.remove(Flame.this);
        }));
        timer.play();
    }

    @Override
    public void update(ArrayList<GameObject> mapObjects)
    {
        CorrectBounds flameBounds = new CorrectBounds(imageView.getBoundsInParent());

        for (int object_number = 0; object_number < mapObjects.size(); object_number++)
        {
            CorrectBounds objectBounds = new CorrectBounds(mapObjects.get(object_number).getImageView().getBoundsInParent());

            if (mapObjects.get(object_number) instanceof LivingObject && flameBounds.intersects(objectBounds))
            {
                ((LivingObject) mapObjects.get(object_number)).die(mapObjects);
            }
        }
    }
}
