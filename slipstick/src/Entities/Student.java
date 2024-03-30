package Entities;

import Constants.Enums;
import Constants.GameConstants;
import GameManagers.Game;
import Items.*;
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
        if (room.GetNeighbours().contains(this.room) && room.CanStepIn()){
            ChangeRoom(room);
        }
        else {
            System.out.println("\t-> Student (" + this.hashCode() + ") cannot step into room (" + room.hashCode() + ")");
        }

    }

    /**
     * Move to room without any checks
     * @param room the room to be moved into
     */
    public void ChangeRoom(Room room) {
        this.room.RemoveStudentFromRoom(this);
        this.room = room;
        room.AddStudentToRoom(this);
        //System.out.println("\t-> Student (" + this.hashCode() + ") stepped into room (" + this.room.hashCode() + ")");

        if(game.GetMap().IsWinningRoom(room))
            game.EndGame(true);
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
        if(selectedItem.getClass() == SlipStick.class){
            game.LastPhase(false,this);
        }
        DropItem(selectedItem);
        selectedItem = null;
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
    public void Kill(Professor professor) {

        Item protectionItem = this.GetProtectionItem(Enums.ThreatType.professor);

        if (protectionItem == null) {   // HAS NO PROTECTION
            System.out.println("\t-> Student (" + this.hashCode() + ") doesn't have protective item, the student dies");
            DropAllItems();
            isDead = true;
            return;
        }

        if (protectionItem.GetProtectionType() == Enums.ProtectionType.wetCloth) {
            WetCloth wetCloth = (WetCloth) protectionItem;
            System.out.println("\t-> Student (" + this.hashCode() + ") has protective item (" + wetCloth.hashCode() + "), it doesn't die");
            wetCloth.ProtectStudentFromProfessor(professor);
            Map map = this.game.GetMap();
            map.TransferStudentToMainHall(this);
        }

        if (protectionItem.GetProtectionType() == Enums.ProtectionType.tvsz) {
            TVSZ tvsz = (TVSZ) protectionItem;
            System.out.println("\t-> Student (" + this.hashCode() + ") has protective item (" + tvsz.hashCode() + "), it doesn't die");
            tvsz.DecreaseUsability();
            Map map = this.game.GetMap();
            map.TransferProfessorToTeachersLounge(professor);
        }
    }

    /**
     * Returns whether the student is dead.
     * @return whether the student is dead
     */
    public boolean IsDead() {
        return isDead;
    }

    /**
     * Picks up specified item from current room
     * @param item the item getting picked up
     */
    @Override
    public void PickUpItem(Item item) {
        if (inventory.size() == GameConstants.InventoryMaxSize) {
            System.out.println("\t-> Player's (" + this.hashCode() + ") inventory is full");
            return;
        }
        if(item.getClass()== SlipStick.class){
            game.LastPhase(true,this);
        }
        inventory.add(item);
        this.room.RemoveItemFromRoom(item);
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

    /**
     * Override of DropItem for transistor usage
     * @param item the selected item
     */
    @Override
    public void DropItem(Item item) {
        super.DropItem(item);

        if (item.getClass() == Transistor.class) {
            item.UseItem(this);
        }
    }

    /**
     * Pairs two transistors
     * @param t1 first transistor (this will start the pairing)
     * @param t2 second transistor
     */
    public void PairTransistors(Transistor t1, Transistor t2) {
        t1.PairTransistor(t2);
    }
}
