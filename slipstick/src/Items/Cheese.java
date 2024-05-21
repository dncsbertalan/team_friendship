package Items;

import Constants.GameConstants;
import Entities.Student;

public class Cheese extends Item {
    private static int ID = 0;

    @Override
    public void AutoName() {
        this.name = GameConstants.Cheese + ++ID;
    }

    @Override
    public void UseItem(Student student) {
        if(activated) {
            student.GetCurrentRoom().ReleaseToxicGas(2);
            student.DeleteItemFromInventory(this);
            System.out.println("cheese");
        }
    }

}
