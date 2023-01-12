package expecti;
import game.DiceChess;
import genetic.FitnessFunction;
public class Evaluator implements Evaluation
{

    /**
     * construct the Object used for getting the evaluation values
     */
    private final FitnessFunction FUNCTION = new FitnessFunction();


    /**
     * final evaluation, including the weights
     */
    public int evaluation;


    /**
     * The constructor of the Evaluator object, this object is used solely to get the final evaluation value
     * @param game the Dicechess game at the current node
     */
    Evaluator(DiceChess game)
    {
    evaluation = FUNCTION.evaluate(game,eval);
    }


    @Override
    public int getEvaluation()
    {
        return evaluation;
    }

    @Override
    public int getQueenValue(){return queenValue;}
}
