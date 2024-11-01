package Entities;

import Constants.GameConstants;
import GameManagers.Commands.Commands;
import GameManagers.Game;
import Labyrinth.Room;

import static Runnable.Main.gameController;
import static Runnable.Main.os;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public boolean StepInto(Room room) {
        if (room.CanStepIn()) {
            this.room.RemoveJanitorFromRoom(this);
            this.room = room;
            room.AddJanitorToRoom(this);
            this.remainingTurns--;
            return true;
        }
        return false;
    }

    /**
     * Notifies the entity that it stepped into a gassed room.
     */
    @Override
    public void SteppedIntoGassedRoom() {
        room.SetRoomAsCleaned();
    }

    /**
     * The function is responsible for moving the janitor and ending the janitors turn.
     */
    @Override
    public void AI() {

        Room stepFromThis = this.GetCurrentRoom();
        Room stepIntoThis = null;
        Random random = new Random();

        //trys to catch a random room from neighbours
        int stopFromEndlessLoop = 0;
        while(stepIntoThis == null && stopFromEndlessLoop < 15){
            int id = (int)(Math.random() * this.GetCurrentRoom().GetNeighbours().size());
            Room tryThis = this.GetCurrentRoom().GetNeighbours().get(id);
            //if a room is available, the entity will step into it
            if(tryThis.CanStepIn()){
                stepIntoThis = tryThis;
            }
        }

        //if no room is available, the entity does nothing
        if(stepIntoThis == null){
            game.GetRoundManager().EndTurn();
            return;
        }

        this.StepInto(stepIntoThis);

        String message = this.GetName() + " went from " + stepFromThis.GetName() + " to " + stepIntoThis.GetName();
        gameController.NewScreenMessage(300, new Color(98, 9, 119), message);

        game.GetRoundManager().EndTurn();
        return;
    }
    /**
     * Evicts every entity that is not paralysed from the room.
     */

    public void EvictEveryone() {
        //no students or professors in the room
        if(this.GetCurrentRoom().CheckForEntityInRoom() <= 0){
            return;
        }

        //gets the room's neighbours, students and professors
        List<Room> neighboursOfRoom = this.GetCurrentRoom().GetNeighbours();
        List<Student> studentsOfRoom = this.GetCurrentRoom().GetStudents();
        List<Professor> professorsOfRoom = this.GetCurrentRoom().GetProfessors();

        List<Room> neighboursOfRoom_ = new ArrayList<>(neighboursOfRoom);

        //roomID is for the currently chosen neighbour list iteration (neighboursOfRoom_)
        //nr is for choosing a room from the original neighbourslist (neighboursOfRoom), and making its neighbours as the list for iteration (neighboursOfRoom_)
        int roomID = 0;
        int nr = 0;
        do{
            ArrayList<Student> studentToRemove = new ArrayList<>();
            int index = 0;
            while (index < studentsOfRoom.size()) {
                Student studentIter = studentsOfRoom.get(index);
                if (!studentIter.IsParalysed()) {
                    //roomID % sizeOfList, for there might be more students than neighbouring rooms
                    Room currentNeighbour = neighboursOfRoom_.get(roomID % neighboursOfRoom_.size());
                    if (currentNeighbour.CanStepIn()) {
                        String asd = "move " + studentIter.GetName() + " " + currentNeighbour.GetName();
                        Commands.Move(asd.split(" "));
                        studentToRemove.add(studentIter);
                    }
                    roomID++;
                }
                index++;
            }
            studentsOfRoom.removeAll(studentToRemove);


        } while(!studentsOfRoom.isEmpty());

        do{
            ArrayList<Professor> professorToRemove = new ArrayList<>();
            int index = 0;
            while (index < professorsOfRoom.size()) {
                Professor professorIter = professorsOfRoom.get(index);
                if (!professorIter.IsParalysed()) {
                    //roomID % sizeOfList, for there might be more professors than neighbouring rooms
                    Room currentNeighbour = neighboursOfRoom_.get(roomID % neighboursOfRoom_.size());
                    if (currentNeighbour.CanStepIn()) {
                        os.println("Moved " + professorIter.GetName() + " from " + professorIter.GetCurrentRoom().GetName() + " to " + currentNeighbour.GetName());
                        professorIter.StepInto(currentNeighbour);
                        professorToRemove.add(professorIter);
                    }
                    roomID++;
                }
                index++;
            }

            professorsOfRoom.removeAll(professorToRemove);


            //if all the professors couldn't fit into the neighbours of current iteration list
            if(!professorsOfRoom.isEmpty()){
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
        } while(!professorsOfRoom.isEmpty());

    }
}
