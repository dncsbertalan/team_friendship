package Labyrinth;

import Entities.Student;
import Entities.Professor;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private Game game;
    private List<Room> rooms;
    private Room winningRoom;
    private TeachersLounge teachersLounge;
    private MainHall mainHall;

    public Map() {
        rooms = new ArrayList<>();
        winningRoom = null;
        teachersLounge = null;
        mainHall = null;
    }

    /**
     * Generate labyrinth of rooms.
     * @param players
     */
    public void GenerateLabyrinth(int players) {

    }

    /**
     * Merge 2 randomly selected rooms.
     * @param r1
     * @param r2
     */
    public void MergeRooms(Room r1, Room r2) {

    }

    /**
     * Divide a randomly selected room into 2 rooms.
     * @param room
     */
    public void SeparateRooms(Room room) {

    }

    /**
     * Transfer a student to the main hall.
     * @param student
     */
    public void TransferStudentToMainHall(Student student) {

    }

    /**
     * Transfer a professor to the teacher's lounge.
     * @param professor
     */
    public void TransferProfessorToTeachersLounge(Professor professor) {

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
     * Check if a room is winning room.
     * @param room
     * @return {@code true} if the room is winning room, {@code false} if it isn't
     */
    public boolean isWinningRoom(Room room) {

    }
}
