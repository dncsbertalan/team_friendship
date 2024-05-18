package Graphics;

import Constants.GameConstants;
import Entities.Entity;
import Entities.Student;
import Graphics.Listeners.GameWindowMouseWheelListener;
import Graphics.Utils.Clickable.ClickableObject;
import Graphics.Listeners.GameWindowMouseListener;
import Graphics.Utils.Clickable.RoomObject;
import Graphics.Utils.HelperMethods;
import Graphics.Utils.MenuButton;
import Graphics.Utils.ScreenMessage;
import Items.*;
import Labyrinth.Room;
import Graphics.Utils.Vector2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

        DrawCurrentRound(graphics2D);
        DrawEntityInfo(graphics2D);
        DrawInventory(graphics2D);
        DrawRoom(graphics2D);
        DrawScreenMessages(graphics2D);
        DrawItemInformationTable(graphics2D);
    }

// region Getters/Setters ==============================================================================================

    public Vector2 GetMousePosition() {
        return mousePosition;
    }

//endregion

// region DRAW =========================================================================================================

    /**
     * Draws the item to the screen.
     * @param graphics2D    graphics2D instance
     * @param item          the item to be drawn
     * @param center        the center position of the item
     * @param scale         the scale of the item's image
     */
    public static void DrawItem(final Graphics2D graphics2D, final Item item, final Vector2 center, final float scale) {

        BufferedImage image;

        if (item instanceof AirFreshener) {
            //image = imageManager.resizeImage(GameConstants.IMAGE_AIR_FRESHENER, scale);
            image = imageManager.GetImage(GameConstants.IMAGE_AIR_FRESHENER);
        }
        else if (item instanceof Beer) {
            //image = imageManager.resizeImage(GameConstants.IMAGE_BEER, scale);
            image = imageManager.GetImage(GameConstants.IMAGE_BEER);
        }
        else if (item instanceof Cheese) {
            //image = imageManager.resizeImage(GameConstants.IMAGE_CHEESE, scale);
            image = imageManager.GetImage(GameConstants.IMAGE_CHEESE);
        }
        else if (item instanceof FFP2Mask) {
            //image = imageManager.resizeImage(GameConstants.IMAGE_FFP2_MASK, scale);
            image = imageManager.GetImage(GameConstants.IMAGE_FFP2_MASK);
        }
        else if (item instanceof SlipStick) {
            //image = imageManager.resizeImage(GameConstants.IMAGE_SLIPSTICK, scale);
            image = imageManager.GetImage(GameConstants.IMAGE_SLIPSTICK);
        }
        else if (item instanceof Transistor) {
            //image = imageManager.resizeImage(GameConstants.IMAGE_TRANSISTOR, scale);
            image = imageManager.GetImage(GameConstants.IMAGE_TRANSISTOR);
        }
        else if (item instanceof TVSZ) {
            //image = imageManager.resizeImage(GameConstants.IMAGE_TVSZ, scale);
            image = imageManager.GetImage(GameConstants.IMAGE_TVSZ);
        }
        else if (item instanceof WetCloth) {
            //image = imageManager.resizeImage(GameConstants.IMAGE_WET_CLOTH, scale);
            image = imageManager.GetImage(GameConstants.IMAGE_WET_CLOTH);
        }
        else {  // FAKE ITEM
            //image = imageManager.resizeImage("temp", scale);
            image = imageManager.GetImage("temp");
        }

        graphics2D.drawImage(image, center.x - image.getWidth() / 2, center.y - image.getHeight() / 2, null);
    }

    /**
     * Draws the currently active student's room.
     * @param graphics2D graphics instance
     */
    private void DrawRoom(Graphics2D graphics2D) {

        Student active = game.GetRoundManager().GetActiveStudent();
        if (active == null) return;

        Room curRoom = active.GetCurrentRoom();
        boolean isFirstMove = gameController.isFirstMove;
        RoomObject roomObject = new RoomObject(this, Vector2.Mult(windowSize, 0.5f), curRoom, false);
        roomObject.Draw(graphics2D);

        // Draw the neighbours
        float angle = 360f / Math.max(curRoom.GetNeighbours().size(), GameConstants.ROOM_MIN_SIDES);
        int dist = (int) ((float) GameConstants.ROOM_SIZE * Math.cos(Math.toRadians(angle / 2.0)));
        Vector2 neighbourDistanceFromCenter = new Vector2(0, -dist - 300);
        neighbourDistanceFromCenter.RotateBy(angle / 2f);
        int drawnRoom = 0;
        Vector2 centerPos = Vector2.Mult(windowSize, 0.5f);

        for (Room neighbour : curRoom.GetNeighbours()) {
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(neighbourDistanceFromCenter,drawnRoom++ * angle));
            RoomObject ro = new RoomObject(this, pos, neighbour, true);
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
        final int invRectSize = 50;
        final int selectedRectSize = 54;
        final int arc = 10;

        for (int i = 0; i < 5; i++) {
            Vector2 pos = GameConstants.GamePanel_INVENTORY_POS();
            pos.AddX(i * (50 + 10));
            if (i == selectedSlot) {
                graphics2D.setColor(Color.yellow);
                graphics2D.fillRoundRect(pos.x - 2, pos.y - 2, selectedRectSize, selectedRectSize, arc, arc);
            }
            graphics2D.setColor(Color.black);
            graphics2D.fillRoundRect(pos.x, pos.y, invRectSize, invRectSize, arc, arc);

            if(i < numberOfStudentsItems){
                Item item = active.GetInventory().get(i);
                DrawItem(graphics2D, item, Vector2.Add(pos, new Vector2(invRectSize / 2, invRectSize / 2)), 1f);
                /*graphics2D.setColor(new Color(205, 48, 245));

                if(i == selectedSlot){
                    graphics2D.fillRect(pos.x + selectedRectSize/4, pos.y + selectedRectSize/4, selectedRectSize / 2, selectedRectSize / 2);

                } else {
                    graphics2D.fillRect(pos.x + (int)invRectSize/3, pos.y + (int)invRectSize/3, (int) invRectSize / 3, (int) invRectSize / 3);

                }*/
            }

        }
    }

    /**
     * Draws the current round to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawCurrentRound(Graphics2D graphics2D) {
        // TODO TEMPORARY
        //Font font = new Font("Times New Roman", Font.BOLD, 25);
        Font font = new Font("Courier New", Font.BOLD, 25);
        graphics2D.setFont(font);

        Vector2 pos = Vector2.Mult(windowSize, 0.9f, 0.05f);
        graphics2D.drawString(GameConstants.GamePanel_ROUND_TEXT + game.GetRoundManager().GetCurrentRound()
        , pos.x, pos.y);
    }

    /**
     * Draws the entities to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawEntityInfo(Graphics2D graphics2D) {
        Vector2 pos = new Vector2(GameConstants.GamePanel_ENTITY_INFO_POS().x, (int) (windowSize.y * 0.15f));
        int spaceBetweenLines = 20;

        Font font = new Font("Times New Roman", Font.BOLD, 20);
        graphics2D.setFont(font);

        ArrayList<Entity> entities = new ArrayList<>(game.GetStudents());
        for (Entity entity : entities) {

            Student entityCastAsStudent = (Student) entity;

            pos.AddY(spaceBetweenLines);
            String textName = entityCastAsStudent.GetName();
            String textStatus;

            if(entityCastAsStudent.IsParalysed()){
                textStatus = " : paralysed";
            } else if(entityCastAsStudent.IsDead()){
                textStatus = " : R.I.P.";
            } else {
                textStatus = " : alive and well";
            }

            String text = textName + textStatus;

            graphics2D.drawString(text, pos.x, pos.y);
            if(entityCastAsStudent == game.GetRoundManager().GetActiveStudent()){
                graphics2D.drawLine(pos.x, pos.y+2, pos.x + getFontMetrics(getFont()).stringWidth(textName), pos.y + 2);
            }
        }
    }

    /**
     * Draws the information about items to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawItemInformationTable (Graphics2D graphics2D){
        int infoPanelWidth = 300;
        int infoPanelHeight = 550;
        Vector2 pos = new Vector2(windowSize.x - 320, windowSize.y - 850);

        graphics2D.setColor(GameConstants.GamePanel_ITEM_INFORMATION_BORDER_COLOR);
        graphics2D.fillRoundRect(pos.x - 5, pos.y - 5, infoPanelWidth + 10, infoPanelHeight + 10, 5, 5);
        graphics2D.setColor(GameConstants.GamePanel_ITEM_INFORMATION_FILL_COLOR);
        graphics2D.fillRoundRect(pos.x, pos.y, infoPanelWidth, infoPanelHeight, 5, 5);
        graphics2D.setFont(new Font("Courier New", Font.BOLD, 20));
        graphics2D.setColor(Color.black);

        graphics2D.drawString(GameConstants.GamePanel_INVENTORY_ITEM_TEXT_1, windowSize.x - 314, windowSize.y - 830);
        graphics2D.drawLine(windowSize.x - 314, windowSize.y - 830, windowSize.x - 314 + 220, windowSize.y - 830);
        int textHeight = (int) -graphics2D.getFontMetrics().getStringBounds("GetHeight!<3", graphics2D).getY();
        graphics2D.drawString(GameConstants.GamePanel_INVENTORY_ITEM_TEXT_2, windowSize.x - 314, (int) (windowSize.y - 830 + 1 * textHeight));
        graphics2D.drawLine(windowSize.x - 314, (windowSize.y - 830 + 1 * textHeight), windowSize.x - 314 + 115, (int)(windowSize.y - 830 + 1 * textHeight));

        graphics2D.drawString(GameConstants.GamePanel_ROOM_ITEM_TEXT, windowSize.x - 314, windowSize.y - 600);
        graphics2D.drawLine(windowSize.x - 314, windowSize.y - 600, windowSize.x - 314 + 280, windowSize.y - 600);

        DrawItemInformation1(graphics2D, textHeight);
        DrawItemInformation2(graphics2D, textHeight);
    }

    private void DrawItemInformation1(Graphics2D graphics2D, int textHeight){

        Student activeStudent = game.GetRoundManager().GetActiveStudent();
        Item selectedItem = activeStudent.GetSelectedItem();
        String name = null;
        String usable = null;
        String activateable = null;
        String remainingUsages = null;

        if(activeStudent != null && selectedItem != null){
            if(selectedItem.getClass() == AirFreshener.class){
                name = "air freshener";
                usable = "-useable";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 830 + 2 * textHeight);
                graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
            } else if(selectedItem.getClass() == Beer.class){
                name = "beer";
                usable = "-useable";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 830 + 2 * textHeight);
                graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
            } else if(selectedItem.getClass() == Cheese.class){
                name = "stinky ass cheese";
                usable = "-useable";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 830 + 2 * textHeight);
                graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
            } else if(selectedItem.getClass() == FFP2Mask.class){
                name = "ffp2 mask";
                remainingUsages = "-" + ((FFP2Mask) selectedItem).GetRemainingUsages() + " active rounds left";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 830 + 2 * textHeight);
                graphics2D.drawString(remainingUsages, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
            } else if(selectedItem.getClass() == SlipStick.class){
                name = "slipstick";
                usable = "-take to winning room";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 830 + 2 * textHeight);
                graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
            } else if(selectedItem.getClass() == Transistor.class){
                name = "transistor";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 830 + 2 * textHeight);
                if(((Transistor) selectedItem).GetPair() == null){
                    activateable = "-not paired";
                    graphics2D.drawString(activateable, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
                } else if(((Transistor) selectedItem).GetPairReadyToTeleport() == false){
                    activateable = "-paired";
                    usable = "-not activated";
                    graphics2D.drawString(activateable, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
                    graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 830 + 4 * textHeight);
                } else{
                    activateable = "-paired";
                    usable = "-activated";
                    graphics2D.drawString(activateable, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
                    graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 830 + 4 * textHeight);
                }
            } else if(selectedItem.getClass() == TVSZ.class){
                name = "tvsz";
                remainingUsages = "-" + ((TVSZ) selectedItem).GetRemainingPages() + " pages left";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 830 + 2 * textHeight);
                graphics2D.drawString(remainingUsages, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
            } else if(selectedItem.getClass() == WetCloth.class){
                name = "wet cloth";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 830 + 2 * textHeight);
                if(((WetCloth) selectedItem).GetActivation() == false){
                    activateable = "-not activated";
                    graphics2D.drawString(activateable, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
                } else {
                    activateable = "-activated";
                    graphics2D.drawString(activateable, windowSize.x - 314, windowSize.y - 830 + 3 * textHeight);
                }
            } else if(selectedItem.getClass() == Fake.class){
                name = "szoptad bohoc";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 830 + 2 * textHeight);
            }
        }
    }

    private void DrawItemInformation2(Graphics2D graphics2D, int textHeight){

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
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 600 + 1 * textHeight);
                graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
            } else if(selectedItem.getClass() == Beer.class){
                name = "beer";
                usable = "-useable";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 600 + 1 * textHeight);
                graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
            } else if(selectedItem.getClass() == Cheese.class){
                name = "stinky ass cheese";
                usable = "-useable";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 600 + 1 * textHeight);
                graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
            } else if(selectedItem.getClass() == FFP2Mask.class){
                name = "ffp2 mask";
                remainingUsages = "-" + ((FFP2Mask) selectedItem).GetRemainingUsages() + " active rounds left";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 600 + 1 * textHeight);
                graphics2D.drawString(remainingUsages, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
            } else if(selectedItem.getClass() == SlipStick.class){
                name = "slipstick";
                usable = "-take to winning room";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 600 + 1 * textHeight);
                graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
            } else if(selectedItem.getClass() == Transistor.class){
                name = "transistor";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 600 + 1 * textHeight);
                if(((Transistor) selectedItem).GetPair() == null){
                    activateable = "-not paired";
                    graphics2D.drawString(activateable, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
                } else if(((Transistor) selectedItem).GetPairReadyToTeleport() == false){
                    activateable = "-paired";
                    usable = "-not activated";
                    graphics2D.drawString(activateable, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
                    graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 600 + 3 * textHeight);
                } else{
                    activateable = "-paired";
                    usable = "-activated";
                    graphics2D.drawString(activateable, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
                    graphics2D.drawString(usable, windowSize.x - 314, windowSize.y - 600 + 3 * textHeight);
                }
            } else if(selectedItem.getClass() == TVSZ.class){
                name = "tvsz";
                remainingUsages = "-" + ((TVSZ) selectedItem).GetRemainingPages() + " pages left";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
                graphics2D.drawString(remainingUsages, windowSize.x - 314, windowSize.y - 600 + 3 * textHeight);
            } else if(selectedItem.getClass() == WetCloth.class){
                name = "wet cloth";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 600 + 1 * textHeight);
                if(((WetCloth) selectedItem).GetActivation() == false){
                    activateable = "-not activated";
                    graphics2D.drawString(activateable, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
                } else {
                    activateable = "-activated";
                    graphics2D.drawString(activateable, windowSize.x - 314, windowSize.y - 600 + 2 * textHeight);
                }
            } else if(selectedItem.getClass() == Fake.class){
                name = "szoptad bohoc";
                graphics2D.drawString(name, windowSize.x - 314, windowSize.y - 600 + 1 * textHeight);
            }
        }
    }

    /**
     * Draws the screen messages to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawScreenMessages(Graphics2D graphics2D) {
        if (screenMessages.isEmpty()) return;

        // Calculates the height of the texts
        int textsHeight = (screenMessages.size() - 1) * GameConstants.SCREEN_MESSAGE_DISTANCE;
        int textHeight = (int) -graphics2D.getFontMetrics().getStringBounds("GetHeight!<3", graphics2D).getY();
        graphics2D.setFont(GameConstants.SCREEN_MESSAGE_FONT);

        for (ScreenMessage sc : screenMessages) {
            textsHeight += textHeight;
        }

        Vector2 posOnScreen = new Vector2(GameConstants.GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT().x,
                windowSize.y - GameConstants.GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT().y - textsHeight);

        for (ScreenMessage sc : screenMessages) {
            int alpha = (int) HelperMethods.Remap(sc.GetTimeLeft(), 60, 0, 255, 0, true);
            graphics2D.setColor(new Color(sc.GetColor().getRed(), sc.GetColor().getGreen(), sc.GetColor().getBlue(), alpha));
            graphics2D.drawString(sc.GetMessage(), posOnScreen.x, posOnScreen.y);
            posOnScreen.AddY(textHeight + GameConstants.SCREEN_MESSAGE_DISTANCE);
        }
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

// endregion

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

// endregion

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

// endregion
}
