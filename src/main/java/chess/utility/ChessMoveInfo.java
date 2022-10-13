package chess.utility;

import chess.interfaces.IChessMoveInfo;

public class ChessMoveInfo implements IChessMoveInfo
{
    private final int deltaFile, deltaRank, maxSteps;

    /**
     * @param maxSteps The number of times the chess piece can use this move consecutively.
     * @param deltaFile The difference between column numbers when a single movement step is applied.
     * @param deltaRank The difference between rows numbers when a single movement step is applied.
     **/
    public ChessMoveInfo(int maxSteps, int deltaFile, int deltaRank)
    {
        if (maxSteps <= 0) { throw new IllegalArgumentException("Forbidden to pass a negative number of steps or no steps at all!");  }
        else if (maxSteps > 7) { throw new IllegalArgumentException("Can not move 8 squares when I already sit on 1 square."); }
        else if (deltaFile == 0 && deltaRank == 0) { throw new IllegalArgumentException("Not moving is not a move!"); }
        this.maxSteps = maxSteps;
        this.deltaFile = deltaFile;
        this.deltaRank = deltaRank;
    }

    public int deltaFile() { return deltaFile; }

    public int deltaRank() { return deltaRank; }

    public int maxSteps() { return maxSteps; }
}
