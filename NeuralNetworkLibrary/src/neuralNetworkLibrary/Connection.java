package neuralNetworkLibrary;

import java.io.Serializable;

public class Connection implements Serializable {
	private static final long serialVersionUID = -8019833366309438802L;
	
	private transient Neuron from;
	private Neuron to;
	private double weight;

	public Connection(Neuron from, Neuron to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public Connection(Neuron from, Neuron to) {
		this.from = from;
		this.to = to;
		weight = Math.random();
		if(Math.random()<0.5)weight*=-1;
	}

	/**
	 * @return the from
	 */
	public Neuron getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(Neuron from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public Neuron getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(Neuron to) {
		this.to = to;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
