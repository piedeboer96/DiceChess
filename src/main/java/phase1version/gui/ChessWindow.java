package phase1version.gui;

import phase1version.chess.ChessMatch;
import phase1version.chess.interfaces.IChessMatch;
import phase1version.gui.interfaces.IClickable;
import phase1version.gui.utility.Chessboard;

public final class ChessWindow extends Window
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		// Creates a human vs human chess game.
		var match = new ChessMatch();
		var window = new ChessWindow(640, 640, true);
		window.display(match);
		var player = window.getPlayer();
		player.playIn(1, match);
	}
	
    private final Chessboard chessboard;
    private IChessMatch match;
    private Player player;
    
    public ChessWindow(int width, int height, boolean humanVsHuman)
    {
        super("A game of chess.", width, height);

        chessboard = new Chessboard(0, 0, width, height);
        add(chessboard);

        clickableSources = new IClickable[1];
        clickableSources[0] = chessboard;

        display();
        
        player = new ChessPlayer(humanVsHuman);
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

    @Override public void refresh() { chessboard.updateUI(); }
}