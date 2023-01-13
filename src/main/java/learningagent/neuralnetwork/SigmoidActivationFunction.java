package learningagent.neuralnetwork;

import learningagent.neuralnetwork.ActivationFunction;

public class SigmoidActivationFunction implements ActivationFunction {
	@Override
	public double apply(double input) {
		return 1.0 / (1.0 + Math.exp(-input));
	}
}