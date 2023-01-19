package learningagent.neuralnetwork;

import learningagent.database.ChessDataRetriever;
import learningagent.database.OneHotEncoding;
import learningagent.neuralnetwork.csvhelp.ArrayToCSV;

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


    /** CSV HELPER */
    ArrayToCSV csvWriter = new ArrayToCSV();

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
    public void fetchAndTrain() throws SQLException {

        ChessDataRetriever dataRetriever
                = new ChessDataRetriever("jdbc:mysql://localhost:3306/chess", "cakeboy", "cake043");
        dataRetriever.setQuery("SELECT FEN, blackWins, whiteWins, draws FROM genetic_dice");
        ResultSet resultSet = dataRetriever.getResultSet();

        double start = System.currentTimeMillis();

        int i=0;
        while (resultSet.next() && i<5) {

            String FEN = resultSet.getString("FEN");
            int blackWins = resultSet.getInt("blackWins");
            int whiteWins = resultSet.getInt("whiteWins");
            int draws = resultSet.getInt("draws");

            System.out.println(FEN + " | BLACK: " + blackWins + " WHITE: " + whiteWins + " D: " + draws);

            if(blackWins==0 && whiteWins==0 && draws==0){
                System.out.println("ERROR");
                break;
            }

            // Use the data to train your neural network
            double[] encodedFEN  = hotEncoder.oneHotEncodeSimplifiedFEN(FEN);
            System.out.println(Arrays.toString(encodedFEN));

            double targetBlack = (double) (blackWins) / (double)  (whiteWins+blackWins+draws);
            System.out.println(targetBlack);
            double targetWhite = (double) (whiteWins) / (double)  (whiteWins+blackWins+draws);

            double[] target = {targetBlack, targetWhite};

            // Feed this to the network
            nn.train(encodedFEN,target,learningRate);

            i++;
        }



        System.out.println("Data exhausted or max iterations reached");

        System.out.print(" " );
        System.out.println(System.currentTimeMillis() - start);;

        // BIASES (1D-ARRAY)
        csvWriter.save1DArrayToCSV(nn.getBiasesHidden(), "biasHidden");
        csvWriter.save1DArrayToCSV(nn.getBiasesOutput(), "biasOutput");

        // WEIGHTS (2D-ARRAY)
        csvWriter.save2DArrayToCSV(nn.getWeightsInputToHidden(), "weightsInputHidden");
        csvWriter.save2DArrayToCSV(nn.getWeightsHiddenToOutput(), "weightsHiddenOutput");


    }

    /** TRAIN THE NETWORK */
    public static void main(String[] args) throws SQLException {
        FetchAndTrain f = new FetchAndTrain();
        f.fetchAndTrain();
    }



}

