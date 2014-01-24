package neuralNetworkLibrary;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public abstract class Layer implements Serializable {
	private static final long serialVersionUID = 8320324059182006752L;
	
	private int neuronCount;
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
	
	protected void reconstructConnections(){
		for (Neuron neuron : neurons) {
			neuron.reconstructConnections();
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

	public void updateNeuronsDelta(double[] expectedOutputVector){
		int i=0;
		for (Neuron neuron : neurons) {
			neuron.updateDelta(expectedOutputVector[i++]-neuron.getOutput());
		}
	}
	
	public void updateNeuronsDelta(){
		for (Neuron neuron : neurons) {
			neuron.updateDelta();
		}
	}
	
	public void updateNeuronWeights(LearningParameters learningParameters){
		for (Neuron neuron : neurons) {
			neuron.updateWeights(learningParameters);
		}
	}

	/**
	 * @return the neurons
	 */
	public List<Neuron> getNeurons() {
		return neurons;
	}

	public int getNeuronCount() {
		return neuronCount;
	}
}
