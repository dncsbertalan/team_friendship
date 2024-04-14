package Items;

import Constants.Enums;
import Constants.GameConstants;
import Entities.Student;

public class FFP2Mask extends Item {
    private static int ID = 0;
    private String name = GameConstants.FFP2Mask + ++ID;
    /**
     * The number of times this item can be used.
     */
    private int remainingUses;

    public FFP2Mask() {
        this.remainingUses = GameConstants.FFP2Mask_MaxUses;
        this.protectionType = Enums.ProtectionType.ffp2Mask;
    }

    @Override
    public void UseItem(Student student) {}

    /**
     * Decreases the remaining uses of this item.
     */
    public void DecreaseDurability() {
        this.remainingUses--;
    }

    /**
     * How many uses are left for the mask.
     * @return : number of remaining uses.
     */
    public int GetRemainingUsees(){
        return remainingUses;
    }
}
