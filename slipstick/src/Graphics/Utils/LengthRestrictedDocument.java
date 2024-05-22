package Graphics.Utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Limits how many character can be read.
 */
public class LengthRestrictedDocument extends PlainDocument {
    private final int limit;

    /**
     * Limits how many character can be read.
     * @param limit the max number of characters that can be read
     */
    public LengthRestrictedDocument(int limit) {
        this.limit = limit;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null)
            return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offs, str, a);
        }
    }
}
