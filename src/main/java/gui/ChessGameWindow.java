package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import chess.interfaces.IChessMatch;
import gui.interfaces.IChessBoardGraphics;
import gui.interfaces.IChessGameWindow;
import gui.interfaces.IHumanPlayer;
import gui.utility.ChessBoardGraphics;
import gui.utility.WindowStateListener;

public class ChessGameWindow implements IChessGameWindow {
	private final ChessBoardGraphics boardGraphics;
	private final WindowStateListener stateListener;
	private boolean pause = false;

	public ChessGameWindow() {
		JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(200, 50, 617, 655);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		MouseListener mouseClick = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				setPause(!getPause());
				if (getPause())
					System.err.println("pause pressed");
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

		};
		frame.setVisible(true);
		frame.addMouseListener((MouseListener) mouseClick);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);

		stateListener = new WindowStateListener();
		frame.addWindowListener(stateListener);

		boardGraphics = new ChessBoardGraphics(10, 10, 73, 75);
		frame.add(boardGraphics);
		updateUI();
	}

	protected void setPause(boolean pause) {

		this.pause = pause;
	}

	public boolean getPause() {

		return pause;
	}

	public void addPlayer(IHumanPlayer player) {
		boardGraphics.addMouseListener(player);
		player.setPlayground(boardGraphics);
	}

	public void displayMatch(IChessMatch match) {
		boardGraphics.trackChessPieces(match.pieces());
		updateUI();
	}

	public void updateUI() {
		boardGraphics.updateUI();
	}

	public IChessBoardGraphics getBoardGraphics() {
		return boardGraphics;
	}

	public boolean hasTerminated() {
		return stateListener.windowIsTerminated();
	}

	public void removePlayer(IHumanPlayer player) {
		boardGraphics.removeMouseListener(player);
		player.setPlayground(null);
	}
}


