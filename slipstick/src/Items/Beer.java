package Items;

import Entities.Student;

import java.util.Random;

/**
 * Increases the user's remaining turns.
 */
public class Beer extends Item {
    /**
     * Increases the student's remaining turns.
     * If the student has any other than the beer currently used, then the function makes the student drop a random one.
     * @param student: Student using the beer.
     */
    @Override
    public void UseItem(Student student) {
        student.IncreaseMoveCount(1);
        if(student.GetInventory().size() != 1){
            Item dropThisItem;
            do{
                dropThisItem = GetRandomItemFromStudent(student);
            } while (dropThisItem == this);

            student.GetCurrentRoom().AddUnpickupableItemToRoom(dropThisItem);
        }
    }

    /**
     * Gets a random item from the student's inventory.
     * @param student: Choosing a random item from this student.
     * @return: The random item chosen.
     */

    public Item GetRandomItemFromStudent(Student student){
        Random random = new Random();
        int minInclusive = 0;
        int maxExclusive = student.GetInventory().size();
        int itemIndex = random.ints(minInclusive, maxExclusive).findFirst().getAsInt();

        Item resultItem = student.GetInventory().get(itemIndex);
        return resultItem;
    }
}
