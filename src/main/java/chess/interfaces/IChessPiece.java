package chess.interfaces;

public interface IChessPiece extends IChessboardSquare, IGhost,Cloneable {
	/**
	 * Determines whether two chess pieces are fundamentally the same object.
	 **/
	boolean equals(IChessPiece other);

	/**
	 * Gets the fen-representation of the chess piece type and its team.
	 **/
	char toFen();

	/**
	 * Gets the information of the moves the chess piece is capable of. See
	 * IChessMoveInfo for more information.
	 **/
	IChessMoveInfo[] movementInfo();

	/**
	 * Determines whether both chess pieces are each other's opponent.
	 **/
	boolean opponentOf(IChessPiece other);

	/**
	 * Determines whether a chess piece is allowed to be promoted to another chess
	 * piece.
	 **/
	boolean promotable();

	/**
	 * Sets the square the chess piece is on.
	 **/
	void setPosition(IChessboardSquare square);

	/**
	 * Gets the team, also known as the color, of the chess piece. The number 0
	 * represents the color black, whereas the number 1 represents the color white.
	 * 
	 * @return An integer that is either 0 or 1.
	 **/
	int team();

	

}
