package chess.interfaces;

/**
 * Provides a utility to assist in the determination of legal moves.
 **/
public interface IGhost
{
    /**
     * Sets the square the chess piece is on without triggering anything that normally would trigger.
     **/
    void ghostTo(IChessBoardSquare square);

    /**
     * Determines whether the chess piece is "hidden from view" for calculation purposes.
     **/
    boolean isHidden();

    /**
     * Hides the chess piece for calculations purposes.
     * Making a chess piece hidden implies that the board won't calculate its moves till it is shown again.
     **/
    void hide();

    /**
     * Shows the chess piece (i.e., un-hides the piece).
     * Making it available for movement calculations.
     **/
    void show();
}
