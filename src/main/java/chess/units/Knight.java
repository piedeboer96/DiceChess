package chess.units;

import chess.interfaces.IChessMoveInfo;
import chess.utility.ChessMoveInfo;

public class Knight extends ChessPiece
{
    private final IChessMoveInfo[] movementInfo;

    public Knight(char fen, int file, int rank)
    {
        super(fen, file, rank);
        movementInfo = new IChessMoveInfo[8];

        movementInfo[0] = new ChessMoveInfo(1, 1, 2);
        movementInfo[1] = new ChessMoveInfo(1, -1, 2);
        movementInfo[2] = new ChessMoveInfo(1, 1, -2);
        movementInfo[3] = new ChessMoveInfo(1, -1, -2);

        movementInfo[4] = new ChessMoveInfo(1, 2, 1);
        movementInfo[5] = new ChessMoveInfo(1, -2, 1);
        movementInfo[6] = new ChessMoveInfo(1, 2, -1);
        movementInfo[7] = new ChessMoveInfo(1, -2, -1);
    }

    public IChessMoveInfo[] movementInfo() { return movementInfo; }
}
