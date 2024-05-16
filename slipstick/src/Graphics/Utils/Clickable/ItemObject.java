package Graphics.Utils.Clickable;

import Graphics.Utils.Vector2;
import Items.Item;
import static Runnable.Main.gameController;

public class ItemObject extends ClickableObject {

    /**
     * The item this item object represents.
     */
    private Item item;

    public ItemObject(Vector2 centerPosition, Item item) {
        super(centerPosition);
        this.item = item;
    }

    public Item GetItem() { return item; }

    @Override
    public void Click() {
        gameController.SetSelectedItem(this);
    }
}
