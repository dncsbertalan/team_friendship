package GameManagers;

import Constants.GameConstants;
import Entities.*;
import GameManagers.Commands.Commands;
import Items.Item;
import Items.WetCloth;
import Labyrinth.Room;

import static Runnable.Main.gameController;

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
        gameController.NewScreenMessage(300, "Round " + rounds + " ended");

        rounds++;
        if (rounds == GameConstants.MaxRounds) {
            game.EndGame(false, OUTCOME.PlayersRanOutOfRounds);
        }

        if (rounds % 7 == 0) {
            Commands.Merge("merge".split(" "));
            Commands.Separate("separate".split(" "));

            gameController.NewScreenMessage(300, "Mystical force changed the labyrinth");
        }
    }

    /**
     * Ends current round.
     */
    public void EndOfRound() {

        // Reset the entity lists
        for (Student student : game.GetStudents()) {
            if (!student.IsDead()) {
                this.studentsLeftThisRound.add(student);
            }
        }
        this.aiEntities.addAll(this.game.GetProfessors());
        this.aiEntities.addAll(this.game.GetJanitors());

        // Reset student steps
        // Decrease student missed rounds
        for (Student student : studentsLeftThisRound) {
            student.ResetMoveCount();
            student.UpdateMissedRounds();

            for (Item item : student.GetInventory()) {
                if (item instanceof WetCloth wetCloth) {
                    wetCloth.DeacreaseRounds(student, null);
                }
            }
        }

        // Reset janitor and prof steps
        // Decrease entities missed rounds
        for (IAI entity : aiEntities) {
            ((Entity) entity).ResetMoveCount();
            ((Entity) entity).UpdateMissedRounds();
        }

        // Check if everyone is dead
        if (studentsLeftThisRound.isEmpty()) {
            game.EndGame(false, OUTCOME.AllPlayersAreDead);
            return;
        }

        // Reset active student
        activeStudent = studentsLeftThisRound.get(0);
        activeAIEntity = null;

        // Decrease cheese gas
        for (Room room : game.GetMap().GetRooms()) {
            room.DecreaseRemainingRoundsBeingGassed();

            for (Item item : room.GetInventory()) {
                if (item instanceof WetCloth wetCloth) {
                    wetCloth.DeacreaseRounds(null, room);
                }
            }
        }
        // TODO: kör vége logikák számlálók...

        // Start a new round
        this.NextRound();
    }

    /**
     * Ends the currently active entity's turn and activates the next.
     */
    public void EndTurn(){

        if (activeStudent != null) {
            activeStudent.ClearTemporaryUnpickableItems();
            studentsLeftThisRound.remove(activeStudent);

            // Make the next student active
            if (!studentsLeftThisRound.isEmpty()) {
                activeStudent = studentsLeftThisRound.get(0);
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