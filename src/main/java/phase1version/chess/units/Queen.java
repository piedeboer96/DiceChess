package phase1version.chess.units;

import phase1version.chess.interfaces.IChessMoveInfo;
import phase1version.chess.utility.ChessMoveInfo;

public final class Queen extends ChessPiece
{
    private static final IChessMoveInfo[] information = getMovementInformation();
    public static final int pointValue = 1000;

    public Queen(char fen, int file, int rank)
    {
        super(fen, file, rank);
        movementInfo = information;
    }

    public static IChessMoveInfo[] getMovementInformation()
    {
        var information = new IChessMoveInfo[8];
        information[0] = new ChessMoveInfo(7, 1, 0);
        information[1] = new ChessMoveInfo(7, 0, 1);
        information[2] = new ChessMoveInfo(7, -1, 0);
        information[3] = new ChessMoveInfo(7, 0, -1);
        information[4] = new ChessMoveInfo(7, 1, 1);
        information[5] = new ChessMoveInfo(7, 1, -1);
        information[6] = new ChessMoveInfo(7, -1, 1);
        information[7] = new ChessMoveInfo(7, -1, -1);
        return information;
    }
}
