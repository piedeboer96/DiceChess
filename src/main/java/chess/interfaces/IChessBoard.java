package chess.interfaces;

import java.util.List;

/**
 * Represents a chessboard.
 **/
public interface IChessBoard
{
    /**
     * Generates the moves a chess piece is capable of.
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
    IChessPiece get(IChessBoardSquare square);

    /**
     * Generates the legal moves of a team.
     **/
    List<IChessMove> legalMovesOf(int team);

    /**
     * Gets the 'un-captured' chess pieces from the board.
     **/
    List<IChessPiece> pieces();
}
