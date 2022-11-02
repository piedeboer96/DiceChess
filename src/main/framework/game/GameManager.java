package framework.game;

import framework.gui.GraphicalUserInterface;
import framework.utility.Console;

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
     * Detaches everything that has been attached to the game, making it lose any
     * functionality gained from having attached the components.
     **/
    void detach();


    /**
     * Changes the active player to the next player in the queue waiting for his/her turn to play.
     **/
    void nextPlayer();
}
