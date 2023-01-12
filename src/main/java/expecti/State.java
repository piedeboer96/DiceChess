package expecti;

import game.DiceChess;

import java.util.List;

public interface State
{

    /**
     * getAllPossibleMoves() calls upon the function within DiceChess, which is used to get all the moves of a team
     * @return a List<MovementOpportunity> containing all the moves a team can make
     */
    public List getAllPossibleMoves();


    /**
     * getAllLegalMoves() calls upon the function within DiceChess, which is used to get all the moves of a team, limited
     * by the dice roll (only the rolled piece can be played)
     * @return a List<MovementOpportunity> containing all the moves a team can make with a dice roll
     */
    public List getAllLegalMoves();

    /**
     * getStateEvaluation() evaluates the chessboard, used to determine the 'goodness' of a move
     * @return must still be implemented___________________________________________________________________________________
     */
    public int getStateEvaluation();


    /**
     * The getMatch() method gets the DiceChess match at this state
     * @return the match at this state
     */
    public DiceChess getMatch();
}
