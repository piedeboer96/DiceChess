package learningagent.reinforcement;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.AdaMax;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * Neural Network that is being learned as qFunction Approximate.
 *
 * Explanation:
 *      • The Q-function neural network takes in the current board state as input
 *      • The output of the network is a Q-value for each legal move available in that state
 *      • During training, the network is updated using the Q-learning algorithm, which uses the difference between the predicted Q-value and the actual rewards received to adjust the network's weights
 *      • Once trained, the network can be used to select actions (moves) in the game, by choosing the action with the highest predicted Q-value for a given state.
 *
 *  In DQN, each output node of the neural network will be associated with one of your possible actions
 *  (assuming a finite discrete action space).
 *
 *  https://ai.stackexchange.com/questions/21669/whats-the-right-way-of-building-a-deep-q-network
 *
 */
public class QFunction {
    private MultiLayerNetwork qFunction;

    public QFunction() {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(123)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam())
                .list()
                .layer(new DenseLayer.Builder().nIn(384).nOut(128).activation(Activation.RELU).build())
                .layer(new OutputLayer.Builder().nIn(128).nOut(80).activation(Activation.IDENTITY).lossFunction(LossFunctions.LossFunction.MSE).build())
                .build();

        this.qFunction = new MultiLayerNetwork(conf);
        this.qFunction.init();
    }

    public MultiLayerNetwork getQFunction() {
        return qFunction;
    }
}
