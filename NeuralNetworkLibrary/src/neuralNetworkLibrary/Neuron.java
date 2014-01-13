package neuralNetworkLibrary;

import java.io.Serializable;
import java.util.ArrayList;

public class Neuron implements Serializable{
	private static final long serialVersionUID = 1151300141203897497L;
	
	private double input;
	private double output;
	private double errorFactor;
	private double delta;
	private double bias;
	private Layer parentLayer;

	private ArrayList<Connection> inputConnections;
	private ArrayList<Connection> outputConnections;

	public Neuron(Layer parentLayer) {
		this.parentLayer = parentLayer;
		inputConnections = new ArrayList<Connection>();
		outputConnections = new ArrayList<Connection>();
		bias = Math.random();
		if(Math.random()<0.5)bias*=-1;
	}

	private void calculateSum() {
		input = 0;
		for (Connection connection : inputConnections) {
			input += connection.weight * connection.getFrom().getOutput();
		}
		input += bias;
	}

	protected void run() {
		calculateSum();
		output = parentLayer.activate(input);
	}

	public void connectTo(Neuron toNeuron) {
		Connection connection = new Connection(this, toNeuron);
		this.addOutputConnection(connection);
		toNeuron.addInputConnection(connection);
	}

	public void connectTo(Neuron toNeuron, double weight) {
		Connection connection = new Connection(this, toNeuron, weight);
		this.addOutputConnection(connection);
		toNeuron.addInputConnection(connection);
	}

	public void addInputConnection(Connection connection) {
		inputConnections.add(connection);
	}

	public void addOutputConnection(Connection connection) {
		outputConnections.add(connection);
	}

	public void updateDelta(double errorFactor) {
		this.delta = parentLayer.derivative(input, output) * errorFactor;
	}

	public void updateDelta() {
		double errorFactor = 0;
		for (Connection connection : outputConnections) {
			errorFactor += connection.getWeight()
					* connection.getTo().getDelta();
		}
		updateDelta(errorFactor);
	}

	public void updateWeights(double learningRate){
		bias += learningRate*delta;
		for (Connection connection : inputConnections) {
			double newWeight = connection.getWeight() + learningRate*connection.getFrom().getOutput()*delta; 
			connection.setWeight(newWeight);
		}
	}

	/**
	 * @return the bias
	 */
	public double getBias() {
		return bias;
	}

	/**
	 * @param bias
	 *            the bias to set
	 */
	public void setBias(double bias) {
		this.bias = bias;
	}

	/**
	 * @return the input
	 */
	public double getInput() {
		return input;
	}

	/**
	 * @param input
	 *            the input to set
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
	 * @param output
	 *            the output to set
	 */
	public void setOutput(double output) {
		this.output = output;
	}

	public void setWeightVector(double[] weightVector) {
		for (int i = 0; i < weightVector.length; i++) {
			inputConnections.get(i).setWeight(weightVector[i]);
		}
	}

	/**
	 * @return the errorFactor
	 */
	public double getErrorFactor() {
		return errorFactor;
	}

	/**
	 * @param errorFactor
	 *            the errorFactor to set
	 */
	public void setErrorFactor(double errorFactor) {
		this.errorFactor = errorFactor;
	}

	/**
	 * @return the delta
	 */
	public double getDelta() {
		return delta;
	}

	/**
	 * @param delta
	 *            the delta to set
	 */
	public void setDelta(double delta) {
		this.delta = delta;
	}
}
