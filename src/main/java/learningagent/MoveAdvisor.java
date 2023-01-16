package learningagent;

import learningagent.database.OneHotEncoding;
import learningagent.neuralnetwork.ActivationFunction;
import learningagent.neuralnetwork.NeuralNetwork;
import learningagent.neuralnetwork.SigmoidActivationFunction;

import java.util.ArrayList;


/**
 * USAGE:
 * -    initialize the network with trained parameters
 * -    pass a list of all possible board positions
 *      and return strongest board position for the desired team
 * -    use method when the list is bigger than 0 (or update the method here)
 *
 */
public class MoveAdvisor {

    /** NEURAL NETWORK */
    // 384 nodes for board position
    // 256 hidden nodes
    // 2 output nodes [0=blackWP, 1=whiteWP]
    // sigmoid activation functions
    ActivationFunction SigmoidActivationFunction = new SigmoidActivationFunction();
    //NeuralNetwork nn = new NeuralNetwork(384,256,2, SigmoidActivationFunction);
    NeuralNetwork nn = new NeuralNetwork();

    /** ENCODER */
    OneHotEncoding hotEncoder = new OneHotEncoding();

    /** BEST MOVE FOR TEAM */
    public double getMostPromisingBoardPosition(String fen, int team) {


        // Board position
        String boardFEN = fen.split(" ", 2)[0];

        // Encode and generate NN prediction
        double[] encodedFEN = hotEncoder.oneHotEncodeSimplifiedFEN(boardFEN);
        double[] prediction = nn.predict(encodedFEN);

        //
        return prediction[team];
    }

}
