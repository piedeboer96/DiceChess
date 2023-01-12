package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

/**
 * Defines the basic structure each window should use.
 **/
public abstract class Window {
    protected final JFrame WINDOW = new JFrame();

    /**
     * @param title The title the window should use.
     * @param size  The size the window should use.
     **/
    protected Window(String title, Dimension size) {
        WINDOW.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WINDOW.setTitle(title);
        WINDOW.setSize(size);
        center();
    }

    private void center() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - WINDOW.getWidth()) / 2;
        int y = (screen.height - WINDOW.getHeight()) / 2;
        WINDOW.setLocation(x, y);
    }

    /**
     * Adds a window listener.
     *
     * @param l The window listener the window should use.
     **/
    public void addListener(WindowListener l) {
        WINDOW.addWindowListener(l);
    }

    /**
     * Sets the window visible.
     *
     * @param b True, if the window should be visible on the screen. <br>
     *          False, if the window should NO longer be visible.
     **/
    public void setVisible(boolean b) {
        WINDOW.setVisible(b);
    }
}
