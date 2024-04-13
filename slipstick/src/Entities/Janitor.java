package Entities;

import Constants.GameConstants;
import GameManagers.Game;
import Labyrinth.Room;

public class Janitor extends Entity implements IAI {

    private static int ID = 0;

    public Janitor(Game g) {
        super(g);
        this.Name = GameConstants.JanitorName + ++ID;
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

    @Override
    public void AI() {

    }
}
