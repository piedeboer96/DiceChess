package phase1version.gui.utility;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JComponent;

import phase1version.chess.interfaces.IChessboardSquare;
import phase1version.gui.interfaces.IHighlighter;

public final class Highlighter extends JComponent implements IHighlighter
{
    private static final long serialVersionUID = 1L;
	private final Color destinationColor, selectionColor;
    private List<IChessboardSquare> destinations;
    
    private IChessboardSquare selected;
    private int[] xs, ys;
    private int sw, sh;

    public Highlighter()
    {
        destinationColor = new Color(120, 180, 120, 120);
        selectionColor = new Color(120, 120, 180, 120);
    }

    public void assignResponsibilityArea(int[] xs, int[] ys, int sw, int sh)
    {
        this.xs = xs;
        this.ys = ys;
        this.sw = sw;
        this.sh = sh;
    }

    @Override public boolean isLightweight() { return true; }

    @Override public void paint(Graphics g)
    {
        if (destinations != null)
        {
            g.setColor(destinationColor);
            for (var destination : destinations) { g.fillRect(xs[destination.file()], ys[destination.rank()], sw, sh);}
        }

        if (selected != null)
        {
            g.setColor(selectionColor);
            g.fillRect(xs[selected.file()], ys[selected.rank()], sw, sh);
        }
    }

    public void rememberDestinations(List<IChessboardSquare> destinations) { this.destinations = destinations; }

    public void rememberSelection(IChessboardSquare selected) { this.selected = selected; }
}
