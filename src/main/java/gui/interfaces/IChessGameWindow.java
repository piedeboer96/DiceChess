package gui.interfaces;

import chess.interfaces.IChessMatch;

/**
 * Represents a window displaying a chess game.
 **/
public interface IChessGameWindow
{
    /**
     * Adds a player to the game window, making interactions with the board possible.
     **/
    void addPlayer(IHumanPlayer player);

    /**
     * Displays a chess match on the window.
     **/
    void displayMatch(IChessMatch match);

    /**
     * Gets the board graphics.
     **/
    IChessBoardGraphics getBoardGraphics();

    /**
     * Determines whether the window has been closed (by pressing the 'X'-button).
     **/
    boolean hasTerminated();

    /**
     * Removes the player from the game window, making interactions with the board not possible.
     **/
    void removePlayer(IHumanPlayer player);
}
