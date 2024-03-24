package GameManagers;
import Constants.GameConstants;
import Entities.*;

/**
 * Responsible for the functioning of game rounds.
 * It determines which entity goes next and how many steps they can take.
 * The RoundManager initiates the division/merging of rooms.
*/
public class RoundManager{
    Game game;
    int rounds = 0;
    int maxRounds = 30;
    Student activeStudent;
    Professor activeProfessor;
    public RoundManager(Game g){
        game = g;
    }
    /**
     * Sets the active student.
     * @param student the new active student
     */
    public void SetActiveStudent(Student student){
        activeStudent = student;
    }

    /**
     * Sets the active professor.
     * @param professor the new active professor
     */
    public void SetActiveProfessor(Professor professor){
        activeProfessor = professor;
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
     *Start new round.
     */
    void nextRound(){}
    /**
     * Ends current round and notifies other classes.
     */
    void EndOfRound(){

    }

    public void NextRound() {
        rounds+=1;
        if(rounds== GameConstants.MaxRounds){
            game.EndGame(false);
        }
    }
}