package Graphics.Utils;

import Constants.GameConstants;

import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {
    private final CustomShapedComponent customShapedComponent;

    public MenuButton(String text, Color backgroundColor, Color borderColor) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        customShapedComponent = new CustomShapedComponent(this) {
            @Override
            protected Shape createShape(JComponent component) {
                double w = component.getWidth();
                double h = component.getHeight();
                return CustomShapeFactory.CreateCustomShape(w, h);
            }

            @Override
            protected Color getBackgroundColor() {
                return backgroundColor;
            }

            @Override
            protected Color getBorderColor() {
                return borderColor;
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        customShapedComponent.paintComponent(g, this);
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        return customShapedComponent.contains(x, y, this);
    }
}
