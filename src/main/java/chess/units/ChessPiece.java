package chess.units;

import chess.interfaces.IChessboardSquare;
import chess.interfaces.IChessPiece;

public abstract class ChessPiece implements IChessPiece {
	/**
	 * The fen representation of the chess piece and the team it belongs to. So, the pieces with a black color have a character from the set { b, k, n, p, q ,r } and the pieces with a white color have a character from the set { B, K, N, P, Q, R }.
	 **/
	private final char fen;

	/**
	 * Represents the team the chess piece belongs to. See team() for more information.
	 **/
	protected final int team;

	/**
	 * The column on the board the chess piece is located in.
	 **/
	protected int file;

	/**
	 * Defines whether a chess piece is temporarily hidden for calculations.
	 **/
	protected boolean hidden;

	/**
	 * The row on the board the chess piece is located in.
	 **/
	protected int rank;

	/**
	 * Piece score to use for material cost evaluation.
	 */
	protected int score;

	/**
	 * @param fen  The fen-representation of the chess piece and the team it belongs to.
	 * @param file The column number the chess piece is located in.
	 * @param rank The row number the chess piece is located in.
	 **/
	public ChessPiece(char fen, int file, int rank) {
		if (Character.isLowerCase(fen)) {
			team = 0;
		} else {
			team = 1;
		}
		this.fen = fen;
		this.file = file;
		this.rank = rank;
	}

	public boolean equals(IChessboardSquare square) {
		return file == square.file() && rank == square.rank();
	}

	public boolean friendOf(IChessPiece other) {
		return team == other.team();
	}

	public int file() {
		return file;
	}

	public void ghostTo(IChessboardSquare square) {
		file = square.file();
		rank = square.rank();
	}

	public void hide() {
		hidden = true;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean opponentOf(IChessPiece other) {
		return team != other.team();
	}

	public boolean promotable() {
		return false;
	}

	public void setPosition(IChessboardSquare square) {
		file = square.file();
		rank = square.rank();
	}

	public int rank() {
		return rank;
	}

	public void show() {
		hidden = false;
	}

	public int team() {
		return team;
	}

	public char toFen() {
		return fen;
	}

	public int toIndex() {
		return (rank * 8) + file;
	}

	@Override
	public String toString() {
		return " [fen=" + fen + ", team=" + team + ", file=" + file + ", rank=" + rank + "]";
	}

	@Override
	public int getScore() {
		return score;
	}

	public void addScore(int score) {
		this.score = this.score + score;
	}

	@Override
	public void resetScore() {
		this.score = 0;

	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}