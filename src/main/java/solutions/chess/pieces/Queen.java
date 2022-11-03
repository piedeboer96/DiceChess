package solutions.chess.pieces;

import framework.chess.Opportunities;
import framework.chess.Permissions;
import framework.game.Location;
import framework.game.Movement;
import framework.game.Setup;
import framework.game.Unit;
import solutions.chess.board.Move;

import java.util.ArrayList;
import java.util.List;

public enum Queen implements Unit {
    BLACK, WHITE;

    private static final Movement[] MOVES = {
            Move.NORTH     , Move.SOUTH,
            Move.WEST      , Move.EAST,
            Move.NORTH_WEST, Move.SOUTH_EAST,
            Move.NORTH_EAST, Move.SOUTH_WEST
    };

    private static final char[] NOTATIONS = { 'q', 'Q' };

    @Override
    public Location[] destinations(Location from, Setup s) {
        if      (from == null) { throw new NullPointerException("Location to read from is null!")         ; }
        else if (s    == null) { throw new NullPointerException("Setup describing circumstances is null!"); }
        List<Location> destinations = new ArrayList<>();
        for (Movement m : MOVES) {
            Location destination = from;
            for (int stepsTaken = 0; stepsTaken < 7; stepsTaken++) {
                try {
                    destination = m.apply(destination);
                    Unit occupier = s.read(destination);
                    if (occupier == null) { destinations.add(destination); }
                    else if (occupier.getTeam() != ordinal()){ destinations.add(destination); break; }
                    else { break; }
                } catch (IllegalArgumentException ignored) { break; }
            }
        }
        return destinations.toArray(Location[]::new);
    }

    @Override
    public Location[] destinations(Location from, Setup s, Opportunities o) {
        throw new UnsupportedOperationException("A queen does not need to consider opportunities to determine destinations!");
    }

    @Override
    public Location[] destinations(Location from, Setup s, Permissions p) {
        throw new UnsupportedOperationException("A queen does not need to consider permissions to determine destinations!");
    }

    @Override
    public char getNotation() {
        return NOTATIONS[ordinal()];
    }

    @Override
    public int getTeam() {
        return ordinal();
    }

    @Override
    public int getType() {
        return 5;
    }
}
