package gui.interfaces;

import chess.interfaces.IChessMatch;

import java.awt.event.MouseListener;

public interface IPlayer extends MouseListener
{
    void playMatch(IChessMatch match);
}
