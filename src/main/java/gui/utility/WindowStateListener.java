package gui.utility;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowStateListener implements WindowListener
{
    private boolean terminated;

    public boolean windowIsTerminated() { return terminated; }

    public void windowOpened(WindowEvent windowEvent) {}

    public void windowClosing(WindowEvent windowEvent) { terminated = true; }

    public void windowClosed(WindowEvent windowEvent) {}

    public void windowIconified(WindowEvent windowEvent) {}

    public void windowDeiconified(WindowEvent windowEvent) {}

    public void windowActivated(WindowEvent windowEvent) {}

    public void windowDeactivated(WindowEvent windowEvent) {}
}
