package chess;

import org.junit.jupiter.api.Test;

import chess.units.ChessPiece;
import chess.utility.ChessboardSquare;

public class ChessPieceTest
{
    @Test public void testEquals()
    {
        var chessPiece = new ChessPiece('a', 1, 2);
        var duplicate = new ChessPiece('a', 1, 2);
        var mirrored = new ChessPiece('a', 2, 1);
        var different = new ChessPiece('b', 1, 2);
        assert chessPiece.equals(duplicate);
        assert !chessPiece.equals(mirrored);
        assert !chessPiece.equals(different);
    }

    @Test public void testSetPosition()
    {
        var chessPiece = new ChessPiece('a', 0, 0);
        chessPiece.setPosition(new ChessboardSquare(1, 2));
        assert chessPiece.file() == 1;
        assert chessPiece.rank() == 2;
    }

    @Test public void testTeam()
    {
        var black = new ChessPiece('a', 0 , 0);
        var white = new ChessPiece('A', 0, 0);
        assert black.team() == 0;
        assert white.team() == 1;
    }
}
