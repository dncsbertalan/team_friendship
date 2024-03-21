package Entities;
import GameManagers.Game;
import Items.Item;
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
     * Game
     */
    private Game game;


    public Entity(Game g) {
        game = g;
    }
    /**
     * Tries to move to the specified room
     * @param room the room it's trying to move into
     */
    public void StepInto(Room room) {
        if (room.CanStepIn()){
            this.room = room;
        } else System.out.println("Can't step into room");
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
     * Sets inGassedRoom true
     */
    public void SteppedIntoGasChamber() {
        inGassedRoom = true;
    }

    /**
     * Gets current room
     * @return entity's current room
     */
    public Room GetCurrentRoom() {
        return room;
    }

    /**
     * Increases Move count by turns specified
     * @param turns number of turns specified
     */
    public void IncreaseMoveCount(int turns) {
        remainingTurns += turns;
    }

    /**
     * Protects Entity
     */
    public void ProtectMe() {
        onLifeSupport = true;
    }
}
