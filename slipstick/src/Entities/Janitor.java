package Entities;

import Constants.GameConstants;
import GameManagers.Game;
import Labyrinth.Room;

import java.util.List;

public class Janitor extends Entity implements IAI {

    private static int ID = 0;

    public Janitor(Game g) {
        super(g);
        this.Name = GameConstants.JanitorName + ++ID;
    }

    /**
     * Tries to move to the specified room
     *
     * @param room the room it's trying to move into
     */
    @Override
    public void StepInto(Room room) {
        if (room.CanStepIn()) {
            this.room.RemoveJanitorFromRoom(this);
            this.room = room;
            room.AddJanitorToRoom(this);
        }
    }

    /**
     * Notifies the entity that it stepped into a gassed room.
     */
    @Override
    public void SteppedIntoGassedRoom() {
        room.SetRoomAsCleaned();
    }

    @Override
    public void AI() {

    }


    public void EvictEveryone() {
        //no students or professors in the room
        if(this.GetCurrentRoom().CheckForEntityInRoom() <= 0){
            return;
        }

        //gets the room's neighbours, students and professors
        List<Room> neighboursOfRoom = this.GetCurrentRoom().GetNeighbours();
        List<Student> studentsOfRoom = this.GetCurrentRoom().GetStudents();
        List<Professor> professorsOfRoom = this.GetCurrentRoom().GetProfessors();

        List<Room> neighboursOfRoom_ = neighboursOfRoom;

        //roomID is for the currently chosen neighbour list iteration (neighboursOfRoom_)
        //nr is for choosing a room from the original neighbourslist (neighboursOfRoom), and making its neighbours as the list for iteration (neighboursOfRoom_)
        int roomID = 0;
        int nr = 0;
        do{
            for(Student studentIter : studentsOfRoom){

                //roomID % sizeOfList, for there might be more students than neighbouring rooms
                Room currentNeighbour = neighboursOfRoom_.get(roomID % neighboursOfRoom_.size());
                if(currentNeighbour.CanStepIn()){
                    studentIter.StepInto(currentNeighbour);
                }
                roomID++;
            }

            //if all the students couldn't fit into the neighbours of current iteration list
            if(studentsOfRoom.isEmpty() == false){
                //if the original still has rooms that we have not tried the neighbours of
                if(nr < neighboursOfRoom.size()){
                    neighboursOfRoom_ = neighboursOfRoom.get(nr).GetNeighbours();
                    nr++;
                }
                //the original will change to the current iteration list and the next cycle will try and go through those room and their neighbours
                else {
                    nr = 0;
                    neighboursOfRoom = neighboursOfRoom_;
                }
            }
        } while(studentsOfRoom.isEmpty());

        do{
            for(Professor professorIter : professorsOfRoom){

                //roomID % sizeOfList, for there might be more professors than neighbouring rooms
                Room currentNeighbour = neighboursOfRoom_.get(roomID % neighboursOfRoom_.size());
                if(currentNeighbour.CanStepIn()){
                    professorIter.StepInto(currentNeighbour);
                }
                roomID++;
            }

            //if all the professors couldn't fit into the neighbours of current iteration list
            if(professorsOfRoom.isEmpty() == false){
                //if the original still has rooms that we have not tried the neighbours of
                if(nr < neighboursOfRoom.size()){
                    neighboursOfRoom_ = neighboursOfRoom.get(nr).GetNeighbours();
                    nr++;
                }
                //the original will change to the current iteration list and the next cycle will try and go through those room and their neighbours
                else {
                    nr = 0;
                    neighboursOfRoom = neighboursOfRoom_;
                }
            }
        } while(professorsOfRoom.isEmpty());

    }
}
