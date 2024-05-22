package Graphics.Utils;

import java.awt.geom.Path2D;

public class CustomShapeFactory {
    public static Path2D CreateCustomShape(double w, double h) {
        Path2D path = new Path2D.Double();
        path.moveTo(0, h / 9);
        path.lineTo(0, 8 * h / 9);
        path.quadTo(5, 8 * h / 9 + 5,w / 9, h);
        path.lineTo(8 * w / 9, h);
        path.quadTo(w - 5, 8 * h / 9 + 5 , w, 8 * h / 9);
        path.lineTo(w, h / 9);
        path.quadTo(w - 5, h / 9 - 5, 8 * w / 9, 0);
        path.lineTo(w / 9, 0);
        path.quadTo(5, h / 9 - 5, 0, h / 9);
        path.closePath();

        return path;
    }
}
