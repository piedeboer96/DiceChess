package phase2version.solutions.chess.pieces;

import phase2version.framework.chess.Opportunities;
import phase2version.framework.chess.Permissions;
import phase2version.framework.game.Location;
import phase2version.framework.game.Movement;
import phase2version.framework.game.Setup;
import phase2version.framework.game.Unit;
import phase2version.solutions.chess.board.Move;
import phase2version.solutions.chess.board.Square;

import java.util.ArrayList;
import java.util.List;

public enum King implements Unit {
    BLACK, WHITE;

    private static final Location[] CASTLE_RANKS = { Square.E8, Square.E1 };

    private static final Movement[] MOVES = {
            Move.NORTH     , Move.SOUTH,
            Move.WEST      , Move.EAST,
            Move.NORTH_WEST, Move.SOUTH_EAST,
            Move.NORTH_EAST, Move.SOUTH_WEST
    };

    private static final char[] NOTATIONS = { 'k', 'K' };

    @Override
    public Location[] destinations(Location from, Setup s) {
        throw new UnsupportedOperationException("A king needs to consider permissions to determine destinations!");
    }

    @Override
    public Location[] destinations(Location from, Setup s, Opportunities o) {
        throw new UnsupportedOperationException("A king does not need to consider opportunities to determine destinations!");
    }

    @Override
    public Location[] destinations(Location from, Setup s, Permissions p) {
        if      (from == null) { throw new NullPointerException("Location to read from is null!")         ; }
        else if (s    == null) { throw new NullPointerException("Setup describing circumstances is null!"); }
        else if (p    == null) { throw new NullPointerException("Permissions to consider is null!")       ; }
        List<Location> destinations = new ArrayList<>();
        // NORTH + SOUTH
        for (int i = 0; i < 2; i++) {
            Movement m = MOVES[i];
            Location destination = from;
            try {
                destination = m.apply(destination);
                Unit occupier = s.read(destination);
                if (occupier == null) { destinations.add(destination); }
                else if (occupier.getTeam() != ordinal()){ destinations.add(destination); }
            } catch (IllegalArgumentException ignored) {}
        }
        // WEST + CASTLE
        int side = 1;
        try {
            Location destination = MOVES[2].apply(from);
            Unit occupier = s.read(destination);
            if (occupier == null) {
                destinations.add(destination);
                if (from == CASTLE_RANKS[ordinal()] && p.has(ordinal(), side--)) {
                    Location castleDestination = MOVES[2].apply(destination);
                    occupier = s.read(destination);
                    if (occupier == null) {
                        destination = MOVES[2].apply(castleDestination);
                        occupier = s.read(destination);
                        if (occupier == null) { destinations.add(castleDestination);}
                    }
                }
            } else if (occupier.getTeam() != ordinal()){ destinations.add(destination); }
        } catch (IllegalArgumentException ignored) {}
        // EAST + CASTLE
        try {
            Location destination = MOVES[3].apply(from);
            Unit occupier = s.read(destination);
            if (occupier == null) {
                destinations.add(destination);
                if (from == CASTLE_RANKS[ordinal()] && p.has(ordinal(), side)) {
                    destination = MOVES[3].apply(destination);
                    occupier = s.read(destination);
                    if (occupier == null) { destinations.add(destination);}
                }
            } else if (occupier.getTeam() != ordinal()){ destinations.add(destination); }
        } catch (IllegalArgumentException ignored) {}
        // REMAINING DIRECTIONS
        for (int i = 4; i < 8; i++) {
            Movement m = MOVES[i];
            Location destination = from;
            try {
                destination = m.apply(destination);
                Unit occupier = s.read(destination);
                if (occupier == null) { destinations.add(destination); }
                else if (occupier.getTeam() != ordinal()){ destinations.add(destination); }
            } catch (IllegalArgumentException ignored) {}
        }
        return destinations.toArray(Location[]::new);
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
        return 6;
    }
}
