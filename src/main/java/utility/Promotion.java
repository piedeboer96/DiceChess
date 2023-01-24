package utility;

import game.ChessPiece;
import game.Square;

/**
 * Defines a rule to determine whether a pawn is eligible for promotion.
 **/
public final class Promotion {
    private static final int[] ELIGIBLE_YS = {7, 0};
    private static final int[] ONE_STEP_AWAY_YS = {6, 1};

    private Promotion() {
    }

    /**
     * Determines whether a chess piece is promotable.
     *
     * @param p The chess piece for which the promotion eligibility should be checked.
     * @param s The square the chess piece is at.
     * @return True, if the chess piece is a promotable pawn. <br>
     *         False, otherwise.
     **/
    public static boolean isEligible(ChessPiece p, Square s) {
        return p.type() == 1 && s.getY() == ELIGIBLE_YS[p.color()];
    }

    /**
     * Determines whether a chess piece is one step away from being promoted.
     *
     * @param p The chess piece for which the promotion eligibility should be checked.
     * @param s The square the chess piece is at.
     * @return True, if the chess piece is a pawn that is one step away from promotion. <br>
     *         False, otherwise.
     **/
    public static boolean isOneStepAway(ChessPiece p, Square s) {
        return p.type() == 1 && s.getY() == ONE_STEP_AWAY_YS[p.color()];
    }
}
