package neuralNetworkLibrary;

public class ThresholdLayer extends Layer {
	private static final long serialVersionUID = 2031061780345687614L;

	public ThresholdLayer(int neuronCount) {
		super(neuronCount);
	}

	@Override
	double activate(double input) {
		if(input>0.5)return 1;
		return 0;
	}

	@Override
	double derivative(double input, double output) {
		return 0;
	}

}
