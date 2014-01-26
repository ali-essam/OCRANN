package neuralNetworkLibrary;

import java.io.Serializable;

public class LearningParameters implements Serializable {
	private static final long serialVersionUID = -4076236859963180905L;
	
	private double learningRate;
	private double momentum;
	private double weightDecay;
	
	public LearningParameters() {
		this.learningRate = 0.001;
		this.momentum = 0;
		this.weightDecay = 0;
	}

	public LearningParameters(double learningRate) {
		this.learningRate = learningRate;
		this.momentum = 0;
		this.weightDecay = 0;
	}

	public LearningParameters(double learningRate, double momentum) {
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.weightDecay = 0;
	}

	public LearningParameters(double learningRate, double momentum,
			double weightDecay) {
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.weightDecay = weightDecay;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getMomentum() {
		return momentum;
	}

	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}

	public double getWeightDecay() {
		return weightDecay;
	}

	public void setWeightDecay(double weightDecay) {
		this.weightDecay = weightDecay;
	}
}
