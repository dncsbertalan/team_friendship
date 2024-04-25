package GameManagers;

import Entities.*;
import Items.*;
import Labyrinth.*;
import Constants.*;
import Labyrinth.Map;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
    private boolean win = false;
    /**
     * The map where the Game takes place.
     */
    private final Map map;
    /**
     * The round manager associated with the game.
     */
    private final RoundManager roundManager;

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
     * @param isWin win/lose
     */
    public void EndGame(boolean isWin) {
        over = true;
        win = isWin;
    }

    public boolean IsEnded() {
        return over;
    }
    public boolean GetWin() {
        return win;
    }

    /**
     * Saves the game into the given file.
     * @param fileName the file
     */
    public void SaveGame(String fileName) {

        try {
            PrintWriter printWriter = new PrintWriter(new File(fileName));
            printWriter.println("rounds:" + GameConstants.MaxRounds);

            for (Room room : this.map.GetRooms()) {
                // room base
                printWriter.write(room.GetName() + ":" + room.CheckCapacity() + ";");
                // room state
                if (room.IsSticky()){
                    printWriter.write("sticky");
                }
                else if (room.IsGassed()) {
                    printWriter.write("gas");
                }
                else {
                    printWriter.write("empty");
                }
                // entities
                printWriter.write("%");
                int entSize = room.GetEntities().size();
                for (int j = 0; j < entSize; j++) {
                    Entity entity = room.GetEntities().get(j);
                    printWriter.write(entity.GetName() + "(");
                    int invSize = entity.GetInventory().size();
                    for (int i = 0; i < invSize; i++) {
                        printWriter.write(entity.GetInventory().get(i).GetName());
                        if (i + 1 < invSize) {
                            printWriter.write(",");
                        }
                    }
                    printWriter.write(")");
                    if (j + 1 < entSize) {
                        printWriter.write(",");
                    }
                }
                // items
                printWriter.write("$");
                int itemSize = room.GetInventory().size();
                for (int i = 0; i < itemSize; i++) {
                    printWriter.write(room.GetInventory().get(i).GetName());
                    if (i + 1 < itemSize) {
                        printWriter.write(",");
                    }
                }
                // neighbours
                printWriter.write("#");
                int neighSize = room.GetNeighbours().size();
                for (int i = 0; i < neighSize; i++) {
                    printWriter.write(room.GetNeighbours().get(i).GetName());
                    if (i + 1 < neighSize) {
                        printWriter.write(",");
                    }
                }
                printWriter.write("\n");
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the game from the given file.
     * @param fileName the file
     * @throws FileNotFoundException if the file doesn't exists
     */
    public void LoadGame(String fileName) throws FileNotFoundException {

        ArrayList<Room> map_rooms = new ArrayList<>();
        HashMap<String, Room> roomNames = new HashMap<>();
        HashMap<String, ArrayList<String>> neighbourNames = new HashMap<>();
        ArrayList<String> lines = new ArrayList<>();

        // reads the file into the lines array
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();

        // set the MaxRounds
        String rounds = lines.get(0); lines.remove(0);
        GameConstants.MaxRounds = Integer.parseInt(rounds.split(":")[1]);

        // process the data in lines
        for (String line : lines) {
            // CREATE THE ROOM
            String[] strings = line.split(":");
            String _name = strings[0];
            strings = strings[1].split(";");
            int _cap = Integer.parseInt(strings[0]);
            strings = strings[1].split("%");
            String _type = strings[0];

            Room newRoom = new Room(_cap, this, _name);
            if (_type.equals("gas")) {
                newRoom.SetToxicity();
            }
            if (_type.equals("sticky")) {
                newRoom.SetSticky();
            }
            if (_name.contains(GameConstants.RoomName_TeachersLounge)) map.AddTeachersLounge(newRoom);
            else if (_name.contains(GameConstants.RoomName_JanitorsRoom)) map.AddJanitorsRoom(newRoom);
            else if (_name.contains(GameConstants.RoomName_MainHall)) map.AddMainHall(newRoom);
            else if (_name.contains(GameConstants.RoomName_WinningRoom)) map.AddWinningRoom(newRoom);
            else map.AddRoom(newRoom);
            roomNames.put(_name, newRoom);
            // GET THE NEIGHBOURS
            strings = strings[1].split("#");
            String[] _neighbours = strings[1].split(",");
            ArrayList<String> neighbourList = new ArrayList<>(Arrays.asList(_neighbours));
            neighbourNames.put(_name, neighbourList);

            strings = strings[0].split("\\$", 2);
            // GET ROOM ITEMS AND ADD THEM TO THE ROOM
            if (!strings[1].isEmpty()) {
                String[] _itemNames = strings[1].split(",");
                for (String _itemName : _itemNames) {
                    Item newItem = GetItemFromName(_itemName);
                    if (newItem != null) {
                        newItem.SetName(_itemName);
                    }
                    newRoom.AddItemToRoom(newItem);
                }
            }

            // GET THE ENTITIES IN THE ROOM AND ADD THEM
            if (!strings[0].isEmpty()) {
                String[] _playerNamesWithItems = strings[0].split(",");
                for (String _playerString : _playerNamesWithItems) {
                    String[] _entity = _playerString.split("\\(", 2);
                    String _entityName = _entity[0];
                    Entity newEntity = GetEntityFromName(_entityName);
                    _entity = _entity[1].split("\\)");
                    if (_entity.length > 0) {
                        String[] _items = _entity[0].split("\\|");
                        if (_items.length > 5)
                            System.out.println("Caution: Items limit exceeded for " + _entityName + "! Exceeding item will not be loaded! ");
                        for (String _item : _items) {
                            Item newItem = GetItemFromName(_item);
                            if (newItem != null) {
                                newItem.SetName(_item);
                            }
                            newEntity.AddItem(newItem);
                        }
                    }
                    if (newEntity.getClass() == Professor.class) {
                        this.professors.add((Professor) newEntity);
                        newRoom.AddProfessorToRoom((Professor) newEntity);
                    }
                    else if (newEntity.getClass() == Janitor.class) {
                        this.janitors.add((Janitor) newEntity);
                        newRoom.AddJanitorToRoom((Janitor) newEntity);
                    }
                    else {
                        this.students.add((Student) newEntity);
                        newRoom.AddStudentToRoom((Student) newEntity);
                    }
                }
            }
        }

        // ADD THE NEIGHBOURS TO THE ROOMS
        for (Room room : map.GetRooms()) {
            for (String key : neighbourNames.get(room.GetName())) {
                room.AddNeighbour(roomNames.get(key));
            }
        }

        // FINALLY
        this.roundManager.Init();
    }

    private Item GetItemFromName(String itemName) {

        if (itemName.contains(GameConstants.AirFreshener)) return new AirFreshener();
        if (itemName.contains(GameConstants.Beer)) return new Beer();
        if (itemName.contains(GameConstants.Cheese)) return new Cheese();
        if (itemName.contains(GameConstants.FakeItem)) return new Fake();
        if (itemName.contains(GameConstants.FFP2Mask)) return new FFP2Mask();
        if (itemName.contains(GameConstants.SlipSlick)) return new SlipStick();
        if (itemName.contains(GameConstants.Transistor)) return new Transistor();
        if (itemName.contains(GameConstants.TVSZ)) return new TVSZ();
        if (itemName.contains(GameConstants.WetCloth)) return new WetCloth();

        return null;
    }

    private Entity GetEntityFromName(String name) {

        if (name.contains(GameConstants.JanitorName)) {
            Janitor janitor = new Janitor(this);
            janitor.SetName(name);
            return janitor;
        }
        if (name.contains(GameConstants.ProfName)) {
            Professor professor = new Professor(this);
            professor.SetName(name);
            return professor;
        }
        return new Student(this, name);
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
//endregion
}
