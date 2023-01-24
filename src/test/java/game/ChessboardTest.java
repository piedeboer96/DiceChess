package game;

import org.junit.jupiter.api.Test;

final class ChessboardTest {
    @Test
    public void testUpdate() {
        Square s = Square.get(0, 7);
        Chessboard b = new Chessboard();
        b.update("8/8/8/8/8/8/8/K7");
        assert b.read(s) == ChessPiece.get(6, 1);
    }

    @Test
    public void testVisualize() {
        Chessboard b = new Chessboard("b7/1k6/2n5/3p4/4q3/5r2/8/BNR2KQP");
        String s = """
                  +---+---+---+---+---+---+---+---+
                8 | b |   |   |   |   |   |   |   |
                  +---+---+---+---+---+---+---+---+
                7 |   | k |   |   |   |   |   |   |
                  +---+---+---+---+---+---+---+---+
                6 |   |   | n |   |   |   |   |   |
                  +---+---+---+---+---+---+---+---+
                5 |   |   |   | p |   |   |   |   |
                  +---+---+---+---+---+---+---+---+
                4 |   |   |   |   | q |   |   |   |
                  +---+---+---+---+---+---+---+---+
                3 |   |   |   |   |   | r |   |   |
                  +---+---+---+---+---+---+---+---+
                2 |   |   |   |   |   |   |   |   |
                  +---+---+---+---+---+---+---+---+
                1 | B | N | R |   |   | K | Q | P |
                  +---+---+---+---+---+---+---+---+
                    a   b   c   d   e   f   g   h""";
        assert b.visualize().equals(s);
    }

    @Test
    public void testToString() {
        Chessboard b = new Chessboard("b7/1k6/2n5/3p4/4q3/5r2/8/BNR2KQP");
        assert b.toString().equals("b7/1k6/2n5/3p4/4q3/5r2/8/BNR2KQP");
    }

    @Test
    public void validateAccess() {
        Chessboard b = new Chessboard();
        for (char file = 97; file < 105; file++) {
            for (int rank = 1; rank < 9; rank++) {
                Square s = Square.get(file, rank);
                assert b.read(s) == null;
                for (int type = 1; type < 7; type++) {
                    for (int color = 0; color < 2; color++) {
                        ChessPiece p = ChessPiece.get(type, color);
                        b.store(p, s);
                        assert b.read(s) == p;
                    }
                }
            }
        }
    }
}
