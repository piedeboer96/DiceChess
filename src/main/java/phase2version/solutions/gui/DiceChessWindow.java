package phase2version.solutions.gui;

import phase2version.framework.game.Player;
import phase2version.framework.game.Setup;
import phase2version.framework.gui.GameWindow;
import phase2version.framework.gui.GraphicalUserInterface;
import phase2version.framework.gui.UserInteractions;
import phase2version.framework.gui.Window;
import phase2version.framework.utility.Console;
import phase2version.framework.utility.Engine;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class DiceChessWindow implements GameWindow, GraphicalUserInterface, UserInteractions, Window {
    private final Chessboard        BOARD;
    private final Engine[]          ENGINES     = { null };
    private final HumanPlayer[]     CONTROLLERS = new HumanPlayer[2];
    private final IntegratedConsole CONSOLE;
    private final JFrame            FRAME;
    private final PassiveDie        DIE;

    public DiceChessWindow() {
        CONTROLLERS[0] = new HumanPlayer();
        CONTROLLERS[1] = new HumanPlayer();

        FRAME = new JFrame("A game of dice chess");
        FRAME.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FRAME.setSize(Configuration.GAME_WINDOW);
        FRAME.setLayout(new BorderLayout());
        FRAME.setResizable(true);

        BOARD = new Chessboard();
        FRAME.add(BOARD, BorderLayout.CENTER);

        JPanel consoleHolder = new JPanel();
        consoleHolder.setPreferredSize(Configuration.SIDE_PANEL);
        consoleHolder.setLayout(new GridBagLayout());
        CONSOLE = new IntegratedConsole();

        JButton clear = new JButton("CLEAR CONSOLE");
        clear.setFocusPainted(false);
        clear.setBackground(Color.WHITE);
        clear.addActionListener(event -> CONSOLE.clear());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        consoleHolder.add(clear, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.weighty = 0.9;
        constraints.weightx = 1;
        consoleHolder.add(CONSOLE, constraints);

        FRAME.add(consoleHolder, BorderLayout.WEST);

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(Configuration.SIDE_PANEL);
        sidePanel.setLayout(new GridLayout(9, 1));

        sidePanel.add(new JPanel()); // row 1
        sidePanel.add(new JPanel()); // row 2

        JPanel dieHolder = new JPanel();
        dieHolder.setPreferredSize(Configuration.CONTROL_BAR);

        DIE = new PassiveDie();
        DIE.setPreferredSize(Configuration.DIE);
        dieHolder.add(DIE);

        sidePanel.add(dieHolder);

        sidePanel.add(new JPanel()); // row 4
        sidePanel.add(new JPanel()); // row 5
        sidePanel.add(new JPanel()); // row 6
        sidePanel.add(new JPanel()); // row 7
        sidePanel.add(new JPanel()); // row 8

        JPanel engineControls = new JPanel();
        engineControls.setPreferredSize(Configuration.CONTROL_BAR);
        engineControls.setLayout(new GridLayout(1, 3));

        JButton revert = new JButton("REVERT");
        revert.setFocusPainted(false);
        revert.setBackground(Color.WHITE);
        revert.addActionListener(event -> {
            if (ENGINES[0] != null) { ENGINES[0].recall(); }
        });
        engineControls.add(revert);

        JButton playAndPause = new JButton("PAUSE");
        playAndPause.setFocusPainted(false);
        playAndPause.setBackground(Color.WHITE);
        playAndPause.addActionListener(event -> {
            if (ENGINES[0] != null) {
                if (ENGINES[0].isRunning() && !ENGINES[0].isPaused()) {
                    ENGINES[0].pause();
                    playAndPause.setText("PLAY");
                } else if (ENGINES[0].isRunning()){
                    ENGINES[0].resume();
                    playAndPause.setText("PAUSE");
                }
            }
        });
        engineControls.add(playAndPause);

        JButton step = new JButton("STEP");
        step.setFocusPainted(false);
        step.setBackground(Color.WHITE);
        step.addActionListener(event -> {
            if (ENGINES[0] != null) { ENGINES[0].step(); }
        });
        engineControls.add(step);

        sidePanel.add(engineControls);

        FRAME.add(sidePanel, BorderLayout.EAST);

        WindowAdapter adapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (ENGINES[0] != null) { ENGINES[0].terminate(); }
            }
        };
        FRAME.addWindowListener(adapter);

        center();
    }

    @Override
    public void activate() {
        if (ENGINES[0] == null) { throw new IllegalStateException("NO ENGINE HAS BEEN ATTACHED!"); }
        FRAME.setVisible(true);
    }

    @Override
    public void attach(Engine e) {
        if (e == null) { throw new NullPointerException("NULL IS NOT CONSIDERED AN ENGINE!"); }
        ENGINES[0] = e;
    }
    
    @Override
    public void center() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - FRAME.getWidth()) / 2;
        int y = (screen.height - FRAME.getHeight()) / 2;
        FRAME.setLocation(x, y);
    }

    @Override
    public void enable(int controller) {
        if (controller != 0 && controller != 1) { throw new IllegalArgumentException("CONTROLLER NUMBER IS NOT VALID!"); }
        BOARD.addMouseListener(CONTROLLERS[controller]);
    }

    @Override
    public Player getPlayer(int controller) {
        if (controller != 0 && controller != 1) { throw new IllegalArgumentException("CONTROLLER NUMBER IS NOT VALID!"); }
        return CONTROLLERS[controller];
    }

    @Override
    public Console getConsole() {
        return CONSOLE;
    }

    @Override
    public void project(Setup s) {
        BOARD.project(s);
    }

    @Override
    public void updateBoard() {
        BOARD.repaint();
    }

    @Override
    public void updateDie(int team, int type) {
        DIE.update(team, type);
    }
}

