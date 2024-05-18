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
    private final Game game;
    private int rounds = 1;
    private Student activeStudent;
    private IAI activeAIEntity;
    private final ArrayList<Student> studentsLeftThisRound;
    private final ArrayList<IAI> aiEntities;
//endregion

    public RoundManager(Game game) {
        this.game = game;

        this.studentsLeftThisRound = new ArrayList<>();
        this.aiEntities = new ArrayList<>();


        activeStudent = null;
        activeAIEntity = null;
    }

    /**
     * Must be called in {@link Game#InitPlayers(ArrayList)} before the game starts
     * or it will throw {@link NullPointerException}.
     */
    public void Init() {
        activeStudent = null;
        activeAIEntity = null;
        studentsLeftThisRound.clear();
        aiEntities.clear();

        this.studentsLeftThisRound.addAll(this.game.GetStudents());
        this.aiEntities.addAll(this.game.GetProfessors());
        this.aiEntities.addAll(this.game.GetJanitors());
        activeStudent = this.studentsLeftThisRound.get(0);
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

    public int GetCurrentRound() {
        return this.rounds;
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
    public void EndOfRound() {

        // TODO: reset remaining lists
        // reset the entities
        this.studentsLeftThisRound.addAll(this.game.GetStudents());
        this.aiEntities.addAll(this.game.GetProfessors());  // TODO: takarító

        activeStudent = this.studentsLeftThisRound.get(0);   // TODO: ennek kezdetben az első playernek kell lenni
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