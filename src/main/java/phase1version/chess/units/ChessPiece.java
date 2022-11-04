package phase1version.chess.units;

import java.awt.Graphics;

import phase1version.chess.interfaces.IChessMoveInfo;
import phase1version.chess.interfaces.IChessPiece;
import phase1version.chess.interfaces.IChessboardSquare;
import phase1version.chess.utility.ChessboardSquare;
import phase1version.gui.interfaces.IDrawable;
import phase1version.gui.utility.Cache;

public class ChessPiece extends ChessboardSquare implements IChessPiece, IDrawable {
	private static final Cache cache = new Cache();
	private final char notation;
	private boolean hidden;

	protected final int team;
	protected IChessMoveInfo[] movementInfo = null;

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

	@Override public Object clone() throws CloneNotSupportedException { return super.clone(); }
	@Override public void draw(Graphics g, int x, int y, int width, int height)
	{
		var image = cache.getImage(notation);
		g.drawImage(image, x, y, width, height, null);
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

	@Override public IChessMoveInfo[] movementInfo() { return movementInfo; }

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
}