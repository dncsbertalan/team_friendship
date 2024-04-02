package GameManagers;

import Entities.*;
import Labyrinth.*;
import Constants.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Initializes the game. Its main task is to handle the actions of the currently active student
 * tracked by the RoundManager, move the professors, and serve as the core of the game, bringing
 * everything together
 */
public class Game {

//region Attributes
    private boolean over;
    /**
     * The map where the Game takes place.
     */
    private Map map;
    /**
     * The round manager associated with the game.
     */
    private RoundManager roundManager;

    /**
     * Indicates whether the game is in its last phase.
     */
    private boolean lastPhase;

    /**
     * If lastPhase, this Student is hunted.
     */
    private Student hunted;

    /**
     * List of students in the game.
     */
    private final List<Student> students;

    /**
     * List of professors in the game.
     */
    private final List<Professor> professors;

    private boolean isRunning;
//endregion

    public Game(){

        students = new ArrayList<>();
        professors = new ArrayList<>();
        this.roundManager = new RoundManager(this);
        this.map = new Map(this);
        isRunning =true;
    }

    /**
     * Setter of the map attribute.
     * @param rm: the new roundManager
     */
    public void SetRoundManager(RoundManager rm) {
        this.roundManager = rm;
    }

    /**
     * Appends the students list with a student.
     * @param student: the new student
     */
    public void AddStudent(Student student){
        students.add(student);
    }

    /**
     * Getter of the students list
     * @return list of students
     */
    public List<Student> GetStudents() {
        return students;
    }

    /**
     * Appends the professors list with a professors.
     * @param professor: the new professor
     */
    public void AddProfessor(Professor professor){
        professors.add(professor);
    }

    /**
     * Getter of the professors list.
     * @return list of professors
     */
    public List<Professor> GetProfessors() {
        return professors;
    }

    /**
     * Setter of the map attribute.
     * @param map: the new map
     */
    public void SetMap(Map map) {
        this.map = map;
    }
    /**
     * Getter of the map attribute.
     * @return the map
     */
    public Map GetMap(){
        return map;
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

    public boolean IsLastPhase() {
        return lastPhase;
    }


    /**
     * Ends the game with the given output.
     * @param b win/lose
     */
    public void EndGame(boolean b) {
        over = b;
    }

    public boolean IsEnded() {
        return over;
    }

//region Game loop and logic

    public void MainGameLoop() {

        double drawInterval = 1_000_000_000.0 / GameConstants.DesiredFPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (isRunning) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                GameLogic();

                delta--;
            }

            if (timer >= 1_000_000_000) {
                timer = 0;
            }

        }
    }

    private void GameLogic() {

        Student activeStudent = roundManager.activeStudent;
        IAI activeAIEntity = roundManager.activeAIEntity;

        System.out.println("pusy");
        // Handle student and professor
        this.HandleStudent(activeStudent);
        this.HandleAIEntities(activeAIEntity);
    }

    private void HandleStudent(Student student) {
        if (student == null) return;

        // if the student dies
        if (student.IsDead()) {
            this.roundManager.EndOfRound();
        }
    }

    private void HandleAIEntities(IAI entities) {
        if (entities == null) return;

        entities.AI();
    }

//endregion
}
