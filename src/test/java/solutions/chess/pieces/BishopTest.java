package solutions.chess.pieces;

import org.junit.jupiter.api.Test;
import utility.Exam;

public final class BishopTest implements Exam {
    @Override @Test
    public void take() {
        testGetTeam();
        testGetType();
        testGetNotation();
    }

    @Test
    public void testGetNotation() {
        assert Bishop.BLACK.getNotation() == 'b';
        assert Bishop.WHITE.getNotation() == 'B';
    }

    @Test
    public void testGetTeam() {
        assert Bishop.BLACK.getTeam() == 0;
        assert Bishop.WHITE.getTeam() == 1;
    }

    @Test
    public void testGetType() {
        assert Bishop.BLACK.getType() == 3;
        assert Bishop.WHITE.getType() == 3;
    }
}
