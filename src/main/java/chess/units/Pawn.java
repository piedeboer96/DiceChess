package chess.units;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMoveInfo;
import chess.utility.ChessMoveInfo;

public class Pawn extends ChessPiece
{
    public final static int pointValue = 100;

    private final IChessMoveInfo[] movementInfo;
    private final int promotionRank, step;
    private boolean doubleStepIsAllowed;

    public Pawn(char fen, int file, int rank)
    {
        super(fen, file, rank);

        int magnitude;
        if (team == 0)
        {
            promotionRank = 7;
            step = 1;
            if (rank == 1)
            {
                doubleStepIsAllowed = true;
                magnitude = 2;
            } else { magnitude = 1; }
        }
        else
        {
            promotionRank = 0;
            step = -1;
            if (rank == 6)
            {
                doubleStepIsAllowed = true;
                magnitude = 2;
            } else { magnitude = 1; }
        }

        movementInfo = new IChessMoveInfo[1];
        movementInfo[0] = new ChessMoveInfo(magnitude, 0, step);
    }

    public IChessMoveInfo[] movementInfo() { return movementInfo; }

    public boolean promotable() { return rank == promotionRank; }

    public void setPosition(IChessBoardSquare square)
    {
        if (doubleStepIsAllowed)
        {
            doubleStepIsAllowed = false;
            movementInfo[0] = new ChessMoveInfo(1, 0, step);
        }
        file = square.file();
        rank = square.rank();
    }
}
