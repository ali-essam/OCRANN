package main;

import java.io.*;

import neuralNetworkLibrary.*;

public class Main {

	public static void saveNetwork(NeuralNet net, String filename) {
		try {
			// Serialize data object to a file
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filename));
			out.writeObject(net);
			out.close();
		} catch (IOException e) {
		}
	}

	public static NeuralNet loadNetwork(String filename) {
		NeuralNet net = null;
		try {
			FileInputStream door = new FileInputStream(filename);
			ObjectInputStream reader = new ObjectInputStream(door);
			net = (NeuralNet) reader.readObject();
			reader.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return net;
	}

	private static void testTraining() {
		NeuralNet net = new NeuralNet();
		net.addLayer(new InputLayer(2));
		net.addLayer(new TanhLayer(2));
		net.addLayer(new TanhLayer(1));

		net.connectAllLayers();

		double initialLearningRate = 0.0001;
		double minLearningRate = 0.00005;

		double learningRate = initialLearningRate;

		double lmse = -1;
		double mse = 0;
		double factor = 0.77;
		int epoch = 0;
		do {
			double newLearningRate = learningRate * factor;
			if (newLearningRate >= minLearningRate)
				learningRate = newLearningRate;
			else
				learningRate = minLearningRate;
			lmse = mse;

			net.train(new double[] { 0, 0 }, new double[] { 0 }, learningRate);
			mse += net.getMSE();
			net.train(new double[] { 0, 1 }, new double[] { 1 }, learningRate);
			mse += net.getMSE();
			net.train(new double[] { 1, 0 }, new double[] { 1 }, learningRate);
			mse += net.getMSE();
			net.train(new double[] { 1, 1 }, new double[] { 0 }, learningRate);
			mse += net.getMSE();

			mse /= 4.0;

			System.out.println("MSE epochNum: " + (epoch + 1) + " = " + mse
					+ ", LR = " + learningRate);

			epoch++;
		} while (Math.abs(lmse - mse) > 1e-7 || epoch == 1);

//		System.out.println("0 + 0 = " + net.run(new double[] { 0, 0 })[0]);
//		System.out.println("0 + 1 = " + net.run(new double[] { 0, 1 })[0]);
//		System.out.println("1 + 0 = " + net.run(new double[] { 1, 0 })[0]);
//		System.out.println("1 + 1 = " + net.run(new double[] { 1, 1 })[0]);
		
		saveNetwork(net, "orNet.tmp");
	}

	private static void testLoadNN(){
		NeuralNet net = loadNetwork("orNet.tmp");
		
		System.out.println("0 + 0 = " + net.run(new double[] { 0, 0 })[0]);
		System.out.println("0 + 1 = " + net.run(new double[] { 0, 1 })[0]);
		System.out.println("1 + 0 = " + net.run(new double[] { 1, 0 })[0]);
		System.out.println("1 + 1 = " + net.run(new double[] { 1, 1 })[0]);
	}
	
	private static void testRunNN() {
		// Sample test case
		// http://www4.rgu.ac.uk/files/chapter2%20-%20intro%20to%20ANNs.pdf
		// Page 14 example 2.2

		// Construct Layers
		NeuralNet net = new NeuralNet();
		net.addLayer(new InputLayer(2));
		net.addLayer(new SigmoidLayer(2));
		net.addLayer(new SigmoidLayer(1));

		net.connectAllLayers();

		// Set weights and biases manually
		net.getLayer(1).getNeuron(0).setWeightVector(new double[] { 0.1, 0.9 });
		net.getLayer(1).getNeuron(0).setBias(0);
		net.getLayer(1).getNeuron(1).setWeightVector(new double[] { 0.5, 0.8 });
		net.getLayer(1).getNeuron(1).setBias(0);

		net.getLayer(2).getNeuron(0).setWeightVector(new double[] { 0.7, 0.5 });
		net.getLayer(2).getNeuron(0).setBias(0);

		double out[] = null;
		try {
			// Run network against input vector
			out = net.run(new double[] { 0.2, 0.4 });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		// Print output layer to console
		for (int i = 0; i < out.length; i++) {
			System.out.println(out[i]);
		}
	}
	
	public static void testDataSet(){
		try {
			DataSetLoader.intialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		NeuralNet net = new NeuralNet();
		net.addLayer(new InputLayer(400));
		net.addLayer(new TanhLayer(300));
		net.addLayer(new TanhLayer(10));

		net.connectAllLayers();

		double initialLearningRate = 0.0001;
		double minLearningRate = 0.00005;

		double learningRate = initialLearningRate;

		double lmse = -1;
		double mse = 0;
		double factor = 0.77;
		int epoch = 0;
		do {
			double newLearningRate = learningRate * factor;
			if (newLearningRate >= minLearningRate)
				learningRate = newLearningRate;
			else
				learningRate = minLearningRate;
			lmse = mse;
			
			for (int i = 0; i < 5000; i++) {
				net.train(DataSetLoader.pixels[i], DataSetLoader.result[i], newLearningRate);
				mse+=net.getMSE();
				if (i % 1000 == 0)
					System.out.println("Epoch Number: " + epoch + ", " + (i+1) + " of 5000");
			}
			
			mse /= 5000.0;

			System.out.println("MSE epochNum: " + (epoch + 1) + " = " + mse
					+ ", LR = " + learningRate);

			epoch++;
			saveNetwork(net, "nets/net"+epoch+".tmp");
		} while (Math.abs(lmse - mse) > 1e-3 || epoch == 1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// testRunNN();
		//testTraining();
		//testLoadNN();
		
		testDataSet();
	}

}
