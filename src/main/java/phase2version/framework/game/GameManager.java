package phase2version.framework.game;

import phase2version.framework.gui.GraphicalUserInterface;
import phase2version.framework.utility.Console;

/**
 * Defines a manager to manage the state of the game.
 **/
public interface GameManager {
    /**
     * Attaches a console to the game, allowing the game to print statements whenever
     * its state has changed significantly.
     * @param c The console that should be attached to the game.
     **/
    void attach(Console c);

    /**
     * Attaches a GUI to the game, allowing the game to update the GUI whenever
     * its state has changed significantly.
     * @param gui The graphical user interface that should be attached to the game.
     **/
    void attach(GraphicalUserInterface gui);

    /**
     * Changes the active player to the next player in the queue waiting for his/her turn to play.
     **/
    void nextPlayer();
}
