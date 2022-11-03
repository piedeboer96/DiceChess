package chess.utility;

import chess.interfaces.IChessboardSquare;

public class ChessboardSquare implements IChessboardSquare
{
	protected int file, rank;
	protected int score;

	public ChessboardSquare(int file, int rank) {
		if (file < 0 || file > 7) { throw new IllegalArgumentException("File is not within board boundaries."); }
		else if (rank < 0 || rank > 7) { throw new IllegalArgumentException("Rank is not within board boundaries"); }
		this.file = file;
		this.rank = rank;
	}

	@Override public boolean equals(IChessboardSquare other) { return file == other.file() && rank == other.rank(); }

	@Override public int file() { return file; }

	@Override public int rank() { return rank; }

	@Override public int toIndex() { return (rank * 8) + file; }

	@Override
	public String toString() {
		return "CBS [file=" + file + ", rank=" + rank + ", score=" + score + "]";
	}

	@Override
	public void addScore(int score) {
		this.score += score;
	}

	@Override
	public void resetScore() {
		this.score = 0;
	}

	@Override
	public int getScore() {
		return score;
	}

}
