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
    private final boolean unpickable;

    public ItemObject(Vector2 centerPosition, Item item, float scale, boolean canBeClicked, boolean unpickable) {
        super(centerPosition, canBeClicked, scale);
        this.item = item;
        this.size = (int) (20 * scale / 100);
        this.position = new Vector2(centerPosition.x - size / 2, centerPosition.y - size / 2);
        this.unpickable = unpickable;
    }

    public Item GetItem() { return item; }

    @Override
    public void Draw(Graphics2D graphics2D, Vector2 mousePos) {
        final boolean inside = IsInside(mousePos);
        final boolean selected = gameController.GetSelectedItem() == item;

        if (canBeClicked && (inside || selected)) {
            graphics2D.setColor(Color.cyan);
            //graphics2D.fillRect(this.position.x - 2, this.position.y - 2, this.size, this.size);

            DrawUtils.DrawItemOutline(graphics2D, item, centerPosition, 100, unpickable);
        }

        DrawUtils.DrawItem(graphics2D, item, centerPosition, 100);
    }

    @Override
    protected void click() {
        gameController.SetSelectedItem(item);
    }
}
