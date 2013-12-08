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

	public List<Double> run(List<Double> input) {
		
		return null;
	}

	public void addLayer(Layer layer) {
		if (layer != null)
			layers.add(layer);
	}

	public void initialize() throws Exception {
		if(layers.size()<2)
			throw new Exception("There must be at least 2 layers (input & output)");
		for (int i = 1; i < layers.size(); i++)
			layers.get(i).initialize(layers.get(i - 1).getOutputVector());
		
		inputLayer = layers.get(0);
		outputLayer = layers.get(layers.size()-1);
	}
}
