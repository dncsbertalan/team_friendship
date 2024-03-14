package Entities;

public class Professor extends Entity{

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
