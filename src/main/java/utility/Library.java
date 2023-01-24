package utility;

import game.ChessPiece;

/**
 * Defines a vector library that is accessed through chess pieces.
 **/
public final class Library {
    /**
     * Defines moving one rank up.
     */
    public static final Vector NORTH = new Vector(0, 1);

    /**
     * Defines moving one rank down.
     */
    public static final Vector SOUTH = new Vector(0, -1);

    /**
     * Defines moving one file left.
     */
    public static final Vector WEST = new Vector(-1, 0);

    /**
     * Defines moving one file right.
     */
    public static final Vector EAST = new Vector(1, 0);

    /**
     * Defines moving one file left and one rank up.
     */
    public static final Vector NORTH_WEST = new Vector(-1, 1);

    /**
     * Defines moving one file right and one rank down.
     */
    public static final Vector SOUTH_EAST = new Vector(1, -1);

    /**
     * Defines moving one file right and one rank up.
     */
    public static final Vector NORTH_EAST = new Vector(1, 1);

    /**
     * Defines moving one file left and one rank down.
     */
    public static final Vector SOUTH_WEST = new Vector(-1, -1);

    /**
     * Defines moving two files left and one rank up.
     */
    public static final Vector NORTH1_WEST2 = new Vector(-2, 1);

    /**
     * Defines moving two files right and one rank down.
     */
    public static final Vector SOUTH1_EAST2 = new Vector(2, -1);

    /**
     * Defines moving two files right and one rank up.
     */
    public static final Vector NORTH1_EAST2 = new Vector(2, 1);

    /**
     * Defines moving two files left and one rank down.
     */
    public static final Vector SOUTH1_WEST2 = new Vector(-2, -1);

    /**
     * Defines moving one file left and two ranks up.
     */
    public static final Vector NORTH2_WEST1 = new Vector(-1, 2);

    /**
     * Defines moving one file right and two ranks down.
     */
    public static final Vector SOUTH2_EAST1 = new Vector(1, -2);

    /**
     * Defines moving one file right and two ranks up.
     */
    public static final Vector NORTH2_EAST1 = new Vector(1, 2);

    /**
     * Defines moving one file left and two ranks down.
     */
    public static final Vector SOUTH2_WEST1 = new Vector(-1, -2);

    /**
     * Defines moving in both file and rank axis.
     */
    public static final Vector[] AXIS = {NORTH, SOUTH, WEST, EAST};

    /**
     * Defines moving in both file and rank diagonals.
     */
    public static final Vector[] DIAGONAL = {NORTH_WEST, SOUTH_EAST, NORTH_EAST, SOUTH_WEST};

    /**
     * Defines moving files and ranks like a knight.
     */
    public static final Vector[] KNIGHT = {NORTH1_WEST2, SOUTH1_EAST2, NORTH1_EAST2, SOUTH1_WEST2, NORTH2_WEST1, SOUTH2_EAST1, NORTH2_EAST1, SOUTH2_WEST1};

    /**
     * Defines moving files and ranks like a monarch (i.e., axis and diagonal movements).
     */
    public static final Vector[] MONARCH = {NORTH, SOUTH, WEST, EAST, NORTH_WEST, SOUTH_EAST, NORTH_EAST, SOUTH_WEST};

    /**
     * Defines the pawn movement matrix, where the movements should be accessed through the pawn's color.
     */
    public static final Vector[][] PAWNS = {{SOUTH, SOUTH_WEST, SOUTH_EAST}, {NORTH, NORTH_WEST, NORTH_EAST}};

    private Library() {
    }

    /**
     * Obtains the movement vectors of a chess piece.
     *
     * @param p The chess piece for which the movement vectors should be obtained.
     * @return The vectors describing the movements the chess piece is capable of.
     **/
    public static Vector[] vectorsOf(ChessPiece p) {
        return switch (p.type()) {
            case 1 -> PAWNS[p.color()];
            case 2 -> KNIGHT;
            case 3 -> DIAGONAL;
            case 4 -> AXIS;
            case 5, 6 -> MONARCH;
            default -> throw new IllegalArgumentException("Type " + p.type() + " could not be recognized.");
        };
    }
}
