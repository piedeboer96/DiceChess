package utility;

import game.ChessPiece;
import game.Chessboard;
import game.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a tool to determine the squares a chess piece can move to from another square
 * using its basic movements.
 **/
public final class Destinations {
    private Destinations() {
    }

    /**
     * Determines the squares a chess piece can move to from another square using
     * its basic movements.
     *
     * @param p The chess piece for which the destinations should be determined.
     * @param s The square from where the destinations should be determined.
     * @param b The board on which the chess piece will be moving.
     **/
    public static List<Square> determine(ChessPiece p, Square s, Chessboard b) {
        List<Square> destinations = new ArrayList<>();
        Vector[] vectors = Library.vectorsOf(p);
        switch (p.type()) {
            case 1 -> {
                Square destination = vectors[0].translate(s);
                if (destination != null && b.read(destination) == null) {
                    destinations.add(destination);
                }
                for (int i = 1; i < 3; i++) {
                    destination = vectors[i].translate(s);
                    if (destination == null) {
                        continue;
                    }
                    ChessPiece occupier = b.read(destination);
                    if (occupier != null && occupier.color() != p.color()) {
                        destinations.add(destination);
                    }
                }
            }
            case 2, 6 -> {
                for (Vector v : vectors) {
                    Square destination = v.translate(s);
                    if (destination == null) {
                        continue;
                    }
                    ChessPiece occupier = b.read(destination);
                    if (occupier == null || occupier.color() != p.color()) {
                        destinations.add(destination);
                    }
                }
            }
            default -> {
                for (Vector v : vectors) {
                    Square destination = s;
                    for (int stepsTaken = 0; stepsTaken < 7; stepsTaken++) {
                        destination = v.translate(destination);
                        if (destination != null) {
                            ChessPiece occupier = b.read(destination);
                            if (occupier == null || occupier.color() != p.color()) {
                                destinations.add(destination);
                                if (occupier == null) {
                                    continue;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        return destinations;
    }

}
