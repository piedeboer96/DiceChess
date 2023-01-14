package learningagent.neuralnetwork;

import learningagent.database.ChessDataRetriever;
import learningagent.database.OneHotEncoding;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 1. Fetch the data from SQL Database table 'chess_games' inside ChessDB
 *          -> This is either maxIterations time or until all data is exhausted
 * 2. Encode the FEN according to one-hot encoding
 * 3. Train the network using the ONE-HOT and desired team win probability
 *
 */
public class FetchAndTrain {

    /** ENCODER */
    OneHotEncoding hotEncoder = new OneHotEncoding();

    /** NEURAL NETWORK */
    ActivationFunction SigmoidActivationFunction = new SigmoidActivationFunction();

    // 384 nodes for board position
    // 256 hidden nodes
    // 2 output nodes
    NeuralNetwork nn = new NeuralNetwork(384,256,2, SigmoidActivationFunction);
    double learningRate = 0.001;

    /** FETCH AND TRAIN FOR COLOUR */
    public void fetchAndTrain(long maxIterations) throws SQLException {

        ChessDataRetriever dataRetriever
                = new ChessDataRetriever("jdbc:mysql://localhost:3306/ChessDB", "cakeboy", "cake043");
        dataRetriever.setQuery("SELECT FEN, blackWins, whiteWins, draws FROM chess_games");
        ResultSet resultSet = dataRetriever.getResultSet();

        int i=0;

            while (resultSet.next() && i<maxIterations) {

                String FEN = resultSet.getString("FEN");
                int whiteWins = resultSet.getInt("whiteWins");
                int blackWins = resultSet.getInt("blackWins");
                int draws = resultSet.getInt("draws");



                if(blackWins==0 && whiteWins==0 && draws==0){
                    System.out.println("ERROR");
                    break;
                }

                // Use the data to train your neural network
                double[] encodedFEN  = hotEncoder.oneHotEncodeSimplifiedFEN(FEN);

                double targetBlack = (double) (blackWins) / (double)  (whiteWins+blackWins+draws);
                double targetWhite = (double) (whiteWins) / (double)  (whiteWins+blackWins+draws);

                double[] target = {targetBlack, targetWhite};

                // Feed this to the network
                nn.train(encodedFEN,target,learningRate);

                i++;

            }







    }

    /** TRAIN THE NETWORK */
    public static void main(String[] args) throws SQLException {
        FetchAndTrain f = new FetchAndTrain();
        f.fetchAndTrain(100000);
    }



}

