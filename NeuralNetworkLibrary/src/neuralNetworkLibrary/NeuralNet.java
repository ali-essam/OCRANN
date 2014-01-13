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
		if (inputVector.length != inputLayer.neuronCount) {
			throw new IllegalArgumentException(
					"input vector length must equal input layer size");
		}
		
		inputLayer.setOutputVector(inputVector);

		// Skip the input layer in calculations
		for (int i = 1; i < layers.size(); i++) {
			layers.get(i).run();
		}

		return outputLayer.getOutputVector();
	}

	public void connectAllLayers(){
		for (int i = 0; i < layers.size()-1; i++) {
			layers.get(i).connectTo(layers.get(i+1));
		}
	}
	
	public void addLayer(Layer layer) {
		outputLayer = layer;
		if (layers.size() == 0)
			inputLayer = layer;
		if (layer != null)
			layers.add(layer);
	}

	public Layer getLayer(int i) {
		return layers.get(i);
	}
}
