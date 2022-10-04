package gui.interfaces;

import chess.interfaces.IChessPlayer;

import java.awt.event.MouseListener;

public interface IHumanPlayer extends IChessPlayer, MouseListener
{
    /**
     * Gets the legal moves the players has.
     * If the team is -1, then gets the current turn owner's legal moves.
     * Otherwise, gets the legal move of the team that has been assigned to this player.
     * Remark: The executioner of this method should wait till the player has made its move.
     **/
    void play();

    /**
     * Sets the board the player is playing on.
     **/
    void setPlayground(IChessBoardGraphics boardGraphics);
}
