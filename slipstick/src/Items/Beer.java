package Items;

import Constants.GameConstants;
import Entities.Student;

import java.util.Random;

/**
 * Increases the user's remaining turns.
 */
public class Beer extends Item {
    private static int ID = 0;

    @Override
    public void AutoName() {
        this.name = GameConstants.Beer + ++ID;
    }

    /**
     * Increases the student's remaining turns.
     * If the student has any other than the beer currently used, then the function makes the student lose (not drop) a random one.
     * The function then removes the beer from the student's inventory.
     * @param student: Student using the beer.
     */
    @Override
    public void UseItem(Student student) {
        student.IncreaseMoveCount(1);
        if(student.GetInventory().size() != 1){
            Item dropThisItem;
            do{
                dropThisItem = student.GetRandomItemFromStudent();
            } while (dropThisItem == this);
            student.GetCurrentRoom().AddUnpickupableItemToRoom(dropThisItem);
            student.AddTemporaryUnpickableItem(dropThisItem, student.GetCurrentRoom());
            student.GetInventory().remove(dropThisItem);
        }
        student.GetInventory().remove(this);
    }
}
