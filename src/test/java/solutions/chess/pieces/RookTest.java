package solutions.chess.pieces;

import org.junit.jupiter.api.Test;
import utility.Exam;

public final class RookTest implements Exam {
    @Override @Test
    public void take() {
        testGetTeam();
        testGetType();
        testGetNotation();
    }

    @Test
    public void testGetNotation() {
        assert Rook.BLACK.getNotation() == 'r';
        assert Rook.WHITE.getNotation() == 'R';
    }

    @Test
    public void testGetTeam() {
        assert Rook.BLACK.getTeam() == 0;
        assert Rook.WHITE.getTeam() == 1;
    }

    @Test
    public void testGetType() {
        assert Rook.BLACK.getType() == 4;
        assert Rook.WHITE.getType() == 4;
    }
}
