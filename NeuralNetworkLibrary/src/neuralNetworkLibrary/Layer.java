package neuralNetworkLibrary;

import java.util.List;
import java.util.ArrayList;

public abstract class Layer {
	public int neuronCount;
	protected List<Neuron> neurons;

	public Layer(int neuronCount) {
		this.neuronCount = neuronCount;
		neurons = new ArrayList<Neuron>(neuronCount);
		for (int i = 0; i < neuronCount; i++) {
			neurons.add(new Neuron(this));
		}
	}


	protected void run() {
		for (int i = 0; i < neurons.size(); i++) {
			neurons.get(i).run();
		}
	}

	public void connectTo(Layer layer){
		for (Neuron myNeuron : neurons) {
			for (Neuron otherNeuron : layer.getNeurons()) {
				myNeuron.connectTo(otherNeuron);
			}
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
	 * @param outputVector
	 *            the outputVector to set
	 */
	public void setOutputVector(double[] outputVector) {
		for (int i = 0; i < this.neuronCount; i++) {
			neurons.get(i).setOutput(outputVector[i]);
		}
	}
	
	public double[] getOutputVector() {
		double[] outputVector = new double[neurons.size()];
		int i=0;
		for (Neuron neuron : neurons) {
			outputVector[i++] = neuron.getOutput();
		}
		return outputVector;
	}


	/**
	 * @return the neurons
	 */
	public List<Neuron> getNeurons() {
		return neurons;
	}
}
