package phase1version.chess.units;

import phase1version.chess.interfaces.IChessMoveInfo;
import phase1version.chess.utility.ChessMoveInfo;

public final class Bishop extends ChessPiece
{
	private static final IChessMoveInfo[] information = getMovementInformation();
	public static final int pointValue = 350;

	public Bishop(char fen, int file, int rank)
	{
		super(fen, file, rank);
		movementInfo = information;
	}

	public static IChessMoveInfo[] getMovementInformation()
	{
		var information = new IChessMoveInfo[4];
		information[0] = new ChessMoveInfo(7, 1, 1);
		information[1] = new ChessMoveInfo(7, 1, -1);
		information[2] = new ChessMoveInfo(7, -1, 1);
		information[3] = new ChessMoveInfo(7, -1, -1);
		return information;
	}
}
