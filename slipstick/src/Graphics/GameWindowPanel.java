package Graphics;

import Constants.GameConstants;
import Entities.Student;
import GameManagers.OUTCOME;
import Graphics.Listeners.GamePanelExitButtonListener;
import Graphics.Listeners.GameWindowMouseWheelListener;
import Graphics.Clickable.ClickableObject;
import Graphics.Listeners.GameWindowMouseListener;
import Graphics.Clickable.RoomObject;
import Graphics.Utils.*;
import Items.*;
import Labyrinth.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static Runnable.Main.*;

public class GameWindowPanel extends JPanel {
    private final GameWindowFrame gameWindowFrame;
    private Vector2 mousePosition;
    private final Vector2 windowSize;
    private final MenuButton exitButton;

    public GameWindowPanel(GameWindowFrame frame) {
        // Set constant fields
        gameWindowFrame = frame;
        windowSize = new Vector2(frame.getWidth(), frame.getHeight());

        this.setBackground(Color.lightGray);
        this.setPreferredSize(new Dimension(GameConstants.GamePanel_WIDTH, GameConstants.GamePanel_HEIGHT));

        // Enable double buffering
        this.setDoubleBuffered(true);

        // Add listeners
        GameWindowMouseListener mouseListener = new GameWindowMouseListener(this);
        this.addMouseListener(mouseListener);
        GameWindowMouseWheelListener mouseWheelListener = new GameWindowMouseWheelListener();
        this.addMouseWheelListener(mouseWheelListener);
        // Add the KeyListener
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                // Handle keyPressed if needed
                gameController.HandleInput(game.GetRoundManager().GetActiveStudent(), e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };
        this.addKeyListener(keyListener);

        // Add the exit button
        this.setLayout(null);
        exitButton = new MenuButton(
                GameConstants.GamePanel_EXIT_BUTTON,
                GameConstants.GamePanel_EXIT_BUTTON_BACKGROUND_COLOR,
                GameConstants.GamePanel_EXIT_BUTTON_BORDER_COLOR,
                GameConstants.GamePanel_EXIT_BUTTON_BORDER_THICKNESS);
            exitButton.setLayout(null);
            exitButton.setBounds(windowSize.x - 125 - 20, windowSize.y - 75 - 20, 125, 75 );
            exitButton.setFont(GameConstants.MenuPanel1_BUTTON_FONT);
            exitButton.addMouseListener(new GamePanelExitButtonListener(frame));
        this.add(exitButton);
    }

