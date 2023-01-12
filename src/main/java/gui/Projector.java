package gui;

import game.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Defines a tool to project a game of dice chess.
 **/
public final class Projector extends Window {
    private static final Dimension DIE_SIZE = new Dimension(120, 120);
    private static final Dimension GAME_PANEL = new Dimension(800, 800);
    private static final Dimension SIDE_PANEL = new Dimension(200, 800);
    private final BoardGraphics BOARD = new BoardGraphics();
    private final DiceGraphics DIE = new DiceGraphics();

    public Projector() {
        super("A game of dice chess", new Dimension(1280, 960));
        JPanel side = new JPanel();
        side.setBackground(new Color(36, 36, 36));
        side.setLayout(new GridBagLayout());
        side.setPreferredSize(SIDE_PANEL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1;
        side.add(DIE, gbc);

        BOARD.setPreferredSize(GAME_PANEL);
        DIE.setPreferredSize(DIE_SIZE);

        WINDOW.setLayout(new BorderLayout());
        WINDOW.add(BOARD, BorderLayout.CENTER);
        WINDOW.add(side, BorderLayout.EAST);
    }


    /**
     * Adds a mouse listener to the component displaying the chessboard.
     *
     * @param l The mouse listener that should be added to the component.
     **/
    public void addListener(MouseListener l) {
        BOARD.addMouseListener(l);
    }


    /**
     * Displays a chessboard.
     *
     * @param b The chessboard that should be displayed. <br>
     *          Null, if the board should become empty (again).
     **/
    public void display(Chessboard b) {
        BOARD.setBoard(b);
    }

    /**
     * Displays the roll result of a die of an active color.
     *
     * @param roll  An integer between 1 and 6 representing the result the die has rolled.
     * @param color The active color for which the die has rolled.
     **/
    public void display(int roll, int color) {
        DIE.onDieRoll(roll, color);
    }


}
