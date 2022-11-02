package solutions.chess.utility;

import org.junit.jupiter.api.Test;
import solutions.chess.board.Square;
import utility.Exam;

public final class EPOpportunitiesTest implements Exam {
    @Override @Test
    public void take() {
        testGet();
        testSet();
    }

    @Test
    public void testGet() {
        EPOpportunities opportunities = new EPOpportunities();
        assert opportunities.get(0) == null;
        assert opportunities.get(1) == null;
    }

    @Test
    public void testSet() {
        EPOpportunities opportunities = new EPOpportunities();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Square s = Square.get(row, column);
                opportunities.set(0, s);
                assert opportunities.get(0) == null;
                assert opportunities.get(1) == s;

                opportunities.set(0, null);
                assert opportunities.get(0) == null;
                assert opportunities.get(1) == null;

                opportunities.set(1, s);
                assert opportunities.get(0) == s;
                assert opportunities.get(1) == null;

                opportunities.set(1, null);
                assert opportunities.get(0) == null;
                assert opportunities.get(1) == null;

            }
        }
    }
}
