package chess;

import chess.interfaces.IChessboardSquare;
import chess.utility.ChessboardSquare;

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
    public static final char[] CHESS_PIECE_NOTATIONS = getChessPieceNotations();

    /**
     * Converts a letter notation to either lower case or upper case.
     * @return Letter in lower case if team is 0.
     *         <P>Letter in upper case if team is 1.</P>
     **/
    public static char convertChessPieceNotation(char notation, int team)
    {
        if (!Character.isLetter(notation)) { throw new IllegalArgumentException("Can only convert letters.");}
        else if (team == 0) { return Character.toLowerCase(notation); }
        else if (team == 1){ return Character.toUpperCase(notation); }
        else { throw new IllegalArgumentException("Team value is unrecognized."); }
    }

    /**
     * Extracts the castle opportunities that both players have from the castle field.
     * @return A matrix with the following structure
     * <P>{ Black can Castle King Side, White can Castle King Side }</P>
     * <P>{ Black can Castle Queen Side, White can Castle Queen Side }</P>
     **/
    public static boolean[][] getCastleOpportunities(String castleField)
    {
        if (castleField.length() > 4) { throw new IllegalArgumentException("Field is too long!"); }
        else if (castleField.length() == 0) { throw new IllegalArgumentException("Can not extract from an empty field!"); }
        boolean[][] matrix = new boolean[2][];
        matrix[0] = new boolean[2];
        matrix[1] = new boolean[2];
        for (int i = 0; i < castleField.length(); i++)
        {
            char c = castleField.charAt(i);
            switch (c)
            {
                case '-' -> { if (castleField.length() != 1) throw new IllegalArgumentException("Field is not properly formatted!"); }
                case 'k' -> matrix[0][0] = true;
                case 'K' -> matrix[0][1] = true;
                case 'q' -> matrix[1][0] = true;
                case 'Q' -> matrix[1][1] = true;
                default -> throw new IllegalArgumentException("Unrecognized character in field!");
            }
        }
        return matrix;
    }

    /**
     * Gets the Forsyth-Edwards Notation of each chess piece type.
     * <P>Remark: Execute convertChessPieceNotation(char, int) to convert the default type notation
     * to the correct type notation of the team that is chess piece (notation) is assigned to.</P>
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
     * Extracts the en-passant target square from the en-passant opportunity field.
     * @return The en-passant target square if there is an opportunity.
     *         <P>Null, if no opportunity is present.</P>
     **/
    public static IChessboardSquare getEnPassantOpportunity(String enPassantField)
    {
        if (enPassantField.length() == 0) { throw new IllegalArgumentException("Can not extract from an empty field!"); }
        char letter = enPassantField.charAt(0);
        boolean letterIsDash = letter == '-';
        if (letterIsDash && enPassantField.length() == 1) { return null; }
        else if (letterIsDash){ throw new IllegalArgumentException("Field is not formatted properly!"); }
        else if (enPassantField.length() != 2) { throw new IllegalArgumentException("Field does not contain the proper square format!"); }
        int file;
        switch (letter)
        {
            case 'a' -> file = 0;
            case 'b' -> file = 1;
            case 'c' -> file = 2;
            case 'd' -> file = 3;
            case 'e' -> file = 4;
            case 'f' -> file = 5;
            case 'g' -> file = 6;
            case 'h' -> file = 7;
            default -> throw new IllegalArgumentException("Can not recognize file character " + letter);
        }
        int rank = Character.getNumericValue(enPassantField.charAt(1)) - 1;
        if (rank < 0 || rank > 7) { throw new IllegalArgumentException("Rank is out of bounds!"); }
        return new ChessboardSquare(file, rank);
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

    /**
     * Extracts the full move counter from the full move field.
     * @return An integer >= 0.
     **/
    public static int getFullMoves(String fullMoveField)
    {
        int fullMoves = Integer.parseInt(fullMoveField);
        if (fullMoves < 0) { throw new IllegalArgumentException("Can not have a negative counter for full moves!"); }
        return fullMoves;
    }

    /**
     * Extracts the half move counter from the half move field.
     * @return An integer between 0 and 50.
     **/
    public static int getHalfMoves(String halfMoveField)
    {
        int halfMoves = Integer.parseInt(halfMoveField);
        if (halfMoves < 0) { throw new IllegalArgumentException("Can not have a negative counter for half moves!"); }
        else if (halfMoves > 50) { throw new IllegalArgumentException("Can only count up to 50 half moves!"); }
        return halfMoves;
    }

    /**
     * Extracts the player from the player field.
     * @return 0 for black or 1 for white.
     **/
    public static int getPlayer(String playerField)
    {
        if (playerField.length() != 1) { throw new IllegalArgumentException("Field has to be of length 1!"); }
        int player;
        switch (playerField.charAt(0))
        {
            case 'b' -> player = 0;
            case 'w' -> player = 1;
            default -> throw new IllegalArgumentException("Field does not contain a valid player property.");
        }
        return player;
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
