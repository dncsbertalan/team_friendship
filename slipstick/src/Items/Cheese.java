package Items;

import Entities.Student;

public class Cheese extends Item {

    @Override
    public void UseItem(Student student) {
        if(activated) {
            student.GetCurrentRoom().ReleaseToxicGas(2);
            student.DeleteItemFromInventory(this);
        }
    }

}
