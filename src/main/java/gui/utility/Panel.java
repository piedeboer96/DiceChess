package gui.utility;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class Panel extends JPanel
{
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
