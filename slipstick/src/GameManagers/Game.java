package GameManagers;

import Entities.*;
import Labyrinth.*;
import java.util.List;

/**
 * Initializes the game. Its main task is to handle the actions of the currently active student
 * tracked by the RoundManager, move the professors, and serve as the core of the game, bringing
 * everything together
 */
public class Game {

    /**
     * The map where the Game takes place.
     */
    Map map;
    /**
     * The round manager associated with the game.
     */
    RoundManager roundManager;

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
    List<Student> students;

    /**
     * List of professors in the game.
     */
    List<Professor> professors;

    /**
     * Getter of the students list
     * @return list of students
     */
    public List<Student> GetStudents() {
        return students;
    }

    /**
     * Setter of the map attribute.
     * @param map: the new map
     */
    public void setMap(Map map) {
        this.map = map;
    }
    /**
     * Setter of the map attribute.
     * @param rm: the new roundManager
     */
    public void SetRoundManager(RoundManager rm) {
        this.roundManager = rm;
    }



    /**
     * Getter of the professors list.
     * @return list of professors
     */
    public List<Professor> GetProfessor() {
        return professors;
    }

    /**
     * Appends the students list with a student.
     * @param student: the new student
     */
    public void AddStudent(Student student){
        students.add(student);
    }

    /**
     * Appends the professors list with a professors.
     * @param professor: the new professor
     */
    public void AddProfessor(Professor professor){
        professors.add(professor);
    }
    /**
     * Places student to the desired position.
     */
    public void UpdateStudentMoves() {
        // dont think we need this one anymore
    }
    /**
     * Places professor to the desired position.
     */
    public void UpdateProfessorMoves() {
        // dont think we need this one anymore
    }

    /**
     * Enables/disables lastPhase
     * @param state: desired state of the attribute
     * @param student: if lastPhase, the hunted student, else null
     */
    public void LastPhase(Boolean state, Student student) {
        if(state){
            lastPhase = true;
            hunted = student;
        }else{
            lastPhase = false;
        }
    }


    /**
     * Ends the game with the given output.
     * @param b win/lose
     */
    public void EndGame(boolean b) {
        if(b) System.out.println("Victory");
        else System.out.println("Lose");
    }

    /**
     * Checks whether the winning condition is fulfilled.
     */
    public void CheckForWinningCondition() {
        // dont think we need this one anymore
    }
}
