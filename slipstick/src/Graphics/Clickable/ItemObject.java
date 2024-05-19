package Graphics.Clickable;

import Graphics.Utils.DrawUtils;
import Graphics.Utils.Vector2;
import Items.Item;

import java.awt.*;

import static Runnable.Main.gameController;

public class ItemObject extends ClickableObject {

    /**
     * The item this item object represents.
     */
    private final Item item;

    public ItemObject(Vector2 centerPosition, Item item, boolean canBeClicked) {
        super(centerPosition, canBeClicked);
        this.item = item;
    }

    public Item GetItem() { return item; }

    @Override
    public void Draw(Graphics2D graphics2D, Vector2 mousePos) {
        final boolean inside = IsInside(mousePos);
        final boolean selected = gameController.GetSelectedItem() == item;

        if (canBeClicked && (inside || selected)) {
            graphics2D.setColor(Color.yellow);
            graphics2D.fillRect(this.position.x - 2, this.position.y - 2, this.size + 10, this.size + 10);
        }

        DrawUtils.DrawItem(graphics2D, item, centerPosition, 100);
    }

    @Override
    protected void click() {
        gameController.SetSelectedItem(item);
    }
}