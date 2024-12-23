package Items;

import Constants.Enums.ProtectionType;
import Constants.GameConstants;
import Entities.Student;

/**
 * Base class for all items in the game.
 * Contains everything generally assosiated with items.
 */
public abstract class Item {

    protected String name;

     /**
     * Wether the item is activated or not.
     */
    protected boolean activated;

    /**
     * The type of protection this item can provide.
     */
    protected ProtectionType protectionType = ProtectionType.none;

    /**
     * Automatically generates a name to the new item.
     */
    public Item() {
        AutoName();
    }

    public void SetName(String name) {
        this.name = name;
    }

    public String GetName() {
        return this.name;
    }

    /**
     * Called in {@link Item#Item()} to generate a specified name by the type of the item.
     */
    protected abstract void AutoName();

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

    /**
     * Returns the activation state of the item.
     * @return true if activated
     */
    public boolean GetActivation() {
        return activated;
    }
}