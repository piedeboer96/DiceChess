package game;

/**
 * Defines a chessboard through providing 64 locations to read and store chess pieces.
 **/
public final class Chessboard {
    private final ChessPiece[][] SETUP;

    /** Creates an empty board. */
    public Chessboard() {
        SETUP = new ChessPiece[8][8];
    }

    /**
     * Creates a board with the piece placement data described in a Forsyth-Edwards Notation.
     *
     * @param FEN The Forsyth-Edwards Notation of the piece placement data the chessboard should have.
     **/
    public Chessboard(String FEN) {
        this();
        update(FEN);
    }

    /**
     * Obtains the chess piece located on a square.
     *
     * @param s The square where the chess piece should be obtained from.
     * @return The chess piece located on the square. <br>
     *         Null, if the square is unoccupied.
     **/
    public ChessPiece read(Square s) {
        return SETUP[s.getY()][s.getX()];
    }

    /**
     * Places a chess piece on a square.
     *
     * @param p The chess piece that should be placed on a square.
     * @param s The square where the chess should be placed on.
     **/
    public void store(ChessPiece p, Square s) {
        if (SETUP[s.getY()][s.getX()] != p) {
            SETUP[s.getY()][s.getX()] = p;
        }
    }

    /**
     * Updates the piece placement data of the chessboard.
     *
     * @param FEN The Forsyth-Edwards Notation of the piece placement data the chessboard should update itself to.
     **/
    public void update(String FEN) {
        int row = 0;
        int column = 0;
        for (int entry = 0; entry < FEN.length(); entry++) {
            char c = FEN.charAt(entry);
            if (Character.isDigit(c)) {
                for (int i = 0; i < Character.getNumericValue(c); i++) {
                    SETUP[row][column++] = null;
                }
            } else if (Character.isLetter(c)) {
                ChessPiece p = ChessPiece.get(c);
                SETUP[row][column++] = p;
            } else if (c == '/') {
                column = 0;
                row++;
            } else {
                throw new IllegalArgumentException("Character " + c + " could not be recognized.");
            }
        }
    }

    /**
     * Obtains a top view visualization of the piece placement data the chessboard has.
     **/
    public String visualize() {
        StringBuilder sb = new StringBuilder();
        sb.append("  +---+---+---+---+---+---+---+---+\n");
        for (int row = 0; row < 8; row++) {
            sb.append((8 - row)).append(" |");
            for (int column = 0; column < 8; column++) {
                if (SETUP[row][column] == null) {
                   sb.append("   ");
                } else {
                    sb.append(' ').append(SETUP[row][column]).append(' ');
                }
                sb.append('|');
            }
            sb.append('\n');
            sb.append("  +---+---+---+---+---+---+---+---+\n");
        }
        sb.append("    a   b   c   d   e   f   g   h");
        return sb.toString();
    }

    /**
     * Obtains the Forsyth-Edwards Notation of the piece placement data of the chessboard.
     *
     * @return A string, where each '/' character indicates the start of the next rank, each letter represents
     *         a chess piece and each digit represents the number of consecutively empty squares in the rank.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            int consecutivelyEmpty = 0;
            for (int column = 0; column < 8; column++) {
                if (SETUP[row][column] == null) {
                    consecutivelyEmpty++;
                    continue;
                } else if (consecutivelyEmpty > 0) {
                    sb.append(consecutivelyEmpty);
                    consecutivelyEmpty = 0;
                }
                sb.append(SETUP[row][column]);
            }
            if (consecutivelyEmpty > 0) {
                sb.append(consecutivelyEmpty);
            }
            if (row < 7) {
                sb.append('/');
            }
        }
        return sb.toString();
    }
}