    /**
     * This method draws the game window.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();
        Graphics2D graphics2D = (Graphics2D) g;

        if (game.IsPreGame()) {     // If the game is not loaded fully
            DrawLoadingScreen(graphics2D);
            return;
        }

        if (game.IsEnded()) {      // If the game ended
            DrawEndScreen(graphics2D);
            return;
        }

        ResetClickables();

        Point point = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(point, this);
        mousePosition = new Vector2(point.x, point.y);

        DrawRoom(graphics2D);
        DrawGasClouds(graphics2D);
        DrawCurrentRound(graphics2D);
        DrawStudentsInfo(graphics2D);
        DrawInventory(graphics2D);
        DrawScreenMessages(graphics2D);
        DrawItemInformationTable(graphics2D);
        DrawControlInformationTable(graphics2D);
    }

// region Getters/Setters ==============================================================================================

    public Vector2 GetMousePosition() {
        return mousePosition;
    }

//endregion ============================================================================================================

// region DRAW =========================================================================================================

    /**
     * Draws the currently active student's room.
     * @param graphics2D graphics instance
     */
    private void DrawRoom(Graphics2D graphics2D) {

        final Student active = game.GetRoundManager().GetActiveStudent();
        if (active == null) return;

        final Room curRoom = active.GetCurrentRoom();
        RoomObject roomObject = new RoomObject(this, Vector2.Mult(windowSize, 0.5f), curRoom, false);
        roomObject.Draw(graphics2D);

        // Draw the neighbours
        float angle = 360f / Math.max(curRoom.GetNeighbours().size(), GameConstants.ROOM_MIN_SIDES);
        int dist = (int) ((float) GameConstants.ROOM_SIZE * Math.cos(Math.toRadians(angle / 2.0)));
        Vector2 neighbourDistanceFromCenter = new Vector2(0, -dist - GameConstants.SMALL_ROOM_DISTANCE);
        neighbourDistanceFromCenter.RotateBy(angle / 2f);
        int drawnRoom = 0;
        Vector2 windowCent = Vector2.Mult(windowSize, 0.5f);

        for (Room neighbour : curRoom.GetNeighbours()) {
            final Vector2 doorPos = Vector2.RotateBy(neighbourDistanceFromCenter,drawnRoom++ * angle);
            Vector2 pos = Vector2.Add(windowCent, doorPos);
            final float rot = (float) Math.toDegrees(Vector2.ToRotation(doorPos));
            RoomObject ro = new RoomObject(this, pos, neighbour,
                    true, rot, neighbour.GetNeighbours().indexOf(curRoom));
            ro.Draw(graphics2D);
        }

        // Add gas effect if gas room
        if (!curRoom.IsGassed()) return;

        Random random = new Random();
        if (random.nextFloat() < GameConstants.GAS_PROBABILITY) {
            NewGasCloud((int) (random.nextFloat(0.5f, 1f) * 180),
                    new Vector2(windowSize.x / 2 + random.nextInt(-GameConstants.GAS_RANGE, GameConstants.GAS_RANGE),
                            windowSize.y / 2 + random.nextInt(-GameConstants.GAS_RANGE, GameConstants.GAS_RANGE)),
                    random.nextFloat(0.5f, 1f),
                    random.nextBoolean() ? 1 : -1
            );
        }
    }

