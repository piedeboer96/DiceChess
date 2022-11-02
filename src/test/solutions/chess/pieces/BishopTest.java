package solutions.chess.pieces;

import framework.game.Location;
import framework.game.Setup;
import framework.game.Unit;
import org.junit.jupiter.api.Test;
import utility.Exam;

public final class BishopTest implements Exam {
    @Override @Test
    public void take() {
        testGetTeam();
        testGetType();
        testGetNotation();
        testDestinations();
    }

    @Test
    public void testDestinations() {
        Setup empty = new Setup() {
            private final Unit[][] GRID = new Unit[8][8];

            @Override
            public Unit read(Location from) {
                return GRID[from.row()][from.column()];
            }

            @Override
            public String snapshot() {
                return null;
            }

            @Override
            public void recover(String snapshot) {

            }
        };
    }

    @Test
    public void testGetNotation() {
        assert Bishop.BLACK.getNotation() == 'b';
        assert Bishop.WHITE.getNotation() == 'B';
    }

    @Test
    public void testGetTeam() {
        assert Bishop.BLACK.getTeam() == 0;
        assert Bishop.WHITE.getTeam() == 1;
    }

    @Test
    public void testGetType() {
        assert Bishop.BLACK.getType() == 3;
        assert Bishop.WHITE.getType() == 3;
    }
}
