package learningagent.neuralnetwork;

import learningagent.database.OneHotEncoding;
import learningagent.neuralnetwork.activationfunction.ActivationFunction;
import learningagent.neuralnetwork.activationfunction.SigmoidActivationFunction;
import learningagent.neuralnetwork.csvhelp.CSVtoArray;

/**
 *
 * Neural Network
 * - Xavier weight initialization
 * - Two training targets (blackWP, whiteWP)
 * - Sigmoid activation function for all layers
 *
 * This neural network has an additional function 'update' for q-learning.
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

    // Helpers CSV
    CSVtoArray csvRead = new CSVtoArray();

    // Encoding
    OneHotEncoding encode = new OneHotEncoding();

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

    public NeuralNetwork(){

        this.inputNodes = 384;
        this.hiddenNodes = 256;
        this.outputNodes = 2;
        this.activationFunction = new SigmoidActivationFunction();

        this.biasesHidden = csvRead.readCSVto1DDoubleArray("biasHidden");
        this.biasesOutput = csvRead.readCSVto1DDoubleArray("biasOutput");
        this.weightsInputToHidden = csvRead.readCSVto2DDoubleArray("weightsInputHidden");
        this.weightsHiddenToOutput = csvRead.readCSVto2DDoubleArray("weightsHiddenOutput");

        System.out.println("Neural Activated");

    }

    public NeuralNetwork(int q){

        this.inputNodes = 384;
        this.hiddenNodes = 256;
        this.outputNodes = 2;
        this.activationFunction = new SigmoidActivationFunction();

        this.biasesHidden = csvRead.readCSVto1DDoubleArray("biasHiddenQ");
        this.biasesOutput = csvRead.readCSVto1DDoubleArray("biasOutputQ");
        this.weightsInputToHidden = csvRead.readCSVto2DDoubleArray("weightsInputHiddenQ");
        this.weightsHiddenToOutput = csvRead.readCSVto2DDoubleArray("weightsHiddenOutputQ");

        System.out.println("Q-network activated");

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
//            System.out.println(outputError[i]);
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

}

