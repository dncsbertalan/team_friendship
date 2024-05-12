package Graphics;

import Utils.Vector2;

import java.awt.*;

public class ClickablePane {

    protected final Vector2 position;
    protected final Vector2 centerPosition;
    protected final int size;

    public ClickablePane(Vector2 centerPosition) {
        this.centerPosition = centerPosition;
        this.position = new Vector2(centerPosition.x - 16, centerPosition.y - 16);
        this.size = 25;
    }

    /**
     * Draws this to the screen.
     * @param graphics2D graphics2D instance
     * @param mousePos the position of the mouse
     */
    public void Draw(Graphics2D graphics2D, Vector2 mousePos) {
        boolean inside = IsInside(mousePos);
        // TODO INSIDE NEM ITT HANEM MÁSHOL CHECK?
        if (inside) {
            graphics2D.setColor(Color.yellow);
            graphics2D.fillRect(this.position.x - 2, this.position.y - 2, this.size + 4, this.size + 4);
        }

        graphics2D.setColor(new Color(150, 75, 0));
        graphics2D.fillRect(this.position.x, this.position.y, this.size, this.size);
    }

    /**
     * Returns wether the given point is inside of this pane's rectangle box.
     * @param position the position of the point
     * @return wether this contains the point or not
     */
    public boolean IsInside(Vector2 position) {
        return position.x < this.position.x + this.size
                && position.x > this.position.x
                && position.y < this.position.y + this.size
                && position.y > this.position.y;
    }
}
