package learningagent.neuralnetwork;

/**
 *
 * Neural Network
 * - Xavier weight initialization
 * - Two training targets (blackWP, whiteWP)
 * - Sigmoid activation function for all layers
 */
public class NeuralNetwork {

    // Nodes
    private int inputNodes;
    private int hiddenNodes;
    private int outputNodes;


    // Weights and biases
    private double[][] weightsInputToHidden;
    private double[] biasesHidden;
    private double[][] weightsHiddenToOutput;
    private double[] biasesOutput;



    // Activation function
    private ActivationFunction activationFunction;

    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes, ActivationFunction activationFunction) {

        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;
        this.activationFunction = activationFunction;

        // Initialize weights and biases using Xavier initialization
        this.weightsInputToHidden = new double[inputNodes][hiddenNodes];
        for (int i = 0; i < inputNodes; i++) {
            for (int j = 0; j < hiddenNodes; j++) {
                this.weightsInputToHidden[i][j] = Math.random() * Math.sqrt(2.0 / inputNodes);
            }
        }
        this.biasesHidden = new double[hiddenNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            this.biasesHidden[i] = Math.random() * Math.sqrt(2.0 / inputNodes);
        }
        this.weightsHiddenToOutput = new double[hiddenNodes][outputNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            for (int j = 0; j < outputNodes; j++) {
                this.weightsHiddenToOutput[i][j] = Math.random() * Math.sqrt(2.0 / hiddenNodes);
            }
        }
        this.biasesOutput = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            this.biasesOutput[i] = Math.random() * Math.sqrt(2.0 / hiddenNodes);
        }
    }

    // We predict the winProbability for black and white
    //      index 0 = black
    //      index 1 = white
    public double[] predict(double[] input) {

        // Compute the output of the hidden layer
        double[] hiddenOutput = new double[hiddenNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            double sum = 0;
            for (int j = 0; j < inputNodes; j++) {
                sum += input[j] * weightsInputToHidden[j][i];
            }
            sum += biasesHidden[i];
            hiddenOutput[i] = activationFunction.apply(sum);
        }

        // Compute the output of the output layer
        double[] output = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            double sum = 0;
            for (int j = 0; j < hiddenNodes; j++) {
                sum += hiddenOutput[j] * weightsHiddenToOutput[j][i];
            }
            sum += biasesOutput[i];
            output[i] = activationFunction.apply(sum);
        }

        return output;
    }

    // THIS WOULD BE ONE TRAINING ITERATION
    public void train(double[] input, double[] target, double learningRate) {

        // Compute the output of the hidden layer
        double[] hiddenOutput = new double[hiddenNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            double sum = 0;
            for (int j = 0; j < inputNodes; j++) {
                sum += input[j] * weightsInputToHidden[j][i];
            }
            sum += biasesHidden[i];
            hiddenOutput[i] = activationFunction.apply(sum);
        }

        // Compute the output of the output layer
        double[] output = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            double sum = 0;
            for (int j = 0; j < hiddenNodes; j++) {
                sum += hiddenOutput[j] * weightsHiddenToOutput[j][i];
            }
            sum += biasesOutput[i];
            output[i] = activationFunction.apply(sum);
        }

        // Compute the error of the output layer
        double[] outputError = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            outputError[i] = target[i] - output[i];

        }

        // Compute the error of the hidden layer
        double[] hiddenError = new double[hiddenNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            double sum = 0;
            for (int j = 0; j < outputNodes; j++) {
                sum += outputError[j] * weightsHiddenToOutput[i][j];
            }
            hiddenError[i] = sum;
        }

        // Update the weights and biases
        for (int i = 0; i < inputNodes; i++) {
            for (int j = 0; j < hiddenNodes; j++) {
                weightsInputToHidden[i][j] += learningRate * hiddenError[j] * input[i];
            }
        }
        for (int i = 0; i < hiddenNodes; i++) {
            biasesHidden[i] += learningRate * hiddenError[i];
        }
        for (int i = 0; i < hiddenNodes; i++) {
            for (int j = 0; j < outputNodes; j++) {
                weightsHiddenToOutput[i][j] += learningRate * outputError[j] * hiddenOutput[i];
            }
        }
        for (int i = 0; i < outputNodes; i++) {
            biasesOutput[i] += learningRate * outputError[i];
        }
    }


    /** GETTERS AND SETTERS */

    // We want to get the weights and biases after training
    public double[][] getWeightsInputToHidden() {
        return weightsInputToHidden;
    }

    public double[] getBiasesHidden() {
        return biasesHidden;
    }

    public double[][] getWeightsHiddenToOutput() {
        return weightsHiddenToOutput;
    }

    public double[] getBiasesOutput() {
        return biasesOutput;
    }

    // To quickly use a trained network we want to set these
    public void setWeightsInputToHidden(double[][] weightsInputToHidden) {
        this.weightsInputToHidden = weightsInputToHidden;
    }

    public void setBiasesHidden(double[] biasesHidden) {
        this.biasesHidden = biasesHidden;
    }

    public void setWeightsHiddenToOutput(double[][] weightsHiddenToOutput) {
        this.weightsHiddenToOutput = weightsHiddenToOutput;
    }

    public void setBiasesOutput(double[] biasesOutput) {
        this.biasesOutput = biasesOutput;
    }

}

