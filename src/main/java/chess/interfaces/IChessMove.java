package chess.interfaces;

import java.util.List;

/**
 * Defines the move a chess piece is capable of according to the current situation on the chessboard.
 * As an example, let's consider the same rook from IChessMoveInfo.
 * This rook can only 7 tiles down and 7 tiles to the right if and only if there are no pieces blocking it.
 * Therefore, the actual squares a chess piece could arrive using one of its defined moves
 * is based on the state of the board.
 * Thus, IChessMove provides a representation of what a chess piece is actually capable of
 * using one of its movements.
 **/
public interface IChessMove
{
    /**
     * Determines whether move allows the chess piece reach the defined square on the board.
     **/
    boolean canReach(IChessBoardSquare square);

    /**
     * Gets the chess piece for which the move is defined.
     **/
    IChessPiece owner();

    /**
     * Gets the possible destination squares this move of the chess piece can lead to.
     **/
    List<IChessBoardSquare> possibilities();
}
