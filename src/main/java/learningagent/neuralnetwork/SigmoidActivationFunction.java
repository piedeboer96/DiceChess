package learningagent.neuralnetwork;

public class SigmoidActivationFunction implements ActivationFunction {
	@Override
	public double apply(double input) {
		return 1.0 / (1.0 + Math.exp(-input));
	}
	@Override
	public double derivative(double input) {
		double sigmoid = apply(input);
		return sigmoid * (1 - sigmoid);
	}
}