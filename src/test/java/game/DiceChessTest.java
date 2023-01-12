package game;

import org.junit.jupiter.api.Test;

import java.util.List;

final class DiceChessTest {
    @Test
    public void testGetBoard() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 0 1");
        Chessboard b = game.getBoard();
        assert b.toString().equals("8/8/8/8/8/8/8/kK6");
    }

    @Test
    public void testGetDie() {
        DiceChess game = new DiceChess();
        assert game.getDie() != null;
    }

    @Test
    public void testGetFullMoves() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 0 1");
        assert game.getFullMoves() == 1;
    }

    @Test
    public void testGetHalfMoves() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 0 1");
        assert game.getHalfMoves() == 0;
    }

    @Test
    public void testGetState() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 0 1");
        assert game.getState() == GameState.ONGOING;
    }

    @Test
    public void testToString() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 0 1");
        assert game.toString().equals("8/8/8/8/8/8/8/kK6 w - - 0 1");
    }

    @Test
    public void testGetBlackAsActiveColor() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 b - - 0 1");
        assert game.getActiveColor() == 0;
    }

    @Test
    public void testGetWhiteAsActiveColor() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 0 1");
        assert game.getActiveColor() == 1;
    }

    @Test
    public void testSwitchActiveColorAsBlack() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 b - - 0 1");
        game.switchActiveColor();
        assert game.getActiveColor() == 1;
        assert game.getFullMoves() == 2;
    }

    @Test
    public void testSwitchActiveColorAsWhite() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 0 1");
        game.switchActiveColor();
        assert game.getActiveColor() == 0;
    }

    @Test
    public void testRevert() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 0 1");
        Movement m = new Movement(Square.get('b', 1), Square.get('a', 2));
        game.register(m);
        game.switchActiveColor();
        game.revert();
        assert game.toString().equals("8/8/8/8/8/8/8/kK6 w - - 0 1");
    }

    @Test
    public void testGameEndsOnBlackKingCapture() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 0 1");
        Movement m = new Movement(Square.get('b', 1), Square.get('a', 1));
        game.register(m);
        assert game.getState() == GameState.WHITE_WON;
    }

    @Test
    public void testGameEndsOnWhiteKingCapture() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 b - - 0 1");
        Movement m = new Movement(Square.get('a', 1), Square.get('b', 1));
        game.register(m);
        assert game.getState() == GameState.BLACK_WON;
    }

    @Test
    public void testGameEndsAsDrawOnBlackMove() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 b - - 49 1");
        Movement m = new Movement(Square.get('a', 1), Square.get('b', 2));
        game.register(m);
        assert game.getState() == GameState.DRAW;
    }

    @Test
    public void testGameEndsAsDrawOnWhiteMove() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 49 1");
        Movement m = new Movement(Square.get('b', 1), Square.get('a', 2));
        game.register(m);
        assert game.getState() == GameState.DRAW;
    }

    @Test
    public void testGameStartsAsBlackWon() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/k7 w - - 0 1");
        assert game.getState() == GameState.BLACK_WON;
    }

    @Test
    public void testGameStartsAsWhiteWon() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/K7 w - - 0 1");
        assert game.getState() == GameState.WHITE_WON;
    }

    @Test
    public void testGameStartsAsDraw() {
        DiceChess game = new DiceChess("8/8/8/8/8/8/8/kK6 w - - 50 1");
        assert game.getState() == GameState.DRAW;
    }

    @Test
    public void testPromote() {
        DiceChess game = new DiceChess("P7/8/8/8/8/8/8/kK6 w - - 0 1");
        Square s = Square.get('a', 8);
        ChessPiece p = ChessPiece.get(5, 1);
        game.promote(s, p);
        assert game.getBoard().read(s) == p;
    }

    @Test
    public void testTeamOpportunitiesRollExcluded() {
        DiceChess game = new DiceChess("R7/8/8/8/8/8/8/kK6 w - - 0 1");
        List<Opportunity> l = game.getTeamOpportunities(1);
        assert l.size() == 2;
    }

    @Test
    public void testTeamOpportunitiesRollIncluded() {
        DiceChess game = new DiceChess("R7/8/8/8/8/8/8/kK6 w - - 0 1");
        List<Opportunity> l = game.getTeamOpportunities(1, 4);
        assert l.size() == 1;
    }
}
