package zzz.solutions.chess.pieces;

import zzz.framework.chess.Opportunities;
import zzz.framework.chess.Permissions;
import zzz.framework.game.Location;
import zzz.framework.game.Movement;
import zzz.framework.game.Setup;
import zzz.framework.game.Unit;
import zzz.solutions.chess.board.Move;

import java.util.ArrayList;
import java.util.List;

public enum Knight implements Unit {
    BLACK, WHITE;

    private static final Movement[] MOVES = {
            Move.L_NORTH_WEST, Move.L_SOUTH_EAST,
            Move.L_NORTH_EAST, Move.L_SOUTH_WEST,
            Move.L_WEST_NORTH, Move.L_EAST_SOUTH,
            Move.L_WEST_SOUTH, Move.L_EAST_NORTH
    };

    private static final char[] NOTATIONS = { 'n', 'N' };

    @Override
    public Location[] destinations(Location from, Setup s) {
        if      (from == null) { throw new NullPointerException("Location to read from is null!")         ; }
        else if (s    == null) { throw new NullPointerException("Setup describing circumstances is null!"); }
        List<Location> destinations = new ArrayList<>();
        for (Movement m : MOVES) {
            try {
                Location destination = m.apply(from);
                Unit occupier = s.read(destination);
                if (occupier == null) { destinations.add(destination); }
                else if (occupier.getTeam() != ordinal()){ destinations.add(destination); }
            } catch (IllegalArgumentException ignored) {}
        }
        return destinations.toArray(Location[]::new);
    }

    @Override
    public Location[] destinations(Location from, Setup s, Opportunities o) {
        throw new UnsupportedOperationException("A knight does not need to consider opportunities to determine destinations!");
    }

    @Override
    public Location[] destinations(Location from, Setup s, Permissions p) {
        throw new UnsupportedOperationException("A knight does not need to consider permissions to determine destinations!");
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
        return 2;
    }
}
