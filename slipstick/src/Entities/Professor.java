package Entities;

import Constants.Enums;
import Constants.GameConstants;
import GameManagers.Game;
import Items.Item;
import Labyrinth.Map;
import Labyrinth.Room;
import Items.FFP2Mask;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Entity implements IAI {

    private static int ID = 0;

    public Professor(Game g) {
        super(g);
        this.Name = GameConstants.ProfName + ++ID;
    }

    @Override
    public void StepInto(Room room) {
        if (room.CanStepIn()) {
            this.room.RemoveProfessorFromRoom(this);
            this.room = room;
            room.AddProfessorToRoom(this);
        }
    }

    @Override
    public void SteppedIntoGassedRoom() {

        Item protectionItem = this.GetProtectionItem(Enums.ThreatType.gas);

        if (protectionItem == null) {   // no protection
            this.MissRounds(GameConstants.RoundsMissed_GasRoom);
            this.DropAllItems();
            Map map = this.game.GetMap();
            map.TransferProfessorToTeachersLounge(this);
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
     * Kills all students in the current room.
     */
    public void KillEveryoneInTheRoom() {
        List<Student> studentsAboutToBeAssassinated = new ArrayList<>(this.room.GetStudents());
        for(Student sIter : studentsAboutToBeAssassinated){
            this.KillStudent(sIter);
        }
    }

    /**
     * Kills specified student.
     * @param student specified student
     */
    public void KillStudent(Student student) {
        student.Kill(this);
    }

    @Override
    public void AI() {

    }
}
