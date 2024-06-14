package Entities;

import Constants.GameConstants;
import GameManagers.Commands.Commands;
import GameManagers.Game;
import Labyrinth.Pair;
import Labyrinth.Room;

import static Runnable.Main.gameController;
import static Runnable.Main.os;

import java.awt.*;
import java.util.*;
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

        //String message = this.GetName() + " went from " + stepFromThis.GetName() + " to " + stepIntoThis.GetName();
        //gameController.NewScreenMessage(300, new Color(98, 9, 119), message);

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
        int tries = 0;
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
            tries++;

        } while(!studentsOfRoom.isEmpty() && tries < 15);

        // if for some reason the janitor didn't find a room to evict students, puts them in the closest available room
        if (!studentsOfRoom.isEmpty()) {
            ArrayList<Student> studentsToRemove = new ArrayList<>();
            for (Student s : studentsOfRoom) {
                HashMap<Room, Integer> distancesFromJanitor = game.GetMap().getDistancesFrom(this.GetCurrentRoom());
                List<Pair<Room, Integer>> sortedDistancesFromJanitor = new ArrayList<>();
                for (HashMap.Entry<Room, Integer> entry : distancesFromJanitor.entrySet()) {
                    sortedDistancesFromJanitor.add(new Pair<>(entry.getKey(), entry.getValue()));
                }

                // ascending order by distance
                sortedDistancesFromJanitor.sort(Comparator.comparingInt(Pair::getSecond));

                for (Pair<Room, Integer> pair : sortedDistancesFromJanitor) {
                    Room r = pair.getFirst();
                    if (r.CanStepIn()) {
                        String command = "move " + s.GetName() + " " + r.GetName();
                        Commands.Move(command.split(" "));
                        studentsToRemove.add(s);
                        break;
                    }
                }
            }
            studentsOfRoom.removeAll(studentsToRemove);
        }

        tries = 0;
        do{
            ArrayList<Professor> professorToRemove = new ArrayList<>();
            int index = 0;
            while (index < professorsOfRoom.size()) {
                Professor professorIter = professorsOfRoom.get(index);
                if (!professorIter.IsParalysed()) {
                    // ensure that the professor in question cannot be put in the same room with a student
                    // if that would happen and there are no possible rooms to move the professor to, send him back to teachers lounge
                    ArrayList<Room> potentialRoomsForProf = new ArrayList<>();
                    for (Room currentNeighbour : neighboursOfRoom_) {
                        if (currentNeighbour.CanStepIn() && currentNeighbour.GetStudents().isEmpty())
                            potentialRoomsForProf.add(currentNeighbour);
                    }

                    if (potentialRoomsForProf.isEmpty()) {
                        professorIter.StepInto(game.GetMap().GetTeachersLounge());
                        professorToRemove.add(professorIter);
                        break;
                    }

                    //roomID % sizeOfList, for there might be more professors than neighbouring rooms
                    Room targetRoomForProf = potentialRoomsForProf.get(roomID % potentialRoomsForProf.size());
                    os.println("Moved " + professorIter.GetName() + " from " + professorIter.GetCurrentRoom().GetName() + " to " + targetRoomForProf.GetName());
                    professorIter.StepInto(targetRoomForProf);
                    professorToRemove.add(professorIter);
                    roomID++;
                }
                index++;
            }

            professorsOfRoom.removeAll(professorToRemove);


            /*if all the professors couldn't fit into the neighbours of current iteration list
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
            }*/
            tries++;
        } while(!professorsOfRoom.isEmpty() && tries < 15);

    }
}
