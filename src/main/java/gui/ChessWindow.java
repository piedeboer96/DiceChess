package gui;

import chess.ChessMatch;
import chess.interfaces.IChessMatch;
import gui.interfaces.IClickable;
import gui.utility.Chessboard;

public final class ChessWindow extends Window
{
    public static void main(String[] args)
    {
        var window = new ChessWindow(640, 640);
        var match  = new ChessMatch();
        window.display(match);
        var player = window.getPlayer();
        player.playIn(1, match);
    }
    private final Chessboard chessboard;
    private final ChessPlayer player;
    private IChessMatch match;

    public ChessWindow(int width, int height)
    {
        super("A game of chess.", width, height);

        chessboard = new Chessboard(0, 0, width, height);
        add(chessboard);

        clickableSources = new IClickable[1];
        clickableSources[0] = chessboard;

        display();

        player = new ChessPlayer();
        addMouseListener(player);
    }

    public void display(IChessMatch match)
    {
        this.match = match;
        var stalker = chessboard.getStalker();
        stalker.follow(match.pieces());
        refresh();
    }

    public IChessMatch getMatch() { return match; }

    public Player getPlayer() { return player; }

    @Override public void refresh() { chessboard.updateUI(); }
}