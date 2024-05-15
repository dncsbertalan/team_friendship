package Graphics.Utils.Clickable;

import Graphics.Utils.Vector2;

import java.awt.*;

public abstract class ClickableObject {

    protected final Vector2 position;
    protected final Vector2 centerPosition;
    protected final int size;

    public ClickableObject(Vector2 centerPosition) {
        this.size = 25;
        this.centerPosition = centerPosition;
        this.position = new Vector2(centerPosition.x - this.size  / 2, centerPosition.y - this.size / 2);
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

    /**
     * Defines what happens when clicked on this Clickable object.
     */
    public abstract void Click();
}