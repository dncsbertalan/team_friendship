package Entities;

import Constants.Enums.*;
import GameManagers.Game;
import Items.Item;
import Items.SlipStick;
import Labyrinth.Room;

import java.util.ArrayList;

public abstract class Entity {
    /**
     * Name of the Entity
     */
    String Name = "Mike Wazowski";
    /**
     * Current Room of the Entity
     */
    Room room;
    /**
     * If the current Room is Gassed
     */
    boolean inGassedRoom;
    /**
     * Items belonging to the Entity (5 max)
     */
    ArrayList<Item> inventory = new ArrayList<>();
    /**
     * Number of moves left this Round
     */
    int remainingTurns;
    /**
     * protected against next death
     */
    boolean onLifeSupport = false;

    /**
     * Game instance.
     */
    protected Game game;

    public Entity(Game g) {
        game = g;
    }

    /**
     * Tries to move to the specified room
     * @param room the room it's trying to move into
     */
    public abstract void StepInto(Room room);

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
     * Picks up specified item from current room
     * @param item the item getting picked up
     */
    public void PickUpItem(Item item) {
        if (inventory.size() == 5) {
            System.out.println("Inventory full");
            return;
        }
        if (item.getClass() != SlipStick.class) {
            return;
        }
        inventory.add(item);
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
        for (Item item : inventory) {
            DropItem(item);
        }
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
     * Protects Entity
     */
    public void ProtectMe() {
        onLifeSupport = true;
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
}
