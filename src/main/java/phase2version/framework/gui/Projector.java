package phase2version.framework.gui;

import phase2version.framework.game.Setup;

/**
 * Defines a projector to display unit setups.
 **/
public interface Projector {
    /**
     * Displays a unit setup on the gui, allowing to the user to see the state of the game.
     * @param s The setup that should be projected.
     *          Null can be passed to clear the setup that has been projected.
     **/
    void project(Setup s);
}
