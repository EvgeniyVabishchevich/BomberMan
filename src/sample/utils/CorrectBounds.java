package sample.utils;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class CorrectBounds extends BoundingBox
{
    public CorrectBounds(Bounds bounds)
    {
        super(bounds.getMinX(), bounds.getMinY(), bounds.getMinZ(),
                bounds.getWidth(), bounds.getHeight(), bounds.getDepth());
    }

    public CorrectBounds(double minX, double minY, double width, double height)
    {
        super(minX, minY, width, height);
    }

    @Override
    public boolean intersects(double x, double y, double z, double w, double h, double d)
    {
        if (!this.isEmpty() && w >= 0.0D && h >= 0.0D && d >= 0.0D)
        {
            return x + w - 1 >= this.getMinX() && y + h - 1 >= this.getMinY() && z + d >= this.getMinZ() && x <= this.getMaxX() - 1 && y <= this.getMaxY() - 1 && z <= this.getMaxZ();
        } else
        {
            return false;
        }
    }
}
