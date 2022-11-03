package phase1version.gui;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import phase1version.chess.ChessMatch;
import phase1version.chess.interfaces.IChessMatch;
import phase1version.gui.interfaces.IClickable;
import phase1version.gui.interfaces.IHighlighter;
import phase1version.gui.utility.Chessboard;
import phase1version.gui.utility.ComponentGroup;
import phase1version.gui.utility.Dice;

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

    private TextArea console;
	private JButton bShowLastAImove;
	private JButton bMessageStack;
	private JButton bClearConsole;

    public DiceChessWindow(int width, int height, boolean humanVsHuman)
    {
        super("A game of dice chess.", width, height);

        // Calculations will be done before we add stuff to the window.
        int panelWidth = width / 10 * 2;
        int boardWidth = width - panelWidth;


        int toolbarHeight = 30;
        int consoleHeight = 100;
        int boardHeight = height - consoleHeight - toolbarHeight; // Roll panel height is the same as the board.
 
        // Create a toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // Create a panel
        JPanel toolbarPanel = new JPanel();

        // Create new buttons
          bShowLastAImove = new JButton("Highlight Last AI Move");
          bMessageStack = new JButton("Explain the AI Move");
          bClearConsole = new JButton("Clear Console");
 
        // Add buttons
        toolbarPanel.add(bShowLastAImove);
        toolbarPanel.add(bMessageStack);
        toolbarPanel.add(bClearConsole);
        toolBar.add(toolbarPanel);
        toolBar.setSize(width, toolbarHeight);


        console = new TextArea(5,1);
       // console.setHorizontalAlignment(SwingConstants.CENTER);
        //console.setVerticalAlignment(SwingConstants.CENTER);
        //console.setSize(boardWidth, consoleHeight);
         
        
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
    
    public IHighlighter getHighlighter() {
		
    	return chessboard.getHighlighter();
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

    public void addListenerHighlightLastMove(ActionListener listener) {
    	bShowLastAImove.addActionListener(listener);
	}
    public void setConsoleText(String text) { 
    	 
    	//console.append(text+"\n");
    	
    	console.append(text+"\n");
    	
    	}

	public void addListenerShowDetails(ActionListener actionListener) {
		bMessageStack.addActionListener(actionListener);
		
	}
	public void addListenerClearConsole(ActionListener actionListener) {
		bClearConsole.addActionListener(actionListener);
		
	}

	public void clearConsole() {
		console.setText("");
		
	}
}
