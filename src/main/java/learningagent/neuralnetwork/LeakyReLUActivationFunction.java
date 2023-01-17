package learningagent.neuralnetwork;

public class LeakyReLUActivationFunction implements ActivationFunction {
    private double alpha;

    public LeakyReLUActivationFunction(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public double apply(double input) {
        if (input > 0) {
            return input;
        } else {
            return alpha * input;
        }
    }
    @Override       //TODO: implement...
    public double derivative(double input) {
        return 0;
    }
}