package chess;

import chess.interfaces.IChessboardSquare;
import chess.units.ChessPiece;
import chess.utility.ChessMove;
import chess.utility.ChessboardSquare;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ChessMoveTest
{
    @Test public void testOwner()
    {
        var chessPiece = new ChessPiece('a', 1, 2);
        var chessMove = new ChessMove(chessPiece, null);
        assert chessMove.owner().equals(chessPiece);
    }

    @Test public void testPossibleDestinations()
    {
        var destinations = new ArrayList<IChessboardSquare>();
        var chessMove = new ChessMove(null, destinations);
        assert chessMove.possibilities().equals(destinations);

        destinations.add(new ChessboardSquare(0, 0));
        assert chessMove.possibilities().equals(destinations);
    }
}
