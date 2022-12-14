package chess;

import org.junit.jupiter.api.Test;

import chess.utility.ChessboardSquare;

public class ChessboardSquareTest
{
    /**
     * Tests the coordinates of each square on the chessboard.
     **/
    @Test public void testCoordinateAccess()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                var chessboardSquare = new ChessboardSquare(i, j);
                assert chessboardSquare.file() == i;
                assert chessboardSquare.rank() == j;
            }
        }
    }

    /**
     * Tests the coordinate range each chessboard square can have.
     **/
    @Test public void testCoordinateIntegrity()
    {
        try { new ChessboardSquare(-1, 0); assert false; }
        catch (IllegalArgumentException exception) { assert true; }

        try { new ChessboardSquare(0, -1); assert false; }
        catch (IllegalArgumentException exception) { assert true; }

        try { new ChessboardSquare(-1, -1); assert false; }
        catch (IllegalArgumentException exception) { assert true; }

        try { new ChessboardSquare(8, 0); assert false; }
        catch (IllegalArgumentException exception) { assert true; }

        try { new ChessboardSquare(0, 8); assert false; }
        catch (IllegalArgumentException exception) { assert true; }

        try { new ChessboardSquare(8, 8); assert false; }
        catch (IllegalArgumentException exception) { assert true; }
    }

    @Test public void testEquals()
    {

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                var chessboardSquare = new ChessboardSquare(i, j);
                var duplicate = new ChessboardSquare(i, j);
                var mirrored = new ChessboardSquare(j, i);
                assert chessboardSquare.equals(duplicate);
                assert i == j || !chessboardSquare.equals(mirrored);
            }
        }
    }
}
