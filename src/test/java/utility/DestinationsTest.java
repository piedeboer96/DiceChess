package utility;

import game.ChessPiece;
import game.Chessboard;
import game.Square;
import org.junit.jupiter.api.Test;

import java.util.List;

final class DestinationsTest {
    /* *
     *    +---+---+---+---+---+---+---+---+
     *  8 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  7 |   | b |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  6 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  5 |   |   |   | P |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  4 |   |   |   |   | p |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  3 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  2 |   |   |   |   |   |   | B |   |
     *    +---+---+---+---+---+---+---+---+
     *  1 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *      a   b   c   d   e   f   g   h
     * */

    @Test
    public void testDetermineForBlackBishop() {
        Chessboard b = new Chessboard("8/1b6/8/3P4/4p3/8/6B1/8");
        ChessPiece p = ChessPiece.get(3, 0);
        Square s = Square.get(1, 1);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 5;
        assert l.contains(Square.get(0, 0)); // N+1 W+1
        assert l.contains(Square.get(2, 0)); // N+1 E+1
        assert l.contains(Square.get(0, 2)); // S+1 W+1
        assert l.contains(Square.get(2, 2)); // S+1 E+1
        assert l.contains(Square.get(3, 3)); // S+2 E+2
    }

    @Test
    public void testDetermineForWhiteBishop() {
        Chessboard b = new Chessboard("8/1b6/8/3P4/4p3/8/6B1/8");
        ChessPiece p = ChessPiece.get(3, 1);
        Square s = Square.get(6, 6);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 5;
        assert l.contains(Square.get(4, 4)); // N+2 W+2
        assert l.contains(Square.get(5, 5)); // N+1 W+1
        assert l.contains(Square.get(7, 5)); // N+1 E+1
        assert l.contains(Square.get(5, 7)); // S+1 W+1
        assert l.contains(Square.get(7, 7)); // S+1 E+1
    }

    /* *
     *    +---+---+---+---+---+---+---+---+
     *  8 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  7 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  6 |   | n |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  5 |   |   | K |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  4 |   |   |   |   |   | k |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  3 |   |   |   |   |   |   | N |   |
     *    +---+---+---+---+---+---+---+---+
     *  2 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  1 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *      a   b   c   d   e   f   g   h
     * */

    @Test
    public void testDetermineForBlackKing() {
        Chessboard b = new Chessboard("8/8/1n6/2K5/5k2/6N1/8/8");
        ChessPiece p = ChessPiece.get(6, 0);
        Square s = Square.get('f', 4);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 8;
        assert l.contains(Square.get('e', 5)); // N+1 W+1
        assert l.contains(Square.get('f', 5)); // N+1
        assert l.contains(Square.get('g', 5)); // N+1 E+1
        assert l.contains(Square.get('e', 4)); // W+1
        assert l.contains(Square.get('g', 4)); // E+1
        assert l.contains(Square.get('e', 3)); // S+1 W+1
        assert l.contains(Square.get('f', 3)); // S+1
        assert l.contains(Square.get('g', 3)); // S+1 E+1
    }

    @Test
    public void testDetermineForWhiteKing() {
        Chessboard b = new Chessboard("8/8/1n6/2K5/5k2/6N1/8/8");
        ChessPiece p = ChessPiece.get(6, 1);
        Square s = Square.get('c', 5);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 8;
        assert l.contains(Square.get('b', 6)); // N+1 W+1
        assert l.contains(Square.get('c', 6)); // N+1
        assert l.contains(Square.get('d', 6)); // N+1 E+1
        assert l.contains(Square.get('b', 5)); // W+1
        assert l.contains(Square.get('d', 5)); // E+1
        assert l.contains(Square.get('b', 4)); // S+1 W+1
        assert l.contains(Square.get('c', 4)); // S+1
        assert l.contains(Square.get('d', 4)); // S+1 E+1
    }

    /* *
     *    +---+---+---+---+---+---+---+---+
     *  8 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  7 |   | n |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  6 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  5 |   |   | P |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  4 |   |   |   |   |   | p |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  3 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  2 |   |   |   |   |   |   | N |   |
     *    +---+---+---+---+---+---+---+---+
     *  1 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *      a   b   c   d   e   f   g   h
     * */

    @Test
    public void testDetermineForBlackKnight() {
        Chessboard b = new Chessboard("8/1n6/8/2P5/5p2/8/6N1/8");
        ChessPiece p = ChessPiece.get(2, 0);
        Square s = Square.get(1, 1);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 4;
        assert l.contains(Square.get('d', 8)); // N+1 E+2
        assert l.contains(Square.get('d', 6)); // S+1 E+2
        assert l.contains(Square.get('a', 5)); // S+2 W+1
        assert l.contains(Square.get('c', 5)); // S+2 E+1
    }

