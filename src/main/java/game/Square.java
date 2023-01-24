package game;

/**
 * Defines a chessboard square through defining its file, rank and algebraic notation.
 **/
public final class Square extends Coordinate {
    private static final Square[][] CACHE = new Square[8][8];
    private final char FILE;
    private final int RANK;

    /**
     * @param file A letter between a and h the square should use as its file.
     * @param rank An integer between 1 and 8 the square should use as its rank.
     * @param x    An integer between 0 and 7 the square should use as its x-coordinate.
     * @param y    An integer between 0 and 7 the square should use as its y-coordinate.
     **/
    private Square(char file, int rank, int x, int y) {
        super(x, y);
        FILE = file;
        RANK = rank;
    }

    /**
     * Obtains a square through its file and rank.
     *
     * @param file A letter between a and h the square should have as its file.
     * @param rank An integer between 1 and 8 the square should have as its rank.
     * @return The square having the provided file and rank.
     **/
    public static Square get(char file, int rank) {
        int x = file - 97;
        int y = 8 - rank;
        if (CACHE[y][x] == null) {
            CACHE[y][x] = new Square(file, rank, x, y);
        }
        return CACHE[y][x];
    }

    /**
     * Obtains a square through its (x, y)-coordinate.
     *
     * @param x An integer between 0 and 7 the square should have as its x-coordinate.
     * @param y An integer between 0 and 7 the square should have as its y-coordinate.
     * @return The square having the provided (x, y)-coordinate.
     **/
    public static Square get(int x, int y) {
        if (CACHE[y][x] == null) {
            CACHE[y][x] = new Square((char) (97 + x), (8 - y), x, y);
        }
        return CACHE[y][x];
    }

    /**
     * Obtains the file of the square.
     *
     * @return A letter between a and h.
     **/
    public char file() {
        return FILE;
    }

    /**
     * Obtains the rank of the square.
     *
     * @return An integer between 1 and 8.
     **/
    public int rank() {
        return RANK;
    }

    /**
     * Obtains the algebraic notation of the square.
     *
     * @return A string consisting of 2 characters, where the first character is the file of the square and
     *         the second character is the rank of the square.
     **/
    @Override
    public String toString() {
        return FILE + String.valueOf(RANK);
    }
}
