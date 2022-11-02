package solutions.chess.board;

import framework.game.Location;

public enum Square implements Location {
    A8 (0, 0), B8 (0, 1), C8 (0, 2), D8 (0,3 ), E8 (0, 4), F8 (0, 5), G8 (0, 6), H8 (0, 7),
    A7 (1, 0), B7 (1, 1), C7 (1, 2), D7 (1,3 ), E7 (1, 4), F7 (1, 5), G7 (1, 6), H7 (1, 7),
    A6 (2, 0), B6 (2, 1), C6 (2, 2), D6 (2,3 ), E6 (2, 4), F6 (2, 5), G6 (2, 6), H6 (2, 7),
    A5 (3, 0), B5 (3, 1), C5 (3, 2), D5 (3,3 ), E5 (3, 4), F5 (3, 5), G5 (3, 6), H5 (3, 7),
    A4 (4, 0), B4 (4, 1), C4 (4, 2), D4 (4,3 ), E4 (4, 4), F4 (4, 5), G4 (4, 6), H4 (4, 7),
    A3 (5, 0), B3 (5, 1), C3 (5, 2), D3 (5,3 ), E3 (5, 4), F3 (5, 5), G3 (5, 6), H3 (5, 7),
    A2 (6, 0), B2 (6, 1), C2 (6, 2), D2 (6,3 ), E2 (6, 4), F2 (6, 5), G2 (6, 6), H2 (6, 7),
    A1 (7, 0), B1 (7, 1), C1 (7, 2), D1 (7,3 ), E1 (7, 4), F1 (7, 5), G1 (7, 6), H1 (7, 7);

    private static final char[] FILES = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
    private static final Square[] SQUARES = values();
    private final int ROW, COLUMN;

    Square(int row, int column) {
        validate(row, column);
        ROW = row;
        COLUMN = column;
    }

    @Override
    public int column() {
        return COLUMN;
    }

    public static Square get(int row, int column) {
        validate(row, column);
        return SQUARES[row * 8 + column];
    }

    /**
     * Gets the file representation of the square.
     * @return A letter between a and h.
     **/
    public char file() {
        return FILES[COLUMN];
    }

    /**
     * Converts a file to its respective column value.
     * @param file The file that should be converted.
     * @return An integer between 0 and 7.
     * @exception IllegalArgumentException Thrown when file is neither a, b, d, e, f, g, nor h.
     **/
    public static int fileToColumn(char file) {
        if (file < 97 || file > 104) { throw new IllegalArgumentException("Character is not between a and h!"); }
        return file - 97;
    }

    /**
     * Gets the rank representation of the square.
     * @return An integer between 1 and 8.
     **/
    public int rank() {
        return 8 - ROW;
    }

    /**
     * Converts a rank to its respective row value.
     * @param rank The rank that should be converted.
     * @return An integer between 0 and 7.
     **/
    public static int rankToRow(int rank) {
        if (rank < 1 || rank > 8) { throw new IllegalArgumentException("Rank is not between 1 and 8!"); }
        return 8 - rank;
    }

    @Override
    public int row() {
        return ROW;
    }

    /**
     * Translates an information field describing an en-passant target square to a board location.
     * @param targetSquareField The information field describing the en-passant target square.
     * @return The en-passant location the information field is describing. <br>
     *         Null, if the field describes that there is no en-passant target square.
     * @exception NullPointerException Thrown when null is provided as an information field.
     * @exception IllegalArgumentException Thrown when the information field is not of the correct format.
     **/
    public static Square translate(String targetSquareField) {
        if (targetSquareField == null) { throw new NullPointerException("Field is not allowed to be empty!"); }
        else if (targetSquareField.length() > 2) { throw new IllegalArgumentException("Expected field to have a length of at most 2!"); }
        char c1 = targetSquareField.charAt(0);
        if (c1 == '-') { return null; }
        int column = fileToColumn(c1);
        char c2 = targetSquareField.charAt(1);
        int row = rankToRow(Character.getNumericValue(c2));
        return get(row, column);
    }

    /**
     * Translates a location to its en-passant information field representative.
     * @param targetSquare The location that should be translated into an information field.
     * @return The information describing the location.
     **/
    public static String translate(Location targetSquare) {
        if (targetSquare == null) { return "-"; }
        return String.valueOf(FILES[targetSquare.column()]) + String.valueOf(8 - targetSquare.row());
    }

    private static void validate(int row, int column) {
        if (row < 0 || row > 7) { throw new IllegalArgumentException("Row is not valid!"); }
        else if (column < 0 || column > 7) { throw new IllegalArgumentException("Column is not valid!"); }
    }
}
