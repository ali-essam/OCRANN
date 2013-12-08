package neuralNetworkLibrary;

import java.util.List;
import java.util.ArrayList;

public class Layer {
	private List<Neuron> neurons;
	private double[] inputVector;
	private double[] outputVector;



	public Layer(int neuronCount) {
		neurons = new ArrayList<Neuron>(neuronCount);
		outputVector = new double[neuronCount];
	}

	protected void initialize(double[] inputVector) {
		for (Neuron neuron : neurons) {
			neuron.initialize(inputVector);
		}
	}

	protected void run() {

	}
	
	/**
	 * @return the inputVector
	 */
	public double[] getInputVector() {
		return inputVector;
	}

	/**
	 * @param inputVector
	 *            the inputVector to set
	 */
	public void setInputVector(double[] inputVector) {
		this.inputVector = inputVector;
	}

	/**
	 * @return the outputVector
	 */
	public double[] getOutputVector() {
		return outputVector;
	}
}