    @Test
    public void testDetermineForWhiteKnight() {
        Chessboard b = new Chessboard("8/1n6/8/3P4/4p3/8/6N1/8");
        ChessPiece p = ChessPiece.get(2, 1);
        Square s = Square.get(6, 6);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 4;
        assert l.contains(Square.get('f', 4)); // N+2 W+1
        assert l.contains(Square.get('h', 4)); // N+2 E+1
        assert l.contains(Square.get('e', 3)); // N+1 W+2
        assert l.contains(Square.get('e', 1)); // S+1 W+2
    }

    /* *
     *    +---+---+---+---+---+---+---+---+
     *  8 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  7 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  6 |   | n |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  5 |   |   | P |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  4 |   |   |   |   |   | p |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  3 |   |   |   |   |   |   | N |   |
     *    +---+---+---+---+---+---+---+---+
     *  2 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  1 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *      a   b   c   d   e   f   g   h
     * */

    @Test
    public void testDetermineForBlackPawn() {
        Chessboard b = new Chessboard("8/8/1n6/2P5/5p2/6N1/8/8");
        ChessPiece p = ChessPiece.get(1, 0);
        Square s = Square.get('f', 4);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 2;
        assert l.contains(Square.get('f', 3)); // S+1
        assert l.contains(Square.get('g', 3)); // S+1 E+1
    }

    @Test
    public void testDetermineForWhitePawn() {
        Chessboard b = new Chessboard("8/8/1n6/2P5/5p2/6N1/8/8");
        ChessPiece p = ChessPiece.get(1, 1);
        Square s = Square.get('c', 5);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 2;
        assert l.contains(Square.get('c', 6)); // N+1
        assert l.contains(Square.get('b', 6)); // N+1 W+1
    }

    /* *
     *    +---+---+---+---+---+---+---+---+
     *  8 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  7 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  6 |   | n |   |   |   |   | P |   |
     *    +---+---+---+---+---+---+---+---+
     *  5 |   | q | K |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  4 |   |   |   |   |   | k | Q |   |
     *    +---+---+---+---+---+---+---+---+
     *  3 |   | p |   |   |   |   | N |   |
     *    +---+---+---+---+---+---+---+---+
     *  2 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  1 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *      a   b   c   d   e   f   g   h
     * */

    @Test
    public void testDetermineForBlackQueen() {
        Chessboard b = new Chessboard("8/8/1n4P1/1qK5/5kQ1/1p4N1/8/8");
        ChessPiece p = ChessPiece.get(5, 0);
        Square s = Square.get('b', 5);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 12;

    }

    @Test
    public void testDetermineForWhiteQueen() {
        Chessboard b = new Chessboard("8/8/1n4P1/1qK5/5kQ1/1p4N1/8/8");
        ChessPiece p = ChessPiece.get(5, 1);
        Square s = Square.get('g', 4);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 12;
    }

    /* *
     *    +---+---+---+---+---+---+---+---+
     *  8 |   |   | r |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  7 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  6 |   | n |   |   |   |   | P |   |
     *    +---+---+---+---+---+---+---+---+
     *  5 |   | q | K |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  4 |   |   |   |   |   | k | Q |   |
     *    +---+---+---+---+---+---+---+---+
     *  3 |   | p |   |   |   |   | N |   |
     *    +---+---+---+---+---+---+---+---+
     *  2 |   |   |   |   |   |   |   |   |
     *    +---+---+---+---+---+---+---+---+
     *  1 |   |   |   |   |   | R |   |   |
     *    +---+---+---+---+---+---+---+---+
     *      a   b   c   d   e   f   g   h
     * */

    @Test
    public void testDetermineForBlackRook() {
        Chessboard b = new Chessboard("2r5/8/1n4P1/1qK5/5kQ1/1p4N1/8/5R2");
        ChessPiece p = ChessPiece.get(4, 0);
        Square s = Square.get('c', 8);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 10;

    }

    @Test
    public void testDetermineForWhiteRook() {
        Chessboard b = new Chessboard("8/8/1n4P1/1qK5/5kQ1/1p4N1/8/8");
        ChessPiece p = ChessPiece.get(4, 1);
        Square s = Square.get('f', 1);
        List<Square> l = Destinations.determine(p, s, b);
        assert l.size() == 10;
    }
}
