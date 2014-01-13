package neuralNetworkLibrary;

public class LinearLayer extends Layer {
	private static final long serialVersionUID = -3344592211862286881L;

	public LinearLayer(int neuronCount) {
		super(neuronCount);
	}

	@Override
	double activate(double input) {
		return input;
	}

	@Override
	double derivative(double input, double output) {
		return 1;
	}

}
