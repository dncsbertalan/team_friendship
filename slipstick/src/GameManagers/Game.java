package GameManagers;

import Entities.*;
import Labyrinth.*;
import Constants.*;
import Labyrinth.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.*;

import static Constants.GameConstants.randomSeed;

/**
 * Initializes the game. Its main task is to handle the actions of the currently active student
 * tracked by the RoundManager, move the professors, and serve as the core of the game, bringing
 * everything together
 */
public class Game {

//region Attributes ====================================================================================================
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
    private final List<Janitor> janitors;

    private boolean isRunning;
    public static Random random;
//endregion

    /**
     * Before the game starts {@link Game#InitPlayers(ArrayList)} and {@link Game#InitRandom(boolean)}
     * must be called properly or during the game it will throw {@link NullPointerException}.
     */
    public Game(){
        this.students = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.janitors = new ArrayList<>();
        this.roundManager = new RoundManager(this);
        this.map = new Map(this);
        this.isRunning = true;
    }

    /**
     * Initialize the students based on the players' names.
     * @param names players' names
     */
    public void InitPlayers(ArrayList<String> names) {
        for (String name : names) {
            students.add(new Student(this, name));
        }
        this.roundManager.Init();
    }

    /**
     * Initialize {@link Game#random} in the {@link Game} class.
     * <p>
     * Must be only used once before the game starts!
     * @param rand the value of the {@link Game#random}
     */
    public void InitRandom(boolean rand) {
        random = new Random();
        if(rand) {
            random.setSeed(System.currentTimeMillis());
        }else {
            random.setSeed(randomSeed);
        }
    }

//region Get/Setters ===================================================================================================
    /**
     * Getter of the roundManager attribute.
     * @return the roundManager
     */
    public RoundManager GetRoundManager(){
        return this.roundManager;
    }

    /**
     * Getter of the map attribute.
     * @return the map
     */
    public Map GetMap(){
        return map;
    }

    /**
     * Getter of the students list
     * @return list of students
     */
    public List<Student> GetStudents() {
        return students;
    }

    /**
     * Getter of the professors list.
     * @return list of professors
     */
    public List<Professor> GetProfessors() {
        return professors;
    }

    public List<Janitor> GetJanitors() {
        return janitors;
    }
//endregion

//region Methods
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
            hunted = null;
        }
    }

    public boolean IsLastPhase() {
        return lastPhase;
    }

    /**
     * Ends the game with the given output.
     * @param b win/lose
     */
    public void EndGame(boolean isWin) {
        over = true;
        // TODO: WHAT HAPPENS WHEN WE WIN
    }

    public boolean IsEnded() {
        return over;
    }

    /**
     * Saves the game into the given file.
     * @param fileName the file
     */
    public void SaveGame(String fileName) {

        // TODO:
    }

    /**
     * Loads the game from the given file.
     * @param fileName the file
     */
    public void LoadGame(String fileName) {

        HashMap<Integer, ArrayList<Integer>> neighbours = new HashMap<>();
        ArrayList<String> lines = new ArrayList<>();

        // reads the file into the lines array
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {

        }

        // process the data in lines
        for (String line : lines) {
            System.out.println(line);
        }
        for (String line : lines) {
            String[] strings = line.split(":");
            for (String s : strings) {
                System.out.println(s);
            }
        }
    }
//endregion

//region Game loop and logic ===========================================================================================

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

        Student activeStudent = roundManager.GetActiveStudent();
        IAI activeAIEntity = roundManager.GetActiveAIEntity();

        System.out.println("pusy");
        // Handle student and professor
        this.HandleStudent(activeStudent);
        this.HandleAIEntities(activeAIEntity);
    }

    private void HandleStudent(Student student) {
        if (student == null) return;

    }

    private void HandleAIEntities(IAI entities) {
        if (entities == null) return;

        entities.AI();
    }

    public void __Berci__LISTALLTEST() {
        for (int i = 0; i < 10; i++ ) {
            this.janitors.add(new Janitor(this));
        }
        for (int i = 0; i < 10; i++ ) {
            this.professors.add(new Professor(this));
        }
        this.students.add(new Student(this, "Player1"));
        this.students.add(new Student(this, "Benedek"));
        this.students.add(new Student(this, "Berci"));
        this.students.add(new Student(this, "Boti"));
        this.students.add(new Student(this, "Kincső"));
        this.students.add(new Student(this, "Norbi"));
        this.roundManager.Init();
    }

//endregion
}
