package chess.units;

import chess.interfaces.IChessMoveInfo;
import chess.interfaces.IChessboardSquare;
import chess.utility.ChessMoveInfo;

public class Pawn extends ChessPiece
{
    private static final int[] initialRanks = { 1, 6 };
    private static final int[] promotionRanks = { 7, 0 };
    private static final int[] steps = { 1 , -1 };
    public static final int pointValue = 100;
    private boolean doubleStepIsAllowed;

    public Pawn(char fen, int file, int rank)
    {
        super(fen, file, rank);

        int magnitude;
        doubleStepIsAllowed = rank == initialRanks[team];
        if (doubleStepIsAllowed) { magnitude = 2; }
        else { magnitude = 1; }

        movementInfo = new IChessMoveInfo[1];
        movementInfo[0] = new ChessMoveInfo(magnitude, 0, steps[team()]);
    }

    @Override public boolean promotable() { return rank == promotionRanks[team]; }

    @Override public void setPosition(IChessboardSquare square)
    {
        super.setPosition(square);
        if (doubleStepIsAllowed)
        {
            doubleStepIsAllowed = false;
            movementInfo[0] = new ChessMoveInfo(1, 0, steps[team]);
        }
    }
}
