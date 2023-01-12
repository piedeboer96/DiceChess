package gui;

import game.ChessPiece;

import javax.swing.*;
import java.awt.*;

/**
 * Defines a component to visualizes the roll of a die for an active color.
 **/
public final class DiceGraphics extends JPanel {
    private ImageIcon icon;

    public DiceGraphics(){
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (icon != null) {
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), null);
        }
    }

    /**
     * Displays the roll result of a die for an active color.
     *
     * @param roll  An integer between 1 and 6 representing the die roll result.
     * @param color The active color for which the die has rolled.
     **/
    public void onDieRoll(int roll, int color) {
        ChessPiece p = ChessPiece.get(roll, color);
        icon = Resources.getIcon(p);
        if (isVisible()) {
            repaint();
        }
    }
}
