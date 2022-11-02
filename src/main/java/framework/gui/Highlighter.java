package framework.gui;

import framework.game.Location;

/**
 * Defines a location highlighter.
 **/
public interface Highlighter {
    /**
     * Highlights all marked locations, making it visible to the user.
     **/
    void highlight();

    /**
     * Marks a location as "highlight".
     * @param l The location that should be marked.
     **/
    void mark(Location l);

    /**
     * Clears the "highlight" mark from a location.
     * @param l The location that should be restored to its default color.
     **/
    void remove(Location l);
}
