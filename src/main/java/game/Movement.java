package game;

/**
 * Defines a chess piece movement through defining its origin and endpoint.
 **/
public final class Movement {
    private final Square ORIGIN, ENDPOINT;

    /**
     * @param origin   The square where the movement should start from.
     * @param endpoint The square where the movement should end at.
     **/
    public Movement(Square origin, Square endpoint) {
        ORIGIN =  origin;
        ENDPOINT = endpoint;
    }

    /**
     * Obtains the endpoint of the movement.
     *
     * @return The square where the movement ends at.
     **/
    public Square endpoint() {
        return ENDPOINT;
    }

    /**
     * Obtains the origin of the movement.
     *
     * @return The square where the movement starts from.
     **/
    public Square origin() {
        return ORIGIN;
    }
}
