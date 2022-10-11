package examples;

import ai.RandomBot;
import chess.ChessMatch;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessPlayer;
import engine.ChessEngine;
import engine.interfaces.IChessEngine;
import gui.ChessGameWindow;
import gui.HumanPlayer;
import gui.interfaces.IChessGameWindow;
import gui.interfaces.IHumanPlayer;

public class HumanVsAI
{
    public static void main(String[] args)
    {
        // Creating a new window.
        IChessGameWindow window = new ChessGameWindow();

        // Creating a new match.
        IChessMatch match = new ChessMatch();

        // Displays the match
        window.displayMatch(match);

        // Creating a human player.
        IHumanPlayer human = new HumanPlayer();

        // Creating a random bot.
        IChessPlayer bot = new RandomBot();

        // Adding the player to the game.
        window.addPlayer(human);

        // Creating a new engine.
        IChessEngine engine = new ChessEngine();

        // Letting engine run, where the human controls the white pieces and the bot controls the black pieces.
        engine.run(match, bot, human, window);

        // The engine will only finish executing run when the game window has been terminated
        // or the match has ended.
        // So, let's print out the final state of the match whenever we are done running.
        System.out.println("Match ended in a state: " + match.getState());
        System.out.println("Fen representation of the match state: " + match.toFen());
    }
}
