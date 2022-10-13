package chess.units;

import chess.interfaces.IChessMoveInfo;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
import chess.utility.ChessboardSquare;
import gui.interfaces.IDrawable;
import gui.utility.Cache;

import java.awt.Graphics;

public class ChessPiece extends ChessboardSquare implements IChessPiece, IDrawable {
	private static final Cache cache = new Cache();
	private final char notation;
	private boolean hidden;

	protected final int team;

	/**
	 * @param fen  The fen-representation of the chess piece and the team it belongs to.
	 * @param file The column number the chess piece is located in.
	 * @param rank The row number the chess piece is located in.
	 **/
	public ChessPiece(char fen, int file, int rank) {
		super(file, rank);
		if (Character.isLowerCase(fen)) { team = 0; }
		else { team = 1; }
		notation = fen;
	}

	@Override public boolean equals(IChessPiece other) { return notation == other.toFen() && equals((IChessboardSquare) other); }

	@Override public boolean friendOf(IChessPiece other) {
		return team == other.team();
	}

	@Override public void ghostTo(IChessboardSquare square)
	{
		file = square.file();
		rank = square.rank();
	}

	@Override public void hide() { hidden = true; }

	@Override public boolean isHidden() { return hidden; }

	@Override public IChessMoveInfo[] movementInfo() { return null; }

	@Override public boolean opponentOf(IChessPiece other) { return team != other.team(); }

	@Override public boolean promotable() { return false; }

	@Override public void setPosition(IChessboardSquare square) {
		file = square.file();
		rank = square.rank();
	}

	@Override public void show() {
		hidden = false;
	}

	@Override public int team() { return team; }

	@Override public char toFen() { return notation; }

	@Override public String toString()
	{
		return " [fen=" + notation + ", team=" + team + ", file=" + file + ", rank=" + rank + "]";
	}

	@Override public Object clone() throws CloneNotSupportedException { return super.clone(); }
	@Override public void draw(Graphics g, int x, int y, int width, int height) {
		var image = cache.getImage(notation);
		g.drawImage(image, x, y, width, height, null);
	}
}