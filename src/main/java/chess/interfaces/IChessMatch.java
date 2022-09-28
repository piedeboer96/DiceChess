package chess.interfaces;

/**
 * Represents a chess match.
 * Remark: This is your main access point to the chess game, its board state (the displacement of the pieces on the board),
 *         the moves each team is allowed to make legally etc.
 *         As this interface extends IChessBoard, it is suggested to read the IChessBoard interface for more information.
 **/
public interface IChessMatch extends IChessBoard
{
    /**
     * Gets the player whose turn it is to make a move.
     * @return An integer that is either 0 for team black or 1 for team white.
     **/
    int getPlayer();

    /**
     * Interprets a fen-string and updates the match accordingly.
     * @param fen A full-fledged fen-string containing all 6 information fields.
     *            See Forsyth-Edward notation wiki for more information about it.
     **/
    void interpret(String fen);

    /**
     * Moves a chess piece from its current square to a destination square.
     * When done moving, gives the turn-to-play to the next player.
     * Remark: In order to use this function properly, see getLegalMoves(int team) from the IChessBoard interface
     *         as that method returns the legal moves a team may make.
     * @param piece The chess piece that is going to get re-positioned.
     * @param destination The square the chess piece is going to.
     * @exception IllegalArgumentException Thrown when an attempt is made to re-position the opponent's chess piece.
     *                                     So, if getPlayer() returns 0, only black pieces are allowed to reposition.
     **/
    void playMove(IChessPiece piece, IChessBoardSquare destination);

    /**
     * Promotes a chess piece to another chess piece type (i.e., the function to promote a pawn to another type.).
     * @param piece The chess piece that is getting promoted.
     * @param target The targeted chess piece type that is going to replace the chess piece that is getting promoted.
     * @exception IllegalArgumentException Thrown when null is passed for the chess piece that is getting promoted or
     *                                     the target that is going to replace the promoted piece.
     *                                     -or- the passed chess piece can not be promoted.
     **/
    void promote(IChessPiece piece, IChessPiece target);

    /**
     * Translate the current state of the match to a fen-string.
     **/
    String toFen();
    
    
    /**
     * @author pierrepaulcharbonnier
     *
     * Move to the next player
     * @return the next player 
     */
    int  nextPlayer();
}
