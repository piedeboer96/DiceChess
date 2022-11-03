package phase1version.gui.utility;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel
{
    private static final long serialVersionUID = 1L;
	private final Color c;
    private int x, y, w, h;

    public Panel(int x, int y, int w, int h)
    {
        c = new Color(72, 72, 72);
        position(x, y, w, h);
    }

    @Override public boolean isLightweight() { return true; }

    @Override public void paint(Graphics g)
    {
        g.setColor(c);
        g.fillRect(x, y, w, h);
    }

    public void position(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
