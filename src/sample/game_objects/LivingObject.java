package sample.game_objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.utils.Vector2;

import java.util.ArrayList;

public abstract class LivingObject extends GameObject
{
    protected Vector2 speed;
    protected Vector2 maxSpeed;

    protected boolean alive = true;

    private Image upImg;
    private Image downImg;
    private Image leftImg;
    private Image rightImg;

    public LivingObject(Image upImg, Image downImg, Image leftImg, Image rightImg, Vector2 coords, Vector2 maxSpeed)
    {
        super(new ImageView(downImg), coords);

        this.upImg = upImg;
        this.downImg = downImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.maxSpeed = maxSpeed;

        speed = new Vector2();
    }

    public abstract void die(ArrayList<GameObject> mapObjects);

    @Override
    public void update(ArrayList<GameObject> mapObjects)
    {
        getImageView().setTranslateX(getImageView().getTranslateX() + speed.getX());
        getImageView().setTranslateY(getImageView().getTranslateY() + speed.getY());
    }

    public void startMoving(Direction direction)
    {
        if (direction == Direction.UP)
        {
            imageView.setImage(upImg);
            speed.add(new Vector2(0, -maxSpeed.getY()));
        }

        if (direction == Direction.DOWN)
        {
            imageView.setImage(downImg);
            speed.add(new Vector2(0, maxSpeed.getY()));
        }

        if (direction == Direction.LEFT)
        {
            imageView.setImage(leftImg);
            speed.add(new Vector2(-maxSpeed.getX(), 0));
        }

        if (direction == Direction.RIGHT)
        {
            imageView.setImage(rightImg);
            speed.add(new Vector2(maxSpeed.getX(), 0));
        }
    }

    public void stopMoving(Direction direction)
    {
        if (direction == Direction.UP)
        {
            speed.add(new Vector2(0, maxSpeed.getY()));
        }

        if (direction == Direction.DOWN)
        {
            speed.add(new Vector2(0, -maxSpeed.getY()));
        }

        if (direction == Direction.LEFT)
        {
            speed.add(new Vector2(maxSpeed.getX(), 0));
        }

        if (direction == Direction.RIGHT)
        {
            speed.add(new Vector2(-maxSpeed.getX(), 0));
        }

        boolean moving_x = !(speed.getX() == 0);
        boolean moving_y = !(speed.getY() == 0);

        if (!(moving_x == moving_y))
        {
            if (moving_x)
            {
                if (speed.getX() > 0) imageView.setImage(rightImg);
                else imageView.setImage(leftImg);
            }

            if (moving_y)
            {
                if (speed.getY() > 0) imageView.setImage(downImg);
                else imageView.setImage(upImg);
            }
        }
    }

    public Vector2 getSpeed()
    {
        return speed;
    }

    public void setSpeed(Vector2 speed)
    {
        this.speed = speed;
    }

    public Vector2 getMaxSpeed()
    {
        return maxSpeed;
    }

    public void setMaxSpeed(Vector2 maxSpeed)
    {
        this.maxSpeed = maxSpeed;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public enum Direction
    {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
