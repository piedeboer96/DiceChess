package gui.utility;

import java.awt.Graphics;

import javax.swing.JPanel;

import gui.interfaces.IClickable;

public class Dice extends JPanel implements IClickable
{
    private static final long serialVersionUID = 1L;
	private final Panel panel;
    private final Die die1, die2;
    private final int x, y, width, height;

    public Dice(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        panel = new Panel(x, y, width, height);

        die1 = new Die(x + 15, height / 2 - 60, 60, 60);
        die2 = new Die(x + 90, height / 2 - 60, 60, 60);
    }

    @Override public boolean clicked(int mouseX, int mouseY)
    {
        return x <= mouseX && mouseX <= (x + width) && y <= mouseY && mouseY <= (y + height);
    }

    @Override public boolean isLightweight() { return true; }

    @Override public void paint(Graphics g)
    {
        panel.paint(g);
        die1.paint(g);
        die2.paint(g);
    }

    public char[] roll(int team)
    {
        die1.reset();
        die1.roll(team);

        die2.reset();
        die2.roll(team);

        char[] results = new char[2];
        results[0] = die1.getResult();
        results[1] = die2.getResult();
        return results;
    }
}
