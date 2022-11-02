package solutions.chess.pieces;

import org.junit.jupiter.api.Test;
import utility.Exam;

public final class PawnTest implements Exam {
    @Override @Test
    public void take() {
        testGetTeam();
        testGetType();
        testGetNotation();
    }

    @Test
    public void testGetNotation() {
        assert Pawn.BLACK.getNotation() == 'p';
        assert Pawn.WHITE.getNotation() == 'P';
    }

    @Test
    public void testGetTeam() {
        assert Pawn.BLACK.getTeam() == 0;
        assert Pawn.WHITE.getTeam() == 1;
    }

    @Test
    public void testGetType() {
        assert Pawn.BLACK.getType() == 1;
        assert Pawn.WHITE.getType() == 1;
    }
}
