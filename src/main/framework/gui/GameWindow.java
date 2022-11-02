package framework.gui;

import framework.utility.Console;
import framework.utility.Engine;

/**
 * Defines the functionality a game window should have.
 **/
public interface GameWindow {
    /**
     * Attaches an engine to the game window, giving the window control over the automated run process.
     * @param e The engine that should be attached.
     * @exception NullPointerException Thrown when null is provided as an engine.
     **/
    void attach(Engine e);

    /**
     * Detaches the engine from the game window, making the window lose control over the automated run process.
     **/
    void detach();

    /**
     * Gets the console the game window uses to display messages.
     * @return The console the game window has.
     **/
    Console getConsole();
}
