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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        List<Student> studentsAboutToBeAssassinated = new ArrayList<>(this.room.GetStudents());
        for(Student sIter : studentsAboutToBeAssassinated){
            this.KillStudent(sIter);
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

        //try to see if any neighbouring rooms has enough capacity
        Room stepFromThis = this.GetCurrentRoom();
        Room stepIntoThis = null;
        for(Room roomIter : this.GetCurrentRoom().GetNeighbours()){
            if(roomIter.CanStepIn() == true){
                stepIntoThis = roomIter;
                break;
            }
        }

        //if not, the entity does nothing
        if(stepIntoThis == null){
            game.GetRoundManager().EndTurn();
            return;
        }

        //if yes, the entity steps into the room
        this.StepInto(stepIntoThis);

        String message1 = this.GetName() + " went from " + stepFromThis.GetName() + " to " + stepIntoThis.GetName();
        gameController.NewScreenMessage(300, new Color(98, 9, 119), message1);

        //the entity randomly picks up an item from its current room
        Random random = new Random();
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
              String message2 = this.GetName() + " picked up " + pickThisUp.GetName();
              gameController.NewScreenMessage(60, new Color(98, 9, 119), message2);
          }
        }
        game.GetRoundManager().EndTurn();
        return;
    }
}
