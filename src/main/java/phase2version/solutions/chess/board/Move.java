package phase2version.solutions.chess.board;

import phase2version.framework.game.Location;
import phase2version.framework.game.Movement;

public enum Move implements Movement {
    NORTH        (-1, 0), SOUTH        ( 1, 0),
    WEST         ( 0,-1), EAST         ( 0, 1),

    NORTH_WEST   (-1,-1), SOUTH_EAST   ( 1, 1),
    NORTH_EAST   (-1, 1), SOUTH_WEST   ( 1,-1),

    L_NORTH_WEST (-2,-1), L_SOUTH_EAST ( 2, 1),
    L_NORTH_EAST (-2, 1), L_SOUTH_WEST( 2,-1),

    L_WEST_NORTH (-1,-2), L_EAST_SOUTH ( 1, 2),
    L_WEST_SOUTH ( 1,-2), L_EAST_NORTH (-1, 2);

    private final int DELTA_ROW, DELTA_COLUMN;

    Move(int deltaRow, int deltaColumn) {
        if      (deltaRow    < -7 || deltaRow    > 7) { throw new IllegalArgumentException("Row delta is not valid!")   ; }
        else if (deltaColumn < -7 || deltaColumn > 7) { throw new IllegalArgumentException("Column delta is not valid!"); }
        DELTA_ROW = deltaRow;
        DELTA_COLUMN = deltaColumn;
    }

    @Override
    public Location apply(Location from) {
        if (from == null) { throw new NullPointerException("Location to step from is null!"); }
        else { return Square.get(from.row() + DELTA_ROW, from.column() + DELTA_COLUMN); }
    }

    @Override
    public int deltaColumn() {
        return DELTA_COLUMN;
    }

    @Override
    public int deltaRow() {
        return DELTA_ROW;
    }
}
