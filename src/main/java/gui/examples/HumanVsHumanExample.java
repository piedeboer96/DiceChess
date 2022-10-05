package gui.examples;

import chess.ChessMatch;
import chess.interfaces.IChessMatch;
import engine.ChessEngine;
import engine.interfaces.IChessEngine;
import gui.ChessGameWindow;
import gui.HumanPlayer;
import gui.interfaces.IChessGameWindow;
import gui.interfaces.IHumanPlayer;

/**
 * Provides an example on how to run a human vs human game.
 **/
public class HumanVsHumanExample
{
    public static void main(String[] args)
    {
        // Creating a new window.
        IChessGameWindow window = new ChessGameWindow();

        // Creating a new match.
        IChessMatch match = new ChessMatch();

        // Displays the match
        window.displayMatch(match);

        // Creating a single human player.
        // Why single? Because we can only have one mouse (listener) on the chessboard.
        // The engine keeps track whether it is a human vs human game or not.
        IHumanPlayer player = new HumanPlayer();

        // Adding the player to the game.
        window.addPlayer(player);

        // Creating a new engine.
        IChessEngine engine = new ChessEngine();

        // Letting engine run.
        engine.run(match, player, player, window);

        // The engine will only finish executing run when the game window has been terminated
        // or the match has ended.
        // So, let's print out the final state of the match whenever we are done running.
        System.out.println("Match ended in a state: " + match.getState());
        System.out.println("Fen representation of the match state: " + match.toFen());
    }
}
