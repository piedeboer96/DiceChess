package learningagent;

import learningagent.database.OneHotEncoding;
import learningagent.deep4j.NeuralNetwork;

import java.io.IOException;


public class MoveAdvisor {

    /** NEURAL NETWORK */
    NeuralNetwork nn = new NeuralNetwork();     // library network

    /** ENCODER */
    OneHotEncoding hotEncoder = new OneHotEncoding();

    /** BEST MOVE FOR TEAM */
    public double getMostPromisingBoardPosition(String fen, int team) throws IOException {


        nn.load("coolNetwork");

        // Board position
        String boardFEN = fen.split(" ", 2)[0];

        // Encode and generate NN prediction
        double[] encodedFEN = hotEncoder.oneHotEncodeSimplifiedFEN(boardFEN);
        double predictBlack = nn.predict(encodedFEN);

        // return for white or black
        if(team==0){
            return predictBlack;
        } else {
            return (1-predictBlack);
        }
    }

}
