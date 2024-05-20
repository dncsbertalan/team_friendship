package Entities;

import Constants.Enums.*;
import Constants.GameConstants;
import GameManagers.Game;
import Items.Item;
import Items.SlipStick;
import Labyrinth.Room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static Runnable.Main.os;

public abstract class Entity {
//region Attributes ====================================================================================================
    /**
     * Name of the Entity
     */
    protected String Name = "anonymous";
    /**
     * Current Room of the Entity
     */
    protected Room room;
    static int id = 0;

    /**
     * Items belonging to the Entity (5 max)
     */
    protected ArrayList<Item> inventory = new ArrayList<>();

    /**
     * Number of moves left this Round
     */
    protected int remainingTurns;

    /**
     * Game instance.
     */
    protected Game game;

    /**
     * Whether the entity is KO from toxic gas.
     */
    private boolean paralysed;
//endregion ============================================================================================================

    public Entity(Game g) {
        game = g;
        id++;
        remainingTurns = GameConstants.STEPS_IN_ONE_ROUND;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name);
    }

    public String GetName() {
        return this.Name;
    }

    public void SetName(String name) {
        this.Name = name;
    }

    /**
     * Tries to move to the specified room
     * @param room the room it's trying to move into
     */
    public abstract boolean StepInto(Room room);

    /**
     * Notifies the entity that it stepped into a gassed room.
     */
    public abstract void SteppedIntoGassedRoom();

    /**
     * Increases Move count by turns specified
     * @param turns number of turns specified
     */
    public void IncreaseMoveCount(int turns) {
        remainingTurns += turns;
    }

    /**
     * Gives back the remaining turns of the entity.
     * @return: the remaining turns of the entity.
     */
    public int GetRemainingTurns(){
        this.paralysed = remainingTurns <= -1;
        return remainingTurns;
    }

    /**
     * Picks up specified item from current room
     * @param item the item getting picked up
     */
    public void PickUpItem(Item item) {
        if (inventory.size() == 5) {
            System.out.println("Inventory full");
            return;
        }
        if (item instanceof SlipStick) {
            // Studentnel megvalositva
            return;
        }
        if(!this.GetCurrentRoom().GetUnpickupableItems().contains(item)){
            this.inventory.add(item);
            this.room.RemoveItemFromRoom(item);
        } else {
            System.out.println("Item is not pickupable.");
        }

    }

    /**
     *Drops the selected item in the current room
     * @param item the selected item
     */
    public void DropItem(Item item) {
        room.AddItemToRoom(item);
        inventory.remove(item);
    }

    /**
     * Drops all items from inventory
     */
    public void DropAllItems() {
        for (Item item : this.inventory) {
            this.room.AddItemToRoom(item);
        }
        inventory.clear();
    }

    /**
     * Deletes specified Item from inventory
     * @param item specified item to be removed
     */
    public void DeleteItemFromInventory(Item item) {
        inventory.remove(item);
    }

    /**
     * Makes Entity miss n turns
     * @param missedTurns number of turns to be missed
     */
    public void MissRounds(int missedTurns) {
        remainingTurns -= missedTurns;
    }

    /**
     * Checks if player misses this turn
     * @return true if player misses turn
     */
    public boolean CheckRoundMiss() {
        return remainingTurns<=0;
    }

    /**
     * Gets current room.
     * @return entity's current room
     */
    public Room GetCurrentRoom() {
        return room;
    }

    /**
     * Sets the current room.
     * @param room entity's new current room
     */
    public void SetCurrentRoom(Room room) {
        this.room = room;
    }

    /**
     * Searches the entity's inventory for protective item that can protect the entity from a threat.
     * @param type the type of threat we look protection against
     * @return the protective item, null if there aren't any
     */
    public Item GetProtectionItem(ThreatType type) {

        // searches for protecting item against gas
        if (type == ThreatType.gas) {
            for (Item item : this.inventory) {
                if (item.GetProtectionType() == ProtectionType.ffp2Mask) {
                    return item;
                }
            }
        }

        // searches for protecting item against gas
        if (type == ThreatType.professor) {
            // prioritize active wet cloth
            for (Item item : this.inventory) {
                if (item.GetProtectionType() == ProtectionType.wetCloth) {
                    return item;
                }
            }
            // if none then look for tvsz
            for (Item item : this.inventory) {
                if (item.GetProtectionType() == ProtectionType.tvsz) {
                    return item;
                }
            }
        }

        return null;
    }

    public ArrayList<Item> GetInventory() {
        return inventory;
    }

    /**
     * Adds and item to the students inventory.
     * @param item  the added item.
     */
    public void AddItem(Item item) {
        if (inventory.size() == 5) {
            System.out.println("Inventory full");
            return;
        }
        this.inventory.add(item);
    }

    public void SetParalysed(boolean isParalysed){
        paralysed = isParalysed;
    }

    public boolean IsParalysed(){
        return paralysed;
    }
}
