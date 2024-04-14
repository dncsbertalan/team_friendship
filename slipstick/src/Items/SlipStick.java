package Items;

import Constants.GameConstants;
import Entities.Student;

/**
 *
 */
public class SlipStick extends Item {
    private static int ID = 0;
    private String name = GameConstants.SlipSlick + ++ID;
    @Override
    public void UseItem(Student student) {

    }
}
