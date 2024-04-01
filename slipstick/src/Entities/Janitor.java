package Entities;

import GameManagers.Game;
import Labyrinth.Room;

public class Janitor extends Entity {

    public Janitor(Game g) {
        super(g);
    }

    /**
     * Tries to move to the specified room
     *
     * @param room the room it's trying to move into
     */
    @Override
    public void StepInto(Room room) {

    }

    /**
     * Notifies the entity that it stepped into a gassed room.
     */
    @Override
    public void SteppedIntoGassedRoom() {

    }
}
