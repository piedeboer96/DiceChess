package phase1version.gui.interfaces;

import java.awt.Graphics;

/**
 * Defines a tool for (back-end) objects to visually represent themselves.
 **/
public interface IDrawable
{
    /**
     * Depending on the object type, the object is either drawn fully or partially.
     * A chess piece executing draw(), will draw itself fully on the graphics object,
     * whereas a chessboard will draw one square on the graphics object.
     * @param g The graphics object that will display the (back-end) object.
     * @param x The x-location on the graphics object representing the top-left corner of the drawing that is going
     *          to be drawn.
     * @param y The y-location on the graphics object representing the top-left corner of the drawing that is going
     *          to be drawn.
     * @param width The total width the (back-end) object may use to draw its drawing.
     * @param height The total height the (back-end) object may use to draw its drawing.
     **/
    void draw(Graphics g, int x, int y, int width, int height);
}
