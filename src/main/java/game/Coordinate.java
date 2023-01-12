package game;

/**
 * Defines a coordinate of a 2-dimensional coordinate system that is represented by an 8 x 8 matrix.
 **/
public abstract class Coordinate {
    private final int X, Y;

    /**
     * @param x An integer between 0 and 7 the coordinate should use as its x-value.
     * @param y An integer between 0 and 7 the coordinate should use as its y-value.
     **/
    protected Coordinate(int x, int y) {
        X = x;
        Y = y;
    }

    /**
     * Obtains the x-value of the coordinate.
     *
     * @return An integer between 0 and 7.
     **/
    public int getX() {
        return X;
    }

    /**
     * Obtains the y-value of the coordinate.
     *
     * @return An integer between 0 and 7.
     **/
    public int getY() {
        return Y;
    }
}
