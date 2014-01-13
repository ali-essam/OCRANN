package neuralNetworkLibrary;

public class TanhLayer extends Layer {
	private static final long serialVersionUID = -6489134890946461496L;

	public TanhLayer(int neuronCount) {
		super(neuronCount);
	}

	@Override
	double activate(double input) {
		return Math.tanh(input);
	}

	@Override
	double derivative(double input, double output) {
		return 1.0 - output * output;
	}

}
