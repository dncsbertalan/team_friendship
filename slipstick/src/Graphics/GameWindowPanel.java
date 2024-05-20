package Graphics;

import Constants.GameConstants;
import Control.GameController;
import Entities.Student;
import Graphics.Listeners.GameWindowMouseWheelListener;
import Graphics.Clickable.ClickableObject;
import Graphics.Listeners.GameWindowMouseListener;
import Graphics.Clickable.RoomObject;
import Graphics.Utils.*;
import Items.*;
import Labyrinth.Room;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static Runnable.Main.*;

public class GameWindowPanel extends JPanel {
    private final GameWindowFrame gameWindowFrame;
    private Vector2 mousePosition;
    private final Vector2 windowSize;

    public GameWindowPanel(GameWindowFrame frame) {

        gameWindowFrame = frame;
        windowSize = new Vector2(frame.getWidth(), frame.getHeight());

        this.setBackground(Color.lightGray);
        this.setPreferredSize(new Dimension(GameConstants.GamePanel_WIDTH, GameConstants.GamePanel_HEIGHT));
        /*int width = GameConstants.GamePanel_WIDTH;
        int height = GameConstants.GamePanel_HEIGHT;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        int centerX = width / 2;
        int centerY = height / 2;
        int circleRadius = 100;
        g.setColor(Color.yellow);
        g.fillOval(centerX - circleRadius, centerY - circleRadius, 2 * circleRadius, 2 * circleRadius);
        g.dispose();
        File outputFile = new File("TransparentCircleImage.png");
        try {
            ImageIO.write(image, "png", outputFile);
            System.out.println("A kép sikeresen létrejött: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba a kép létrehozása közben: " + e.getMessage());
        }*/

        // Panel init
        this.setDoubleBuffered(true);

        GameWindowMouseListener mouseListener = new GameWindowMouseListener(this);
        this.addMouseListener(mouseListener);
        GameWindowMouseWheelListener mouseWheelListener = new GameWindowMouseWheelListener();
        this.addMouseWheelListener(mouseWheelListener);
        // Add the KeyListener
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Handle keyPressed if needed
                gameController.HandleInput(game.GetRoundManager().GetActiveStudent(), e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        this.addKeyListener(keyListener);

        this.setLayout(null);
        MenuButton menuButton = new MenuButton(
                GameConstants.GamePanel_EXIT_BUTTON,
                GameConstants.GamePanel_EXIT_BUTTON_BACKGROUND_COLOR,
                GameConstants.GamePanel_EXIT_BUTTON_BORDER_COLOR,
                GameConstants.GamePanel_EXIT_BUTTON_BORDER_THICKNESS);
        this.add(menuButton);
        menuButton.setLayout(null);
        menuButton.setBounds(windowSize.x - 125 - 20, windowSize.y - 75 - 20, 125, 75 );
        menuButton.setFont(GameConstants.MenuPanel1_BUTTON_FONT);
        menuButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                soundManager.playSoundLooped("menu");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        //menuButton.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();
        Graphics2D graphics2D = (Graphics2D) g;

        if (game.IsPreGame()) {     // If the game is not loaded fully
            DrawLoadingScreen(graphics2D);
            return;
        }

        if (game.IsEnded()) {      // If th game ended
            DrawEndScreen(graphics2D);
            return;
        }

        ResetClickables();

        Point point = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(point, this);
        mousePosition = new Vector2(point.x, point.y);

        DrawRoom(graphics2D);
        DrawCurrentRound(graphics2D);
        DrawStudentsInfo(graphics2D);
        DrawInventory(graphics2D);
        DrawScreenMessages(graphics2D);
        DrawItemInformationTable(graphics2D);
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
        Vector2 centerPos = Vector2.Mult(windowSize, 0.5f);

        for (Room neighbour : curRoom.GetNeighbours()) {
            final Vector2 doorPos = Vector2.RotateBy(neighbourDistanceFromCenter,drawnRoom++ * angle);
            Vector2 pos = Vector2.Add(centerPos, doorPos);
            final float rot = (float) Math.toDegrees(Vector2.ToRotation(doorPos));
            RoomObject ro = new RoomObject(this, pos, neighbour,
                    true, rot, neighbour.GetNeighbours().indexOf(curRoom));
            ro.Draw(graphics2D);
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
        final int activeStudentPlusInfo = 1;
        int activeStudentLine = 0;
        for (Student student : game.GetStudents()) {
            String line = student.GetName();

            if(student.IsParalysed()){
                line += " : paralysed";
            } else if(student.IsDead()){
                line += " : R.I.P.";
            } else {
                line += " : alive and well";
            }

            infoLines.add(line);

            if(student == game.GetRoundManager().GetActiveStudent()){
                String plusInfoLine1 = GameConstants.REMAINING_ROUND_TEXT + student.GetRemainingTurns();
                infoLines.add(plusInfoLine1);
                activeStudentLine = game.GetStudents().indexOf(student);
                infoLineFonts.put(plusInfoLine1, activeFont);
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
        final int infoBoxTextsWidth = HelperMethods.GetLongestLineLength(infoLines, g);
        final int infoBoxTextsHeight = (infoLines.size() - 1 - activeStudentPlusInfo) * characterHeight + (1 + activeStudentPlusInfo) * activeCharacterHeight;
        final int infoBoxBackWidth = infoBoxTextsWidth + rightLeftPadding * 2 + backBoxSizeDiff * 2;
        final int infoBoxBackHeight = infoBoxTextsHeight + topPadding + bottomPadding + backBoxSizeDiff * 2;
        final int infoBoxWidth = infoBoxTextsWidth + rightLeftPadding * 2;
        final int infoBoxHeight = infoBoxTextsHeight + topPadding + bottomPadding;
        final int arc = 10;
        final float activeStudentBoxOutlineWidth = 2f;
        final int activeStudentBoxPosY = activeCharacterHeight * activeStudentLine + activeCharacterHeight / 4;
        final int activeStudentBoxHeight = activeCharacterHeight * (1 + activeStudentPlusInfo);
        Vector2 pos = new Vector2(GameConstants.GamePanel_ENTITY_INFO_POS().x, windowSize.y / 2 - infoBoxBackHeight / 2);

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
        Graphics2D g = (Graphics2D) graphics2D.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int infoPanelWidth = (int) (windowSize.x * 0.15);
        int infoPanelHeight = (int) (windowSize.x * 0.25);

        int x_coordinate_16_percent = (int) (windowSize.x * 0.165);
        int y_coordinate_79_percent = (int) (windowSize.y * 0.79);
        int y_coordinate_60_percent = (int) (windowSize.y * 0.6);
        int y_coordinate_77_percent = (int) (windowSize.y * 0.77);

        int x_calculated_coord = windowSize.x - x_coordinate_16_percent;
        int y_calculated_coord = windowSize.y - y_coordinate_79_percent;

        int x_first_text_coord = windowSize.x - x_coordinate_16_percent;;
        int y_first_text_coord = windowSize.y - y_coordinate_77_percent;;

        int x_second_text_coord = windowSize.x - x_coordinate_16_percent;
        int y_second_text_coord = windowSize.y - y_coordinate_60_percent;

        Vector2 pos = new Vector2(x_calculated_coord, y_calculated_coord);

        g.setColor(GameConstants.GamePanel_ITEM_INFORMATION_BORDER_COLOR);
        g.fillRoundRect(pos.x - 5, pos.y - 5, infoPanelWidth + 10, infoPanelHeight + 10, 5, 5);
        g.setColor(GameConstants.GamePanel_ITEM_INFORMATION_FILL_COLOR);
        g.fillRoundRect(pos.x, pos.y, infoPanelWidth, infoPanelHeight, 5, 5);
        g.setFont(new Font("Courier New", Font.BOLD, 22));
        g.setColor(Color.black);


        g.drawString(GameConstants.GamePanel_INVENTORY_ITEM_TEXT_1, x_first_text_coord, y_first_text_coord);

        int textHeight = (int) -g.getFontMetrics().getStringBounds("GetHeight!<3", g).getY();

        g.drawString(GameConstants.GamePanel_INVENTORY_ITEM_TEXT_2, x_first_text_coord, (y_first_text_coord + 1 * textHeight));

        g.drawString(GameConstants.GamePanel_ROOM_ITEM_TEXT_1, x_second_text_coord, y_second_text_coord);

        g.drawString(GameConstants.GamePanel_ROOM_ITEM_TEXT_2, x_second_text_coord, (y_second_text_coord + 1 * textHeight));

        g.setFont(new Font("Courier New", Font.BOLD, 17));

        DrawItemInformation1(g, textHeight, x_first_text_coord, y_first_text_coord);
        DrawItemInformation2(g, textHeight, x_second_text_coord, y_second_text_coord);

        g.dispose();
    }

    private void DrawItemInformation1(Graphics2D graphics2D, int textHeight, int x_first_text_coord, int y_first_text_coord){

        Student activeStudent = game.GetRoundManager().GetActiveStudent();
        if (activeStudent == null) return;

        Item selectedItem = activeStudent.GetSelectedItem();
        String name = null;
        String usable = null;
        String activateable = null;
        String remainingUsages = null;

        if (selectedItem != null){
            if(selectedItem.getClass() == AirFreshener.class){
                name = "air freshener";
                usable = "-useable";
                graphics2D.drawString(name, x_first_text_coord, y_first_text_coord + 2 * textHeight);
                graphics2D.drawString(usable, x_first_text_coord, y_first_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == Beer.class){
                name = "beer";
                usable = "-useable";
                graphics2D.drawString(name, x_first_text_coord, y_first_text_coord + 2 * textHeight);
                graphics2D.drawString(usable, x_first_text_coord, y_first_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == Cheese.class){
                name = "stinky ass cheese";
                usable = "-useable";
                graphics2D.drawString(name, x_first_text_coord, y_first_text_coord + 2 * textHeight);
                graphics2D.drawString(usable, x_first_text_coord, y_first_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == FFP2Mask.class){
                name = "ffp2 mask";
                remainingUsages = "-" + ((FFP2Mask) selectedItem).GetRemainingUsages() + " active rounds left";
                graphics2D.drawString(name, x_first_text_coord, y_first_text_coord + 2 * textHeight);
                graphics2D.drawString(remainingUsages, x_first_text_coord, y_first_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == SlipStick.class){
                name = "slipstick";
                usable = "-take to winning room";
                graphics2D.drawString(name, x_first_text_coord, y_first_text_coord + 2 * textHeight);
                graphics2D.drawString(usable, x_first_text_coord, y_first_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == Transistor.class){
                name = "transistor";
                graphics2D.drawString(name, x_first_text_coord, y_first_text_coord + 2 * textHeight);
                if(((Transistor) selectedItem).GetPair() == null){
                    activateable = "-not paired";
                    graphics2D.drawString(activateable, x_first_text_coord, y_first_text_coord + 3 * textHeight);
                } else if(((Transistor) selectedItem).GetPairReadyToTeleport() == false){
                    activateable = "-paired";
                    usable = "-not activated";
                    graphics2D.drawString(activateable, x_first_text_coord, y_first_text_coord + 3 * textHeight);
                    graphics2D.drawString(usable, x_first_text_coord, y_first_text_coord + 4 * textHeight);
                } else{
                    activateable = "-paired";
                    usable = "-activated";
                    graphics2D.drawString(activateable, x_first_text_coord, y_first_text_coord + 3 * textHeight);
                    graphics2D.drawString(usable, x_first_text_coord, y_first_text_coord + 4 * textHeight);
                }
            } else if(selectedItem.getClass() == TVSZ.class){
                name = "tvsz";
                remainingUsages = "-" + ((TVSZ) selectedItem).GetRemainingPages() + " pages left";
                graphics2D.drawString(name, x_first_text_coord, y_first_text_coord + 2 * textHeight);
                graphics2D.drawString(remainingUsages, x_first_text_coord, y_first_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == WetCloth.class){
                name = "wet cloth";
                graphics2D.drawString(name, x_first_text_coord, y_first_text_coord + 2 * textHeight);
                if(((WetCloth) selectedItem).GetActivation() == false){
                    activateable = "-not activated";
                    graphics2D.drawString(activateable, x_first_text_coord, y_first_text_coord + 3 * textHeight);
                } else {
                    activateable = "-activated";
                    graphics2D.drawString(activateable, x_first_text_coord, y_first_text_coord + 3 * textHeight);
                }
            } else if(selectedItem.getClass() == Fake.class){
                name = "szoptad bohoc";
                graphics2D.drawString(name, x_first_text_coord, y_first_text_coord + 2 * textHeight);
            }
        }
    }

    private void DrawItemInformation2(Graphics2D graphics2D, int textHeight, int x_second_text_coord, int y_second_text_coord){

        //Student activeStudent = game.GetRoundManager().GetActiveStudent(); //ig ez nem fog kelleni de meglatjuk
        Item selectedItem = gameController.GetSelectedItem();
        String name = null;
        String usable = null;
        String activateable = null;
        String remainingUsages = null;

        //if(activeStudent != null && selectedItem != null){
        if(selectedItem != null){
            if(selectedItem.getClass() == AirFreshener.class){
                name = "air freshener";
                usable = "-useable";
                graphics2D.drawString(name, x_second_text_coord, y_second_text_coord + 2 * textHeight);
                graphics2D.drawString(usable, x_second_text_coord, y_second_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == Beer.class){
                name = "beer";
                usable = "-useable";
                graphics2D.drawString(name, x_second_text_coord, y_second_text_coord+ 2 * textHeight);
                graphics2D.drawString(usable, x_second_text_coord, y_second_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == Cheese.class){
                name = "stinky ass cheese";
                usable = "-useable";
                graphics2D.drawString(name, x_second_text_coord, y_second_text_coord + 2 * textHeight);
                graphics2D.drawString(usable, x_second_text_coord, y_second_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == FFP2Mask.class){
                name = "ffp2 mask";
                remainingUsages = "-" + ((FFP2Mask) selectedItem).GetRemainingUsages() + " active rounds left";
                graphics2D.drawString(name, x_second_text_coord, y_second_text_coord + 2 * textHeight);
                graphics2D.drawString(remainingUsages, x_second_text_coord, y_second_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == SlipStick.class){
                name = "slipstick";
                usable = "-take to winning room";
                graphics2D.drawString(name, x_second_text_coord, y_second_text_coord + 2 * textHeight);
                graphics2D.drawString(usable, x_second_text_coord, y_second_text_coord + 3 * textHeight);
            } else if(selectedItem.getClass() == Transistor.class){
                name = "transistor";
                graphics2D.drawString(name, x_second_text_coord, y_second_text_coord + 2 * textHeight);
                if(((Transistor) selectedItem).GetPair() == null){
                    activateable = "-not paired";
                    graphics2D.drawString(activateable, x_second_text_coord, y_second_text_coord + 3 * textHeight);
                } else if(((Transistor) selectedItem).GetPairReadyToTeleport() == false){
                    activateable = "-paired";
                    usable = "-not activated";
                    graphics2D.drawString(activateable, x_second_text_coord, y_second_text_coord + 3 * textHeight);
                    graphics2D.drawString(usable, x_second_text_coord, y_second_text_coord + 4 * textHeight);
                } else{
                    activateable = "-paired";
                    usable = "-activated";
                    graphics2D.drawString(activateable, x_second_text_coord, y_second_text_coord + 3 * textHeight);
                    graphics2D.drawString(usable, x_second_text_coord, y_second_text_coord + 4 * textHeight);
                }
            } else if(selectedItem.getClass() == TVSZ.class){
                name = "tvsz";
                remainingUsages = "-" + ((TVSZ) selectedItem).GetRemainingPages() + " pages left";
                graphics2D.drawString(name, x_second_text_coord, y_second_text_coord + 3 * textHeight);
                graphics2D.drawString(remainingUsages, x_second_text_coord, y_second_text_coord + 4 * textHeight);
            } else if(selectedItem.getClass() == WetCloth.class){
                name = "wet cloth";
                graphics2D.drawString(name, x_second_text_coord, y_second_text_coord + 2 * textHeight);
                if(((WetCloth) selectedItem).GetActivation() == false){
                    activateable = "-not activated";
                    graphics2D.drawString(activateable, x_second_text_coord, y_second_text_coord + 3 * textHeight);
                } else {
                    activateable = "-activated";
                    graphics2D.drawString(activateable, x_second_text_coord, y_second_text_coord + 3 * textHeight);
                }
            } else if(selectedItem.getClass() == Fake.class){
                name = "szoptad bohoc";
                graphics2D.drawString(name, x_second_text_coord, y_second_text_coord + 2 * textHeight);
            }
        }
    }

    /**
     * Draws the screen messages to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawScreenMessages(Graphics2D graphics2D) {
        if (screenMessages.isEmpty()) return;

        Graphics2D g = (Graphics2D) graphics2D.create();

        // Calculates the height of the texts
        int textsHeight = (screenMessages.size() - 1) * GameConstants.SCREEN_MESSAGE_DISTANCE;
        int textHeight = (int) -g.getFontMetrics().getStringBounds("GetHeight!<3", g).getY();
        g.setFont(GameConstants.SCREEN_MESSAGE_FONT);

        for (ScreenMessage sc : screenMessages) {
            textsHeight += textHeight;
        }

        Vector2 posOnScreen = new Vector2(GameConstants.GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT().x,
                windowSize.y - GameConstants.GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT().y - textsHeight);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (ScreenMessage sc : screenMessages) {
            int alpha = (int) HelperMethods.Remap(sc.GetTimeLeft(), 60, 0, 255, 0, true);
            g.setColor(new Color(sc.GetColor().getRed(), sc.GetColor().getGreen(), sc.GetColor().getBlue(), alpha));
            g.drawString(sc.GetMessage(), posOnScreen.x, posOnScreen.y);
            posOnScreen.AddY(textHeight + GameConstants.SCREEN_MESSAGE_DISTANCE);
        }

        g.dispose();
    }

    /**
     * Draws the loading screen.
     * @param graphics2D graphics instance
     */
    private void DrawLoadingScreen(Graphics2D graphics2D) {
        // Load the image
        BufferedImage image;
        try {
            File file = new File(GameConstants.MenuPanel1_LOGO_FILEPATH);
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Draw the logo image
        Vector2 center = Vector2.Mult(windowSize, 0.5f);
        graphics2D.drawImage(image, center.x - (image.getWidth() / 2), center.y - (image.getHeight() / 2), null);
    }

    /**
     * Draws the end screen.
     * @param graphics2D graphics instance
     */
    private void DrawEndScreen(Graphics2D graphics2D) {
        // TODO: reposion exit button
        if (game.GetWin()) {    // If the game was won

            return;
        }
        // If the game was lost
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
}
