package gui;

import chess.ChessMatch;
import chess.interfaces.IChessMatch;
import gui.interfaces.IClickable;
import gui.utility.*;

public final class DiceChessWindow extends Window
{
    private final Chessboard chessboard;
    private final ComponentGroup componentGroup;
    private IChessMatch match;


    public DiceChessWindow(int width, int height)
    {
        super("A game of dice chess.", width, height);

        int panelWidth = width / 10 * 2;
        int boardWidth = width - panelWidth;

        chessboard = new Chessboard(0, 0, boardWidth, height);
        Dice dice = new Dice(boardWidth, 0, panelWidth, height);
        componentGroup = new ComponentGroup(2);
        componentGroup.add(chessboard);

        componentGroup.add(dice);
        add(componentGroup);

        clickableSources = new IClickable[2];
        clickableSources[0] = chessboard;
        clickableSources[1] = dice;

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

    @Override public void refresh()
    {
        componentGroup.updateUI();
    }
}
