package solutions.chess.board;

import org.junit.jupiter.api.Test;
import utility.Exam;

public final class SquareTest implements Exam {
    @Override @Test
    public void take() {
        testColumn();
        testRow();
        testGet();
        testFile();
        testRank();
        testFileToColumn();
        testRankToRow();
        testTranslate();
    }

    @Test
    public void testColumn() {
        assert Square.A8.column() == 0; assert Square.B8.column() == 1; assert Square.C8.column() == 2; assert Square.D8.column() == 3; assert Square.E8.column() == 4; assert Square.F8.column() == 5; assert Square.G8.column() == 6; assert Square.H8.column() == 7;
        assert Square.A7.column() == 0; assert Square.B7.column() == 1; assert Square.C7.column() == 2; assert Square.D7.column() == 3; assert Square.E7.column() == 4; assert Square.F7.column() == 5; assert Square.G7.column() == 6; assert Square.H7.column() == 7;
        assert Square.A6.column() == 0; assert Square.B6.column() == 1; assert Square.C6.column() == 2; assert Square.D6.column() == 3; assert Square.E6.column() == 4; assert Square.F6.column() == 5; assert Square.G6.column() == 6; assert Square.H6.column() == 7;
        assert Square.A5.column() == 0; assert Square.B5.column() == 1; assert Square.C5.column() == 2; assert Square.D5.column() == 3; assert Square.E5.column() == 4; assert Square.F5.column() == 5; assert Square.G5.column() == 6; assert Square.H5.column() == 7;
        assert Square.A4.column() == 0; assert Square.B4.column() == 1; assert Square.C4.column() == 2; assert Square.D4.column() == 3; assert Square.E4.column() == 4; assert Square.F4.column() == 5; assert Square.G4.column() == 6; assert Square.H4.column() == 7;
        assert Square.A3.column() == 0; assert Square.B3.column() == 1; assert Square.C3.column() == 2; assert Square.D3.column() == 3; assert Square.E3.column() == 4; assert Square.F3.column() == 5; assert Square.G3.column() == 6; assert Square.H3.column() == 7;
        assert Square.A2.column() == 0; assert Square.B2.column() == 1; assert Square.C2.column() == 2; assert Square.D2.column() == 3; assert Square.E2.column() == 4; assert Square.F2.column() == 5; assert Square.G2.column() == 6; assert Square.H2.column() == 7;
        assert Square.A1.column() == 0; assert Square.B1.column() == 1; assert Square.C1.column() == 2; assert Square.D1.column() == 3; assert Square.E1.column() == 4; assert Square.F1.column() == 5; assert Square.G1.column() == 6; assert Square.H1.column() == 7;
    }

    @Test
    public void testFile() {
        assert Square.A8.file() == 'a'; assert Square.B8.file() == 'b'; assert Square.C8.file() == 'c'; assert Square.D8.file() == 'd'; assert Square.E8.file() == 'e'; assert Square.F8.file() == 'f'; assert Square.G8.file() == 'g'; assert Square.H8.file() == 'h';
        assert Square.A7.file() == 'a'; assert Square.B7.file() == 'b'; assert Square.C7.file() == 'c'; assert Square.D7.file() == 'd'; assert Square.E7.file() == 'e'; assert Square.F7.file() == 'f'; assert Square.G7.file() == 'g'; assert Square.H7.file() == 'h';
        assert Square.A6.file() == 'a'; assert Square.B6.file() == 'b'; assert Square.C6.file() == 'c'; assert Square.D6.file() == 'd'; assert Square.E6.file() == 'e'; assert Square.F6.file() == 'f'; assert Square.G6.file() == 'g'; assert Square.H6.file() == 'h';
        assert Square.A5.file() == 'a'; assert Square.B5.file() == 'b'; assert Square.C5.file() == 'c'; assert Square.D5.file() == 'd'; assert Square.E5.file() == 'e'; assert Square.F5.file() == 'f'; assert Square.G5.file() == 'g'; assert Square.H5.file() == 'h';
        assert Square.A4.file() == 'a'; assert Square.B4.file() == 'b'; assert Square.C4.file() == 'c'; assert Square.D4.file() == 'd'; assert Square.E4.file() == 'e'; assert Square.F4.file() == 'f'; assert Square.G4.file() == 'g'; assert Square.H4.file() == 'h';
        assert Square.A3.file() == 'a'; assert Square.B3.file() == 'b'; assert Square.C3.file() == 'c'; assert Square.D3.file() == 'd'; assert Square.E3.file() == 'e'; assert Square.F3.file() == 'f'; assert Square.G3.file() == 'g'; assert Square.H3.file() == 'h';
        assert Square.A2.file() == 'a'; assert Square.B2.file() == 'b'; assert Square.C2.file() == 'c'; assert Square.D2.file() == 'd'; assert Square.E2.file() == 'e'; assert Square.F2.file() == 'f'; assert Square.G2.file() == 'g'; assert Square.H2.file() == 'h';
        assert Square.A1.file() == 'a'; assert Square.B1.file() == 'b'; assert Square.C1.file() == 'c'; assert Square.D1.file() == 'd'; assert Square.E1.file() == 'e'; assert Square.F1.file() == 'f'; assert Square.G1.file() == 'g'; assert Square.H1.file() == 'h';
    }

    @Test
    public void testFileToColumn() {
        char[] files = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        for (char c : files) { assert Square.fileToColumn(c) == (c - 97); }
    }

