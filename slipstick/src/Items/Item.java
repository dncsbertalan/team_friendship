package Items;

import Entities.Student;

public abstract class Item {

    public enum ProtectionType {
        none, tvsz, wetCloth, ffp2Mask
    }

    private boolean activate;
    private ProtectionType protectionType = ProtectionType.none;

    /**
     *
     * @param student
     */
    public void UseItem(Student student) {

    }

    /**
     *
     */
    public void ActivateItem() {

    }

    /**
     *
     */
    public void DeactivateItem() {

    }

    public ProtectionType getProtectionType() {
        return protectionType;
    }
}
