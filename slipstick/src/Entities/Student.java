package Entities;

import Items.Item;
import Items.SlipStick;

public class Student extends Entity{
    
    int steps;
    /**
     * True if student is dead
     */
    boolean isDead = false;
    /**
     * The selected item
     */
    Item selectedItem;

    /**
     * Select an item from the inventory for further use
     * @param item selected item
     */
    public void SelectItem(Item item) {
        selectedItem = item;
    }

    /**
     * Select an Item from the room for pickup
     * @param item selected item
     */
    public void SelectItemFromRoom(Item item) {
        SelectItem(item);
    }

    /**
     * Drops the selected Item
     */
    public void DropSelectedItem() {
        DropItem(selectedItem);
    }

    /**
     * Uses the specified Item
     * @param item the specified item to be used
     */
    public void UseItem(Item item) {
        item.UseItem(this);
    }

    /**
     * Use selected item
     */
    public void UseSelectedItem() {
        UseItem(selectedItem);
    }

    /**
     * Activate the specified item
     * @param item the specified item
     */
    public void ActivateItem(Item item) {
        item.ActivateItem();
    }

    /**
     * Increases Move count by turns specified
     * @param turns number of turns specified
     */
    public void IncreaseMoveCount(int turns) {
        remainingTurns += turns;
    }

    /**
     * Kills the student
     */
    public void Die() {
        DropAllItems();
        isDead = true;
    }

    /**
     * Player wins
     */
    public void Win() {
        System.out.println("You won");
    }

    /**
     * Checks if the student is holding the Slipstick
     * @return true if the student has a slipstick
     */
    public boolean CheckForSlipstick() {
        boolean holder = false;
        for(Item item : inventory) {
            if (item.getClass() == SlipStick.class) {
                holder = true;
                break;
            }
        }
        return holder;
    }
}
