package learningagent.neuralnetwork;

public interface ActivationFunction {
	double apply(double input);
	double derivative(double input);
}
