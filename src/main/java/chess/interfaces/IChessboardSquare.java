package chess.interfaces;

/**
 * Represents a square on a chessboard.
 **/
public interface IChessboardSquare
{
    /**
     * Determines whether 2 square objects have the same file and rank.
     * If so, then both objects are representing the same square.
     **/
    boolean equals(IChessboardSquare other);

    /**
     * Gets the column in which the represented square is located in.
     **/
    int file();

    /**
     * Gets the row in which the represented square is located in.
     **/
    int rank();

    /**
     * Converts the (file, rank) coordinate into a single index that can be used by the chessboard representation.
     **/
    int toIndex();
    /**
	 * This field will contain the score given to a piece, high score are better
	 * moves then lower score .
	 **/
	void addScore(int score);
	void resetScore();
	int getScore();
  
}