    /**
     * Draws the inventory to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawInventory(Graphics2D graphics2D) {

        final Student active = game.GetRoundManager().GetActiveStudent();
        if (active == null) return;

        final int numberOfStudentsItems = active.GetInventory().size();
        final int selectedSlot = active.GetSelectedInventorySlot();
        final int invRectSize = 70;
        final float invSelectedRectScale = 1.2f;
        final int spaceBetweenSlots = 10;
        final int arc = 10;
        Vector2 pos = GameConstants.GamePanel_INVENTORY_POS();

        for (int i = 0; i < 5; i++) {
            int size = invRectSize;
            float itemScale = 200;

            if (i == selectedSlot) {
                size = (int) (invRectSize * invSelectedRectScale);
                itemScale *= invSelectedRectScale;
                graphics2D.setColor(Color.yellow);
                int y = pos.y + invRectSize / 2 - size / 2;
                graphics2D.fillRoundRect(pos.x, y, size + 4, size + 4, arc, arc);
            }
            graphics2D.setColor(new Color(0, 0, 0, 150));
            int y = pos.y + invRectSize / 2 - size / 2;
            graphics2D.fillRoundRect(pos.x + 2, y + 2, size, size, arc, arc);

            if(i < numberOfStudentsItems){
                Item item = active.GetInventory().get(i);
                DrawUtils.DrawItem(graphics2D, item, Vector2.Add(pos, new Vector2(size / 2, invRectSize / 2)), itemScale);
            }

            pos.AddX(size + spaceBetweenSlots);
        }
    }

    /**
     * Draws the current round to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawCurrentRound(Graphics2D graphics2D) {
        Font font = new Font("Courier New", Font.BOLD, 25);
        ArrayList<String> lines = new ArrayList<>();
        String roundText = GameConstants.GamePanel_ROUND_TEXT + game.GetRoundManager().GetCurrentRound();
        lines.add(roundText);
        PanelWithText roundPanel = new PanelWithText(graphics2D, lines, font, 0, 15);
        roundPanel.Draw(new Vector2(windowSize.x - roundPanel.GetBoxWidth() - 20, 20));
    }

    /**
     * Draws the entities to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawStudentsInfo(Graphics2D graphics2D) {
        Graphics2D g = (Graphics2D) graphics2D.create();
        Font basicFont = new Font("Courier new", Font.PLAIN, 16);
        Font activeFont = new Font("Courier new", Font.BOLD, 19);
        g.setFont(basicFont);

        final HashMap<Font, Integer> fontHeight = new HashMap<>();
        final int characterHeight = (int) g.getFontMetrics().getStringBounds("GetHeight!<3", g).getHeight();
        g.setFont(activeFont);
        final int activeCharacterHeight = (int) g.getFontMetrics().getStringBounds("GetHeight!<3", g).getHeight();
        fontHeight.put(basicFont, characterHeight);
        fontHeight.put(activeFont, activeCharacterHeight);

        // Puts the information into a string array
        final ArrayList<String> infoLines = new ArrayList<>();
        final HashMap<String, Font> infoLineFonts = new HashMap<>();
        final int activeStudentPlusInfo = 2;
        int activeStudentLine = 0;
        for (Student student : game.GetStudents()) {
            String line = student.GetName();

            if(student.IsDead()) {
                line += GameConstants.INFO_DEAD;
            } else {
                line += GameConstants.INFO_ALIVE;
            }

            infoLines.add(line);

            // paralyzed info
            if (student.IsParalysed()) {
                infoLines.add(GameConstants.INFO_PARALYZED);
                infoLineFonts.put(GameConstants.INFO_PARALYZED, basicFont);
            }

            // missed rounds info
            if (student.GetMissedRounds() > 1) {
                infoLines.add(GameConstants.INFO_MISSED_ROUND1 + (student.GetMissedRounds() - 1) + GameConstants.INFO_MISSED_ROUND2);
                infoLineFonts.put(GameConstants.INFO_MISSED_ROUND1 + (student.GetMissedRounds() - 1) + GameConstants.INFO_MISSED_ROUND2, basicFont);
            }
            else if (student.GetMissedRounds() == 1) {
                infoLines.add(GameConstants.INFO_MISSED_ROUND3);
                infoLineFonts.put(GameConstants.INFO_MISSED_ROUND3, basicFont);
            }

            if(student == game.GetRoundManager().GetActiveStudent()){
                activeStudentLine = infoLines.size() - 1;
                String plusInfoLine1 = GameConstants.REMAINING_ROUND_TEXT + student.GetRemainingTurns();
                infoLines.add(plusInfoLine1);
                infoLineFonts.put(plusInfoLine1, activeFont);

                String plusOnfoLine2 = GameConstants.REMAINING_ITEM_PICK_UP + student.getRemainingItemPickUp();
                infoLines.add(plusOnfoLine2);
                infoLineFonts.put(plusOnfoLine2, activeFont);
                infoLineFonts.put(line, activeFont);
            }
            else {
                infoLineFonts.put(line, basicFont);
            }
        }

        final int topPadding = 2;
        final int bottomPadding = 10;
        final int rightLeftPadding = 10;
        final int backBoxSizeDiff = 3;
        final int infoBoxTextsWidth = HelperMethods.GetLongestLineLength(infoLines, infoLineFonts, g);
        final int infoBoxTextsHeight = (infoLines.size() - 1 - activeStudentPlusInfo) * characterHeight + (1 + activeStudentPlusInfo) * activeCharacterHeight;
        final int infoBoxBackWidth = infoBoxTextsWidth + rightLeftPadding * 2 + backBoxSizeDiff * 2;
        final int infoBoxBackHeight = infoBoxTextsHeight + topPadding + bottomPadding + backBoxSizeDiff * 2;
        final int infoBoxWidth = infoBoxTextsWidth + rightLeftPadding * 2;
        final int infoBoxHeight = infoBoxTextsHeight + topPadding + bottomPadding;
        final int arc = 10;
        final float activeStudentBoxOutlineWidth = 2f;
        final int activeStudentBoxPosY = characterHeight * activeStudentLine + activeCharacterHeight / 4;
        final int activeStudentBoxHeight = activeCharacterHeight * (1 + activeStudentPlusInfo);
        Vector2 pos = new Vector2(GameConstants.GamePanel_ENTITY_INFO_POS_X, windowSize.y / 4 - infoBoxBackHeight / 2);

        // Background box
        final Color infoBackBoxColor = new Color(115,85,90,150);
        g.setColor(infoBackBoxColor);
        g.fillRoundRect(pos.x, pos.y, infoBoxBackWidth, infoBoxBackHeight, arc, arc);
        pos.Add(new Vector2(backBoxSizeDiff, backBoxSizeDiff));

        // Front box
        final Color infoBoxColor = new Color(255, 255, 255, 186);
        g.setColor(infoBoxColor);
        g.fillRoundRect(pos.x, pos.y, infoBoxWidth, infoBoxHeight, arc, arc);

        // The currently active students box
        final Color activeBoxColor = new Color(101, 206, 96, 110);
        g.setColor(activeBoxColor);
        g.fillRoundRect(pos.x, pos.y + activeStudentBoxPosY, infoBoxWidth, activeStudentBoxHeight, arc, arc);

        // The currently active students box outline
        final Color activeBoxOutlineColor = new Color(81, 189, 77, 203);
        g.setColor(activeBoxOutlineColor);
        g.setStroke(new BasicStroke(activeStudentBoxOutlineWidth));
        g.drawRoundRect(pos.x, pos.y + activeStudentBoxPosY, infoBoxWidth, activeStudentBoxHeight, arc, arc);

        // Draw the info lines
        pos.Add(new Vector2(rightLeftPadding, topPadding));
        g.setColor(Color.black);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (String infoLine : infoLines) {
            Font font = infoLineFonts.get(infoLine);
            g.setFont(font);
            pos.AddY(fontHeight.get(font));
            g.drawString(infoLine, pos.x, pos.y);
            //g.drawRect(pos.x, pos.y - fontHeight.get(font), 10, fontHeight.get(font));
        }

        g.dispose();
    }

    /**
     * Draws the information about items to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawItemInformationTable (Graphics2D graphics2D){
        final Student activeStudent = game.GetRoundManager().GetActiveStudent();
        if (activeStudent == null) return;

        Graphics2D g = (Graphics2D) graphics2D.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int infoPanelWidth = (int) (windowSize.x * 0.15);
        int infoPanelHeight = (int) (windowSize.x * 0.25);

        int x_coordinate_16_percent = (int) (windowSize.x * 0.165);
        int y_coordinate_90_percent = (int) (windowSize.y * 0.90);
        int y_coordinate_71_percent = (int) (windowSize.y * 0.71);
        int y_coordinate_88_percent = (int) (windowSize.y * 0.88);

        int x_calculated_coord = windowSize.x - x_coordinate_16_percent;
        int y_calculated_coord = windowSize.y - y_coordinate_90_percent;

        int x_first_text_coord = windowSize.x - x_coordinate_16_percent;;
        int y_first_text_coord = windowSize.y - y_coordinate_88_percent;;

        int x_second_text_coord = windowSize.x - x_coordinate_16_percent;
        int y_second_text_coord = windowSize.y - y_coordinate_71_percent;

        Vector2 pos = new Vector2(x_calculated_coord, y_calculated_coord);

        final int arc = 10;

        g.setColor(GameConstants.GamePanel_ITEM_INFORMATION_BORDER_COLOR);
        g.fillRoundRect(pos.x - 5, pos.y - 5, infoPanelWidth + 10, infoPanelHeight + 10, arc, arc);
        g.setColor(GameConstants.GamePanel_ITEM_INFORMATION_FILL_COLOR);
        g.fillRoundRect(pos.x, pos.y, infoPanelWidth, infoPanelHeight, arc, arc);
        g.setFont(new Font("Courier New", Font.BOLD, 22));
        g.setColor(Color.black);


        g.drawString(GameConstants.GamePanel_INVENTORY_ITEM_TEXT_1, x_first_text_coord, y_first_text_coord);

        int textHeight = (int) -g.getFontMetrics().getStringBounds("GetHeight!<3", g).getY();

        g.drawString(GameConstants.GamePanel_INVENTORY_ITEM_TEXT_2, x_first_text_coord, (y_first_text_coord + textHeight));

        g.drawString(GameConstants.GamePanel_ROOM_ITEM_TEXT_1, x_second_text_coord, y_second_text_coord);

        g.drawString(GameConstants.GamePanel_ROOM_ITEM_TEXT_2, x_second_text_coord, (y_second_text_coord + textHeight));

        g.setFont(new Font("Courier New", Font.BOLD, 17));

        final Item selectedItem = activeStudent.GetSelectedItem();
        if (selectedItem != null) DrawItemInformation1(g, textHeight, x_first_text_coord, y_first_text_coord, selectedItem);

        final Item item = gameController.GetSelectedItem();
        if (item != null) DrawItemInformation1(g, textHeight, x_second_text_coord, y_second_text_coord, item);
        //DrawItemInformation2(g, textHeight, x_second_text_coord, y_second_text_coord);

        g.dispose();
    }

    private void DrawItemInformation1(Graphics2D graphics2D, int textHeight, int x_first_text_coord, int y_first_text_coord, Item item){



        final ArrayList<String> info = GetItemInfo(item);

        for (int i = 0; i < info.size(); i++) {
            graphics2D.drawString(info.get(i), x_first_text_coord, y_first_text_coord + (i + 2) * textHeight);
        }
    }

    private void DrawControlInformationTable(Graphics2D graphics2D) {
        Graphics2D g = (Graphics2D) graphics2D.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int infoPanelWidth = (int) (windowSize.x * 0.15);
        int infoPanelHeight = (int) (windowSize.y * 0.25);

        int x_coordinate_16_percent = (int) (windowSize.x * 0.165);
        int y_coordinate_42_percent = (int) (windowSize.y * 0.42);
        int y_coordinate_40_percent = (int) (windowSize.y * 0.40);

        int x_calculated_coord = windowSize.x - x_coordinate_16_percent;
        int y_calculated_coord = windowSize.y - y_coordinate_42_percent;

        int x_first_text_coord = windowSize.x - x_coordinate_16_percent;
        int y_first_text_coord = windowSize.y - y_coordinate_40_percent;

        final int arc = 10;

        g.setFont(new Font("Courier New", Font.BOLD, 22));
        int titleHeight = g.getFontMetrics().getHeight();

        g.setFont(new Font("Courier New", Font.BOLD, 17));
        int textHeight = g.getFontMetrics().getHeight();

        ArrayList<String> controls = new ArrayList<>();
        controls.add("¤ lmb: click door/item");
        controls.add("¤ mwhl: inventory");
        controls.add("¤ c: pick up item");
        controls.add("¤ d: drop item");
        controls.add("¤ a: activate item");
        controls.add("¤ u: use item");
        controls.add("¤ p: pair transistor");
        controls.add("¤ e: end turn");

        int totalTextHeight = titleHeight + (controls.size() + 1) * textHeight;

        // Ensure the panel is tall enough to hold all the text
        if (infoPanelHeight < totalTextHeight + 20) { // add some padding
            infoPanelHeight = totalTextHeight + 20;
        }

        g.setColor(GameConstants.GamePanel_ITEM_INFORMATION_BORDER_COLOR);
        g.fillRoundRect(x_calculated_coord - 5, y_calculated_coord - 5, infoPanelWidth + 10, infoPanelHeight + 10, arc, arc);
        g.setColor(GameConstants.GamePanel_ITEM_INFORMATION_FILL_COLOR);
        g.fillRoundRect(x_calculated_coord, y_calculated_coord, infoPanelWidth, infoPanelHeight, arc, arc);

        g.setFont(new Font("Courier New", Font.BOLD, 22));
        g.setColor(Color.black);
        g.drawString("Controls:", x_first_text_coord, y_first_text_coord);

        g.setFont(new Font("Courier New", Font.BOLD, 17));

        for (int i = 0; i < controls.size(); i++) {
            g.drawString(controls.get(i), x_first_text_coord, y_first_text_coord + (i + 2) * textHeight);
        }

        g.dispose();
    }


    /**
     * Draws the screen messages to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawScreenMessages(Graphics2D graphics2D) {
        if (screenMessages.isEmpty()) return;

        Graphics2D g = (Graphics2D) graphics2D.create();

        // Calculates the height of the texts
        g.setFont(GameConstants.SCREEN_MESSAGE_FONT);
        int characterHeight = (int) -g.getFontMetrics().getStringBounds("GetHeight!<3", g).getY();
        int textsHeight = screenMessages.size() * characterHeight;

        Vector2 posOnScreen = new Vector2(GameConstants.GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT,
                windowSize.y - GameConstants.GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT - textsHeight);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (ScreenMessage sc : screenMessages) {
            posOnScreen.AddY(characterHeight);
            int alpha = (int) HelperMethods.Remap(sc.GetTimeLeft(), 60, 0, 255, 0, true);
            g.setColor(new Color(sc.GetColor().getRed(), sc.GetColor().getGreen(), sc.GetColor().getBlue(), alpha));
            g.drawString(sc.GetMessage(), posOnScreen.x, posOnScreen.y);
        }

        g.dispose();
    }

    private void DrawGasClouds(Graphics2D graphics2D) {
        if (gasClouds.isEmpty()) return;

        synchronized (gasClouds) {
            for (GasCloud gc : gasClouds) {
                gc.Draw(graphics2D);
            }
        }
    }

    /**
     * Draws the loading screen.
     * @param graphics2D graphics instance
     */
    private void DrawLoadingScreen(Graphics2D graphics2D) {
        // Draw the logo image
        BufferedImage image = imageManager.resizeImage(GameConstants.IMAGE_LOGO, 150);
        Vector2 center = Vector2.Mult(windowSize, 0.5f);
        graphics2D.drawImage(image, center.x - (image.getWidth() / 2), center.y - (image.getHeight() / 2), null);
    }

