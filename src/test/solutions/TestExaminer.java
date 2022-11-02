package solutions;

import org.junit.jupiter.api.Test;
import solutions.chess.board.MovementTest;
import solutions.chess.board.SquareTest;
import solutions.chess.pieces.*;
import solutions.chess.utility.CastlePermissionsTest;
import solutions.chess.utility.EPOpportunitiesTest;
import utility.Exam;

public final class TestExaminer {
    @Test
    public void examine() {
        Exam[] exams = {
                new SquareTest(),
                new MovementTest(),
                new CastlePermissionsTest(),
                new EPOpportunitiesTest(),
                new BishopTest(),
                new KingTest(),
                new KnightTest(),
                new PawnTest(),
                new QueenTest(),
                new RookTest(),
        };
        for (Exam e : exams) { e.take(); }
    }
}
