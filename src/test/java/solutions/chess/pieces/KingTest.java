package solutions.chess.pieces;

import org.junit.jupiter.api.Test;
import utility.Exam;

public final class KingTest implements Exam {
    @Override @Test
    public void take() {
        testGetTeam();
        testGetType();
        testGetNotation();
    }

    @Test
    public void testGetNotation() {
        assert King.BLACK.getNotation() == 'k';
        assert King.WHITE.getNotation() == 'K';
    }

    @Test
    public void testGetTeam() {
        assert King.BLACK.getTeam() == 0;
        assert King.WHITE.getTeam() == 1;
    }

    @Test
    public void testGetType() {
        assert King.BLACK.getType() == 6;
        assert King.WHITE.getType() == 6;
    }
}
