package neuralNetworkLibrary;

class Neuron {
	private double input;
	private double output;
	private double error;
	private double bias;

	double[] weightVector;
	double[] inputVector;

	public Neuron() {

	}

	protected void run() {

	}

	protected void initialize(double[] inputVector) {
		weightVector = new double[inputVector.length];
		this.inputVector = inputVector;
		// Initialize weights with random values from 0.0 to 1.0
		for (int i = 0; i < weightVector.length; i++)
			weightVector[i] = Math.random();
	}

	/**
	 * @return the input
	 */
	public double getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(double input) {
		this.input = input;
	}

	/**
	 * @return the output
	 */
	public double getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(double output) {
		this.output = output;
	}

	/**
	 * @return the error
	 */
	public double getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(double error) {
		this.error = error;
	}
}
