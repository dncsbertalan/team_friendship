package Entities;

import Constants.Enums;
import Constants.GameConstants;
import GameManagers.Game;
import Items.FFP2Mask;
import Items.Item;
import Items.SlipStick;
import Labyrinth.Map;
import Labyrinth.Room;

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

    public Student(Game g) {
        super(g);
    }

    @Override
    public void StepInto(Room room) {

        if (room.CanStepIn()) {
            this.room.RemoveStudentFromRoom(this);
            this.room = room;
            room.AddStudentToRoom(this);
        }
        else {
            System.out.println("Student can't step into room");
        }
    }

    @Override
    public void SteppedIntoGassedRoom() {
        System.out.println("\t-> Student (" + this.hashCode() + ") is in gassed room (" + this.room.hashCode() + ")");
        Item protectionItem = this.GetProtectionItem(Enums.ThreatType.gas);

        if (protectionItem == null) {   // no protection
            System.out.println("\t-> Student (" + this.hashCode() + ") doesn't have protective item.");
            this.MissRounds(GameConstants.RoundsMissed_GasRoom);
            this.DropAllItems();
            Map map = this.game.GetMap();
            map.TransferStudentToMainHall(this);
        }
        else {  // has protection
            if (protectionItem.GetProtectionType() == Enums.ProtectionType.ffp2Mask) {
                FFP2Mask ffp2Mask = (FFP2Mask) protectionItem;
                System.out.println("\t-> Student (" + this.hashCode() + ") has protective item (" + ffp2Mask.hashCode() + ")");
                ffp2Mask.DecreaseDurability();
                this.IncreaseMoveCount(GameConstants.FFP2Mask_MoveCountIncrease);
            }
        }
    }

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
