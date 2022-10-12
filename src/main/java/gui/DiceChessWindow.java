package gui;

import chess.ChessMatch;
import chess.interfaces.IChessMatch;
import gui.interfaces.IClickable;
import gui.utility.*;

public final class DiceChessWindow extends Window
{
    public static void main(String[] args)
    {
        var match = new ChessMatch();
        var window = new DiceChessWindow(800, 640);
        window.display(match);
    }
    private final Chessboard chessboard;
    private final DiceChessPlayer player;
    private IChessMatch match;


    public DiceChessWindow(int width, int height)
    {
        super("A game of dice chess.", width, height);

        int panelWidth = width / 10;
        int boardWidth = width - (2 * panelWidth);

        chessboard = new Chessboard(panelWidth, 0, boardWidth, height);
        var leftPanel = new Panel(0, 0, panelWidth, height);
        var rightPanel = new Panel(panelWidth + boardWidth, 0, panelWidth, height);
        var componentGroup = new ComponentGroup(3);
        componentGroup.add(chessboard);
        componentGroup.add(leftPanel);
        componentGroup.add(rightPanel);
        add(componentGroup);

        clickableSources = new IClickable[2];
        clickableSources[0] = chessboard;

        display();

        player = new DiceChessPlayer();
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

    @Override public void refresh()
    {
        chessboard.updateUI();
    }
}
