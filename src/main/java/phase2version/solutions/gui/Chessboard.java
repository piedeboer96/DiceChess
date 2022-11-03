package phase2version.solutions.gui;

import phase2version.framework.game.Location;
import phase2version.framework.game.Setup;
import phase2version.framework.game.Unit;
import phase2version.framework.gui.Highlighter;
import phase2version.framework.gui.Projector;
import phase2version.solutions.chess.board.Square;
import phase2version.utility.ImageIconCache;

import javax.swing.*;
import java.awt.Graphics;

public final class Chessboard extends JComponent implements Highlighter, Projector {
    private final boolean[][] HIGHLIGHT;
    private final int[] SS, XS, YS;
    private Setup game;

    public Chessboard() {
        HIGHLIGHT = new boolean[8][8];
        SS = new int[2];
        XS = new int[9];
        YS = new int[9];
    }

    public Location determine(int x, int y) {
        if (x < XS[0] || y < YS[0]) { return null; }
        for (int column = 1; column < 9; column++) {
            if (x > XS[column]) { continue; }
            for (int row = 1; row < 9; row++) {
                if (y > YS[row]) { continue; }
                return Square.get(row - 1, column - 1);
            }
        }
        return null;
    }

    @Override
    public void highlight() {
        repaint();
    }

    @Override
    public void mark(Location l) {
        if (l != null) { HIGHLIGHT[l.row()][l.column()] = true;}
    }

    @Override
    public void paintComponent(Graphics g) {
        SS[0] = getWidth() / 8;
        SS[1] = getHeight() / 8;
        int previous = 0;
        for (int iteration = 1; iteration < 9; iteration++) {
            XS[iteration] = XS[previous] + SS[0];
            YS[iteration] = YS[previous] + SS[1];
            previous = iteration;
        }
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boolean even = (row + column) % 2 == 0;
                if (even) { g.setColor(Configuration.EVEN_SQUARE); }
                else { g.setColor(Configuration.ODD_SQUARE); }
                g.fillRect(XS[column], YS[row], SS[0], SS[1]);

                if (HIGHLIGHT[row][column]) {
                    g.setColor(Configuration.HIGHLIGHT);
                    g.fillRect(XS[column] + 3, YS[row] + 3, SS[0] - 6, SS[1] - 6);
                }

                if (even || HIGHLIGHT[row][column]) { g.setColor(Configuration.ODD_SQUARE); }
                else { g.setColor(Configuration.EVEN_SQUARE); }
                Square location = Square.get(row, column);
                g.drawString(location.toString(), XS[column] + 5, YS[row] + 15);

                if (game == null) { continue; }

                Unit u = game.read(location);
                if (u == null) { continue; }
                ImageIcon icon = ImageIconCache.getChessIcon(u.getType(), u.getTeam());
                g.drawImage(icon.getImage(), XS[column] + 3, YS[row], SS[0] - 6, SS[1], null);
            }
        }
    }

    @Override
    public void project(Setup s) {
        game = s;
    }

    @Override
    public void remove(Location l) {
        if (l != null) { HIGHLIGHT[l.row()][l.column()] = false; }
    }
}
