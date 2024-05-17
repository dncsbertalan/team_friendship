package Graphics.Utils.Clickable;

import Graphics.Utils.Vector2;
import Items.Item;
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
    protected void click() {
        gameController.SetSelectedItem(this);
    }
}
