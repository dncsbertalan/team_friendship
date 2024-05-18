package Graphics.Utils.Clickable;

import Graphics.Utils.Vector2;
import Graphics.GameWindowPanel;
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
            graphics2D.fillRect(this.position.x - 2, this.position.y - 2, this.size + 20, this.size + 20);
        }

        GameWindowPanel.DrawItem(graphics2D, item, centerPosition, 100);
    }

    @Override
    protected void click() {
        gameController.SetSelectedItem(item);
        gameController.NewScreenMessage(120, "added");
    }
}
