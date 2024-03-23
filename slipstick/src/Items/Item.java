package Items;

import Entities.Student;

/**
 * Base class for all items in the game.
 * Contains everything generally assosiated with items.
 */
public abstract class Item {

    public enum ProtectionType {
        none, tvsz, wetCloth, ffp2Mask
    }

    /**
     * Wether the item is activated or not.
     */
    protected boolean activated;

    /**
     * The type of protection this item can provide.
     */
    private ProtectionType protectionType = ProtectionType.none;

    /**
     * Called when an item is used by the student.
     * @param student the item user
     */
    public abstract void UseItem(Student student);

    /**
     * Activates the item.
     */
    public void ActivateItem() {
        this.activated = true;
    }

    /**
     * Deactivates the item.
     */
    public void DeactivateItem() {
        this.activated = false;
    }

    /**
     * Returns the type of protection this item can provide.
     * @return the type of protection
     */
    public ProtectionType GetProtectionType() {
        return this.protectionType;
    }
}