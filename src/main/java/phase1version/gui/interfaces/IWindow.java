package phase1version.gui.interfaces;

/**
 * Defines the basic functionality each window should have.
 **/
public interface IWindow
{
    /**
     * Determines the mouse location coordinates relative to the content that is getting displayed
     * (i.e., do not bother with the title bar coordinates).
     * @return { x-coordinate, y-coordinate }
     **/
    int[] determineLocationInContentBody(int mouseX, int mouseY);

    /**
     * Closes the window and releases all resources used, making the window unusable.
     **/
    void dispose();

    /**
     *
     **/
    IClickable findInteractionSource(int mouseX, int mouseY);

    /**
     * Refreshes the graphical component of the window to display the latest changes.
     **/
    void refresh();
}
