package Items;

import Constants.Enums;
import Constants.GameConstants;
import Entities.Student;

public class FFP2Mask extends Item {
    private static int ID = 0;

    /**
     * The number of times this item can be used.
     */
    private int remainingUses;

    public FFP2Mask() {
        super();
        this.remainingUses = GameConstants.FFP2Mask_MaxUses;
        this.protectionType = Enums.ProtectionType.ffp2Mask;
    }

    @Override
    public void AutoName() {
        this.name = GameConstants.FFP2Mask + ++ID;
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
    public int GetRemainingUsages(){
        return remainingUses;
    }
}
