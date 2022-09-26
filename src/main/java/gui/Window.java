package gui;

import chess.interfaces.IChessMatch;
import gui.interfaces.IPlayer;
import gui.utility.ChessBoardGraphics;
import gui.utility.Player;

import javax.swing.*;
import java.awt.*;

public class Window
{
    private static int activeSessions;
    private final IPlayer player;

    public Window()
    {
        String title;
        if (activeSessions++ == 0) { title = "Dice Chess"; }
        else { title = "Dice Chess " + activeSessions; }

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(200, 50, 617, 655);
        frame.setResizable(false);
        frame.setVisible(true);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);

        ChessBoardGraphics board = new ChessBoardGraphics(10, 10, 73, 75);
        frame.add(board);

        player = new Player(board);
        board.addMouseListener(player);
        board.updateUI();
    }

    public void displayMatch(IChessMatch match) { player.playMatch(match); }
}
