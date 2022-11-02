package solutions.chess.pieces;

import org.junit.jupiter.api.Test;
import utility.Exam;

public final class KnightTest implements Exam {
    @Override @Test
    public void take() {
        testGetTeam();
        testGetType();
        testGetNotation();
    }

    @Test
    public void testGetNotation() {
        assert Knight.BLACK.getNotation() == 'n';
        assert Knight.WHITE.getNotation() == 'N';
    }

    @Test
    public void testGetTeam() {
        assert Knight.BLACK.getTeam() == 0;
        assert Knight.WHITE.getTeam() == 1;
    }

    @Test
    public void testGetType() {
        assert Knight.BLACK.getType() == 2;
        assert Knight.WHITE.getType() == 2;
    }
}
