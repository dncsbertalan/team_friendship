package Graphics.Utils;

import Constants.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class MenuButton extends JButton {
    private Shape shape;

    public MenuButton(String label) {
        super(label);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        shape = createShape();

        // fill
        g2.setColor(GameConstants.Menu_BUTTON_BACKGROUND_COLOR);
        g2.fill(shape);

        // border
        g2.setColor(GameConstants.Menu_BUTTON_BORDER_COLOR);
        g2.setStroke(new BasicStroke(5.0f));
        g2.draw(shape);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = createShape();
        }
        return shape.contains(x, y);
    }

    private Shape createShape() {
        double w = getWidth();
        double h = getHeight();

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
