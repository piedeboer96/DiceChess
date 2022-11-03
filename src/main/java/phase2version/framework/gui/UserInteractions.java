package phase2version.framework.gui;

import phase2version.framework.game.Player;

/**
 * Defines user interactions that are run in the background by a graphical user interface.
 **/
public interface UserInteractions {
    /**
     * Enables user interactions on a controller.
     * @param controller The number of the controller that should be enabled.
     *                   This should be either 0 [first controller] or 1 [second controller].
     * @exception IllegalArgumentException Thrown when controller number is neither 0 nor 1.
     **/
    void enable(int controller);

    /**
     * Obtains the interaction controller in the form of a player.
     * @param controller The number of the controller that should be obtained.
     *                   This should be either 0 [first controller] or 1 [second controller].
     * @exception IllegalArgumentException Thrown when controller number is neither 0 nor 1.
     **/
    Player getPlayer(int controller);
}
