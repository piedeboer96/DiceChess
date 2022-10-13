package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

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

    public DiceChessWindow(int width, int height, boolean humanVsHuman)
    {
        super("A game of dice chess.", width, height);

        int panelWidth = width / 10 * 2;
        int boardWidth = width - panelWidth;
        
        /***************/
        
       
 
        // set layout for frame
       // f.setLayout(new BorderLayout());
 
        // create a toolbar
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        
 
        // create a panel
        JPanel p = new JPanel();
 
              // create new buttons
        JButton bRun = new JButton("Run AI");
        JButton bPause = new JButton("Pause AI");
        JButton bStep = new JButton("Step AI");
 
        // add buttons
        p.add(bRun);
        p.add(bPause);
        p.add(bStep);
 
        tb.add(p);
        add(tb, BorderLayout.NORTH);
        

        
         
        
        int tbW=25;
		 
		chessboard = new Chessboard(0, 0, boardWidth, height-tbW);
         dice = new Dice(boardWidth, 0, panelWidth, height-tbW);
        componentGroup = new ComponentGroup(3);
 
        componentGroup.add(tb);
        componentGroup.add(chessboard);

        componentGroup.add(dice);
        add(componentGroup);

        clickableSources = new IClickable[2];
        clickableSources[0] = chessboard;
        clickableSources[1] = dice;

        display();
        
        player = new DiceChessPlayer(humanVsHuman);
        addMouseListener(player);
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
}
