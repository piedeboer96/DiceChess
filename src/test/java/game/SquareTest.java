package game;

import org.junit.jupiter.api.Test;

final class SquareTest {
    @Test
    public void validateGet() {
        for (char file = 97; file < 105; file++) {
            int x = (file - 97);
            for (int rank = 1; rank < 9; rank++) {
                int y = (8 - rank);
                Square s = Square.get(file, rank);
                assert s != null;
                assert s.file() == file;
                assert s.rank() == rank;
                assert s.getX() == x;
                assert s.getY() == y;
                assert s.toString().equals(file + String.valueOf(rank));
                assert Square.get(x, y) == s;
            }
        }
    }
}
