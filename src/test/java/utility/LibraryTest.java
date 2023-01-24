package utility;

import game.ChessPiece;
import org.junit.jupiter.api.Test;

final class LibraryTest {
    @Test
    public void validateNorth() {
        assert Library.NORTH.getX() == 0;
        assert Library.NORTH.getY() == 1;
    }

    @Test
    public void validateSouth() {
        assert Library.SOUTH.getX() == 0;
        assert Library.SOUTH.getY() == -1;
    }

    @Test
    public void validateWest() {
        assert Library.WEST.getX() == -1;
        assert Library.WEST.getY() == 0;
    }

    @Test
    public void validateEast() {
        assert Library.EAST.getX() == 1;
        assert Library.EAST.getY() == 0;
    }

    @Test
    public void validateNorthWest() {
        assert Library.NORTH_WEST.getX() == -1;
        assert Library.NORTH_WEST.getY() == 1;
    }

    @Test
    public void validateSouthEast() {
        assert Library.SOUTH_EAST.getX() == 1;
        assert Library.SOUTH_EAST.getY() == -1;
    }

    @Test
    public void validateNorthEast() {
        assert Library.NORTH_EAST.getX() == 1;
        assert Library.NORTH_EAST.getY() == 1;
    }

    @Test
    public void validateSouthWest() {
        assert Library.SOUTH_WEST.getX() == -1;
        assert Library.SOUTH_WEST.getY() == -1;
    }

    @Test
    public void validateNorth1West2() {
        assert Library.NORTH1_WEST2.getX() == -2;
        assert Library.NORTH1_WEST2.getY() == 1;
    }

    @Test
    public void validateSouth1East2() {
        assert Library.SOUTH1_EAST2.getX() == 2;
        assert Library.SOUTH1_EAST2.getY() == -1;
    }

    @Test
    public void validateNorth1East2() {
        assert Library.NORTH1_EAST2.getX() == 2;
        assert Library.NORTH1_EAST2.getY() == 1;
    }

    @Test
    public void validateSouth1West2() {
        assert Library.SOUTH1_WEST2.getX() == -2;
        assert Library.SOUTH1_WEST2.getY() == -1;
    }

    @Test
    public void validateNorth2West1() {
        assert Library.NORTH2_WEST1.getX() == -1;
        assert Library.NORTH2_WEST1.getY() == 2;
    }

    @Test
    public void validateSouth2East1() {
        assert Library.SOUTH2_EAST1.getX() == 1;
        assert Library.SOUTH2_EAST1.getY() == -2;
    }

    @Test
    public void validateNorth2East1() {
        assert Library.NORTH2_EAST1.getX() == 1;
        assert Library.NORTH2_EAST1.getY() == 2;
    }

    @Test
    public void validateSouth2West1() {
        assert Library.SOUTH2_WEST1.getX() == -1;
        assert Library.SOUTH2_WEST1.getY() == -2;
    }

    @Test
    public void validateAxis() {
        assert Library.AXIS[0] == Library.NORTH;
        assert Library.AXIS[1] == Library.SOUTH;
        assert Library.AXIS[2] == Library.WEST;
        assert Library.AXIS[3] == Library.EAST;
    }

    @Test
    public void validateDiagonal() {
        assert Library.DIAGONAL[0] == Library.NORTH_WEST;
        assert Library.DIAGONAL[1] == Library.SOUTH_EAST;
        assert Library.DIAGONAL[2] == Library.NORTH_EAST;
        assert Library.DIAGONAL[3] == Library.SOUTH_WEST;
    }

    @Test
    public void validateKnight() {
        assert Library.KNIGHT[0] == Library.NORTH1_WEST2;
        assert Library.KNIGHT[1] == Library.SOUTH1_EAST2;
        assert Library.KNIGHT[2] == Library.NORTH1_EAST2;
        assert Library.KNIGHT[3] == Library.SOUTH1_WEST2;
        assert Library.KNIGHT[4] == Library.NORTH2_WEST1;
        assert Library.KNIGHT[5] == Library.SOUTH2_EAST1;
        assert Library.KNIGHT[6] == Library.NORTH2_EAST1;
        assert Library.KNIGHT[7] == Library.SOUTH2_WEST1;
    }

    @Test
    public void validateMonarch() {
        assert Library.MONARCH[0] == Library.NORTH;
        assert Library.MONARCH[1] == Library.SOUTH;
        assert Library.MONARCH[2] == Library.WEST;
        assert Library.MONARCH[3] == Library.EAST;
        assert Library.MONARCH[4] == Library.NORTH_WEST;
        assert Library.MONARCH[5] == Library.SOUTH_EAST;
        assert Library.MONARCH[6] == Library.NORTH_EAST;
        assert Library.MONARCH[7] == Library.SOUTH_WEST;
    }

    @Test
    public void validatePawns() {
        assert Library.PAWNS[0][0] == Library.SOUTH;
        assert Library.PAWNS[0][1] == Library.SOUTH_WEST;
        assert Library.PAWNS[0][2] == Library.SOUTH_EAST;
        assert Library.PAWNS[1][0] == Library.NORTH;
        assert Library.PAWNS[1][1] == Library.NORTH_WEST;
        assert Library.PAWNS[1][2] == Library.NORTH_EAST;
    }

    @Test
    public void validateVectorsOfBishop() {
        for (int color = 0; color < 2; color++) {
            ChessPiece p = ChessPiece.get(3, color);
            Vector[] vectors = Library.vectorsOf(p);
            assert vectors == Library.DIAGONAL;
        }
    }

    @Test
    public void validateVectorsOfKing() {
        for (int color = 0; color < 2; color++) {
            ChessPiece p = ChessPiece.get(6, color);
            Vector[] vectors = Library.vectorsOf(p);
            assert vectors == Library.MONARCH;
        }
    }

    @Test
    public void validateVectorsOfKnight() {
        for (int color = 0; color < 2; color++) {
            ChessPiece p = ChessPiece.get(2, color);
            Vector[] vectors = Library.vectorsOf(p);
            assert vectors == Library.KNIGHT;
        }
    }

    @Test
    public void validateVectorsOfPawn() {
        for (int color = 0; color < 2; color++) {
            ChessPiece p = ChessPiece.get(1, color);
            Vector[] vectors = Library.vectorsOf(p);
            assert vectors == Library.PAWNS[color];
        }
    }

    @Test
    public void validateVectorsOfQueen() {
        for (int color = 0; color < 2; color++) {
            ChessPiece p = ChessPiece.get(5, color);
            Vector[] vectors = Library.vectorsOf(p);
            assert vectors == Library.MONARCH;
        }
    }

    @Test
    public void validateVectorsOfRook() {
        for (int color = 0; color < 2; color++) {
            ChessPiece p = ChessPiece.get(4, color);
            Vector[] vectors = Library.vectorsOf(p);
            assert vectors == Library.AXIS;
        }
    }
}
