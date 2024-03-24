package Items;

import Entities.Student;
import Labyrinth.Room;

public class Transistor extends Item {
    /**
     * Pair of this transistor (mutual) if unpaired is null
     */
    private Transistor pair;
    /**
     * if paired and dropped the room it is in
     */
    private Room room; // currentRoom rename maybe?
    /**
     * true if pair is ready to teleport
     */
    private boolean pairReadyToTeleport; //pairSet?

    @Override
    public void UseItem(Student student) {
        //if not activated or not paired it just gets dropped
        if(!activated || pair == null) {
            return;
        }
        //First active transistor to be set down
        if(!pairReadyToTeleport) {
            SetCurrentRoom(student.GetCurrentRoom());
            NotifyPairImReady();
        }
        //Second transistor
        if (pairReadyToTeleport) {
            SetCurrentRoom(student.GetCurrentRoom());
            //teleport worked => both transistors deleted
            if(student.StepInto(pair.GetCurrentRoom())) {
                pair.GetCurrentRoom().RemoveItemFromRoom(pair);
                this.GetCurrentRoom().RemoveItemFromRoom(this);
            }
        }
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

    //possibly useless
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
    public void PairTransistor(Transistor transistor) {
        this.pair = transistor;
        transistor.pair = this;
    }
}