    /**
     * Draws the end screen.
     * @param graphics2D graphics instance
     */
    private void DrawEndScreen(Graphics2D graphics2D) {
        // Exit Button reposition
        exitButton.setBounds((int)  (windowSize.x * 0.5f) - 125 / 2, windowSize.y - 75 - 20, 125, 75 );

        // Logo
        BufferedImage image = imageManager.resizeImage(GameConstants.IMAGE_LOGO, 150);
        Vector2 logoCenter = new Vector2((int)  (windowSize.x * 0.5f),  (int) (windowSize.y * 0.3f));
        DrawUtils.DrawImageCentered(graphics2D, image, logoCenter);

        graphics2D.setFont(new Font("Courier new", Font.BOLD, 60));
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Rectangle2D rect, CODRect = null;
        String message, CODMessage = "";

        if (game.GetWin()) {    // If the game was won
            rect = graphics2D.getFontMetrics().getStringBounds(GameConstants.WIN_MESSAGE, graphics2D);
            message = GameConstants.WIN_MESSAGE;
        }
        else {                  // If the game was lost
            rect = graphics2D.getFontMetrics().getStringBounds(GameConstants.LOST_MESSAGE, graphics2D);
            message = GameConstants.LOST_MESSAGE;
            if (game.GetOutcome() == OUTCOME.AllPlayersAreDead) {
                CODRect = graphics2D.getFontMetrics().getStringBounds(GameConstants.LOST_ALL_DEAD, graphics2D);
                CODMessage = GameConstants.LOST_ALL_DEAD;
            }
            else if (game.GetOutcome() == OUTCOME.PlayersRanOutOfRounds) {
                CODRect = graphics2D.getFontMetrics().getStringBounds(GameConstants.LOST_ALL_DEAD, graphics2D);
                CODMessage = GameConstants.LOST_NO_MORE_ROUNDS;
            }
        }

        Vector2 messagePos = new Vector2((int) (windowSize.x * 0.5f - rect.getWidth() / 2), (int)  (windowSize.y * 0.62f + rect.getHeight()));
        graphics2D.drawString(message, messagePos.x, messagePos.y);

        if (CODRect != null) {
            Vector2 CODMessagePos = new Vector2((int) (windowSize.x * 0.5f - CODRect.getWidth() / 2), (int)  (windowSize.y * 0.7f + rect.getHeight()));
            graphics2D.drawString(CODMessage, CODMessagePos.x, CODMessagePos.y);
        }

        Vector2 janitorCen = new Vector2((int) (windowSize.x * 0.15f), (int)  (windowSize.y * 0.7f));
        Vector2 profCen = new Vector2(janitorCen.x + 77, (int)  (windowSize.y * 0.7f));
        Vector2 stud1Cen = new Vector2((int) (windowSize.x * 0.8f), (int)  (windowSize.y * 0.7f));
        Vector2 stud2Cen = new Vector2(stud1Cen.x + 77, (int)  (windowSize.y * 0.7f));

        DrawUtils.DrawImageCentered(graphics2D, DrawUtils.RotateImage(imageManager.resizeImage(GameConstants.IMAGE_JANITOR2, 500), -10.), janitorCen);
        DrawUtils.DrawImageCentered(graphics2D, DrawUtils.RotateImage(imageManager.resizeImage(GameConstants.IMAGE_PROFESSOR3, 500), 10.), profCen);
        DrawUtils.DrawImageCentered(graphics2D, DrawUtils.RotateImage(imageManager.resizeImage(GameConstants.IMAGE_STUDENT1, 500), -10.), stud1Cen);
        DrawUtils.DrawImageCentered(graphics2D, DrawUtils.RotateImage(imageManager.resizeImage(GameConstants.IMAGE_STUDENT4, 500), 10.), stud2Cen);
    }

// endregion ===========================================================================================================

// region Clickable objects ============================================================================================

