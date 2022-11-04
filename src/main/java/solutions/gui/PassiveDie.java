package solutions.gui;

import utility.ImageIconCache;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;

public final class PassiveDie extends JComponent {
    private static final long serialVersionUID = 1L;
	private int team, type;
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Configuration.ODD_SQUARE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.fillRect(3, 3, getWidth() - 6, getHeight() - 6);

        if (type >= 1 && type <= 6 && (team == 0 || team == 1)) {
            ImageIcon icon = ImageIconCache.getChessIcon(type, team);
            g.drawImage(icon.getImage(), 3, 0, getWidth() - 6, getHeight(), null);
        }
    }

    public void update(int team, int type) {
        if (team != 0 && team != 1) { throw new IllegalArgumentException("TEAM NUMBER IS NOT VALID!"); }
        else if (type < 1 || type > 6) { throw new IllegalArgumentException("UNIT TYPE IS NOT VALID!"); }
        this.team = team;
        this.type = type;
        repaint();
    }
}
