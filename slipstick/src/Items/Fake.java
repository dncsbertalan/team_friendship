package Items;

import Constants.GameConstants;
import Entities.Student;

public class Fake extends Item{
    private static int ID = 0;

    @Override
    public void AutoName() {
        this.name = GameConstants.FakeItem + ++ID;
    }

    /**
     * Called when an item is used by the student.
     *
     * @param student the item user
     */
    @Override
    public void UseItem(Student student) {
        System.out.println("Nothing happened, it was a fake item! ^^");
        student.GetInventory().remove(this);
    }
}
