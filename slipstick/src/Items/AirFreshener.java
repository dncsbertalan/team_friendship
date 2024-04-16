package Items;

import Constants.GameConstants;
import Entities.Student;

public class AirFreshener extends Item {
    private static int ID = 0;

    @Override
    public void AutoName() {
        this.name = GameConstants.AirFreshener + ++ID;
    }

    /**
     * Deactivates toxic gas in the room it is used in.
     * @param student: Student using the AirFresher.
     */
    @Override
    public void UseItem(Student student) {
        student.GetCurrentRoom().DeactivateToxicGas();
        student.DeleteItemFromInventory(this);
    }
}
