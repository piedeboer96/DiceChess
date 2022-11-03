package chess;

import org.junit.jupiter.api.Test;

import phase1version.chess.utility.ChessMoveInfo;

public class ChessMoveInfoTest
{
    @Test public void testDeltaFile()
    {
        var positiveMoveInfo = new ChessMoveInfo(1, 1, 0);
        var negativeMoveInfo = new ChessMoveInfo(1, -1, 0);
        assert positiveMoveInfo.deltaFile() == 1;
        assert negativeMoveInfo.deltaFile() == -1;
    }

    @Test public void testDeltaRank()
    {
        var positiveMoveInfo = new ChessMoveInfo(1, 0, 1);
        var negativeMoveInfo = new ChessMoveInfo(1, 0, -1);
        assert positiveMoveInfo.deltaRank() == 1;
        assert negativeMoveInfo.deltaRank() == -1;
    }

    @Test public void testMaxSteps()
    {
        try { new ChessMoveInfo(-1, 0, 0); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { new ChessMoveInfo(8, 0, 0); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { new ChessMoveInfo(1, 0, 0); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        var moveInfo = new ChessMoveInfo(1, 0, 1);
        assert moveInfo.maxSteps() == 1;
    }
}
