package framework.gui;

/**
 * Defines the graphical part of user interactions.
 **/
public interface GraphicalUserInterface extends Projector {
    /**
     * Updates the gui to display the latest changes that have been made.
     **/
    void updateBoard();

    /**
     * Updates the gui to display the latest die roll result.
     * @param team The team that has rolled the die.
     * @param roll The result of the die roll.
     * @exception IllegalArgumentException Thrown when team is neither 0 nor 1.
     *                                     -or- roll is not between 1 and 6.
     **/
    void updateDie(int team, int roll);
}
