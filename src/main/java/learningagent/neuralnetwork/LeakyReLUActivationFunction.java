package learningagent.neuralnetwork;

import learningagent.neuralnetwork.ActivationFunction;

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
}