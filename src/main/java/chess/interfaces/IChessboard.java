package chess.interfaces;

import java.util.List;

/**
 * Represents a chessboard.
 **/
public interface IChessboard
{
    /**
     * Generates the moves a chess piece is capable of.
     * Remark: Each direction the chess piece can move in, is independently stored.
     *         Meaning, the capability of moving 3 squares up and the capability of moving 2 squares right
     *         are separately from each other.
     * @return A list containing all moves a chess piece is capable of.
     **/
    List<IChessMove> generateMovesOf(IChessPiece piece);

    /**
     * Generates moves for the entire team.
     **/
    List<IChessMove> generateMovesOf(int team);


    List<IChessMove> filterMovesOf(int team, char[] values);

    /**
     * Determines whether a chess piece is located on a square.
     * If so, then the chess piece is returned. Null, otherwise.
     **/
    IChessPiece get(IChessboardSquare square);

    /**
     * Gets the king of the provided team.
     **/
    IChessPiece getKing(int team);

    /**
     * Generates the legal moves of a team.
     * Remark: This is your go-to method to access all legal moves a team has.
     *         The list contains every move a chess piece is allowed to make independently.
     *         Meaning, if a rook is allowed to move horizontally in both directions, there are 2 IChessMove objects
     *         in the list, one denoting the movement into the western-direction (or say left-side of the rook) and
     *         the one object denoting the movement into the eastern-direction (or say right-side of the rook).
     *         For more information about how the movement is stored, see IChessMove.
     * @return A list with all legal moves a team has. Returns an empty list (or say list of size 0) if your team is checkmated.
     **/
    List<IChessMove> legalMovesOf(int team);

    /**
     * Gets all chess piece that are placed on the board.
     * @return A list containing all active "black and white coloured" chess pieces.
     **/
    List<IChessPiece> pieces();

    /**
     * Determines whether a team is checkmated.
     * Remark: Should only be called when there are no moves left.
     **/
    boolean playerIsCheckMated(int team);
}
