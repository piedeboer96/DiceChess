package phase1version.gui.utility;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class ComponentGroup extends JPanel
{
    private static final long serialVersionUID = 1L;
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
