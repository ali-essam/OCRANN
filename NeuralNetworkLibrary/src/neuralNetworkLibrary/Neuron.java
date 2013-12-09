package neuralNetworkLibrary;

public class Neuron {
	private double input;
	private double output;
	private double error;
	private double bias;
	private Layer parentLayer;

	double[] weightVector;
	double[] inputVector;

	public Neuron(Layer parentLayer) {
		this.parentLayer = parentLayer;
	}
	
	private void calculateSum(){
		input = 0;
		for(int i=0;i<inputVector.length;i++){
			input += inputVector[i]*weightVector[i];
		}
		input += bias;
	}
	
	/**
	 * @return the bias
	 */
	public double getBias() {
		return bias;
	}

	/**
	 * @param bias the bias to set
	 */
	public void setBias(double bias) {
		this.bias = bias;
	}

	protected void run() {
		calculateSum();
		output = parentLayer.activate(input);
	}

	protected void initialize(double[] inputVector) {
		weightVector = new double[inputVector.length];
		this.inputVector = inputVector;
		// Initialize weights with random values from 0.0 to 1.0
		for (int i = 0; i < weightVector.length; i++)
			weightVector[i] = Math.random();
		bias = Math.random();
	}

	/**
	 * @param set weight for W(ji) where j is the current neuron and i
	 * is the neuron in previous layer
	 */
	public void setWeight(int i,double weight) {
		this.weightVector[i] = weight;
	}
	
	/**
	 * @return the weightVector
	 */
	public double[] getWeightVector() {
		return weightVector;
	}

	/**
	 * @param weightVector the weightVector to set
	 */
	public void setWeightVector(double[] weightVector) {
		this.weightVector = weightVector;
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
