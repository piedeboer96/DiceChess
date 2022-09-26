package chess.interfaces;


/**
 * Defines how a movement is performed by defining the number of steps it can take and
 * the direction (delta file, delta rank) the step is in.
 * Example: A rook that can move freely horizontally can be broken down to being capable of taking 8 steps to the left
 * and 8 steps to the right. So, there are two information containers for the horizontal movement of a rook.
 **/
public interface IChessMoveInfo
{
    /**
     * The file difference between the chess piece's current square and its next possible square.
     **/
    int deltaFile();

    /**
     * The rank difference between the chess piece's current square and its next possible square.
     **/
    int deltaRank();

    /**
     * The maximum number of steps this movement is allowed to make.
     **/
    int maxSteps();
}
