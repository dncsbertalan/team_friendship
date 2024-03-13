package Items;

import Entities.Student;

public class TVSZ extends Item {

    private int remainigPages;

    @Override
    public void UseItem(Student student) {

    }

    /**
     * Decreases the remaining uses of this item.
     */
    public void DecreaseUsability() {
        this.remainigPages--;
    }
}
