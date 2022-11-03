package zzz.solutions.chess.pieces;

import zzz.framework.chess.Opportunities;
import zzz.framework.chess.Permissions;
import zzz.framework.game.Location;
import zzz.framework.game.Movement;
import zzz.framework.game.Setup;
import zzz.framework.game.Unit;
import zzz.solutions.chess.board.Move;
import zzz.solutions.chess.board.Square;

import java.util.ArrayList;
import java.util.List;

public enum Pawn implements Unit {
    BLACK, WHITE;

    private static final int[] DOUBLE_STEP_RANKS = { 1, 6 };

    private static final Movement[][] MOVES = {
            { Move.SOUTH, Move.SOUTH_WEST, Move.SOUTH_EAST },
            { Move.NORTH, Move.NORTH_WEST, Move.NORTH_EAST }
    };

    private static final char[] NOTATIONS = { 'p', 'P' };
    public static final int[] PROMOTION_ROWS = { 7, 0 };
    public static final Location[][] TARGET_SQUARES = {
            { Square.A6, Square.B6, Square.C6, Square.D6, Square.E6, Square.F6, Square.G6, Square.H6 },
            { Square.A3, Square.B3, Square.C3, Square.D3, Square.E3, Square.F3, Square.G3, Square.H3 }
    };

    public static final Location[][] TARGET_SQUARES_OWNERS = {
            { Square.A4, Square.B4, Square.C4, Square.D4, Square.E4, Square.F4, Square.G4, Square.H4 },
            { Square.A5, Square.B5, Square.C5, Square.D5, Square.E5, Square.F5, Square.G5, Square.H5 }
    };
    @Override
    public Location[] destinations(Location from, Setup s) {
        throw new UnsupportedOperationException("A pawn needs to consider opportunities to determine destinations!");
    }

    @Override
    public Location[] destinations(Location from, Setup s, Opportunities o) {
        if      (from == null) { throw new NullPointerException("Location to read from is null!")         ; }
        else if (s    == null) { throw new NullPointerException("Setup describing circumstances is null!"); }
        else if (o    == null) { throw new NullPointerException("Opportunities to consider is null!")     ; }
        List<Location> destinations = new ArrayList<>();
        Movement[] moves = MOVES[ordinal()];
        try {
            Location destination = moves[0].apply(from);
            Unit occupier = s.read(destination);
            if (occupier == null) {
                destinations.add(destination);
                if (from.row() == DOUBLE_STEP_RANKS[ordinal()]) {
                    destination = moves[0].apply(destination);
                    occupier = s.read(destination);
                    if (occupier == null) { destinations.add(destination); }
                }
            }
        } catch (IllegalArgumentException ignored) {}
        for (int i = 1; i < 3; i++) {
            try {
                Location destination = moves[i].apply(from);
                Unit occupier = s.read(destination);
                if (occupier != null && occupier.getTeam() != ordinal()) { destinations.add(destination); }
                else if (occupier == null && destination == o.get(ordinal())) { destinations.add(destination); }
            } catch (IllegalArgumentException ignored) {}
        }
        return destinations.toArray(Location[]::new);
    }

    @Override
    public Location[] destinations(Location from, Setup s, Permissions p) {
        throw new UnsupportedOperationException("A pawn does not need to consider permissions to determine destinations!");
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
        return 1;
    }
}
