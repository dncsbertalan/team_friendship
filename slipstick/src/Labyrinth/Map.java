package Labyrinth;

import Entities.Student;
import Entities.Professor;
import GameManagers.*;

import java.util.ArrayList;
import java.util.List;

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
        winningRoom = null;
        teachersLounge = null;
        mainHall = null;
        this.game = game;
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
    public void MergeRooms(Room r1, Room r2) {
        System.out.println("Room merge:");

        if (r1.CheckForEntityInRoom() != 0) {
            System.out.println(">Room merge not successful");
            System.out.println(">The first given room was not empty");
            return;
        }

        if (r2.CheckForEntityInRoom() != 0) {
            System.out.println(">Room merge not successful");
            System.out.println(">The second given room was not empty");
            return;
        }

        int r1capacity = r1.CheckCapacity();
        int r2capacity = r2.CheckCapacity();
        Room biggerRoom;

        // Decide which rooms' capacity is bigger
        // If the rooms' capacities are equal, randomly choose one
        if (r1capacity == r2capacity) {
            // Generate a random value from 0.0 to 1.0
            double randomValue = Math.random();
            if (randomValue < 0.5) {
                biggerRoom = r1;
            }
            else {
                biggerRoom = r2;
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
    }

    /**
     * Divide a randomly selected room into 2 rooms.
     * @param room The randomly selected room to be separated.
     */
    public void SeparateRooms(Room room) {
        System.out.println("Room division:");

        if (room.CheckForEntityInRoom() != 0) {
            System.out.println(">Room division not successful");
            System.out.println(">The given room is not empty");
            //TODO: In order to print all the entities in the given room
            //      the room needs to have a function that lists or at least
            //      returns the entities in that room
            return;
        }

        Room newRoom = new Room(this.game);
        room.SendSomeNeighbour(newRoom);
        room.SendEveryOtherItem(newRoom);
        rooms.add(newRoom);
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

    }

    /**
     * Transfer professor to given room.
     * @param professor
     * @param room
     */
    public void TransferProfessorToRoom(Professor professor, Room room) {

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
    public void AddWinningRoom() {

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
        return false;
    }
}
