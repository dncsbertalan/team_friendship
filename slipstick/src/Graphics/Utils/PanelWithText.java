package Graphics.Utils;

import Constants.GameConstants;
import Entities.Student;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

import static Runnable.Main.game;

public class PanelWithText {

    private final Graphics2D graphics2D;
    private final ArrayList<String> lines;
    private final Font font;

    private final int topPadding;
    private final static int bottomPadding = 10;
    private final static int rightLeftPadding = 10;
    private final static int backBoxSizeDiff = 3;
    private final int arc;

    /**
     * Creates a PanelWithText object.
     * @param lines the line to be shown on the panel.
     */
    public PanelWithText(final Graphics2D graphics2D, final ArrayList<String> lines, final Font font) {
        this(graphics2D, lines, font, 2, 10);
    }

    public PanelWithText(final Graphics2D graphics2D, final ArrayList<String> lines, final Font font, int topPadding, int arc) {
        this.graphics2D = graphics2D;
        this.lines = lines;
        this.font = font;
        this.topPadding = topPadding;
        this.arc = arc;
    }

    /**
     * Draws a panel to the screen with the lines given.
     * @param postion       the position of the panel
     */
    public void Draw(Vector2 postion) {
        Graphics2D g = (Graphics2D) graphics2D.create();

        g.setFont(font);

        final int characterHeight = (int) g.getFontMetrics().getStringBounds("GetHeight!<3", g).getHeight();

        final int infoBoxTextsWidth = HelperMethods.GetLongestLineLength(lines, g);
        final int infoBoxTextsHeight = lines.size()* characterHeight;
        final int infoBoxBackWidth = infoBoxTextsWidth + rightLeftPadding * 2 + backBoxSizeDiff * 2;
        final int infoBoxBackHeight = infoBoxTextsHeight + topPadding + bottomPadding + backBoxSizeDiff * 2;
        final int infoBoxWidth = infoBoxTextsWidth + rightLeftPadding * 2;
        final int infoBoxHeight = infoBoxTextsHeight + topPadding + bottomPadding;

        // Background box
        final Color infoBackBoxColor = new Color(115,85,90,150);
        g.setColor(infoBackBoxColor);
        g.fillRoundRect(postion.x, postion.y, infoBoxBackWidth, infoBoxBackHeight, arc, arc);
        postion.Add(new Vector2(backBoxSizeDiff, backBoxSizeDiff));

        // Front box
        final Color infoBoxColor = new Color(255, 255, 255, 186);
        g.setColor(infoBoxColor);
        g.fillRoundRect(postion.x, postion.y, infoBoxWidth, infoBoxHeight, arc, arc);

        // Draw the info lines
        postion.Add(new Vector2(rightLeftPadding, topPadding));
        g.setColor(Color.black);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (String infoLine : lines) {
            postion.AddY(characterHeight);
            g.drawString(infoLine, postion.x, postion.y);
            //g.drawRect(pos.x, pos.y - fontHeight.get(font), 10, fontHeight.get(font));
        }

        g.dispose();
    }

    /**
     * Returns the width of the box.
     * @return  the width of the box
     */
    public int GetBoxWidth() {
        final int infoBoxTextsWidth = HelperMethods.GetLongestLineLength(lines, graphics2D);
        return infoBoxTextsWidth + rightLeftPadding * 2 + backBoxSizeDiff * 2;
    }

    /**
     * Returns the height of the box.
     * @return  the height of the box
     */
    public int GetBoxHeight() {
        final int characterHeight = (int) graphics2D.getFontMetrics().getStringBounds("GetHeight!<3", graphics2D).getHeight();
        final int infoBoxTextsHeight = lines.size()* characterHeight;
        return infoBoxTextsHeight + topPadding + bottomPadding + backBoxSizeDiff * 2;
    }
}
