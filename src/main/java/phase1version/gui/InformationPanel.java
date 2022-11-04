package phase1version.gui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import phase1version.gui.utility.Panel;

/**
 * Show information on current actions in the game...
 */
public class InformationPanel extends Panel {

    private static final long serialVersionUID = 1L;
	public InformationPanel(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    // automatically wrap lines
    JTextArea textArea = new JTextArea(5, 20);
    JScrollPane scrollPane = new JScrollPane(textArea);


}
