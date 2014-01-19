package neuralNetworkLibrary;

import java.util.List;

public class TrainingDataItem {
	private double[] inputVector;
	private double[] expectedOutputVector;
	
	public TrainingDataItem(List<Double> input, List<Double> output) {
		this.inputVector = new double[input.size()];
		for (int i = 0; i < input.size(); i++) {
			this.inputVector[i] = input.get(i);
		}
		
		this.expectedOutputVector = new double[output.size()];
		for (int i = 0; i < output.size(); i++) {
			this.expectedOutputVector[i] = output.get(i);
		}
	}
	
	public TrainingDataItem(double[] input, double[] output) {
		this.inputVector = new double[input.length];
		for (int i = 0; i < input.length; i++) {
			this.inputVector[i] = input[i];
		}
		
		this.expectedOutputVector = new double[output.length];
		for (int i = 0; i < output.length; i++) {
			this.expectedOutputVector[i] = output[i];
		}
	}

	/**
	 * @return the inputVector
	 */
	public double[] getInputVector() {
		return inputVector;
	}

	/**
	 * @return the expectedOutputVector
	 */
	public double[] getExpectedOutputVector() {
		return expectedOutputVector;
	}
}
