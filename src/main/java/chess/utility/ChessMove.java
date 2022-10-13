package chess.utility;

import java.util.List;

import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;

public class ChessMove implements IChessMove
{
    private final IChessPiece owner;
    private final List<IChessboardSquare> destinations;

    public ChessMove(IChessPiece owner, List<IChessboardSquare> destinations)
    {
        this.owner = owner;
        this.destinations = destinations;
    }

    public boolean canReach(IChessboardSquare square)
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

    public IChessPiece owner() { return owner; }

    public List<IChessboardSquare> possibilities() { return destinations;
    
    }

	@Override
	public String toString() {
		final int maxLen = 10;
		return "ChessMove [" + owner + ", destinations="
				+ (destinations != null ? destinations.subList(0, Math.min(destinations.size(), maxLen)) : null) + "]";
	}
    
    
}
