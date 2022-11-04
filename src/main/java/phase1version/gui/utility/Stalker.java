package phase1version.gui.utility;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JComponent;

import phase1version.chess.interfaces.IChessPiece;
import phase1version.gui.interfaces.IStalker;

public final class Stalker extends JComponent implements IStalker
{
    private static final long serialVersionUID = 1L;
	private final Cache cache;
    private List<IChessPiece> chessPieces;
    private int[] xs, ys;
    private int sw, sh;

    public Stalker()
    {
        cache = new Cache();
    }

    public void assignResponsibilityArea(int[] xs, int[] ys, int sw, int sh)
    {
        this.xs = xs;
        this.ys = ys;
        this.sw = sw;
        this.sh = sh;
    }

    public void follow(List<IChessPiece> chessPieces) { this.chessPieces = chessPieces; }

    @Override public void paint(Graphics g)
    {
        if (chessPieces == null) { return; }
        for (var chessPiece : chessPieces)
        {
            var image = cache.getImage(chessPiece.toFen());
            g.drawImage(image, xs[chessPiece.file()], ys[chessPiece.rank()], sw, sh, null);
        }
    }
}
