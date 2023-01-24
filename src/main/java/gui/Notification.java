package gui;

import game.GameState;

import javax.swing.*;
import java.awt.*;

/**
 * Defines a notification (pop-up).
 **/
public final class Notification extends Window {
    /**
     * @param state The state of the game.
     **/
    public Notification(GameState state) {
        super("EOG Notification", new Dimension(360, 90));
        JLabel message;
        switch (state) {
            case DRAW -> message = new JLabel("Game ended in a draw.");
            case BLACK_WON ->  message = new JLabel("Black has won.");
            case WHITE_WON ->  message = new JLabel("White has won.");
            default -> message = new JLabel("Warning: Terminated before the game ended.");
        }
        message.setHorizontalAlignment(SwingConstants.CENTER);
        WINDOW.add(message);
    }
}
