package Graphics.Utils;

import Constants.GameConstants;

import javax.swing.*;
import java.awt.*;

public class PlayerNameLabel extends JLabel {
    private final CustomShapedComponent customShapedComponent;

    public PlayerNameLabel(String text) {
        super(text);
        customShapedComponent = new CustomShapedComponent(this) {
            @Override
            protected Shape createShape(JComponent component) {
                double w = component.getWidth();
                double h = component.getHeight();
                return CustomShapeFactory.CreateCustomShape(w, h);
            }

            @Override
            protected Color getBackgroundColor() {
                return GameConstants.MenuPanel2_TEXTFIELD_LABEL_BACKGROUND_COLOR;
            }

            @Override
            protected Color getBorderColor() {
                return GameConstants.MenuPanel2_TEXTFIELD_LABEL_BORDER_COLOR;
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
