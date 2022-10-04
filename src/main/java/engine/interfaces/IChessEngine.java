package engine.interfaces;

import chess.interfaces.IChessMatch;
import chess.interfaces.IChessPlayer;
import gui.interfaces.IChessGameWindow;

/**
 * Provides an automation of the game processes such as switching turns, asking the player to make a move etc.
 **/
public interface IChessEngine
{
    /**
     * Runs the match till either the match has ended or the window is terminated.
     **/
    void run(IChessMatch match, IChessPlayer black, IChessPlayer white, IChessGameWindow window);
}
