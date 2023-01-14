package expecti;


import game.ChessPiece;
import game.DiceChess;
import game.Square;

import java.util.List;

public class ExpectiminimaxState implements State
{

    // the DiceChess match at the current node
    private DiceChess match;

    // the dice value rolled, only used by the root node
    private int rolled;

    private int[] weights;

    //hardcoded evaluation function


    @Override
    public String toString() {
        match.switchActiveColor();
        return match.toString();
    }

    /**
     * this constructor is only used for the root node
     * @param snapshot the FEN representation of the current game state
     * @param rolled the dice value rolled, to determine allLegalMoves()
     */
        public ExpectiminimaxState(String snapshot, int rolled, int[] weights)
        {
            match = new DiceChess(snapshot);
            this.rolled = rolled;
            this.weights = weights;
        }


    /**
     * this constructor is used for all max/min nodes, excluding the root node
     * @param fen the current fen of the state
     */
        public ExpectiminimaxState(String fen, int[] weights)
        {
            this.match = new DiceChess(fen);
            this.weights = weights;
        }

        
    @Override
    public List getAllPossibleMoves()
    {
        int team = match.getActiveColor();
        return match.getTeamOpportunities(team);
    }

    @Override
    public List getAllLegalMoves()
    {
        int team = match.getActiveColor();
        if(rolled == 0)
        {
            throw new RuntimeException("only used for root");
        }
        else
        {
            return match.getTeamOpportunities(team, rolled);
        }
    }

    @Override
    public int getStateEvaluation()
    {
       Evaluator eval = new Evaluator(match, weights);
       return eval.getEvaluation();

//       if(match.getActiveColor() == 0)
//       {
//           return switch (match.getState())
//                   {
//                       case DRAW -> 0;
//                       case BLACK_WON -> eval.getQueenValue()*100;
//                       case WHITE_WON -> eval.getQueenValue()*-100;
//                       default -> eval.getEvaluation();
//                   };
//       }
//       else
//       {
//           return switch (match.getState())
//                   {
//                       case DRAW -> 0;
//                       case BLACK_WON -> eval.getQueenValue()*-100;
//                       case WHITE_WON -> eval.getQueenValue()*100;
//                       default -> eval.getEvaluation();
//                   };
//       }

    }

    @Override
    public DiceChess getMatch()
    {
        return this.match;
    }
}

