package expecti;

public interface Evaluation
{
    final int queenValue = 805;

    /**
     * The getEvaluation() method returns the evaluation of the game
     * @return int representing the evaluation function result
     */
    public int getEvaluation();

    /**
     * This method returns the value of the queen, useful for calculating the value of the king
     * (multiplication of the queen value)
     * @return the evaluation value of a queen piece
     */
    public int getQueenValue();


}
