package main;

import java.io.*;

import mnistDataset.MNISTDatasetLoader;
import mnistDataset.OnDataitemLoadListener;
import neuralNetworkLibrary.*;

public class Main {

	static LearningParameters learningParameters; 
	
	public static void saveNetwork(NeuralNet net, String filename) {
		try {
			// Serialize data object to a file
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filename));
			out.writeObject(net);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return net;
	}

	private static void testTraining() {
		NeuralNet net = new NeuralNet(learningParameters);
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

			net.train(new double[] { 0, 0 }, new double[] { 0 });
			mse += net.getMSE();
			net.train(new double[] { 0, 1 }, new double[] { 1 });
			mse += net.getMSE();
			net.train(new double[] { 1, 0 }, new double[] { 1 });
			mse += net.getMSE();
			net.train(new double[] { 1, 1 }, new double[] { 0 });
			mse += net.getMSE();

			mse /= 4.0;

			System.out.println("MSE epochNum: " + (epoch + 1) + " = " + mse
					+ ", LR = " + learningRate);

			epoch++;
		} while (Math.abs(lmse - mse) > 1e-7 || epoch == 1);

		// System.out.println("0 + 0 = " + net.run(new double[] { 0, 0 })[0]);
		// System.out.println("0 + 1 = " + net.run(new double[] { 0, 1 })[0]);
		// System.out.println("1 + 0 = " + net.run(new double[] { 1, 0 })[0]);
		// System.out.println("1 + 1 = " + net.run(new double[] { 1, 1 })[0]);

		saveNetwork(net, "orNet.tmp");
	}

	private static void testLoadNN() {
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
		NeuralNet net = new NeuralNet(learningParameters);
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

	public static void testDataSet() {
		try {
			DataSetLoader.intialize();
		} catch (IOException e) {
			e.printStackTrace();
		}

		NeuralNet net = new NeuralNet(learningParameters);
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

			int N = 2;
			
			net.getLearningParameters().setLearningRate(newLearningRate);

			for (int i = 0; i < N; i++) {
				net.train(DataSetLoader.pixels[i], DataSetLoader.result[i]);
				mse += net.getMSE();
				if (i % 1000 == 0)
					System.out.println("Epoch Number: " + epoch + ", "
							+ (i + 1) + " of 5000");
			}

			mse /= N;

			System.out.println("MSE epochNum: " + (epoch + 1) + " = " + mse
					+ ", LR = " + learningRate);

			epoch++;
		} while (epoch <= 1000);// while (Math.abs(lmse - mse) > 1e-3 || epoch
								// == 1);
		saveNetwork(net, "nets/net" + epoch + ".tmp");

	}

	private static void testLoadNetDataSet() {
		if (DataSetLoader.pixels == null)
			try {
				DataSetLoader.intialize();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		NeuralNet net = loadNetwork("nets/net10001.tmp");
		double[] out = net.run(DataSetLoader.pixels[0]);
		for (int i = 0; i < out.length; i++) {
			System.out.println(i + " = " + out[i] + " Expected:"
					+ DataSetLoader.result[0][i]);
		}
	}

	private static void testTrainingHelper() {
		try {
			DataSetLoader.intialize();
		} catch (IOException e) {
			e.printStackTrace();
		}

		NeuralNet net = new NeuralNet(learningParameters);
		net.addLayer(new InputLayer(400));
		net.addLayer(new TanhLayer(300));
		net.addLayer(new TanhLayer(10));

		net.connectAllLayers();

		TrainingDataSet trainingDataSet = new TrainingDataSet();

		for (int i = 0; i < DataSetLoader.pixels.length - 4000; i++) {
			trainingDataSet.addTrainingDataItem(new TrainingDataItem(
					DataSetLoader.pixels[i], DataSetLoader.result[i]));
		}

		final TrainingHelper trainingHelper = new TrainingHelper(net,
				trainingDataSet, trainingDataSet.subDataSet(0, 200));

		trainingHelper.setOnEpochFinishListner(new OnEpochFinishListener() {
			@Override
			public void onEpochFinish() {
				System.out.println("epoch num: " + trainingHelper.getEpoch() + ", MSE= "+trainingHelper.getEpochMSE());
				if (trainingHelper.getEpoch() == 20)
					trainingHelper.stopTraining();
			}
		});

		trainingHelper.setOnItemTrainListener(new OnItemTrainListener() {

			@Override
			public void onItemTrain(int trainingDataItemIndex) {
				System.out.println(trainingDataItemIndex + " of "
						+ trainingHelper.getTrainingDataSet().size());
			}
		});

		trainingHelper.setLearningRate(0.001);
		trainingHelper.train();
	}
	
	
	private static void testMNISTTrainingHelper() {
		NeuralNet net = new NeuralNet(learningParameters);
		net.addLayer(new InputLayer(784));
		net.addLayer(new TanhLayer(330));
		net.addLayer(new TanhLayer(10));

		net.connectAllLayers();

		MNISTDatasetLoader.setOnDataitemLoadListener(new OnDataitemLoadListener() {
			
			@Override
			public void onDataitemLoadListener(int currentProgress) {
				if(currentProgress%1000==0)System.out.println(currentProgress);
				
			}
		});
		TrainingDataSet trainingDataSet = MNISTDatasetLoader.loadMNISTDataset("MNIST/train-images.idx3-ubyte", "MNIST/train-labels.idx1-ubyte");

		final TrainingHelper trainingHelper = new TrainingHelper(net,
				trainingDataSet, trainingDataSet.subDataSet(0, 200));

		trainingHelper.setOnEpochFinishListner(new OnEpochFinishListener() {
			@Override
			public void onEpochFinish() {
				System.out.println("epoch num: " + trainingHelper.getEpoch() + ", MSE= "+trainingHelper.getEpochMSE());
				if (trainingHelper.getEpoch() == 20)
					trainingHelper.stopTraining();
			}
		});

		trainingHelper.setOnItemTrainListener(new OnItemTrainListener() {

			@Override
			public void onItemTrain(int trainingDataItemIndex) {
				if(trainingDataItemIndex%1000==0)System.out.println(trainingDataItemIndex + " of "
						+ trainingHelper.getTrainingDataSet().size());
			}
		});

		//trainingHelper.setLearningRate(0.001);
		trainingHelper.train();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// testRunNN();
		// testTraining();
		// testLoadNN();

		// testDataSet();

		// testLoadNetDataSet();
		learningParameters = new LearningParameters(0.001, 0.1, 0.01);
		testMNISTTrainingHelper();
	}

}
