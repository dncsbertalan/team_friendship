package Items;

import Constants.Enums;
import Entities.Student;

public class TVSZ extends Item {

    /**
     * The number of times this item can be used.
     */
    private int remainigPages;

    public TVSZ() {
        this.protectionType = Enums.ProtectionType.tvsz;
    }

    @Override
    public void UseItem(Student student) {}

    /**
     * Decreases the remaining uses of this item.
     */
    public void DecreaseUsability() {
        this.remainigPages--;
    }
}
