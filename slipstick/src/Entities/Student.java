package Entities;

import Constants.Enums;
import Constants.GameConstants;
import Control.GameController;
import GameManagers.Game;
import GameManagers.OUTCOME;
import Items.*;
import Labyrinth.Map;
import Labyrinth.Room;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import static Runnable.Main.soundManager;

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

    private final ArrayList<Item> tempUnpickableItems = new ArrayList<>();
    private final HashMap<Item, Room> tempUnpickableRooms = new HashMap<>();

    private int remainingItemPickUp = GameConstants.ITEM_PICK_UP_IN_ONE_ROUND;

    public Student(Game g) {
        super(g);
    }

    public Student(Game game, String name) {
        this(game);
        this.Name = name;
    }

    @Override
    public boolean StepInto(Room room, boolean sentByJanitor) {
        if (sentByJanitor) {
            ChangeRoom(room);
            return true;
        }

        if (room.GetNeighbours().contains(this.room) && room.CanStepIn() && remainingTurns>0){
            ChangeRoom(room);
            this.remainingTurns--;
            return true;
        }
        return false;
    }

    /**
     * Move to room without any checks
     * @param room the room to be moved into
     */
    public void ChangeRoom(Room room) {
        this.room.RemoveStudentFromRoom(this);
        this.room = room;
        room.AddStudentToRoom(this);

        if (game.GetMap().IsWinningRoom(room) && this.CheckForSlipstick()) {
            game.EndGame(true, OUTCOME.Win);
        }
    }

    @Override
    public void SteppedIntoGassedRoom() {
        Item protectionItem = this.GetProtectionItem(Enums.ThreatType.gas);

        if (protectionItem == null) {   // no protection
            this.MissRounds(GameConstants.RoundsMissed_GasRoom);
            this.DropAllItems();
            this.SetParalysed(true);
        }
        else {  // has protection
            if (protectionItem.GetProtectionType() == Enums.ProtectionType.ffp2Mask) {
                FFP2Mask ffp2Mask = (FFP2Mask) protectionItem;
                ffp2Mask.DecreaseDurability();
                this.IncreaseMoveCount(GameConstants.FFP2Mask_MoveCountIncrease);
                if (ffp2Mask.GetRemainingUsages() == 0) this.inventory.remove(ffp2Mask);
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
    }

    /**
     * Return the index of the selected inventory slot of the student.
     * @return the selected slot's index.
     */
    public int GetSelectedInventorySlot() {
        return this.selectedInventorySlot;
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
     * Adds a temporary unpickable item.
     * @param item  the item
     */
    public void AddTemporaryUnpickableItem(Item item, Room room) {
        tempUnpickableItems.add(item);
        tempUnpickableRooms.put(item, room);
    }

    /**
     * Clears temporary unpickable item.s
     */
    public void ClearTemporaryUnpickableItems() {
        for (Item item : tempUnpickableItems) {
            Room curRoom = tempUnpickableRooms.get(item);
            curRoom.GetUnpickupableItems().remove(item);
            curRoom.AddItemToRoom(item);
        }
        tempUnpickableItems.clear();
        tempUnpickableRooms.clear();
    }

    /**
     * Kills the student
     */
    public void Kill(Professor professor) {

        Item protectionItem = this.GetProtectionItem(Enums.ThreatType.professor);

        if (protectionItem == null) {   // HAS NO PROTECTION
            DropAllItems();
            isDead = true;
            String message = professor.GetName() + " killed " + this.Name;
            game.GetGameController().NewScreenMessage(300, Color.RED, message);
            //this.game.GetRoundManager().EndTurn();
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
            if (tvsz.GetRemainingPages() > 0) {
                if (tvsz.GetRemainingPages() == 1)
                    soundManager.playSoundOnce(GameConstants.SOUND_TVSZ_NO_REMAINING_PAGES);
                tvsz.DecreaseUsability();
                Map map = this.game.GetMap();
                map.TransferProfessorToTeachersLounge(professor);
            }
            else {
                DropAllItems();
                isDead = true;
                String message = professor.GetName() + " killed " + this.Name;
                game.GetGameController().NewScreenMessage(300, Color.RED, message);
            }
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
        if (remainingItemPickUp > 0) {
            if (item instanceof SlipStick){
                game.LastPhase(true,this);
            }
            if(this.GetCurrentRoom().GetUnpickupableItems().contains(item) == false){
                this.inventory.add(item);
                try {   // if the new item is in the same index as the selected inventory slot than the item gets selected.
                    this.selectedItem = this.inventory.get(this.selectedInventorySlot);
                } catch (IndexOutOfBoundsException ex) {
                    this.selectedItem = null;
                }
                this.room.RemoveItemFromRoom(item);
                remainingItemPickUp--;
            } else {
                System.out.println("Item is not pickupable.");
            }
        }
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

    public Item GetSelectedItem() {
        try {
            this.selectedItem = this.inventory.get(this.selectedInventorySlot);
        } catch (IndexOutOfBoundsException ex) {
            this.selectedItem = null;
        }
        return selectedItem;
    }

    public void SetSelectedItem(Item item) {
        selectedItem = item;
    }

    public int getRemainingItemPickUp() {
        return remainingItemPickUp;
    }

    public void resetRemainingItemPickUp() {
        this.remainingItemPickUp = GameConstants.ITEM_PICK_UP_IN_ONE_ROUND;
    }
}
