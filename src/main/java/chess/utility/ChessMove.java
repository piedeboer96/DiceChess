package chess.utility;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;

import java.util.List;

public class ChessMove implements IChessMove
{
    private final IChessPiece owner;
    private final List<IChessBoardSquare> destinations;

    public ChessMove(IChessPiece owner, List<IChessBoardSquare> destinations)
    {
        this.owner = owner;
        this.destinations = destinations;
    }

    public boolean canReach(IChessBoardSquare square)
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

    public List<IChessBoardSquare> possibilities() { return destinations; 
    
    }

	@Override
	public String toString() {
		final int maxLen = 10;
		return "ChessMove [owner=" + owner + ", destinations="
				+ (destinations != null ? destinations.subList(0, Math.min(destinations.size(), maxLen)) : null) + "]";
	}
    
    
}
