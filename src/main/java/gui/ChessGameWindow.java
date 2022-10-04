package gui;

import chess.interfaces.IChessMatch;
import gui.interfaces.IChessBoardGraphics;
import gui.interfaces.IChessGameWindow;
import gui.interfaces.IHumanPlayer;
import gui.utility.ChessBoardGraphics;
import gui.utility.WindowStateListener;

import javax.swing.*;
import java.awt.*;

public class ChessGameWindow implements IChessGameWindow
{
    private final ChessBoardGraphics boardGraphics;
    private final WindowStateListener stateListener;

    public ChessGameWindow()
    {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(200, 50, 617, 655);
        frame.setResizable(false);
        frame.setVisible(true);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);

        stateListener = new WindowStateListener();
        frame.addWindowListener(stateListener);

        boardGraphics = new ChessBoardGraphics(10, 10, 73, 75);
        frame.add(boardGraphics);
        boardGraphics.updateUI();
    }

    public void addPlayer(IHumanPlayer player)
    {
        boardGraphics.addMouseListener(player);
        player.setPlayground(boardGraphics);
    }

    public void displayMatch(IChessMatch match)
    {
        boardGraphics.trackChessPieces(match.pieces());
        boardGraphics.updateUI();
    }

    public IChessBoardGraphics getBoardGraphics() { return boardGraphics; }

    public boolean hasTerminated() { return stateListener.windowIsTerminated(); }

    public void removePlayer(IHumanPlayer player)
    {
        boardGraphics.removeMouseListener(player);
        player.setPlayground(null);
    }
}
