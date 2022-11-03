package phase1version.chess.utility;

import java.util.List;

import phase1version.chess.interfaces.IChessMove;
import phase1version.chess.interfaces.IChessPiece;
import phase1version.chess.interfaces.IChessboardSquare;

public class ChessMove implements IChessMove
{
    private final IChessPiece owner;
    private final List<IChessboardSquare> destinations;

    public ChessMove(IChessPiece moveOwner, List<IChessboardSquare> ownerDestinations)
    {
        owner = moveOwner;
        destinations = ownerDestinations;
    }

    @Override public boolean canReach(IChessboardSquare square)
    {
        for (var destination : destinations)
        {
            if (destination.equals(square))
            {
                return true;
            }
        }
        return false;
    }

    @Override public IChessPiece owner() { return owner; }

    @Override public List<IChessboardSquare> possibilities() { return destinations; }

	@Override
	public String toString() {
		final int maxLen = 10;
		return "ChessMove [" + owner + ", destinations="
				+ (destinations != null ? destinations.subList(0, Math.min(destinations.size(), maxLen)) : null) + "]";
	}
    
    
}
