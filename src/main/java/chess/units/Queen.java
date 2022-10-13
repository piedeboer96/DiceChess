package chess.units;

import chess.interfaces.IChessMoveInfo;
import chess.utility.ChessMoveInfo;

public class Queen extends ChessPiece
{
	 public final static int pointValue = 1000;
    private final IChessMoveInfo[] movementInfo;

    public Queen(char fen, int file, int rank)
    {
        super(fen, file, rank);
        movementInfo = new IChessMoveInfo[8];
        
        movementInfo[0] = new ChessMoveInfo(7, 1, 0);
        movementInfo[1] = new ChessMoveInfo(7, 0, 1);
        movementInfo[2] = new ChessMoveInfo(7, -1, 0);
        movementInfo[3] = new ChessMoveInfo(7, 0, -1);

        movementInfo[4] = new ChessMoveInfo(7, 1, 1);
        movementInfo[5] = new ChessMoveInfo(7, 1, -1);
        movementInfo[6] = new ChessMoveInfo(7, -1, 1);
        movementInfo[7] = new ChessMoveInfo(7, -1, -1);
    }

    public IChessMoveInfo[] movementInfo() { return movementInfo; }
}
