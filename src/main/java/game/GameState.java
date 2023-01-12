package game;

/**
 * Defines the states of dice chess.
 **/
public enum GameState {
    /**
     * Defines an ongoing game.
     **/
    ONGOING,

    /**
     * Defines a game that has ended in a draw.
     **/
    DRAW,

    /**
     * Defines a game where black won.
     **/
    BLACK_WON,

    /**
     * Defines a game where white won.
     **/
    WHITE_WON
}
