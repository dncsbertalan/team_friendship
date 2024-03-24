package Labyrinth;

import Entities.*;
import GameManagers.Game;
import Items.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Labyrinth.Room class. It represents the rooms of the game.
 * It handles the entities and items placed in the room.
 * It handles the neighbour rooms and the accessibility of those.
 */
public class Room {
    /**
     * Reference for the Game object.
     */
    private Game game;
    /**
     * Reference for the Map object.
     */
    private Map map;
    /**
     * All items placed in the room.
     */
    private List<Item> roomsListOfItems;
    /**
     * All students placed in the room.
     */
    private List<Student> roomsListOfStudents;
    /**
     * All professors placed in the room.
     */
    private List<Professor> roomsListOfProfessors;
    /**
     * All rooms that are currently neighbours to this room.
     */
    private List<Room> roomsListOfNeighbours;
    /**
     * Shows whether a room is filled with toxic gas.
     */
    private boolean gassed;
    /**
     * Shows the maximum of entities there can be in the room.
     */
    private int remainingRoundsBeingGassed;
    /**
     * Shows the maximum of entities there can be in the room.
     */
    private int capacity;
    /**
     * Constructor.
     * @param g: The game object the room will have a reference for.
     * @param c: Room's initial capacity.
     */
    public Room(int c, Game g){
        roomsListOfStudents = new ArrayList<>();
        roomsListOfProfessors = new ArrayList<>();
        roomsListOfItems = new ArrayList<>();
        roomsListOfNeighbours = new ArrayList<>();
        gassed = false;
        remainingRoundsBeingGassed = 0;

        capacity = c;
        game = g;
    }
    /**
     * Constructor.
     * The room's initial capacity is a random value between 2 (inclusive) and 6 (exclusive).
     * @param g: The game object the room will have a reference for.
     */
    public Room(Game g){
        roomsListOfStudents = new ArrayList<>();
        roomsListOfProfessors = new ArrayList<>();
        roomsListOfItems = new ArrayList<>();
        roomsListOfNeighbours = new ArrayList<>();
        gassed = false;
        remainingRoundsBeingGassed = 0;

        Random random = new Random();
        int minInclusive = 2;
        int maxExclusive = 6;
        capacity = random.ints(minInclusive, maxExclusive).findFirst().getAsInt();

        game = g;
    }
    /**
     * Sets the room's capacity to the value given as argument.
     * @param c: the new value for the room's capacity.
     */
    public void setCapacity(int c){
        capacity = c;
    }
    /**
     * Returns the maximum of entities that can be placed in the room.
     * @return: Maximum of entities that can be placed in the room.
     */
    public int CheckCapacity(){
        return capacity;
    }
    /**
     * Returns the number of entities currently placed in the room.
     * @return: Number of entities currently placed in the room.
     */
    public int CheckForEntityInRoom(){
        return roomsListOfStudents.size() + roomsListOfProfessors.size();
    }
    /**
     * The room adds all its current neighbours to the other room.
     * From then those rooms are only the other room's neighbours.
     * @param r: The destination room for all the current neighbours.
     */
    public void SendAllNeighbours(Room r){
        for(int i = 0; i < roomsListOfNeighbours.size(); i++){
                r.AddNeighbour(roomsListOfNeighbours.get(i));
                this.RemoveNeighbour(roomsListOfNeighbours.get(i));
        }
    }
    /**
     * The room adds all items placed in it to the other room.
     * From then those items are placed only in the other room.
     * @param r: The destination room for all items.
     */
    public void SendAllItems(Room r){
        r.roomsListOfItems = this.roomsListOfItems;
        this.roomsListOfItems.clear();
    }
    /**
     * The room adds every second of the items placed in it to the other room.
     * From then those items are placed only in the other room.
     * @param r: The destination room for every second items.
     */
    public void SendEveryOtherItem(Room r){
        int i = 0;
        for(Item itemIter : this.roomsListOfItems){
            if(i % 2 == 0){
                r.AddItemToRoom(itemIter);
                this.RemoveItemFromRoom(itemIter);
            }
        i++;
        }
    }
    /**
     * The room adds every second of its neighbours to the other room.
     * From then those rooms are only the other room's neighbours.
     * @param r: The destination room for every second current neighbours.
     */
    public void SendSomeNeighbour(Room r){
        for(int i = 0; i < roomsListOfNeighbours.size(); i++){
            if(i % 2 == 0){
                r.AddNeighbour(roomsListOfNeighbours.get(i));
                this.RemoveNeighbour(roomsListOfNeighbours.get(i));
            }
        }
    }
    /**
     * The room adds the item given as a parameter to its own list of items.
     * @param i: the item being placed in the room.
     */
    public void AddItemToRoom(Item i){
        this.roomsListOfItems.add(i);
    }
    /**
     * The room removes the item given as a parameter from its own list of items.
     * @param i: the item being removed from the room.
     */
    public void RemoveItemFromRoom(Item i){
        this.roomsListOfItems.remove(i);
    }
    /**
     * The room releases toxic gas for n round.
     * Sets gassed attribute to true.
     * @param n: for how many round is the room filled with toxic gas.
     */
    public void ReleaseToxicGas(int n){
        gassed = true;
        remainingRoundsBeingGassed = n;
    }
    /**
     * The room is no longer is filled with toxic gas.
     * Sets gassed attribute to false.
     */
    public void DeactivateToxicGas(){
        gassed = false;
    }
    /**
     * The room is no longer is filled with toxic gas for one less round, if not already 0.
     */
    public void DecreaseRemainingRoundsBeingGassed(){
        if(remainingRoundsBeingGassed < 0){
            remainingRoundsBeingGassed--;
        }
    }

