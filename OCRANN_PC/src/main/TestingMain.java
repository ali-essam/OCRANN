package main;

import java.io.*;

import mnistDataset.MNISTDatasetLoader;
import mnistDataset.OnDataitemLoadListener;
import neuralNetworkLibrary.*;


public class TestingMain {
	
	private static void copyDataitemToArray(TrainingDataItem dataItem,
			double[] inputVector, double[] expectedOutputVector) {
		for (int i = 0; i < dataItem.getInputVector().size(); i++) {
			inputVector[i] = ((Number)dataItem.getInputVector().get(i)).doubleValue();
		}

		for (int i = 0; i < dataItem.getExpectedOutputVector().size(); i++) {
			expectedOutputVector[i] = (Double) dataItem
					.getExpectedOutputVector().get(i);
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

	public static void runningTest()
	{
		TrainingDataSet trainingDataSet = MNISTDatasetLoader.loadMNISTDataset("MNIST/t10k-images.idx3-ubyte", "MNIST/t10k-labels.idx1-ubyte");
		NeuralNet neuralNet = loadNetwork("nets/net8");
		
		int score = 0;
		double[] inputVector = new double[neuralNet.getInputLayer()
				.getNeuronCount()];
		double[] expectedOutputVector = new double[neuralNet
				.getOutputLayer().getNeuronCount()];
		int ind = 1;
		for (TrainingDataItem trainingDataItem : trainingDataSet) 
		{
			copyDataitemToArray(trainingDataItem, inputVector,
					expectedOutputVector);
			double[] out = neuralNet.run(inputVector);
			
			double outMax = -1, expectedOutMax = -1;
			int maxInd = -1, expectedMaxInd = -1;
			for (int i = 0; i < out.length; i++) {
				if (outMax <= out[i]){
					outMax = out[i];
					maxInd = i;
				}
				if (expectedOutMax <= expectedOutputVector[i]){
					expectedOutMax = expectedOutputVector[i];
					expectedMaxInd = i;
				}
			}
			
			System.out.println("Finished " + ind + " out of " + trainingDataSet.size());
			
			if (maxInd == expectedMaxInd)
				score++;
			
			ind++;
		}
		
		System.out.println("The network correct score is: " + score + " out of " + trainingDataSet.size() + " with accurecy " + (double)score / trainingDataSet.size()*100 + "%");
	}

	public static void main(String[] args) {
		runningTest();
	}

}
