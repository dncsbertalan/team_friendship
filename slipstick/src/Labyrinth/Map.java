package Labyrinth;

import Constants.GameConstants;
import Entities.Student;
import Entities.Professor;
import GameManagers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
        room.SendSomeNeighbour(newRoom);
        room.SendEveryOtherItem(newRoom);
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
