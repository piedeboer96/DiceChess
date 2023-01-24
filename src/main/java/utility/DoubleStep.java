package utility;

import game.ChessPiece;
import game.Chessboard;
import game.Square;

import java.util.List;

public final class DoubleStep {
    private static final int[] DOUBLE_STEP_YS = {3, 4};
    private static final int[] SINGLE_STEP_YS = {2, 5};
    private static final int[] INITIAL_YS = {1, 6};
    private DoubleStep() {
    }

    public static void expand(ChessPiece p, Square s, Chessboard b, List<Square> l) {
        if (p.type() != 1 || s.getY() != INITIAL_YS[p.color()]) {
            return;
        }
        Square s1 = Square.get(s.getX(), SINGLE_STEP_YS[p.color()]);
        Square s2 = Square.get(s.getX(), DOUBLE_STEP_YS[p.color()]);
        if (b.read(s1) == null && b.read(s2) == null) {
            l.add(s2);
        }
    }
}
