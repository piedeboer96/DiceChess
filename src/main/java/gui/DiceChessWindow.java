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
        var player = window.getPlayer();
        player.playIn(1, match);
    }
    private final Chessboard chessboard;
    private final Dice dice;
    private final DiceChessPlayer player;
    private final ComponentGroup componentGroup;
    private IChessMatch match;


    public DiceChessWindow(int width, int height)
    {
        super("A game of dice chess.", width, height);

        int panelWidth = width / 10 * 2;
        int boardWidth = width - panelWidth;

        chessboard = new Chessboard(0, 0, boardWidth, height);
        dice = new Dice(boardWidth, 0, panelWidth, height);
        componentGroup = new ComponentGroup(2);
        componentGroup.add(chessboard);

        componentGroup.add(dice);
        add(componentGroup);

        clickableSources = new IClickable[2];
        clickableSources[0] = chessboard;
        clickableSources[1] = dice;

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
        componentGroup.updateUI();
    }
}
