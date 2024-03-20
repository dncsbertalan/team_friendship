package GameManagers;
import Entities.Professor;
import Entities.*;

import java.util.ArrayList;

/**
 * Initializes the game. Its main task is to handle the actions of the currently active student
 * tracked by the RoundManager, move the professors, and serve as the core of the game, bringing
 * everything together
 */
public class Game{
    /**
     * Indicates whether the game is in its last phase.
     */
    boolean lastPhase;
    /**
     * If lastPhase, this Student is hunted.
     */
    Student hunted;
    /**
     * List of students in the game.
     */
    ArrayList<Student> students;
    /**
     * List of professors in the game.
     */
    ArrayList<Professor> professors;

    /**
     * @return list of students
     */
    public ArrayList<Student> GetStudents() {

    }

    /**
     * @return list of professors
     */
    public ArrayList<Professor> GetProfessor() {

    }

    /*
    * Places student to the desired position.
     */
    public void UpdateStudentMoves() {

    }

    /*
     * Places professor to the desired position.
     */
    public void UpdateProfessorMoves() {

    }

    /*
    *Enables LastPhase.
     */
    public void LastPhase(Student student) {

    }

    /*
    *Disables LastPhase.
     */
    public void LastPhaseOff() {

    }

    /**
    *Ends the game with the given output.
    * @param b win/lose
     */
    public void EndGame(boolean b) {

    }

    /**
     * Checks whether the given entity has an item from the given Itemclass.
     * @param ItemClass class of the item
     * @param entity the carrier
     * @return
     */
    public boolean CheckForItem(String ItemClass, Entity entity) {

    }

    /**
     * Checks whether the winning condition is fulfilled.
     */
    public void CheckForWinningCondition() {

    }
}