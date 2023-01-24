package game;

/**
 * Defines a chess piece through defining its type and color.
 **/
public final class ChessPiece {
    private static final ChessPiece[][] CACHE = new ChessPiece[7][2];
    private static final char[][] FEN = {{}, {'p', 'P'}, {'n', 'N'}, {'b', 'B'}, {'r', 'R'}, {'q', 'Q'}, {'k', 'K'}};
    private final int TYPE, COLOR;

    /**
     * @param type  An integer between 1 and 6 the chess piece should use as its type.
     * @param color An integer between 0 and 1 the chess piece should use as its color.
     **/
    private ChessPiece(int type, int color) {
        TYPE = type;
        COLOR = color;
    }

    /**
     * Obtains a chess piece through its type and color.
     *
     * @param type  An integer between 1 and 6 the chess piece should have as its type.
     * @param color An integer between 0 and 1 the chess piece should have as its color.
     * @return The chess piece having the provided type and color.
     **/
    public static ChessPiece get(int type, int color) {
        if (CACHE[type][color] == null) {
            CACHE[type][color] = new ChessPiece(type, color);
        }
        return CACHE[type][color];
    }

    /**
     * Obtains a chess piece through its Forsyth-Edwards Notation.
     *
     * @param FEN The Forsyth-Edwards Notation the chess piece should have.
     * @return The chess piece having the provided Forsyth-Edwards Notation.
     **/
    public static ChessPiece get(char FEN) {
        for (int type = 1; type < 7; type++) {
            for (int color = 0; color < 2; color++) {
                if (ChessPiece.FEN[type][color] == FEN) {
                    return get(type, color);
                }
            }
        }
        throw new IllegalArgumentException("Character " + FEN + " could not be recognized.");
    }

    /**
     * Obtains the color of the chess piece.
     *
     * @return 0, if the chess piece has a black color. <br>
     *         1, if the chess piece has a white color.
     **/
    public int color() {
        return COLOR;
    }


    /**
     * Obtains the type of the chess piece.
     *
     * @return 1, if the chess piece is a pawn. <br>
     *         2, if the chess piece is a knight <br>
     *         3, if the chess piece is a bishop. <br>
     *         4, if the chess piece is a rook. <br>
     *         5, if the chess piece is a queen. <br>
     *         6, if the chess piece is a king.
     **/
    public int type() {
        return TYPE;
    }

    /**
     * Obtains the Forsyth-Edwards Notation of the chess piece.
     *
     * @return A letter from the set {p, n, b, r, q, k}, if the chess piece has a black color. <br>
     *         A letter from the set {P, N, B, R, Q, K}, if the chess piece has a white color.
     **/
    @Override
    public String toString() {
        return String.valueOf(FEN[TYPE][COLOR]);
    }
}