    /**
     * The room adds student to the room's list of entities.
     * @param s: the student being placed in the room.
     */
    public void AddStudentToRoom(Student s){
        System.out.println("\t-> Student (" + s.hashCode() + ") stepped into room (" + this.hashCode() + ")");
        this.roomsListOfStudents.add(s);
        s.SetCurrentRoom(this);

        if (gassed) {
            s.SteppedIntoGassedRoom();
        }
    }
    /**
     * The room adds professor to the room's list of entities.
     * @param p: the professor being placed in the room.
     */
    public void AddProfessorToRoom(Professor p){
        System.out.println("\t-> Professor (" + p.hashCode() + ") stepped into room (" + this.hashCode() + ")");
        this.roomsListOfProfessors.add(p);
        p.SetCurrentRoom(this);
        if (gassed) {
            p.SteppedIntoGassedRoom();
        }
    }

    /**
     * The room removes student from the room's list of entities.
     * @param s: The student being removed from the room.
     */
    public void RemoveStudentFromRoom(Student s){
        this.roomsListOfStudents.remove(s);
    }

    /**
     * The room removes professor from the room's list of entities.
     * @param p: The professor being removed from the room.
     */
    public void RemoveProfessorFromRoom(Professor p){
        this.roomsListOfProfessors.remove(p);
    }

    /**
     * Shows whether a room's capacity is big enough for another entity.
     * @return Whether another entity can fit in a room.
     */
    public boolean CanStepIn(){
        int allEntitiesCount = roomsListOfProfessors.size() + roomsListOfStudents.size();
        if(allEntitiesCount == capacity){
            return false;
        }
        return true;
    }
    /**
     * Shows whether a room is filled with toxic gas currently.
     * @return Whether a room is filled with toxic gas.
     */
    public boolean IsGassed(){
        return gassed;
    }

    /**
     * When a student enters the room, it signals all the professors currently in the room to try and kill the student
     * @param s: The student about to get assassinated.
     */
    public void NotifyProfessors(Student s){
        for(Professor profIter : roomsListOfProfessors){
            profIter.KillStudent(s);
        }
    }

    /**
     * Adds a new neighbour to the room's list of neighbours.
     * @param r: The room being added as a new neighbour.
     */
    public void AddNeighbour(Room r){
        roomsListOfNeighbours.add(r);
    }

    /**
     * Removes a neighbour from the room's list of neighbours.
     * @param r: The room being removed as a neighbour.
     */
    public void RemoveNeighbour(Room r){
        roomsListOfNeighbours.remove(r);
    }

    /**
     * Makes the room a toxic/gas room permanently.
     */
    public void SetToxicity(){
        gassed = true;
    }

    public List<Room> GetNeighbours() {
        return roomsListOfNeighbours;
    }
}
