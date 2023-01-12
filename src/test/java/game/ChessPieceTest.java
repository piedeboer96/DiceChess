package game;

import org.junit.jupiter.api.Test;

final class ChessPieceTest {
    @Test
    public void validateGet() {
        char[][] FEN = {{}, {'p', 'P'}, {'n', 'N'}, {'b', 'B'}, {'r', 'R'}, {'q', 'Q'}, {'k', 'K'} };
        for (int type = 1; type < 7; type++) {
            for (int color = 0; color < 2; color++) {
                ChessPiece p = ChessPiece.get(type, color);
                assert p != null;
                assert p.type() == type;
                assert p.color() == color;
                assert p.toString().equals(String.valueOf(FEN[type][color]));
                assert p == ChessPiece.get(FEN[type][color]);
            }
        }
    }
}
