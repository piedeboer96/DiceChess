package gui.utility;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import chess.interfaces.IChessboardSquare;
import chess.utility.ChessboardSquare;
import gui.interfaces.IChessboard;
import gui.interfaces.IClickable;
import gui.interfaces.IHighlighter;
import gui.interfaces.IStalker;

public class Chessboard extends JPanel implements IChessboard, IClickable
{
    private static final long serialVersionUID = 1L;
	private final Color evenSquareColor, oddSquareColor;
    private final Highlighter highlighter;
    private final Stalker stalker;
    private int[] xs, ys;
    private int sw, sh;

    public Chessboard(int x, int y, int width, int height)
    {
        evenSquareColor = new Color(120, 120, 120);
        oddSquareColor = new Color(36, 36, 36);
        highlighter = new Highlighter();
        stalker = new Stalker();
        position(x, y, width, height);
    }

    public boolean clicked(int mouseX, int mouseY)
    {
        return xs[0] <= mouseX && mouseX <= xs[8] && ys[0] <= mouseY && mouseY <= ys[8];
    }

    public IChessboardSquare determineSquare(int mouseX, int mouseY)
    {
        int file = -1;
        int previous = 0;
        for (int iteration = 1; iteration < 9; iteration++)
        {
            if (xs[previous] <= mouseX && mouseX <= xs[iteration])
            {
                file = previous;
                break;
            }
            previous = iteration;
        }
        if (file == -1) { return null; }

        int rank = -1;
        previous = 0;
        for (int iteration = 1; iteration < 9; iteration++)
        {
            if (ys[previous] <= mouseY && mouseY <= ys[iteration])
            {
                rank = previous;
                break;
            }
            previous = iteration;
        }
        if (rank == -1) { return null; }

        return new ChessboardSquare(file, rank);
    }

    private void drawSquares(Graphics g)
    {
        for (int file = 0; file < 8; file++)
        {
            for (int rank = 0; rank < 8; rank++)
            {
                if ((file + rank) % 2 == 0) { g.setColor(evenSquareColor); }
                else { g.setColor(oddSquareColor); }
                g.fillRect(xs[file], ys[rank], sw, sh);
                drawPositionSquares(g, xs[file], ys[rank], file, rank);
            }
        }
    }

    private void drawPositionSquares(Graphics g, int xs, int ys, int file, int rank) {
        if ((file + rank) % 2 == 0) { g.setColor(oddSquareColor); }
        else { g.setColor(evenSquareColor); }
        g.drawString(file + "," + rank, xs + (int)(0.75*sw), ys + (int)(0.15*sh));
    }

    public IHighlighter getHighlighter() { return highlighter; }

    public IStalker getStalker() { return stalker; }

    @Override public boolean isLightweight() { return true; }

    @Override public void paint(Graphics g)
    {
        drawSquares(g);
        stalker.paint(g);
        highlighter.paint(g);
    }

    public void position(int x, int y, int width, int height)
    {
        sw = width / 8;
        sh = height / 8;

        xs = new int[9];
        ys = new int[9];

        xs[0] = x;
        ys[0] = y;

        int previous = 0;
        for (int iteration = 1; iteration < 9; iteration++)
        {
            xs[iteration] = xs[previous] + sw;
            ys[iteration] = ys[previous] + sh;
            previous = iteration;
        }

        highlighter.assignResponsibilityArea(xs, ys, sw, sh);
        stalker.assignResponsibilityArea(xs, ys, sw, sh);
    }
}
