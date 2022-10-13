package chess.units;

import chess.interfaces.IChessMoveInfo;
import chess.utility.ChessMoveInfo;

public class Rook extends ChessPiece
{
	 public final static int pointValue = 525;
    private final IChessMoveInfo[] movementInfo;

    public Rook(char fen, int file, int rank)
    {
        super(fen, file, rank);
        movementInfo = new IChessMoveInfo[4];
        movementInfo[0] = new ChessMoveInfo(7, 1, 0);
        movementInfo[1] = new ChessMoveInfo(7, 0, 1);
        movementInfo[2] = new ChessMoveInfo(7, -1, 0);
        movementInfo[3] = new ChessMoveInfo(7, 0, -1);
    }

    public IChessMoveInfo[] movementInfo() { return movementInfo; }
}
