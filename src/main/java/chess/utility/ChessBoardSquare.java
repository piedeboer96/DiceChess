package chess.utility;

import chess.interfaces.IChessBoardSquare;

public class ChessBoardSquare implements IChessBoardSquare {
	private final int file, rank;
	private int score;

	public ChessBoardSquare(int file, int rank) {
		if (file < 0 || file > 7) {
			throw new IllegalArgumentException("File number is not within board boundaries.");
		} else if (rank < 0 || rank > 7) {
			throw new IllegalArgumentException("Rank number is not within board boundaries");
		}
		this.file = file;
		this.rank = rank;
	}

	public boolean equals(IChessBoardSquare other) {
		return file == other.file() && rank == other.rank();
	}

	public int file() {
		return file;
	}

	public int rank() {
		return rank;
	}

	public int toIndex() {
		return (rank * 8) + file;

	}

	@Override
	public String toString() {
		return "ChessBoardSquare [file=" + file + ", rank=" + rank + ", score=" + score + "]";
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
