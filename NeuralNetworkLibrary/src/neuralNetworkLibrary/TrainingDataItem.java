package neuralNetworkLibrary;

import java.util.ArrayList;
import java.util.List;

public class TrainingDataItem {
	private List<Number> inputVector;
	private List<Number> expectedOutputVector;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TrainingDataItem(List inputVector, List expectedOutputVector) {
		this.inputVector = inputVector;
		this.expectedOutputVector = expectedOutputVector;
	}
	
	public TrainingDataItem(double[] input, double[] output) {
		this.inputVector = new ArrayList<Number>();
		for (int i = 0; i < output.length; i++) {
			this.inputVector.add(output[i]);
		}
		
		this.expectedOutputVector = new ArrayList<Number>();
		for (int i = 0; i < output.length; i++) {
			this.expectedOutputVector.add(output[i]);
		}
	}

	/**
	 * @return the inputVector
	 */
	public List<Number> getInputVector() {
		return inputVector;
	}

	/**
	 * @return the expectedOutputVector
	 */
	public List<Number> getExpectedOutputVector() {
		return expectedOutputVector;
	}
}
