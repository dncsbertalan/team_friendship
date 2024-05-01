package Items;

import Constants.GameConstants;
import Entities.Student;

/**
 *
 */
public class SlipStick extends Item {
    private static int ID = 0;

    @Override
    public void AutoName() {
        this.name = GameConstants.SlipSlick + ++ID;
    }

    @Override
    public void UseItem(Student student) {

    }
}
