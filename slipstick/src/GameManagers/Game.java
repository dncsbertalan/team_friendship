package GameManagers;
import Entities.Professor;
import Entities.*;

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
    List<Student> students;
    /**
     * @return List of professors in the game.
     */
    List<Professor> professors;

    /**
     * Returns the students list.
     */
    public List<Student> GetStudents();

    /**
     * @return professors
     */
    public List<Professor> GetProfessor();
    /*

     */
    public void UpdateStudentMoves();
    public void UpdateProfessorMoves();
    public void LastPhase(Student student);
    public void LastPhaseOff();
    public void EndGame(boolean b);

    /**
     *
     * @param ItemClass
     * @param entity
     * @return
     */
    public boolean CheckForItem(String ItemClass, Entity entity);
    public void CheckForWinningCondition();
}