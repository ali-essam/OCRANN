package neuralNetworkLibrary;

import java.util.List;
import java.util.ArrayList;

public class NeuralNet {
	private List<Layer> layers;

	public NeuralNet() {
		layers = new ArrayList<Layer>();
	}

	public double[] run(List<Double> input) {
		// TODO Implement run
		return null;
	}

	public void addLayer(Layer layer) {
		if (layer != null)
			layers.add(layer);
	}

	public void initialize() {
		for (int i = 1; i < layers.size(); i++)
			layers.get(i).initialize(layers.get(i - 1).getOutputVector());
	}
}
