package Items;

import Entities.Student;
import Labyrinth.Room;

public class Transistor extends Item {

    private Transistor pair;
    private Room room; // currentRoom rename maybe?
    private boolean pairReadyToTeleport;

    @Override
    public void UseItem(Student student) {

    }

    /**
     * Sets the current room where this transistor is dropped.
     * @param room the current room
     */
    public void SetCurrentRoom(Room room) {
        this.room = room;
    }

    /**
     * Returns the room the transistor is currently dropped.
     * @return the current room
     */
    public Room GetCurrentRoom() {
        return this.room;
    }

    /**
     * Sets the pair ready, so it can now teleport.
     */
    public void SetPairReady() {
        this.pairReadyToTeleport = true;
    }

    /**
     * Notifies the pair transistor that this transistor is activated and is dropped.
     * The pair can initiate a teleport.
     */
    public void NotifyPairImReady() {
        this.pair.SetPairReady();
    }

    /**
     * Sets this transistor's pair.
     * @param transistor the new pair
     */
    public  void PairTransistor(Transistor transistor) {
        this.pair = transistor;
    }
}
