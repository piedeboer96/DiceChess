package gui;

import chess.ChessMatch;
import chess.interfaces.IChessMatch;
import gui.interfaces.IClickable;
import gui.utility.Chessboard;

public final class ChessWindow extends Window
{
    private final Chessboard chessboard;
    private IChessMatch match;

    public ChessWindow(int width, int height)
    {
        super("A game of chess.", width, height);

        chessboard = new Chessboard(0, 0, width, height);
        add(chessboard);

        clickableSources = new IClickable[1];
        clickableSources[0] = chessboard;

        display();

    }

    public void display(IChessMatch match)
    {
        this.match = match;
        var stalker = chessboard.getStalker();
        stalker.follow(match.pieces());
        refresh();
    }

    public IChessMatch getMatch() { return match; }

    @Override public void refresh() { chessboard.updateUI(); }
}