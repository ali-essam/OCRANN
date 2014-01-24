package neuralNetworkLibrary;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class NeuralNet implements Serializable {
	private static final long serialVersionUID = 9187767511560185035L;

	private transient ArrayList<Layer> layers;
	private transient Layer inputLayer;
	private transient Layer outputLayer;
	private double mse;
	private LearningParameters learningParameters;

	public LearningParameters getLearningParameters() {
		return learningParameters;
	}

	public void setLearningParameters(LearningParameters learningParameters) {
		this.learningParameters = learningParameters;
	}

	public NeuralNet(LearningParameters learningParameters) {
		layers = new ArrayList<Layer>();
		this.learningParameters = learningParameters;
	}

	public double[] run(double[] inputVector) throws IllegalArgumentException {
		if (inputVector.length != inputLayer.getNeuronCount()) {
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

	public void connectAllLayers() {
		for (int i = 0; i < layers.size() - 1; i++) {
			layers.get(i).connectTo(layers.get(i + 1));
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

	public void updateMSE(double[] expectedOutputVector, double[] outputVector) {
		mse = 0;
		for (int i = 0; i < outputVector.length; i++) {
			double error = outputVector[i] - expectedOutputVector[i];
			mse += error * error;
		}
		mse /= 2.0;
	}

	public void train(double[] inputVector, double[] expectedOutputVector) {
		run(inputVector);
		outputLayer.updateNeuronsDelta(expectedOutputVector);

		for (int i = layers.size() - 2; i > 0; i--) {
			layers.get(i).updateNeuronsDelta();
		}

		for (int i = 1; i < layers.size(); i++) {
			layers.get(i).updateNeuronWeights(this.learningParameters);
		}

		updateMSE(expectedOutputVector, this.outputLayer.getOutputVector());
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeInt(layers.size());
		for (int i = layers.size() - 1; i >= 0; i--) {
			oos.writeObject(layers.get(i));
		}
	}

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		int layersSize = ois.readInt();
		this.layers = new ArrayList<Layer>(layersSize);
		for (int i = layersSize - 1; i >= 0; i--) {
			this.layers.add(0, (Layer)ois.readObject());
		}
		
		for (Layer layer : layers) {
			layer.reconstructConnections();
		}
		
		inputLayer = layers.get(0);
		outputLayer = layers.get(layers.size()-1);
	}

	/**
	 * @return the mse
	 */
	public double getMSE() {
		return mse;
	}

	public Layer getInputLayer() {
		return inputLayer;
	}


	public Layer getOutputLayer() {
		return outputLayer;
	}

}
