package Graphics.Utils;

/**
 * Represents a message on the screen.
 */
public class ScreenMessage {

    private int timeLeft;
    private final String message;

    public ScreenMessage(int timeLeft, String message) {
        this.timeLeft = timeLeft;
        this.message = message;
    }

    public String GetMessage() {
        return message;
    }

    public int GetTimeLeft() { return timeLeft; }

    /**
     * Updates the screen message. Decreases the time this screen message has left.
     */
    public void Update() {
        timeLeft--;
    }
}
