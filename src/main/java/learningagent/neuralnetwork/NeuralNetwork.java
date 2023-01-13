package learningagent.neuralnetwork;

public class NeuralNetwork {

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

        // Initialize weights and biases randomly
        this.weightsInputToHidden = new double[inputNodes][hiddenNodes];
        this.biasesHidden = new double[hiddenNodes];
        this.weightsHiddenToOutput = new double[hiddenNodes][outputNodes];
        this.biasesOutput = new double[outputNodes];

        for (int i = 0; i < inputNodes; i++) {
            for (int j = 0; j < hiddenNodes; j++) {
                this.weightsInputToHidden[i][j] = Math.random();
            }
        }
        for (int i = 0; i < hiddenNodes; i++) {
            this.biasesHidden[i] = Math.random();
        }
        for (int i = 0; i < hiddenNodes; i++) {
            for (int j = 0; j < outputNodes; j++) {
                this.weightsHiddenToOutput[i][j] = Math.random();
            }
        }
        for (int i = 0; i < outputNodes; i++) {
            this.biasesOutput[i] = Math.random();
        }
    }

    // We only predict the winProbability
    public double predict(double[] input) {

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
        double output=0.000001;

        for (int i = 0; i < outputNodes; i++) {

            double sum = 0;
            for (int j = 0; j < hiddenNodes; j++) {
                sum += hiddenOutput[j] * weightsHiddenToOutput[j][i];
            }
            sum += biasesOutput[i];
            output = activationFunction.apply(sum);
        }

        return output;
    }

    // THIS WOULD BE ONE TRAINING ITERATION
    public double train(double[] input, double target, double learningRate) {

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

        // Compute the output of the output layer - adapted for single value
        double output = 0.0001;
        for (int i = 0; i < outputNodes; i++) {
            double sum = 0;
            for (int j = 0; j < hiddenNodes; j++) {
                sum += hiddenOutput[j] * weightsHiddenToOutput[j][i];
            }
            sum += biasesOutput[i];
            output = activationFunction.apply(sum);
        }

        // Compute the error of the output layer
        double outputError = 0;
        outputError = target - output;

        // Compute the error of the hidden layer
        double[] hiddenError = new double[hiddenNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            double sum = 0;
            for (int j = 0; j < outputNodes; j++) {
                sum += outputError * weightsHiddenToOutput[i][j];
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
                weightsHiddenToOutput[i][j] += learningRate * outputError * hiddenOutput[i];
            }
        }
        for (int i = 0; i < outputNodes; i++) {
            biasesOutput[i] += learningRate * outputError;
        }

        return outputError;
    }




    /** IF WE WANT THE FEATURES AFTER TRAINING */
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

