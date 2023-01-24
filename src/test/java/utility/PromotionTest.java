package utility;

import game.ChessPiece;
import game.Square;
import org.junit.jupiter.api.Test;

final class PromotionTest {
    @Test
    public void testIsEligible() {
        int[] ys = {7, 0};
        for (int color = 0; color < 2; color++) {
            ChessPiece p = ChessPiece.get(1, color);
            for (int x = 0; x < 8; x++) {
                Square s = Square.get(x, ys[color]);
                assert Promotion.isEligible(p, s);
            }
        }
    }

    @Test
    public void testIsOneStepAway() {
        int[] ys = {6, 1};
        for (int color = 0; color < 2; color++) {
            ChessPiece p = ChessPiece.get(1, color);
            for (int x = 0; x < 8; x++) {
                Square s = Square.get(x, ys[color]);
                assert Promotion.isOneStepAway(p, s);
            }
        }
    }
}
