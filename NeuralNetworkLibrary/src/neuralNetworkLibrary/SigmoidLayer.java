package neuralNetworkLibrary;

public class SigmoidLayer extends Layer {

	public SigmoidLayer(int neuronCount) {
		super(neuronCount);
		// TODO Auto-generated constructor stub
	}

	@Override
	double activate(double input) {
		return 1.0 / (1.0 + Math.exp(-input));
	}

	@Override
	double derivative(double input, double output) {
		return output * (1.0 - output);
	}

}
