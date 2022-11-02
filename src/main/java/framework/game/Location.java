package framework.game;

/**
 * Defines the coordinates of a 2-dimensional coordinate system that is represented by an 8 x 8 matrix.
 **/
public interface Location {
    /**
     * Gets the column the location is representing.
     * @return An integer between 0 and 7.
     **/
    int column();

    /**
     * Gets the row the location is representing.
     * @return An integer between 0 and 7.
     **/
    int row();
}
