package learningagent.database;

import learningagent.datapipeline.OneHotEncoding;
import learningagent.neuralnetwork.ActivationFunction;
import learningagent.neuralnetwork.NeuralNetwork;
import learningagent.neuralnetwork.SigmoidActivationFunction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 1. Fetch the data from SQL Database table 'chess_games' inside ChessDB
 *          -> This is either maxIterations time or until all data is exhausted
 * 2. Encode the FEN according to one-hot encoding
 * 3. Train the network using the ONE-HOT and desired team win probability
 *
 * TODO:  - train for both teams, use 2 networks or 1
 *        - test, validate split
 *        - monitor heap
 */
public class FetchAndTrain {

    /** ENCODER */
    OneHotEncoding hotEncoder = new OneHotEncoding();

    /** NEURAL NETWORK */
    ActivationFunction SigmoidActivationFunction = new SigmoidActivationFunction();

    // 384 nodes for board position
    // 1 node for team
    // 1 node for outcome
    NeuralNetwork nn = new NeuralNetwork(384+2,256,1, SigmoidActivationFunction);
    double learningRate = 0.01;
    long maxIterations = 10000;


    /** FETCH AND TRAIN FOR COLOUR */
    public void fetchAndTrain(long maxIterations, int team) throws SQLException {

        ChessDataRetriever dataRetriever
                = new ChessDataRetriever("jdbc:mysql://localhost:3306/ChessDB", "cakeboy", "cake043");
        dataRetriever.setQuery("SELECT FEN, blackWins, whiteWins, draws FROM chess_games");
        ResultSet resultSet = dataRetriever.getResultSet();

        int i=0;

        // black
        if(team==0){

            while (resultSet.next() && i<maxIterations) {

                String FEN = resultSet.getString("FEN");
                int whiteWins = resultSet.getInt("whiteWins");
                int blackWins = resultSet.getInt("blackWins");
                int draws = resultSet.getInt("draws");

                System.out.println("sFEN: " + FEN);

                // Use the data to train your neural network
                double[] encodedFEN  = hotEncoder.oneHotEncodeSimplifiedFEN(FEN);

                if(blackWins==0 && whiteWins==0 && draws==0){
                    System.out.println("ERROR");
                    break;
                }

                double target = (double) (blackWins) / (double)  (whiteWins+blackWins+draws) ;


                // Feed this to the network
                nn.train(encodedFEN,target,learningRate);

                i++;

            }

            // white
        } else {

            while (resultSet.next() && i<maxIterations) {

                String FEN = resultSet.getString("FEN");
                int whiteWins = resultSet.getInt("whiteWins");
                int blackWins = resultSet.getInt("blackWins");
                int draws = resultSet.getInt("draws");

                System.out.println("sFEN: " + FEN);

                if(blackWins==0 && whiteWins==0 && draws==0){
                    System.out.println("ERROR");
                    break;
                }

                // Use the data to train your neural network
                double[] encodedFEN  = hotEncoder.oneHotEncodeSimplifiedFEN(FEN);
                double target = (double) (whiteWins) / (double)  (whiteWins+blackWins+draws) ;

                // Feed this to the network
                nn.train(encodedFEN,target,learningRate);

                i++;

            }






        }

        System.out.println("=======");

//        System.out.println(Arrays.deepToString(nn.getWeightsInputToHidden()));
        System.out.println("TO OUTPUT: " + Arrays.deepToString(nn.getWeightsHiddenToOutput()));


    }

    /** TRAIN THE NETWORK */
    public static void main(String[] args) throws SQLException {
        FetchAndTrain f = new FetchAndTrain();
        f.fetchAndTrain(100, 0);
    }



}
