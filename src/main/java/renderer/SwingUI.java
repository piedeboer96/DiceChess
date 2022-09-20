package renderer;

import javax.swing.*;
import java.awt.*;

/**
 * Extends JFrame to support additional functionality.
 **/
public class SwingUI extends JFrame {
    private final Dimension screenSize;

    public SwingUI(int width, int height){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(width, height);
        setTitle("Dice Chess");

        Toolkit kit = Toolkit.getDefaultToolkit();
        screenSize = kit.getScreenSize();
    }

    /**
     * Centers the frame to the middle of the screen.
     **/
    public void center(){
        Dimension size = getSize();
        int x = (screenSize.width - size.width) / 2;
        int y = (screenSize.height - size.height) / 2;
        setLocation(x, y);
    }
}
