package neuralNetworkLibrary;

import java.util.List;
import java.util.ArrayList;

public abstract class Layer {
	public int neuronCount;
	protected List<Neuron> neurons;
	protected double[] inputVector;
	protected double[] outputVector;

	public Layer(int neuronCount) {
		this.neuronCount = neuronCount;
		neurons = new ArrayList<Neuron>(neuronCount);
		for (int i = 0; i < neuronCount; i++) {
			neurons.add(new Neuron(this));
		}
		outputVector = new double[neuronCount];
	}

	protected void initialize(double[] inputVector) {
		this.setInputVector(inputVector);
		for (Neuron neuron : neurons) {
			neuron.initialize(inputVector);
		}
	}

	protected void run() {
		for (int i = 0; i < neurons.size(); i++) {
			neurons.get(i).run();
			outputVector[i] = neurons.get(i).getOutput();
		}
	}

	abstract double activate(double input);

	abstract double derivative(double input, double output);

	/**
	 * @return neuron at index i
	 */
	public Neuron getNeuron(int i) {
		return neurons.get(i);
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

	/**
	 * @param outputVector
	 *            the outputVector to set
	 */
	public void setOutputVector(double[] outputVector) {
		for (int i = 0; i < this.outputVector.length; i++) {
			this.outputVector[i] = outputVector[i];
		}
	}
}
