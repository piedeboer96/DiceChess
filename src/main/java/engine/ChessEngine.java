package engine;

import chess.MatchState;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessPlayer;
import engine.interfaces.IChessEngine;
import gui.interfaces.IChessBoardGraphics;
import gui.interfaces.IChessGameWindow;
import gui.interfaces.IHumanPlayer;

public class ChessEngine implements IChessEngine
{
    public void run(IChessMatch match, IChessPlayer black, IChessPlayer white, IChessGameWindow window)
    {
        // Creating a player array to reduce the number of if-statements in the main loop.
        IChessPlayer[] players = new IChessPlayer[2];

        // If both players are humans, then we only require a single human player (as there can be only 1 mouse listener on the board).
        if (black instanceof IHumanPlayer && white instanceof IHumanPlayer)
        {
            black.playIn(-1, match);
            players[0] = black;
            players[1] = black;
        }
        else
        {
            //Assigning the teams and the match each player plays in.
            black.playIn(0, match);
            white.playIn(1, match);

            // Storing them in the proper order.
            players[0] = black;
            players[1] = white;
        }

        // Gets the board graphics.
        IChessBoardGraphics graphics = window.getBoardGraphics();

        while (!window.hasTerminated() && match.getState() == MatchState.ONGOING)
        {
            // Get the turn owner.
            int turnOwner = match.getPlayer();

            // Get the player.
            IChessPlayer player = players[turnOwner];

            // Let the player play.
            player.play();

            // If the player is a human wait till he is done playing while the game is not terminated.
            boolean playerIsHuman = player instanceof IHumanPlayer;
            while (playerIsHuman && !window.hasTerminated())
            {
                // If the turn owner has changed, then the human has made its move.
                if (turnOwner != match.getPlayer())
                {
                    // Delay a bit to give the computer enough time to finish its operations
                    // in the background thread that is managing the user input.
                    sleep(3);
                    break;
                }
                else { sleep(1); }
            }

            // Update the graphics.
            graphics.updateUI();

            // If desired we can delay here to follow AI movements turn by turn.
            sleep(2);
        }
    }

    private void sleep(int milliseconds)
    {
        try { Thread.sleep(milliseconds); }
        catch (InterruptedException ignored) {}
    }
}
