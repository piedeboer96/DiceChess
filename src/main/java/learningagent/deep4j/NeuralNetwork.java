package learningagent.deep4j;

import learningagent.database.ChessDataRetriever;
import learningagent.database.OneHotEncoding;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NeuralNetwork {

    private MultiLayerNetwork network;
    private DataNormalization normalizer;

    public NeuralNetwork() {
        int inputNodes = 384;
        int outputNodes = 1;
        int hiddenNodes = 512;
        int seed = 123;
//        double learningRate = 0.001;
//        double l2 = 0.01;       // L2 regularization coefficient
//        double dropOut = 0.5;  // dropout rate

        // Define the architecture of the network
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .updater(new Adam())
//                .l2(l2)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(inputNodes)
                        .nOut(hiddenNodes)
                        .activation(Activation.SIGMOID)
                        .weightInit(WeightInit.XAVIER)
//                        .dropOut(dropOut)
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .nIn(hiddenNodes)
                        .nOut(outputNodes)
                        .activation(Activation.SIGMOID)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .build();

        network = new MultiLayerNetwork(conf);
        network.init();

        normalizer = new NormalizerStandardize();
    }

    public void train(List<DataSet> dataList) throws IOException {

        normalizer.fit(dataList.get(0));

        // Now you can iterate through the list and train your network on each DataSet
        for (DataSet data : dataList) {
            // Normalize data
            normalizer.transform(data);
            // Train the network
            network.fit(data);
            //System.out.println("train");
        }

        save("coolNetwork");

    }

    public void fetchAndTrain() throws SQLException {

        ChessDataRetriever dataRetriever
                = new ChessDataRetriever("jdbc:mysql://localhost:3306/chess", "cakeboy", "cake043");
        dataRetriever.setQuery("SELECT FEN, blackWins, whiteWins, draws FROM genetic_dice");
        ResultSet resultSet = dataRetriever.getResultSet();


            while (resultSet.next()) {

                String FEN = resultSet.getString("FEN");
                int blackWins = resultSet.getInt("blackWins");
                int whiteWins = resultSet.getInt("whiteWins");
                int draws = resultSet.getInt("draws");

                // Skip invalid data
                if(blackWins==0 && whiteWins==0 && draws==0){
                    System.out.println("ERROR");
                    continue;
                }

                // Encode the FEN string
                OneHotEncoding hotEncoder = new OneHotEncoding();
                //String boardFEN =
                double[] encodedFEN  = hotEncoder.oneHotEncodeSimplifiedFEN(FEN);

                // Calculate the target value
                double targetBlack = (double) (blackWins) / (double) (whiteWins+blackWins+draws);

                // input array
                INDArray inputArray = Nd4j.create(encodedFEN);
                INDArray outputArray = Nd4j.create(new double[] {targetBlack}, new int[] {1});

                // Create DataSet
                DataSet data = new DataSet(inputArray, outputArray);

               // normalizer.transform(data);
                // Train the network
                network.fit(data);
        }


    }

    public double predict(double[] input) {


        // Convert input to DL4J INDArray
        INDArray inputArray = Nd4j.create(input);

        // Perform prediction
        INDArray output = network.output(inputArray);

        // Return the first and only element of the output array
        return output.getDouble(0);
    }


    public void save(String filename) throws IOException {
        File locationToSave = new File(filename);
        ModelSerializer.writeModel(network, locationToSave, true);
    }

    public void load(String filename) throws IOException {

        File locationToLoad = new File(filename);
        network = ModelSerializer.restoreMultiLayerNetwork(locationToLoad);
    }

    public static void main(String[] args) throws SQLException {
        NeuralNetwork nn = new NeuralNetwork();
        nn.fetchAndTrain();
    }

}