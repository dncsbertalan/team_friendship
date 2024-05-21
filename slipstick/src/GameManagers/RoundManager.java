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
    private final ArrayList<Student> studentsParalyzedThisRound;
    private final ArrayList<IAI> aiEntities;
//endregion

    public RoundManager(Game game) {
        this.game = game;

        this.studentsLeftThisRound = new ArrayList<>();
        this.studentsParalyzedThisRound = new ArrayList<>();
        this.aiEntities = new ArrayList<>();

        activeStudent = null;
        activeAIEntity = null;
    }

    /**
     * Must be called in {@link Game#InitEntities()} before the game starts or it will throw {@link NullPointerException}.
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

        // Reset the entity lists
        for (Student student : game.GetStudents()) {
            if (!student.IsDead() && !student.IsParalysed()) {
                this.studentsLeftThisRound.add(student);
            } else if (student.IsParalysed()) {
                this.studentsParalyzedThisRound.add(student);
            }
        }
        this.aiEntities.addAll(this.game.GetProfessors());
        this.aiEntities.addAll(this.game.GetJanitors());

        // Reset student steps
        for (Student student : studentsLeftThisRound) {
            student.ResetMoveCount();
        }

        // Reset janitor and prof steps
        for (IAI entity : aiEntities) {
            ((Entity) entity).ResetMoveCount();
        }

        // Check if everyone is dead
        if (studentsLeftThisRound.isEmpty() && studentsParalyzedThisRound.isEmpty()) {
            game.EndGame(false);
            return;
        }

        // Reset active student
        if (!studentsLeftThisRound.isEmpty()) {
            activeStudent = studentsLeftThisRound.get(0);
        } else if (!studentsParalyzedThisRound.isEmpty()) {
            activeStudent = studentsParalyzedThisRound.get(0);
        } else {
            activeStudent = null;
        }

        activeAIEntity = null;

        // TODO: kör vége logikák számlálók...

        // Start a new round
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

            if (!studentsParalyzedThisRound.isEmpty()) {
                activeStudent = studentsParalyzedThisRound.get(0);
                if (activeStudent.GetRemainingTurns() == -1) {
                    activeStudent.SetParalysed(false);
                    activeStudent.ResetMoveCount();
                }
                return;
            }
            // if there are no students left this round
            activeStudent = null;
            if (!aiEntities.isEmpty()) {                                                                                                                                                                                                                                                                                                                                                                                     //activeAIEntity = aiEntities.get(0);
                // Because the ai entity array is not empty so this professor is just temporary,
                // it will be deleted right away in the next loop and never used again.
                // Hopefully the Garbage Collector finds it and devourers it <33 ^^
                activeAIEntity = new Professor(game);
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

        // If there are no entities left this round
        this.EndOfRound();
    }
}