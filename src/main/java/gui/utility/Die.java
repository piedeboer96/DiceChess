package gui.utility;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * Defines and provides the graphical component of a die.
 **/
public class Die extends JComponent
{
    private static final long serialVersionUID = 1L;
	private static final Cache cache = new Cache();
    private final int x, y, width, height;
    private final Color border, fill;
    private enum DieState { IDLE, ROLLED };
    private DieState state;
    private char rollResult;

    public Die(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        border = new Color(36, 36, 36);
        fill = new Color(180, 180, 180);
    }

    public char getResult() { return rollResult; }

    @Override public boolean isLightweight() { return true; }

    @Override public void paint(Graphics g)
    {
        g.setColor(border);
        g.drawRect(x, y, width, height);
        g.setColor(fill);
        g.fillRect(x + 1, y + 1, width - 1, height - 1);
        if (state == DieState.ROLLED)
        {
            var image = cache.getImage(rollResult);
            g.drawImage(image, x, y, width, height, null);
        }
    }

    public void reset() { state = DieState.IDLE; }

    public void roll(int team)
    {
        if (state == DieState.ROLLED) { return; }
        state = DieState.ROLLED;
        var die = new gui.die.Die();
        rollResult = die.roll(team);
    }
}
