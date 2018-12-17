package sample.utils;

public class Vector2
{
    private double x, y;

    public Vector2(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2()
    {
        this.x = 0;
        this.y = 0;
    }

    public void add(Vector2 additionalVector)
    {
        x += additionalVector.getX();
        y += additionalVector.getY();
    }

    public double getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}
