package renderer;

import board.FenBoard;
import board.FenBoardListener;

import javax.swing.*;
import java.awt.*;

/**
 * Extends JFrame to support additional functionality.
 **/
public class SwingUI extends JFrame {
    private final Dimension screenSize;

    public SwingUI(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
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

    // Contains an example on how to run the swing ui.
    public static void main(String[] args){
        FenBoard board = new FenBoard(640 - 4 * 64, 50, 64, 64);
        FenBoardListener listener = new FenBoardListener(board);

        SwingUI gui = new SwingUI();
        gui.setVisible(true);
        gui.center();
        gui.add(board);
        board.interpret("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"); //Initial state.
    }
}
