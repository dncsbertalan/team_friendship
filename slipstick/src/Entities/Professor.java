package Entities;

import GameManagers.Game;
import Labyrinth.Map;
import Labyrinth.Room;

public class Professor extends Entity{

    public Professor(Game g) {
        super(g);
    }

    public void StepInto(Room room) {
        if (room.CanStepIn()){
            this.SetCurrentRoom(room);
            Map map = game.GetMap();
            room.RemoveProfessorFromRoom(this);
            room.AddProfessorToRoom(this);
        } else System.out.println("Can't step into room");
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
