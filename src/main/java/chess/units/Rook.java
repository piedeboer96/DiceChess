package chess.units;

import chess.interfaces.IChessMoveInfo;
import chess.utility.ChessMoveInfo;

public class Rook extends ChessPiece
{
    private static final IChessMoveInfo[] information = getMovementInformation();
    public static final int pointValue = 525;

    public Rook(char fen, int file, int rank)
    {
        super(fen, file, rank);
        movementInfo = information;
    }

    public static IChessMoveInfo[] getMovementInformation()
    {
        var information = new IChessMoveInfo[4];
        information[0] = new ChessMoveInfo(7, 1, 0);
        information[1] = new ChessMoveInfo(7, 0, 1);
        information[2] = new ChessMoveInfo(7, -1, 0);
        information[3] = new ChessMoveInfo(7, 0, -1);
        return information;
    }
}
