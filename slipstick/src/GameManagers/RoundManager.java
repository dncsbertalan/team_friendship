package GameManagers;
import Constants.GameConstants;
import Entities.*;

import java.util.ArrayList;

/**
 * Responsible for the functioning of game rounds.
 * It determines which entity goes next and how many steps they can take.
 * The RoundManager initiates the division/merging of rooms.
*/
public class RoundManager{

//region Attributes ====================================================================================================
    Game game;
    int rounds = 0;
    Student activeStudent;
    IAI activeAIEntity;
    ArrayList<Student> studentsLeftThisRound;
    ArrayList<IAI> aiEntities;
//endregion

    public RoundManager(Game game) {
        this.game = game;

        this.studentsLeftThisRound = new ArrayList<>();
        this.studentsLeftThisRound.addAll(this.game.GetStudents());
        this.aiEntities = new ArrayList<>();
        this.aiEntities.addAll(this.game.GetProfessors());  // TODO: takarító

        activeStudent = null;   // TODO: ennek kezdetben az első playernek kell lenni
        activeAIEntity = null;
    }

    /**
     * Sets the active student.
     * @param student the new active student
     */
    public void SetActiveStudent(Student student) {
        activeStudent = student;
    }

    /**
     * @return the active student
     */
    public Student GetActiveStudent() {
        return activeStudent;
    }

    /**
     * @return the active professor
     */
    public IAI GetActiveAIEntity() {
        return activeAIEntity;
    }

    /**
     *Start new round.
     */
    public void NextRound() {
        rounds++;
        if (rounds == GameConstants.MaxRounds) {
            game.EndGame(false);
        }
    }

    /**
     * Ends current round.
     */
    void EndOfRound() {

        // reset the entities
        this.studentsLeftThisRound.addAll(this.game.GetStudents());
        this.aiEntities.addAll(this.game.GetProfessors());  // TODO: takarító

        activeStudent = null;   // TODO: ennek kezdetben az első playernek kell lenni
        activeAIEntity = null;

        // TODO: kör vége logikák számlálók...

        // start a new round
        this.NextRound();
    }

    /**
     * Ends the currently active entity's turn and activates the next.
     */
    public void EndTurn(){

        // TODO: unpickable items reset

        if (activeStudent != null) {
            studentsLeftThisRound.remove(activeStudent);
            // Make the next student active
            if (!studentsLeftThisRound.isEmpty()) {
                activeStudent = studentsLeftThisRound.get(0);
                return;
            }
            // if there are no students left this round
            activeStudent = null;
            if (!aiEntities.isEmpty()) {
                activeAIEntity = aiEntities.get(0);
            }
        }
        if (activeAIEntity != null) {
            aiEntities.remove(activeAIEntity);
            // Make the next entity active
            if (!aiEntities.isEmpty()) {
                activeAIEntity = aiEntities.get(0);
                return;
            }
        }
        // if there are no entities left this round
        this.EndOfRound();
    }
}