    private final ArrayList<ClickableObject> clickableObjects = new ArrayList<>();

    /**
     * Adds a new clickable object to the screen.
     * @param clickableObject the clickable object to be added
     */
    public void AddClickable(ClickableObject clickableObject) {
        clickableObjects.add(clickableObject);
    }

    /**
     * Resests the clickable objects aka clears the content of the clickableObjects field.
     */
    private void ResetClickables() {
        clickableObjects.clear();
    }

    /**
     * Checks wether a click happened on a clickable object.
     * If yes then it executes the objects command.
     * @param mousePosition the position of the mouse on the screen
     */
    public void ClickOnScreen(Vector2 mousePosition) {
        for (ClickableObject clickableObject : clickableObjects) {
            if (clickableObject.IsInside(mousePosition)) {
                clickableObject.Click();
                return;
            }
        }
        gameController.ClearSelectedItem();
    }

// endregion ===========================================================================================================

// region Message panel ================================================================================================

    private final ArrayList<ScreenMessage> screenMessages = new ArrayList<>();

    /**
     * Creates a new {@link ScreenMessage} and adds it the screen messages list.
     * <p>If the number of messages exceeds the {@link GameConstants#MAX_SCREEN_MESSAGES} the
     * oldest message gets deleted.</p>
     * @param timeLeft  the time this message has left
     * @param message   the message
     */
    public void CreateScreenMessage(int timeLeft, String message) {
        if (screenMessages.size() == GameConstants.MAX_SCREEN_MESSAGES) {
            screenMessages.remove(0);
        }
        screenMessages.add(new ScreenMessage(timeLeft, message));
    }

