package GameGUI;
import game.Match;
import javax.swing.JFrame;
import java.awt.*;

public class GUIFrame {
    final JFrame frame;
    final Match match;
    final GUIPanel panel;
    final GUIPanel.SwingBoardListener listener;
    private final Dimension screenSize;

    public GUIFrame() {
        this.frame = new JFrame("Dice Chess");
        this.frame.setBounds(200, 50, 617, 655);
        this.frame.setLayout(new BorderLayout());
        this.panel = new GUIPanel(10, 10, 73, 75);
        this.match = new Match("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        this.panel.setPieces(match.board);
        this.listener = new GUIPanel.SwingBoardListener(panel, match);
        this.frame.add(this.panel, BorderLayout.CENTER);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        screenSize = kit.getScreenSize();
        frame.setLocation(screenSize.width / 2 - panel.getWidth() / 2, screenSize.height / 2 - panel.getHeight() / 2);
    }
}
