package gui;

import gui.utility.Panel;

import javax.swing.*;
import javax.swing.JTextArea;

/**
 * Show information on current actions in the game...
 */
public class InformationPanel extends Panel {

    public InformationPanel(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    // automatically wrap lines
    JTextArea textArea = new JTextArea(5, 20);
    JScrollPane scrollPane = new JScrollPane(textArea);



}
