package Items;

import Constants.Enums;
import Constants.GameConstants;
import Entities.Student;

public class FFP2Mask extends Item {

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
}
