package chess;

/**
 * Represents the Forsyth-Edwards Notation (which is abbreviated to FEN).
 * <P>Developed to allow resuming chess games any time anywhere from a (last) recorded match state.</P>
 * <P>A FEN-string contains a total of 6 information fields to fully represent a chess game. Those fields are:</P>
 * <P>1. The board field; A sequence of letters and numbers to describe how each row on the chessboard is 'decorated'.</P>
 * <P>2. The player field; A letter to denote whose turn to play it is.</P>
 * <P>3. The en-passant opportunity field; A letter (representing the file) and a number (representing the rank) combination
 * denoting the en-passant target square.</P>
 * <P>4. The castle opportunity field; A sequence of letters to denote each player's opportunity to castle king and
 * queen side.</P>
 * <P>5. The half move field; A positive integer to denote the number of turns that have passed without a pawn moving
 * or any capture being made.</P>
 * <P>6. The full move field; A positive integer to denote the number of turn cycles that have been made.</P>
 **/
public final class ForsythEdwardsNotation
{

    /**
     * { Bishop, King, Knight, Pawn, Queen, Rook }
     **/
    public static final char[] chessPieceNotations = getChessPieceNotations();

    public static char applyNotationCasing(char notation, int team)
    {
        if (team == 0) { return Character.toLowerCase(notation); }
        else { return Character.toUpperCase(notation); }
    }

    /**
     * Gets the Forsyth-Edwards Notation of each chess piece type.
     * @return An alphabetically sorted array containing the FEN for each chess piece type.
     *        <P>Entry Visualisation: { Bishop, King, Knight, Pawn, Queen, Rook }</P>
     **/
    public static char[] getChessPieceNotations()
    {
        char[] notations = new char[6];
        notations[0] = 'b';
        notations[1] = 'k';
        notations[2] = 'n';
        notations[3] = 'p';
        notations[4] = 'q';
        notations[5] = 'r';
        return notations;
    }

    /**
     * Extracts the information fields from the FEN-string.
     * @return { board field, player field, en-passant field, castle field, half move field, full move field }
     **/
    public static String[] getFields(String fenString)
    {
        String[] fields = fenString.split(" ");
        if (fields.length != 6) { throw new IllegalArgumentException("String does not contain 6 fields!"); }
        return fields;
    }

    public static int getPlayer(String playerField)
    {
        if (playerField.length() != 1) { throw new IllegalArgumentException("Field has to be of length 1!"); }
        int player;

        return 0;
    }

    /**
     * Extracts the information for each row from the board field.
     * @return { top row, ..., bottom row }
     **/
    public static String[] getRows(String boardField)
    {
        String[] rows = boardField.split("/");
        if (rows.length != 8) { throw new IllegalArgumentException("Field does not describe each row on the chessboard!"); }
        return rows;
    }

}
