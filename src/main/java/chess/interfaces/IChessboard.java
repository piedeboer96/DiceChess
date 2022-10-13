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
     * Generates the legal moves of a chess player.
     **/
    List<IChessMove> legalMovesOf(int team);

    /**
     * Generates the legal moves of a 'dice chess' player.
     **/
    List<IChessMove> legalMovesOf(int team, char[] values);

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
