package Entities;

import Constants.Enums;
import Constants.GameConstants;
import GameManagers.Game;
import Items.Item;
import Items.SlipStick;
import Labyrinth.Map;
import Labyrinth.Room;
import Items.FFP2Mask;

import java.awt.*;
import java.util.*;
import java.util.List;

import static Runnable.Main.gameController;

public class Professor extends Entity implements IAI {

    private static int ID = 0;

    public Professor(Game g) {
        super(g);
        this.Name = GameConstants.ProfName + ++ID;
    }

    @Override
    public boolean StepInto(Room room) {
        if (room.CanStepIn()) {
            this.room.RemoveProfessorFromRoom(this);
            this.room = room;
            room.AddProfessorToRoom(this);
            this.remainingTurns--;
            return true;
        }
        return false;
    }

    @Override
    public void SteppedIntoGassedRoom() {

        Item protectionItem = this.GetProtectionItem(Enums.ThreatType.gas);

        if (protectionItem == null) {   // no protection
            this.MissRounds(GameConstants.RoundsMissed_GasRoom);
            this.DropAllItems();
            Map map = this.game.GetMap();
            this.SetParalysed(true);
        }
        else {  // has protection
            if (protectionItem.GetProtectionType() == Enums.ProtectionType.ffp2Mask) {
                FFP2Mask ffp2Mask = (FFP2Mask) protectionItem;
                ffp2Mask.DecreaseDurability();
                this.IncreaseMoveCount(GameConstants.FFP2Mask_MoveCountIncrease);
                if (ffp2Mask.GetRemainingUsages() == 0) this.inventory.remove(ffp2Mask);
            }
        }
    }

    /**
     * Kills all students in the current room.
     */
    public void KillEveryoneInTheRoom() {
        if (!IsParalysed()) {
            List<Student> studentsAboutToBeAssassinated = new ArrayList<>(this.room.GetStudents());
            for(Student sIter : studentsAboutToBeAssassinated){
                this.KillStudent(sIter);
            }
        }
    }

    /**
     * Kills specified student.
     * @param student specified student
     */
    public void KillStudent(Student student) {
        student.Kill(this);
    }

    /**
     * The function is responsible for moving the professor, making the professor pick up items and
     * ending the professors turn.
     */
    @Override
    public void AI() {

        Room stepFromThis = this.GetCurrentRoom();
        Room stepIntoThis = null;
        Random random = new Random();

        if(game.IsLastPhase()){
            stepIntoThis = ClosestRoomToHuntedStudent();
        } else {
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
        }

        //if no room is available, the entity does nothing
        if(stepIntoThis == null){
            game.GetRoundManager().EndTurn();
            return;
        }

        //if yes, the entity steps into the room
        this.StepInto(stepIntoThis);

        String message1 = this.GetName() + " went from " + stepFromThis.GetName() + " to " + stepIntoThis.GetName();
        gameController.NewScreenMessage(300, new Color(98, 9, 119), message1);

        //the entity randomly picks up an item from its current room
        if(random.nextBoolean()){
          Item pickThisUp = null;

          //if the room has no items, nothing is there to pick up
          if(this.GetCurrentRoom().GetInventory().isEmpty() == false){
              for(Item pickUpIter : this.GetCurrentRoom().GetInventory()){
                  //the entity cannot pick up the slipstick
                  if(pickUpIter.getClass() != SlipStick.class){
                      pickThisUp = pickUpIter;
                      break;
                  }
              }
          }

          if(pickThisUp != null){
              this.PickUpItem(pickThisUp);
              String message2 = "   " + this.GetName() + " picked up " + pickThisUp.GetName();
              gameController.NewScreenMessage(300, new Color(98, 9, 119), message2);
          }
        }
        game.GetRoundManager().EndTurn();
        return;
    }

