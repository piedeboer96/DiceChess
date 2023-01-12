package game;

import java.util.List;

/**
 * Defines a movement opportunity of a chess piece through defining the origin of the movement and the
 * available squares the chess piece can move to from the origin.
 **/
public final class Opportunity {
    private final ChessPiece OWNER;
    private final Square ORIGIN;
    private final List<Square> OPTIONS;

    /**
     * @param owner   The chess piece for which the opportunity should be defined.
     * @param origin  The square from where the opportunity should be defined.
     * @param options The squares the chess piece can move to from the origin.
     **/
    public Opportunity(ChessPiece owner, Square origin, List<Square> options) {
        OWNER = owner;
        ORIGIN = origin;
        OPTIONS = options;
    }

    /**
     * Obtains the squares the chess piece can move to.
     *
     * @return A list of squares with a size >= 0.
     **/
    public List<Square> options() {
        return OPTIONS;
    }

    /**
     * Obtains the origin of the opportunity.
     *
     * @return The square from where the opportunity is defined.
     **/
    public Square origin() {
        return ORIGIN;
    }

    /**
     * Obtains the owner of the opportunity.
     *
     * @return The chess piece for which the opportunity is defined.
     **/
    public ChessPiece owner() {
        return OWNER;
    }

    /**
     * Selects the square the chess piece will move to.
     *
     * @param index The index of the selected square in the list of squares.
     * @return The movement from the origin of the opportunity to the selected square.
     **/
    public Movement select(int index) {
        return new Movement(ORIGIN, OPTIONS.get(index));
    }

    /**
     * Obtains the number of squares the chess piece can move to.
     *
     * @return An integer >= 0.
     **/
    public int size() {
        return OPTIONS.size();
    }
}
