package Items;

import Entities.Student;

public class AirFreshener extends Item {
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
