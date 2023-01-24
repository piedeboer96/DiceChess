package learningagent.neuralnetwork.activationfunction;

public interface ActivationFunction {
	double apply(double input);
	double derivative(double input);
}
