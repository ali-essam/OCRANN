package neuralNetworkLibrary;

class Neuron {
	double input;
	double output;
	double error;
	double bias;

	double[] weightVector;
	double[] inputVector;

	public Neuron() {

	}

	protected void run(double[] inputVector) {

	}

	protected void initialize(double[] inputVector) {
		weightVector = new double[inputVector.length];
		this.inputVector = inputVector;
		// Initialize weights with random values from 0.0 to 1.0
		for (int i = 0; i < weightVector.length; i++)
			weightVector[i] = Math.random();
	}
}
