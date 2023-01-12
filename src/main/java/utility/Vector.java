package utility;

import game.Square;

/**
 * Defines a 2-dimensional vector where each coordinate is bounded by the interval [-2, 2].
 **/
public final class Vector {
    private final int X, Y;

    /**
     * @param x An integer between -2 and 2 the vector should use as its x-coordinate.
     * @param y An integer between -2 and 2 the vector should use as its y-coordinate.
     **/
    public Vector(int x, int y) {
        if (x < -2 || x > 2) {
            throw new IllegalArgumentException("Value of the x-coordinate needs to be an integer between -2 and 2.");
        } else if (y < -2 || y > 2) {
            throw new IllegalArgumentException("Value of the y-coordinate needs to be an integer between -2 and 2.");
        }
        X = x;
        Y = y;
    }

    /**
     * Obtains the x-coordinate of the vector.
     *
     * @return An integer between -2 and 2.
     **/
    public int getX() {
        return X;
    }

    /**
     * Obtains the y-coordinate of the vector.
     *
     * @return An integer between -2 and 2.
     **/
    public int getY() {
        return Y;
    }

    /**
     * Translates the (x, y)-coordinate of a square.
     *
     * @param s The square that should be translated by the vector.
     * @return The square representing the translated (x, y)-coordinate. <br>
     *         Null, if at least one value of the translated (x, y)-coordinate falls
     *         outside the (matrix coordinate) interval [0, 7].
     **/
    public Square translate(Square s) {
        int x = s.getX() + X;
        int y = s.getY() - Y; // Performing a subtraction instead of addition since the
                              // matrix entry [0][0] is the top-left corner of the matrix.
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        } else {
            return Square.get(x, y);
        }
    }
}
