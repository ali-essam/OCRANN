package neuralNetworkLibrary;

import java.util.List;
import java.util.ArrayList;

public class NeuralNet {
	private List<Layer> layers;
	private Layer inputLayer;
	private Layer outputLayer;
	public NeuralNet() {
		layers = new ArrayList<Layer>();
	}



	public double[] run(double[] inputVector) throws IllegalArgumentException {
		if(inputVector.length != inputLayer.neuronCount){
			throw new IllegalArgumentException("input vector length must equal input layer size");
		}
		
		inputLayer.setOutputVector(inputVector);
		
		// Skip the input layer in calculations
		for (int i = 1; i < layers.size(); i++) {
			layers.get(i).run();
		}
		
		return outputLayer.outputVector;
	}

	public void addLayer(Layer layer) {
		if (layer != null)
			layers.add(layer);
	}
	
	public Layer getLayer(int i) {
		return layers.get(i);
	}

	public void initialize() throws Exception {
		if(layers.size()<2)
			throw new Exception("There must be at least 2 layers (input & output)");
		
		// Connect each output layer to the input layer of next layer
		for (int i = 1; i < layers.size(); i++)
			layers.get(i).initialize(layers.get(i - 1).getOutputVector());
		
		inputLayer = layers.get(0);
		outputLayer = layers.get(layers.size()-1);
	}
}
