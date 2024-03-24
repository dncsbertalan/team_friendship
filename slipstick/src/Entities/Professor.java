package Entities;

import Constants.Enums;
import Constants.GameConstants;
import GameManagers.Game;
import Items.Item;
import Items.SlipStick;
import Labyrinth.Map;
import Labyrinth.Room;
import Items.FFP2Mask;
import Items.Item;
import Labyrinth.Map;
import Labyrinth.Room;

public class Professor extends Entity{

    public Professor(Game g) {
        super(g);
    }

    @Override
    public void StepInto(Room room) {
        if (room.CanStepIn()) {
            this.room.RemoveProfessorFromRoom(this);
            this.room = room;
            room.AddProfessorToRoom(this);
        }
        else {
            System.out.println("Student can't step into room");
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
     * Kills all students in the current room
     */
    public void KillEveryoneInTheRoom() {
        /* for(Entity entity : room.entities) {
            if(entity.getClass() == Student.class) {
                KillStudent(entity);
            }
        }*/
    }

    /**
     * Kills specified student
     * @param student specified student
     */
    public void KillStudent(Student student) {
        student.Die();
    }

}