    /**
     * Creates a new {@link ScreenMessage} and adds it the screen messages list.
     * <p>If the number of messages exceeds the {@link GameConstants#MAX_SCREEN_MESSAGES} the
     * oldest message gets deleted.</p>
     * @param timeLeft  the time this message has left
     * @param color     the color of the message
     * @param message   the message
     */
    public void CreateScreenMessage(int timeLeft, Color color, String message) {
        if (screenMessages.size() == GameConstants.MAX_SCREEN_MESSAGES) {
            screenMessages.remove(0);
        }
        screenMessages.add(new ScreenMessage(timeLeft, color, message));
    }

    /**
     * Updates the screen messages.
     */
    public void UpdateScreenMessages() {
        ArrayList<ScreenMessage> toDelete = new ArrayList<>();

        for (ScreenMessage message : screenMessages) {
            message.Update();
            if (message.GetTimeLeft() == 0) toDelete.add(message);
        }

        for (ScreenMessage del : toDelete) {
            screenMessages.remove(del);
        }
    }

// endregion ===========================================================================================================

// region Gas ==========================================================================================================

    private final ArrayList<GasCloud> gasClouds = new ArrayList<>();
    private final ArrayList<GasCloud> toBeKilledGasClouds = new ArrayList<>();

    public void NewGasCloud(int timeLeft, Vector2 position, float scale, int direction) {
        synchronized (gasClouds) {

            gasClouds.add(new GasCloud(timeLeft, position, scale, direction));
        }
    }

