package gui;

import game.ChessPiece;
import game.Chessboard;
import game.Square;

import javax.swing.*;
import java.awt.*;

/**
 * Defines a component to visualize the piece placement data of a chessboard.
 **/
public final class BoardGraphics extends JComponent {
    private static final Color EVEN_SQUARE = new Color(120, 180, 120);
    private static final Color ODD_SQUARE = new Color(60, 90, 60);
    private final int[] SS = new int[2], XS = new int[9], YS = new int[9];
    private Chessboard b;

    public BoardGraphics() {
        setOpaque(true);
    }

    /**
     * Obtains the square the user has clicked on.
     *
     * @param x The x-coordinate of the mouse press/click.
     * @param y The y-coordinate of the mouse press/click.
     * @return The square the user has clicked. <br>
     *         Null, if the (x, y)-coordinate falls outside the board.
     **/
    public Square determineLocation(int x, int y) {
        if (x < XS[0] || x > XS[8]) {
            return null;
        } else if (y < YS[0] || y > YS[8]) {
            return null;
        }
        for (int i = 1; i < 9; i++) {
            if (x < XS[i]) {
                for (int j = 1; j < 9; j++) {
                    if (y < YS[j]) {
                        return Square.get(i - 1, j - 1);
                    }
                }
            }
        }
        return null;
    }


    @Override
    public void paintComponent(Graphics g) {
        SS[0] = getWidth() / 8;
        SS[1] = getHeight() / 8;
        int previous = 0;
        for (int index = 1; index < 9; index++) {
            XS[index] = XS[previous] + SS[0];
            YS[index] = YS[previous] + SS[1];
            previous = index;
        }
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                boolean evenIndex = (y + x) % 2 == 0;
                if (evenIndex) {
                    g.setColor(EVEN_SQUARE);
                } else {
                    g.setColor(ODD_SQUARE);
                }
                g.fillRect(XS[x], YS[y], SS[0], SS[1]);

                if (b == null) {
                    continue;
                }

                Square s = Square.get(x, y);
                if (evenIndex) {
                    g.setColor(ODD_SQUARE);
                } else {
                    g.setColor(EVEN_SQUARE);
                }
                String label = s.toString().toUpperCase();
                g.drawString(label, XS[x] + 5, YS[y] + 15);

                ChessPiece p = b.read(s);
                if (p == null) {
                    continue;
                }

                ImageIcon icon = Resources.getIcon(p);
                g.drawImage(icon.getImage(), XS[x], YS[y], SS[0], SS[1], null);
            }
        }
    }

    public void setBoard(Chessboard b) {
        this.b = b;
        if (isVisible()) {
            repaint();
        }
    }
}
