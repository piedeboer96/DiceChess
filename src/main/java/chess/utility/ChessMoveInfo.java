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
        this.maxSteps = maxSteps;
        this.deltaFile = deltaFile;
        this.deltaRank = deltaRank;
    }

    public int deltaFile() { return deltaFile; }

    public int deltaRank() { return deltaRank; }

    public int maxSteps() { return maxSteps; }
}
