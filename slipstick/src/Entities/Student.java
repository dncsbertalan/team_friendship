package Entities;

import Constants.Enums;
import Constants.GameConstants;
import GameManagers.Game;
import Items.*;
import Labyrinth.Map;
import Labyrinth.Room;

import java.util.Random;

public class Student extends Entity{
    
    private int selectedInventorySlot;
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

    public Student(Game game, String name) {
        this(game);
        this.Name = name;
    }

    @Override
    public void StepInto(Room room) {
        if (room.GetNeighbours().contains(this.room) && room.CanStepIn()){
            ChangeRoom(room);
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

        if(game.GetMap().IsWinningRoom(room))
            game.EndGame(true);
    }

    @Override
    public void SteppedIntoGassedRoom() {
        Item protectionItem = this.GetProtectionItem(Enums.ThreatType.gas);

        if (protectionItem == null) {   // no protection
            this.MissRounds(GameConstants.RoundsMissed_GasRoom);
            this.DropAllItems();
            Map map = this.game.GetMap();
            map.TransferStudentToMainHall(this);
        }
        else {  // has protection
            if (protectionItem.GetProtectionType() == Enums.ProtectionType.ffp2Mask) {
                FFP2Mask ffp2Mask = (FFP2Mask) protectionItem;
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
     * Selects a slot from the student's inventory. Must be a valid slot number between
     * 0 and {@link GameConstants#InventoryMaxSize}.
     * <p>
     * Automatically converts slot number to array index.
     * @param slot the number of the inventory slot
     * @throws IllegalArgumentException if the slot number is out of the range
     */
    public void SelectInventorySlot(int slot) throws IllegalArgumentException {
        if (slot < 1 || slot > GameConstants.InventoryMaxSize) throw new IllegalArgumentException();

        this.selectedInventorySlot = slot - 1;
        try {
            this.selectedItem = this.inventory.get(this.selectedInventorySlot);
        } catch (IndexOutOfBoundsException ex) {
            this.selectedItem = null;
        }
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
            DropAllItems();
            isDead = true;
            this.game.GetRoundManager().EndTurn();
            return;
        }

        if (protectionItem.GetProtectionType() == Enums.ProtectionType.wetCloth) {
            WetCloth wetCloth = (WetCloth) protectionItem;
            wetCloth.ProtectStudentFromProfessor(professor);
            Map map = this.game.GetMap();
            map.TransferStudentToMainHall(this);
        }

        if (protectionItem.GetProtectionType() == Enums.ProtectionType.tvsz) {
            TVSZ tvsz = (TVSZ) protectionItem;
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
            System.out.println(this.Name + "'s inventory is full");
            return;
        }
        if(item.getClass()== SlipStick.class){
            game.LastPhase(true,this);
        }
        inventory.add(item);
        try {   // if the new item is in the same index as the selected inventory slot than the item gets selected.
            this.selectedItem = this.inventory.get(this.selectedInventorySlot);
        } catch (IndexOutOfBoundsException ex) {
            this.selectedItem = null;
        }
        this.room.RemoveItemFromRoom(item);
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

    /**
     * Gets a random item from the student's inventory.
     * @return: The random item chosen.
     */
    public Item GetRandomItemFromStudent(){
        Random random = new Random();
        int minInclusive = 0;
        int maxExclusive = this.GetInventory().size();
        int itemIndex = random.ints(minInclusive, maxExclusive).findFirst().getAsInt();

        Item resultItem = this.GetInventory().get(itemIndex);
        return resultItem;
    }
}
