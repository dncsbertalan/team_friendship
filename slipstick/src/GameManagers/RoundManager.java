package GameManagers;

/**
 * Responsible for the functioning of game rounds.
 * It determines which entity goes next and how many steps they can take.
 * The RoundManager initiates the division/merging of rooms.
*/
public class RoundManager{
    Student activeStudent;
    Professor activeProfessor;
    /**
     * Sets the active student with given number of steps.
     * @param student the new active student
     * @param steps the number of its turns
     */
    public void SetActiveStudent(Student student, int steps){
        activeStudent = student;
        student.steps =  steps;
    }

    /**
     * Sets the active professor with given number of steps.
     * @param professor the new active professor
     * @param steps the number of its turns
     */
    public void SetActiveProfessor(Professor professor, int steps){
        activeProfessor = professor;
        professor.steps = steps;
    }

    /**
     * @return the active student
     */
    public Student GetActiveStudent(){
        return activeStudent;
    }

    /**
     * @return the active professor
     */
    public Professor GetActiveprofessor(){
        return activeProfessor;
    }

    /**
     * Indicates that the active student finished with its turns in the current round.
     */
   public void StudentFinished(){

   }

    /**
     * Ends current round and notifies other classes.
     */
    void EndOfRound(){

    }
}