    public Room ClosestRoomToHuntedStudent(){
        Room result = null;

        List<Room> shortestPathToStudent = game.GetMap().findShortestPath(room, game.GetHuntedStudent().GetCurrentRoom());
        if (shortestPathToStudent == null || shortestPathToStudent.isEmpty()) {
            return result;
        }

        for (Room n : room.GetNeighbours()) {
            if (shortestPathToStudent.contains(n)) {
                result = n;
                break;
            }
        }

        return result;
        //////////////////
        //**BFS KEZDETE**//
        ///////////////////
        /*List<Room> resultBFS = new ArrayList<>();
        //összes olyan szoba, amin keresztül elindulhatok a source szobából
        List<Room> roomsOfSource = this.GetCurrentRoom().GetNeighbours();
        //összes olyan szoba, amin keresztül elérhetem a destination szobát
        List<Room> roomsOfDestination = game.GetHuntedStudent().GetCurrentRoom().GetNeighbours();

        java.util.Map<Room, Boolean> visitedThisStop = new HashMap<>();
        java.util.Map<Room, Room> cameFromThisStop = new HashMap<>();

        //mivel BFS-t egy csúcsból lehet indítani egy gráfban, de a teljes roomsOfSource listából lehetne indulni, ezért mindegyiket "összekötjük" egy kiinduló ponttal: superSourceRoom
        Room superSourceRoom = new Room(game);

        //a fifoForRooms alapján fog menni az algoritmus
        //ha egy room-ot újonnan meglátogat, bekerül a fifo-ba és elmentődik, hogy melyik stop-ból éri el
        //az éppen vizsgálandó room-ot mindig a fifo legtetejéről veszi ki
        Queue<Room> fifoForRooms = new LinkedList<>();
        initializeBFSMaps(game.GetMap().GetRoomAndTheirNeighbourList(), visitedThisStop, cameFromThisStop);

        //a source-hoz tartozó room-okra beállítom, hogy a superSourceRoom-ból értük el, meglátogatta az algoritmus és beteszi a fifo-ba
        for(Room roomIter : roomsOfSource){
                cameFromThisStop.put(roomIter, superSourceRoom);
                visitedThisStop.put(roomIter, true);
                fifoForRooms.add(roomIter);
        }
        //ezzel vizsgálom majd, hogy sikeresen elérte-e az algoritmus a destination egy room-ját
        Boolean gotDestination = false;
        //ebbe fogom elmenteni, hogy pontosan melyik room-ból érte el a destination egy room-ját
        Room gotDestinationFromThis = new Room(game);
        //a ciklus addig megy, amíg minden room-on végig nem érünk, vagyis amíg a fifo ki nem ürül
        while(!fifoForRooms.isEmpty()){
            //a fifo-ból kiveszem a legfelső elemet, és elmentem az értékét
            Room currentRoom = fifoForRooms.poll();
            //ha az éppen vizsgált room a destination-höz tartozik, akkor
            //  1. elmentem, hogy ebből értük el a destination-t
            //  2. beállítom igaz értékre, hogy sikerült elérni a destination-t
            //  3. kilépek a ciklusból
            if(roomsOfDestination.contains(currentRoom)){
                gotDestinationFromThis = currentRoom;
                gotDestination = true;
                break;
            }
            //ha még nem értem el a destination-t, akkor megnézem az éppen vizsgált stop szomszédjait
            //ha még nem járta be az algoritmus a szomszédot, akkor most bejárja, elmenti, hogy az éppen vizsgált stop-ból érte el, és beteszi fifo-ba
            if (game.GetMap().GetRoomAndTheirNeighbourList().get(currentRoom) == null)
                continue;

            for(Room roomIter : game.GetMap().GetRoomAndTheirNeighbourList().get(currentRoom)){
                if(Boolean.FALSE.equals(visitedThisStop.get(roomIter))){
                    fifoForRooms.add(roomIter);
                    visitedThisStop.put(roomIter, true);
                    cameFromThisStop.put(roomIter, currentRoom);
                }
            }
        }
        //hogyha az algoritmus bejárása után sem sikerült elérni a destination-t, akkor az elérhetetlen, null-t adok vissza
        if(Boolean.FALSE.equals(gotDestination)){
            return null;
        }
        //safety-net változó, hogy véletlenül se birizgáljam meg rosszul azt, hogy honnan értem el a destination-t
        Room gotDestinationFromThis2 = gotDestinationFromThis;

        //bejárom visszafelé destination-től a source-ig az algoritmus által talált utat, elmentem a stop-okat a reverse listába
        List<Room> reverse = new ArrayList<>();
        while(cameFromThisStop.get(gotDestinationFromThis2) != superSourceRoom){
            reverse.add(gotDestinationFromThis2);
            gotDestinationFromThis2 = cameFromThisStop.get(gotDestinationFromThis2);
        }
        reverse.add(gotDestinationFromThis2);*/
        ///////////////////
        //***BFS VÉGE****//
        ///////////////////
        //az eredmény az egyenlő lesz a reverse lista fordítottjával
        //for(Room roomIter : reverse){
        //    resultBFS.add(0, roomIter);
        //}

        //result = resultBFS.get(0);
    }

    public static void initializeBFSMaps(java.util.Map<Room, List<Room>> hashmapOfNeighbours, java.util.Map<Room, Boolean> visitedThisStop, java.util.Map<Room, Room> cameFromThisStop){
        for(int i = 0; i < hashmapOfNeighbours.size(); i++){
            for (Room stopKey : hashmapOfNeighbours.keySet() ) {
                visitedThisStop.put(stopKey, false);
                cameFromThisStop.put(stopKey, null);
            }
        }
    }
}
