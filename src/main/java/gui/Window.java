package gui;

import gui.interfaces.IClickable;
import gui.interfaces.IWindow;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

public abstract class Window extends JFrame implements IWindow
{
    protected int dw, dh;
    protected Insets insets;

    protected IClickable[] clickableSources;

    public Window(String title, int displayWidth, int displayHeight)
    {
        dw = displayWidth;
        dh = displayHeight;
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }


    protected void center()
    {
        var toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        Dimension window = getSize();
        int x = (screen.width - window.width) / 2;
        int y = (screen.height - window.height) / 2;
        setLocation(x, y);
    }
    
    public int[] determineLocationInContentBody(int mouseX, int mouseY)
    {
        int[] coordinates = new int[2];
        coordinates[0] = mouseX - insets.left;
        coordinates[1] = mouseY - insets.top;
        return coordinates;
    }

    public void display()
    {
        setVisible(true);

        insets = getInsets();
        setSize(dw + insets.left + insets.right, dh + insets.top + insets.bottom);
        center();
    }

    public IClickable findInteractionSource(int mouseX, int mouseY)
    {
        if (clickableSources == null) { return null; }
        for (var clickable : clickableSources)
        {
            if (clickable.clicked(mouseX, mouseY))
            {
                return clickable;
            }
        }
        return null;
    }

    public abstract void refresh();
}
