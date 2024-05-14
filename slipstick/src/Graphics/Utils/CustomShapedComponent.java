package Graphics.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class CustomShapedComponent {
    protected Shape shape;

    public CustomShapedComponent(JComponent component) {
        component.setOpaque(false);
        component.setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    public void paintComponent(Graphics g, JComponent component) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        shape = createShape(component);

        // fill
        g2.setColor(getBackgroundColor());
        g2.fill(shape);

        // border
        g2.setColor(getBorderColor());
        g2.setStroke(new BasicStroke(5.0f));
        g2.draw(shape);

        g2.dispose();
    }

    public boolean contains(int x, int y, JComponent component) {
        if (shape == null || !shape.getBounds().equals(component.getBounds())) {
            shape = createShape(component);
        }
        return shape.contains(x, y);
    }

    protected abstract Shape createShape(JComponent component);

    protected abstract Color getBackgroundColor();

    protected abstract Color getBorderColor();
}
