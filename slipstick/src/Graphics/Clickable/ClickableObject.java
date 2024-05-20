package Graphics.Clickable;

import Graphics.Utils.Vector2;

import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;

public abstract class ClickableObject {

    protected Vector2 position;
    protected final Vector2 centerPosition;
    protected int size;
    protected boolean canBeClicked;
    protected final float scale;

    public ClickableObject(Vector2 centerPosition, boolean canBeClicked, float scale) {
        this.centerPosition = centerPosition;
        this.canBeClicked = canBeClicked;
        this.scale = scale;
    }

    /**
     * Draws this to the screen.
     * @param graphics2D graphics2D instance
     * @param mousePos the position of the mouse
     */
    public void Draw(Graphics2D graphics2D, Vector2 mousePos) {
        boolean inside = IsInside(mousePos);

        if (canBeClicked && inside) {
            graphics2D.setColor(Color.yellow);
            graphics2D.fillRect(centerPosition.x - size / 2 - 2, centerPosition.y - size / 2 - 2, this.size + 4, this.size + 4);
        }

        graphics2D.setColor(new Color(150, 75, 0));
        graphics2D.fillRect(centerPosition.x - size / 2, centerPosition.y - size / 2, this.size, this.size);
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
    public void Click() {
        if (canBeClicked) click();
    }

    protected abstract void click();
}
