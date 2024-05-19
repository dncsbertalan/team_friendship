package Labyrinth;

import Constants.GameConstants;
import Entities.Student;
import Entities.Professor;
import GameManagers.*;
import Items.*;

import java.util.*;
import java.util.function.Supplier;

/**
 * Handle actions regarding rooms.
 */
public class Map {
    //################################################################################
    //                                  ATTRIBUTES
    //################################################################################
    /**
     * Instance of the game.
     */
    private Game game;

    /**
     * All the rooms of the labyrinth.
     */
    private List<Room> rooms;

    /**
     * The room the students have to secure the slipstick in.
     */
    private Room winningRoom;

    /**
     * Instance of the teachers lounge,
     * because it is not an ordinary room.
     */
    private Room teachersLounge;
    private Room janitorsRoom;

    /**
     * Instance of the main hall,
     * because it is not an ordinary room.
     */
    private Room mainHall;

    private Random random;

    //################################################################################
    //                                  METHODS
    //################################################################################

    /**
     * Constructor of the map.
     */
    public Map(Game game) {
        rooms = new ArrayList<>();
        winningRoom = new Room(game);
        teachersLounge = new Room(game);
        mainHall = new Room(game);
        janitorsRoom = new Room(game);
        this.game = game;
        random = new Random();
    }

    /**
     * Returns the room give as parameter.
     * @param name the name of the room
     * @return the room if exists, {@code null} otherwise
     */
    public Room GetRoomByName(String name) {
        for (Room room : rooms) {
            if (room.GetName().equals(name)) return room;
        }
        return null;
    }

