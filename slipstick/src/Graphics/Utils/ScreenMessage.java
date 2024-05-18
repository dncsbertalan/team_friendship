package Graphics.Utils;

import Constants.GameConstants;

import java.awt.*;

/**
 * Represents a message on the screen.
 */
public class ScreenMessage {

    private int timeLeft;
    private final String message;
    private final Color color;

    public ScreenMessage(int timeLeft, String message) {
        this(timeLeft, GameConstants.SCREEN_MESSAGE_DEFAUL_COLOR, message);
    }

    public ScreenMessage(int timeLeft, Color color, String message) {
        this.timeLeft = timeLeft * GameConstants.DesiredFPS;
        this.color = color;
        this.message = message;
    }

    public String GetMessage() {
        return message;
    }

    public int GetTimeLeft() { return timeLeft; }

    public Color GetColor() { return color; }

    /**
     * Updates the screen message. Decreases the time this screen message has left.
     */
    public void Update() {
        timeLeft--;
    }
}
