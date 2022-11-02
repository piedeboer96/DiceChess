package solutions.gui;

import framework.utility.Console;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Insets;

public final class IntegratedConsole extends JScrollPane implements Console {
    private final JTextPane CONTENT;
    private StringBuilder builder;

    public IntegratedConsole() {
        builder = new StringBuilder();
        CONTENT = new JTextPane();
        CONTENT.setEditable(false);
        CONTENT.setMargin(new Insets(5, 15, 5, 15));
        setViewportView(CONTENT);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    @Override
    public void clear() {
        builder = new StringBuilder();
        CONTENT.setText(builder.toString());
    }

    @Override
    public String content() {
        return builder.toString();
    }

    @Override
    public void write(String message) {
        builder.append(message);
        CONTENT.setText(builder.toString());
    }

    @Override
    public void writeln(String message) {
        builder.append(message).append('\n');
        CONTENT.setText(builder.toString());
    }
}