    @Test
    public void testGet() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Square s = Square.get(row, column);
                assert s.row() == row;
                assert s.column() == column;
            }
        }
    }

    @Test
    public void testRank() {
        assert Square.A8.rank() == 8; assert Square.B8.rank() == 8; assert Square.C8.rank() == 8; assert Square.D8.rank() == 8; assert Square.E8.rank() == 8; assert Square.F8.rank() == 8; assert Square.G8.rank() == 8; assert Square.H8.rank() == 8;
        assert Square.A7.rank() == 7; assert Square.B7.rank() == 7; assert Square.C7.rank() == 7; assert Square.D7.rank() == 7; assert Square.E7.rank() == 7; assert Square.F7.rank() == 7; assert Square.G7.rank() == 7; assert Square.H7.rank() == 7;
        assert Square.A6.rank() == 6; assert Square.B6.rank() == 6; assert Square.C6.rank() == 6; assert Square.D6.rank() == 6; assert Square.E6.rank() == 6; assert Square.F6.rank() == 6; assert Square.G6.rank() == 6; assert Square.H6.rank() == 6;
        assert Square.A5.rank() == 5; assert Square.B5.rank() == 5; assert Square.C5.rank() == 5; assert Square.D5.rank() == 5; assert Square.E5.rank() == 5; assert Square.F5.rank() == 5; assert Square.G5.rank() == 5; assert Square.H5.rank() == 5;
        assert Square.A4.rank() == 4; assert Square.B4.rank() == 4; assert Square.C4.rank() == 4; assert Square.D4.rank() == 4; assert Square.E4.rank() == 4; assert Square.F4.rank() == 4; assert Square.G4.rank() == 4; assert Square.H4.rank() == 4;
        assert Square.A3.rank() == 3; assert Square.B3.rank() == 3; assert Square.C3.rank() == 3; assert Square.D3.rank() == 3; assert Square.E3.rank() == 3; assert Square.F3.rank() == 3; assert Square.G3.rank() == 3; assert Square.H3.rank() == 3;
        assert Square.A2.rank() == 2; assert Square.B2.rank() == 2; assert Square.C2.rank() == 2; assert Square.D2.rank() == 2; assert Square.E2.rank() == 2; assert Square.F2.rank() == 2; assert Square.G2.rank() == 2; assert Square.H2.rank() == 2;
        assert Square.A1.rank() == 1; assert Square.B1.rank() == 1; assert Square.C1.rank() == 1; assert Square.D1.rank() == 1; assert Square.E1.rank() == 1; assert Square.F1.rank() == 1; assert Square.G1.rank() == 1; assert Square.H1.rank() == 1;
    }

    @Test
    public void testRankToRow() {
        for (int rank = 1; rank < 9; rank++) {
            assert Square.rankToRow(rank) == (8 - rank);
        }
    }

    @Test
    public void testRow() {
        assert Square.A8.row() == 0; assert Square.B8.row() == 0; assert Square.C8.row() == 0; assert Square.D8.row() == 0; assert Square.E8.row() == 0; assert Square.F8.row() == 0; assert Square.G8.row() == 0; assert Square.H8.row() == 0;
        assert Square.A7.row() == 1; assert Square.B7.row() == 1; assert Square.C7.row() == 1; assert Square.D7.row() == 1; assert Square.E7.row() == 1; assert Square.F7.row() == 1; assert Square.G7.row() == 1; assert Square.H7.row() == 1;
        assert Square.A6.row() == 2; assert Square.B6.row() == 2; assert Square.C6.row() == 2; assert Square.D6.row() == 2; assert Square.E6.row() == 2; assert Square.F6.row() == 2; assert Square.G6.row() == 2; assert Square.H6.row() == 2;
        assert Square.A5.row() == 3; assert Square.B5.row() == 3; assert Square.C5.row() == 3; assert Square.D5.row() == 3; assert Square.E5.row() == 3; assert Square.F5.row() == 3; assert Square.G5.row() == 3; assert Square.H5.row() == 3;
        assert Square.A4.row() == 4; assert Square.B4.row() == 4; assert Square.C4.row() == 4; assert Square.D4.row() == 4; assert Square.E4.row() == 4; assert Square.F4.row() == 4; assert Square.G4.row() == 4; assert Square.H4.row() == 4;
        assert Square.A3.row() == 5; assert Square.B3.row() == 5; assert Square.C3.row() == 5; assert Square.D3.row() == 5; assert Square.E3.row() == 5; assert Square.F3.row() == 5; assert Square.G3.row() == 5; assert Square.H3.row() == 5;
        assert Square.A2.row() == 6; assert Square.B2.row() == 6; assert Square.C2.row() == 6; assert Square.D2.row() == 6; assert Square.E2.row() == 6; assert Square.F2.row() == 6; assert Square.G2.row() == 6; assert Square.H2.row() == 6;
        assert Square.A1.row() == 7; assert Square.B1.row() == 7; assert Square.C1.row() == 7; assert Square.D1.row() == 7; assert Square.E1.row() == 7; assert Square.F1.row() == 7; assert Square.G1.row() == 7; assert Square.H1.row() == 7;
    }

    @Test
    public void testTranslate() {
        char[] files = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Square s = Square.get(row, column);
                String infoField = files[column] + String.valueOf(8 - row);
                assert Square.translate(s).equals(infoField);
                assert Square.translate(infoField) == s;
            }
        }
    }
}
