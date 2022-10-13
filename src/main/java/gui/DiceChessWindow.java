package gui;

import java.awt.BorderLayout;

import javax.swing.*;

import chess.ChessMatch;
import chess.interfaces.IChessMatch;
import gui.interfaces.IClickable;
import gui.utility.Chessboard;
import gui.utility.ComponentGroup;
import gui.utility.Dice;

public final class DiceChessWindow extends Window
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		var match = new ChessMatch();
		var window = new DiceChessWindow(900, 740, true);
		window.display(match);
		var player = window.getPlayer();
		player.playIn(1,  match);
	}
	
    private final Chessboard chessboard;
    private final ComponentGroup componentGroup;
    private IChessMatch match;
    private Player player;
	private Dice dice;

    private JLabel console;

    public DiceChessWindow(int width, int height, boolean humanVsHuman)
    {
        super("A game of dice chess.", width, height);

        // Calculations will be done before we add stuff to the window.
        int panelWidth = width / 10 * 2;
        int boardWidth = width - panelWidth;


        int toolbarHeight = 30;
        int consoleHeight = 80;
        int boardHeight = height - consoleHeight - toolbarHeight; // Roll panel height is the same as the board.
 
        // Create a toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // Create a panel
        JPanel toolbarPanel = new JPanel();

        // Create new buttons
        JButton bRun = new JButton("Run AI");
        JButton bPause = new JButton("Pause AI");
        JButton bStep = new JButton("Step AI");
 
        // Add buttons
        toolbarPanel.add(bRun);
        toolbarPanel.add(bPause);
        toolbarPanel.add(bStep);
        toolBar.add(toolbarPanel);
        toolBar.setSize(width, toolbarHeight);


        console = new JLabel();
        console.setHorizontalAlignment(SwingConstants.CENTER);
        console.setVerticalAlignment(SwingConstants.CENTER);
        console.setSize(boardWidth, consoleHeight);
        add(toolBar, BorderLayout.NORTH);
        add(console, BorderLayout.SOUTH);

		chessboard = new Chessboard(0, 0, boardWidth, boardHeight);
        dice = new Dice(boardWidth, 0, panelWidth, boardHeight);
        componentGroup = new ComponentGroup(2);
        componentGroup.add(chessboard);
        componentGroup.add(dice);
        add(componentGroup, BorderLayout.CENTER);

        clickableSources = new IClickable[2];
        clickableSources[0] = chessboard;
        clickableSources[1] = dice;

        display();
        
        player = new DiceChessPlayer(humanVsHuman);
        addMouseListener(player);
    }

    @Override public int[] determineLocationInContentBody(int mouseX, int mouseY)
    {
        int[] coordinates = new int[2];
        coordinates[0] = mouseX - insets.left;
        coordinates[1] = mouseY - insets.top - 25;
        return coordinates;
    }

    public void display(IChessMatch match)
    {
        this.match = match;
        var stalker = chessboard.getStalker();
        stalker.follow(match.pieces());
        refresh();
    }

    public IChessMatch getMatch() { return match; }
    
    public Player getPlayer() { return player; }

    @Override public void refresh()
    {
        componentGroup.updateUI();
    }
    public Dice getDice() {
		return dice;
	}

    public void setConsoleText(String text) { console.setText(text); }
}
