package neuralNetworkLibrary;

public class InputLayer extends Layer {

	public InputLayer(int neuronCount) {
		super(neuronCount);
		// TODO Auto-generated constructor stub
	}

	@Override
	double activate(double input) {
		return input;
	}

	@Override
	double derivative(double input, double output) {
		return 0;
	}

}
