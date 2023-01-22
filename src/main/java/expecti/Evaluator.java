package expecti;
import game.DiceChess;
import genetic.FitnessFunction;
public class Evaluator implements Evaluation
{

    /**
     * construct the Object used for getting the evaluation values
     */
    private final FitnessFunction FUNCTION = new FitnessFunction();
    private double[] weights;

    /**
     * final evaluation, including the weights
     */
    public double evaluation;


    /**
     * The constructor of the Evaluator object, this object is used solely to get the final evaluation value
     * @param game the Dicechess game at the current node
     */
    Evaluator(DiceChess game, double[] weights)
    {
//    evaluation = FUNCTION.evaluate(game,weights);
    }


    @Override
    public double getEvaluation()
    {
        return evaluation;
    }

    @Override
    public int getQueenValue(){return queenValue;}
}
