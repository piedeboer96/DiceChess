package solutions.chess.board;

import org.junit.jupiter.api.Test;
import utility.Exam;

public final class MovementTest implements Exam {
    @Override @Test
    public void take() {
        testDeltaColumn();
        testDeltaRow();
        testApply();
    }

    @Test
    public void testApply() {
        Move[] moves = Move.values();
        for (Move m : moves) {
            for (int row = 0; row < 8; row++) {
                int rowSum = row + m.deltaRow();
                boolean rowInvalid = rowSum < 0 || rowSum > 7;
                for (int column = 0; column < 8; column++) {
                    int columnSum = column + m.deltaColumn();
                    boolean columnInvalid = columnSum < 0 || columnSum > 7;
                    Square s = Square.get(row, column);
                    if (rowInvalid || columnInvalid) {
                        try { m.apply(s); assert false; }
                        catch (IllegalArgumentException ignored){}
                    } else { assert m.apply(s) == Square.get(rowSum, columnSum); }
                }
            }
        }
    }

    @Test
    public void testDeltaColumn() {
        assert Move.NORTH.deltaColumn() ==  0; assert Move.SOUTH.deltaColumn() == 0;
        assert Move.WEST.deltaColumn()  == -1; assert Move.EAST.deltaColumn()  == 1;

        assert Move.NORTH_WEST.deltaColumn() == -1; assert Move.SOUTH_EAST.deltaColumn() ==  1;
        assert Move.NORTH_EAST.deltaColumn() ==  1; assert Move.SOUTH_WEST.deltaColumn() == -1;

        assert Move.L_NORTH_WEST.deltaColumn() == -1; assert Move.L_SOUTH_EAST.deltaColumn() ==  1;
        assert Move.L_NORTH_EAST.deltaColumn() ==  1; assert Move.L_SOUTH_WEST.deltaColumn() == -1;

        assert Move.L_WEST_NORTH.deltaColumn() == -2; assert Move.L_EAST_SOUTH.deltaColumn() ==  2;
        assert Move.L_WEST_SOUTH.deltaColumn() == -2; assert Move.L_EAST_NORTH.deltaColumn() ==  2;
    }

    @Test
    public void testDeltaRow() {
        assert Move.NORTH.deltaRow() == -1; assert Move.SOUTH.deltaRow() == 1;
        assert Move.WEST.deltaRow()  ==  0; assert Move.EAST.deltaRow()  == 0;

        assert Move.NORTH_WEST.deltaRow() == -1; assert Move.SOUTH_EAST.deltaRow() ==  1;
        assert Move.NORTH_EAST.deltaRow() == -1; assert Move.SOUTH_WEST.deltaRow() ==  1;

        assert Move.L_NORTH_WEST.deltaRow() == -2; assert Move.L_SOUTH_EAST.deltaRow() ==  2;
        assert Move.L_NORTH_EAST.deltaRow() == -2; assert Move.L_SOUTH_WEST.deltaRow() ==  2;

        assert Move.L_WEST_NORTH.deltaRow() == -1; assert Move.L_EAST_SOUTH.deltaRow() ==  1;
        assert Move.L_WEST_SOUTH.deltaRow() ==  1; assert Move.L_EAST_NORTH.deltaRow() == -1;
    }
}