    public void UpdateGasClouds() {
        if (gasClouds.isEmpty()) return;

        Student active = game.GetRoundManager().GetActiveStudent();
        if (active == null) return;

        synchronized (gasClouds) {
            if (!active.GetCurrentRoom().IsGassed()) {
                gasClouds.clear();
                return;
            }

            for (GasCloud gasCloud : gasClouds) {
                gasCloud.Update();
            }

            for (GasCloud gasCloud : toBeKilledGasClouds) {
                gasClouds.remove(gasCloud);
            }

            toBeKilledGasClouds.clear();
        }
    }

    public void KillGasCloud(GasCloud gasCloud) {
        toBeKilledGasClouds.add(gasCloud);
    }

// endregion ===========================================================================================================

    private ArrayList<String> GetItemInfo(Item item) {
        final ArrayList<String> info = new ArrayList<>();

        if (item instanceof AirFreshener) {
            info.add("Air Freshener");
            info.add(" ¤ usable");
        }
        else if (item instanceof Beer) {
            info.add("Holy Beer");
            info.add(" ¤ usable");
        }
        else if (item instanceof Cheese) {
            info.add("Camambert");
            info.add(item.GetActivation() ? " ¤ activated" : " ¤ deactivated");
            info.add(" ¤ usable");
        }
        else if (item instanceof FFP2Mask ffp2Mask) {
            info.add("FFP2 Mask");
            info.add(" ¤ " + ffp2Mask.GetRemainingUsages() + " uses left");
        }
        else if (item instanceof SlipStick) {
            info.add("Slipstick");
            info.add(" ¤ take it to the");
            info.add("   winning room");
        }
        else if (item instanceof Transistor transistor) {
            info.add("Transistor");
            info.add(transistor.GetPairReadyToTeleport() ? " ¤ use to teleport" : " ¤ use to setup");
            info.add(transistor.GetActivation() ? " ¤ activated" : " ¤ deactivated");
            info.add(transistor.GetPair() == null ? " ¤ not paired" : " ¤ paired");

        }
        else if (item instanceof TVSZ) {
            info.add("TVSZ written onto");
            info.add("bat leather");
            info.add(" ¤ " + ((TVSZ) item).GetRemainingPages() + " pages left");
        }
        else if (item instanceof WetCloth wetCloth) {
            info.add("Wet Cloth");
            info.add(item.GetActivation() ? " ¤ activated" : " ¤ deactivated");
            info.add(" ¤ can be used for");
            info.add("   " + wetCloth.GetRoundsLeft() + " rounds");
        }
        else if (item instanceof Fake fake) {
            return GetItemInfo(fake.GetFakedItem());
        }
        return info;
    }
}