    /**
     * Generate labyrinth of rooms.
     * @param players
     */
    public void GenerateLabyrinth(int players) {
        // Initialize the number of rooms depending on the number of players
        HashMap<String, Integer> itemQuantities = new HashMap<>();
        initializeItemQuantities(itemQuantities, players);
        int numberOfRooms = initializeTheNumberOfRooms(players);

        // Initialize the Rooms needed
        for (int i = 0; i < numberOfRooms; i++) {
            int roomNum = i + 1;
            rooms.add(i, new Room(random.nextInt(6) + 1, game, "Room_" + roomNum));
        }

        // Randomly connect rooms to each other
        randomlyConnectRooms(numberOfRooms);

        // Run dfs on the map, to see if it is connected
        Pair<List<Pair<Room, Room>>, List<List<Room>>> dfsResult = dfs();
        List<Pair<Room, Room>> spanningTree = dfsResult.getFirst();
        List<List<Room>> components = dfsResult.getSecond();

        // If it's not connected, then randomly connect the components
        if (spanningTree.isEmpty()) {
            for (int i = 0; i < components.size() - 1; i++) {
                int numOfConnections= random.nextInt(3) + 1;
                for (int j = 0; j < numOfConnections; j++) {
                    Room r1 = components.get(i).get(0);
                    Room r2 = components.get(i + 1).get(0);
                    r1.AddNeighbour(r2);
                    r2.AddNeighbour(r1);
                }
            }
        }

        // Recalculate the spanning tree
        dfsResult = dfs();
        spanningTree = dfsResult.getFirst();
        components = dfsResult.getSecond();

        if (components.size() != 1) {
            System.out.println("error at generating map");
            return;
        }

        // Initialize special rooms
        mainHall = new Room(4, game, "MainHall");
        Room connectionPointOfMainHall = spanningTree.get(0).getFirst();
        mainHall.AddNeighbour(connectionPointOfMainHall);
        connectionPointOfMainHall.AddNeighbour(mainHall);
        rooms.add(mainHall);

        HashMap<Room, Integer> distances = getDistancesFrom(mainHall);
        List<Pair<Room, Integer>> sortedDistances = new ArrayList<>();
        for (HashMap.Entry<Room, Integer> entry : distances.entrySet()) {
            sortedDistances.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        sortedDistances.sort((p1, p2) -> Integer.compare(p2.getSecond(), p1.getSecond()));

        teachersLounge = new Room(4, game, "TeachersLounge");
        Room furthestRoom = sortedDistances.get(0).getFirst();
        teachersLounge.AddNeighbour(furthestRoom);
        furthestRoom.AddNeighbour(teachersLounge);
        rooms.add(teachersLounge);

        janitorsRoom = new Room(4, game, "JanitorsRoom");
        Room secondFurthestRoom = sortedDistances.get(1).getFirst();
        janitorsRoom.AddNeighbour(secondFurthestRoom);
        secondFurthestRoom.AddNeighbour(janitorsRoom);
        rooms.add(janitorsRoom);

        // Map item names to supplier functions
        HashMap<String, Supplier<Item>> itemSuppliers = createItemSuppliers();

        // Place items
        initializeKeyItems(sortedDistances, itemQuantities, itemSuppliers);

        // Collect the remaining items in a list
        List<Item> otherItems = new ArrayList<>();
        for (HashMap.Entry<String, Integer> entry : itemQuantities.entrySet()) {
            if (!entry.getKey().equals("SlipStick") && !entry.getKey().equals("TVSZ") && !entry.getKey().equals("FFP2")) {
                for (int i = 0; i < entry.getValue(); i++) {
                    otherItems.add(itemSuppliers.get(entry.getKey()).get());
                }
            }
        }

        // Scatter the remaining items around the map
        Collections.shuffle(otherItems);
        for (Item item : otherItems) {
            Room randomRoom = rooms.get(random.nextInt(rooms.size()));
            if (randomRoom.GetInventory().size() < 3) {
                randomRoom.AddItemToRoom(item);
            }
        }
    }

    private int initializeTheNumberOfRooms(int players) {
        int numberOfRooms = 0;
        switch (players) {
            case 1:
                numberOfRooms = 15;
                break;
            case 2:
                numberOfRooms = 20;
                break;
            case 3:
                numberOfRooms = 30;
                break;
            case 4:
                numberOfRooms = 40;
                break;
        }
        return numberOfRooms;
    }

    private void initializeItemQuantities(HashMap<String, Integer> itemQuantities, int players) {
        itemQuantities.put("SlipStick", 1);
        itemQuantities.put("FFP2", players);
        itemQuantities.put("TVSZ", players);
        switch (players) {
            case 1:
                itemQuantities.put("AirFreshener", 2);
                itemQuantities.put("Beer", 3);
                itemQuantities.put("Cheese", 2);
                itemQuantities.put("Transistor", 2);
                itemQuantities.put("WetCloth", 3);
                itemQuantities.put("Fake", 6);
                break;
            case 2:
                itemQuantities.put("AirFreshener", 3);
                itemQuantities.put("Beer", 5);
                itemQuantities.put("Cheese", 4);
                itemQuantities.put("Transistor", 2);
                itemQuantities.put("WetCloth", 5);
                itemQuantities.put("Fake", 8);
                break;
            case 3:
                itemQuantities.put("AirFreshener", 5);
                itemQuantities.put("Beer", 8);
                itemQuantities.put("Cheese", 6);
                itemQuantities.put("Transistor", 4);
                itemQuantities.put("WetCloth", 7);
                itemQuantities.put("Fake", 11);
                break;
            case 4:
                itemQuantities.put("AirFreshener", 8);
                itemQuantities.put("Beer", 12);
                itemQuantities.put("Cheese", 7);
                itemQuantities.put("Transistor", 4);
                itemQuantities.put("WetCloth", 8);
                itemQuantities.put("Fake", 15);
                break;
        }
    }

    private void randomlyConnectRooms(int numberOfRooms) {
        int remainingRooms = numberOfRooms;
        for (Room room : rooms) {
            int numberOfNeighbours = random.nextInt(6) + 1;

            if (numberOfNeighbours <= remainingRooms) {
                remainingRooms = remainingRooms - numberOfNeighbours;
            }
            else {
                numberOfNeighbours = remainingRooms;
                remainingRooms = 0;
            }

            for (int i = 0; i < numberOfNeighbours; i++) {
                Room newNeighbour = null;
                do {
                    newNeighbour = rooms.get(random.nextInt(numberOfRooms));
                } while (newNeighbour.equals(room) || newNeighbour.GetNeighbours().size() > 5);

                room.AddNeighbour(newNeighbour);
                newNeighbour.AddNeighbour(room);
            }

            if (remainingRooms == 0) break;
        }
    }

    // Returns a spanning tree, and the components the graph has
    private Pair<List<Pair<Room, Room>>, List<List<Room>>> dfs() {
        List<Pair<Room, Room>> spanningTree = new ArrayList<>();
        List<List<Room>> components = new ArrayList<>();

        if (rooms.isEmpty()) {
            components.add(new ArrayList<>());
            return new Pair<>(spanningTree, components);
        }

        Set<Room> visited = new HashSet<>();
        for (Room startRoom : rooms) {
            if (!visited.contains(startRoom)) {
                List<Room> component = new ArrayList<>();
                Stack<Room> stack = new Stack<>();
                stack.push(startRoom);
                visited.add(startRoom);

                while (!stack.isEmpty()) {
                    Room currentRoom = stack.pop();
                    component.add(currentRoom);
                    for (Room neighbour : currentRoom.GetNeighbours()) {
                        if (!visited.contains(neighbour)) {
                            visited.add(neighbour);
                            stack.push(neighbour);
                            spanningTree.add(new Pair<>(currentRoom, neighbour));
                        }
                    }
                }
                components.add(component);
            }
        }

        if (components.size() == 1) {
            return new Pair<>(spanningTree, components);
        } else {
            return new Pair<>(new ArrayList<>(), components);
        }
    }

    // Returns the distances of the map's rooms from a given room
    private HashMap<Room, Integer> getDistancesFrom(Room startRoom) {
        HashMap<Room, Integer> distances = new HashMap<>();
        Queue<Room> queue = new LinkedList<>();
        Set<Room> visited = new HashSet<>();

        queue.add(startRoom);
        distances.put(startRoom, 0);
        visited.add(startRoom);

        while (!queue.isEmpty()) {
            Room currentRoom = queue.poll();
            int currentDistance = distances.get(currentRoom);

            for (Room neighbour : currentRoom.GetNeighbours()) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                    visited.add(neighbour);
                    distances.put(neighbour, currentDistance + 1);
                }
            }
        }

