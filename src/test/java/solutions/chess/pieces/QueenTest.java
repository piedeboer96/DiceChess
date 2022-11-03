package solutions.chess.pieces;

import org.junit.jupiter.api.Test;
import utility.Exam;

public final class QueenTest implements Exam {
    @Override @Test
    public void take() {
        testGetTeam();
        testGetType();
        testGetNotation();
    }

    @Test
    public void testGetNotation() {
        assert Queen.BLACK.getNotation() == 'q';
        assert Queen.WHITE.getNotation() == 'Q';
    }

    @Test
    public void testGetTeam() {
        assert Queen.BLACK.getTeam() == 0;
        assert Queen.WHITE.getTeam() == 1;
    }

    @Test
    public void testGetType() {
        assert Queen.BLACK.getType() == 5;
        assert Queen.WHITE.getType() == 5;
    }
}
