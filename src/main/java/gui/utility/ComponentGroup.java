package gui.utility;

import javax.swing.*;
import java.awt.Graphics;

public class ComponentGroup extends JPanel
{
    private final JComponent[] components;
    private int iterator;

    public ComponentGroup(int size) { components = new JComponent[size]; }

    public void add(JComponent component) { components[iterator++] = component; }

    @Override public boolean isLightweight() { return true; }

    @Override public void paint(Graphics g)
    {
        super.paint(g);
        for (var component : components)
        {
            if (component == null) { continue; }
            component.paint(g);
        }
    }
}
