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
    NeuralNetwork nn = new NeuralNetwork(384,256,2, SigmoidActivationFunction);

    /** ENCODER */
    OneHotEncoding hotEncoder = new OneHotEncoding();

    /** BEST MOVE FOR TEAM */
    public String getMostPromisingBoardPosition(ArrayList<String> boardPositions, int team) {

        double bestBlack = 0.0;
        double bestWhite = 0.0;

        String tempFEN = "";
        String boardFEN = "";

        String bestBoardBLACK = "";
        String bestBoardWHITE = "";

        double blackWP;
        double whiteWP;

        double[] encodedFEN;
        double[] prediction;

        for(int i=0; i<boardPositions.size(); i++){

            // Board position
            tempFEN = boardPositions.get(i);
            boardFEN = tempFEN.split(" ", 2)[0];

            // Encode and generate NN prediction
            encodedFEN = hotEncoder.oneHotEncodeSimplifiedFEN(boardFEN);
            prediction = nn.predict(encodedFEN);

            blackWP = prediction[0];
            whiteWP = prediction[1];

            if(blackWP>bestBlack){
                bestBlack=blackWP;
                bestBoardBLACK=tempFEN;
            }

            if(whiteWP> bestWhite){
                bestWhite=whiteWP;
                bestBoardWHITE=tempFEN;
            }

        }

        if(team==0){
            return bestBoardBLACK;
        } else {
            return bestBoardWHITE;
        }

    }

}
