package chess.units;

import chess.interfaces.IChessMoveInfo;
import chess.utility.ChessMoveInfo;

public class Bishop extends ChessPiece
{

	 public final static int pointValue = 350;

    private final IChessMoveInfo[] movementInfo;

    public Bishop(char fen, int file, int rank)
    {
        super(fen, file, rank);
        movementInfo = new IChessMoveInfo[4];
        movementInfo[0] = new ChessMoveInfo(8, 1, 1);
        movementInfo[1] = new ChessMoveInfo(8, 1, -1);
        movementInfo[2] = new ChessMoveInfo(8, -1, 1);
        movementInfo[3] = new ChessMoveInfo(8, -1, -1);
    }

    public IChessMoveInfo[] movementInfo() { return movementInfo; }


}
