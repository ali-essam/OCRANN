package neuralNetworkLibrary;

public class InputLayer extends Layer {
	private static final long serialVersionUID = 3101699753266445264L;

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
