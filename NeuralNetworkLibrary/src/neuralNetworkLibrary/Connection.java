package neuralNetworkLibrary;

public class Connection {

	Neuron from;
	Neuron to;
	double weight;

	public Connection(Neuron from, Neuron to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public Connection(Neuron from, Neuron to) {
		this.from = from;
		this.to = to;
		weight = Math.random();
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