        return distances;
    }

    private HashMap<String, Supplier<Item>> createItemSuppliers() {
        HashMap<String, Supplier<Item>> itemSuppliers = new HashMap<>();
        itemSuppliers.put("AirFreshener", AirFreshener::new);
        itemSuppliers.put("Beer", Beer::new);
        itemSuppliers.put("Cheese", Cheese::new);
        itemSuppliers.put("Transistor", Transistor::new);
        itemSuppliers.put("WetCloth", WetCloth::new);
        itemSuppliers.put("Fake", Fake::new);
        itemSuppliers.put("SlipStick", SlipStick::new);
        itemSuppliers.put("TVSZ", TVSZ::new);
        itemSuppliers.put("FFP2", FFP2Mask::new);
        return itemSuppliers;
    }

    private void initializeKeyItems(List<Pair<Room, Integer>> sortedDistances, HashMap<String, Integer> itemQuantities, HashMap<String, Supplier<Item>> itemSuppliers) {
        // SlipStick (farthest from MainHall)
        Room slipStickRoom = sortedDistances.get(0).getFirst();
        slipStickRoom.AddItemToRoom(itemSuppliers.get("SlipStick").get());

        // Key items (closest to MainHall)
        int placedTVSZ = 0;
        int placedFFP2 = 0;
        for (Pair<Room, Integer> pair : sortedDistances) {
            Room room = pair.getFirst();
            if (placedTVSZ < itemQuantities.get("TVSZ")) {
                room.AddItemToRoom(itemSuppliers.get("TVSZ").get());
                placedTVSZ++;
            }
            if (placedFFP2 < itemQuantities.get("FFP2")) {
                room.AddItemToRoom(itemSuppliers.get("FFP2").get());
                placedFFP2++;
            }
            if (placedTVSZ >= itemQuantities.get("TVSZ") && placedFFP2 >= itemQuantities.get("FFP2")) {
                return;
            }
        }
    }

    /**
     * Merge 2 randomly selected rooms.
     * @param r1 The first randomly selected room.
     * @param r2 The second randomly selected room.
     */
    public Room MergeRooms(Room r1, Room r2) {
        if (r1.CheckForEntityInRoom() != 0) {
            return null;
        }

        if (r2.CheckForEntityInRoom() != 0) {
            return null;
        }

        int r1capacity = r1.CheckCapacity();
        int r2capacity = r2.CheckCapacity();
        Room biggerRoom;

        // Decide which rooms' capacity is bigger
        // If the rooms' capacities are equal, randomly choose one
        if (r1capacity == r2capacity) {
            if (game.IsRandom()) {
                // Generate a random value from 0.0 to 1.0
                double randomValue = Math.random();
                if (randomValue < 0.5) {
                    biggerRoom = r1;
                }
                else {
                    biggerRoom = r2;
                }
            } else {
                // If random is off, always choose the first room
                biggerRoom = r1;
            }
        } else {
            biggerRoom = (r1capacity > r2capacity) ? r1 : r2;
        }

        if (biggerRoom.equals(r1)) {
            r2.SendAllNeighbours(r1);
            r2.SendAllItems(r1);
            rooms.remove(r2);
        } else {
            r1.SendAllNeighbours(r2);
            r1.SendAllItems(r2);
            rooms.remove(r1);
        }

        return biggerRoom;
    }

    /**
     * Divide a randomly selected room into 2 rooms.
     * @param room The randomly selected room to be separated.
     */
    public Room SeparateRooms(Room room) {
        if (room.CheckForEntityInRoom() != 0) {
            return null;
        }

        Room newRoom = new Room(this.game);
        room.SendSomeNeighbourTo(newRoom);
        newRoom.AddNeighbour(room);
        room.AddNeighbour(newRoom);
        room.SendEveryOtherItemTo(newRoom);
        rooms.add(newRoom);
        return newRoom;
    }

    /**
     * Transfer a student to the main hall.
     * @param student
     */
    public void TransferStudentToMainHall(Student student) {
        student.GetCurrentRoom().RemoveStudentFromRoom(student);    // remove student
        mainHall.AddStudentToRoom(student);                         // add student
        student.SetCurrentRoom(mainHall);                           // sets student's current room
    }

    /**
     * Transfer a professor to the teacher's lounge.
     * @param professor
     */
    public void TransferProfessorToTeachersLounge(Professor professor) {
        professor.GetCurrentRoom().RemoveProfessorFromRoom(professor);  // remove prof
        teachersLounge.AddProfessorToRoom(professor);                   // add prof
        professor.SetCurrentRoom(teachersLounge);                       // sets prof's current room
    }

    /**
     * Transfer a student to given room.
     * @param student
     * @param room
     */
    public void TransferStudentToRoom(Student student, Room room) {
        //nem kell ig
    }

    /**
     * Transfer professor to given room.
     * @param professor
     * @param room
     */
    public void TransferProfessorToRoom(Professor professor, Room room) {
        //mar ezse ig
    }

    /**
     * Release toxic gas in given room.
     * @param room
     */
    public void ReleaseToxicGas(Room room) {

    }

    /**
     * Deactivate toxic gas in given room.
     * @param room
     */
    public void DeactivateToxicGas(Room room) {

    }

    /**
     * Chooses the room the students have to secure the slipstick in.
     */
    public void AddWinningRoom(Room room) {
        this.rooms.add(room);
        winningRoom = room;
    }

    /**
     * Adds a room to the map.
     * @param room the room to be added
     */
    public void AddRoom(Room room) {
        rooms.add(room);
    }

    /**
     * Adds a main hall to the map.
     * @param mainHall the main hall to be added.
     */
    public void AddMainHall(Room mainHall) {
        this.mainHall = mainHall;
        rooms.add(mainHall);
    }

    /**
     * Adds a janitors' room to the map.
     * @param janitorsRoom the janitors' room to be added.
     */
    public void AddJanitorsRoom(Room janitorsRoom) {
        this.janitorsRoom = janitorsRoom;
        rooms.add(janitorsRoom);
    }

    /**
     * Adds a teachers' lounge to the map.
     * @param teachersLounge the teachers' lounge to be added.
     */
    public void AddTeachersLounge(Room teachersLounge) {
        this.teachersLounge = teachersLounge;
        rooms.add(teachersLounge);
    }

    /**
     * Check if a room is winning room.
     * @param room
     * @return {@code true} if the room is winning room, {@code false} if it isn't
     */
    public boolean IsWinningRoom(Room room) {
        return room == winningRoom;
    }

    public List<Room> GetRooms() { return rooms; }

    public Room GetMainHall() { return mainHall; }
    public Room GetTeachersLounge() { return teachersLounge; }
    public Room GetJanitorsRoom() { return janitorsRoom; }
}
