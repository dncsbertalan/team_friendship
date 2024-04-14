package Items;

import Constants.GameConstants;
import Entities.Student;

public class Cheese extends Item {
    private static int ID = 0;
    private String name = GameConstants.Cheese + ++ID;;

    @Override
    public void UseItem(Student student) {
        if(activated) {
            student.GetCurrentRoom().ReleaseToxicGas(2);
            student.DeleteItemFromInventory(this);
        }
    }

}
