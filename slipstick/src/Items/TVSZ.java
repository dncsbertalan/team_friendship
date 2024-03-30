package Items;

import Constants.Enums;
import Constants.GameConstants;
import Entities.Student;

public class TVSZ extends Item {

    /**
     * The number of times this item can be used.
     */
    private int remainigPages;

    public TVSZ() {
        this.remainigPages = GameConstants.TVSZ_MaxUses;
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

    /**
     * How many pages are there still in the TVSZ.
     * @return : number of remaining pages.
     */
    public int GetRemainingPages(){
        return remainigPages;
    }
}
