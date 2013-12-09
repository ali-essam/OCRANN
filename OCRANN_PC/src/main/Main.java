package main;

import neuralNetworkLibrary.*;

public class Main {

	private static void testRunNN(){
		// Sample test case
		// http://www4.rgu.ac.uk/files/chapter2%20-%20intro%20to%20ANNs.pdf
		// Page 14 example 2.2
		
		//Construct Layers 
		NeuralNet net = new NeuralNet();
		net.addLayer(new InputLayer(2));
		net.addLayer(new SigmoidLayer(2));
		net.addLayer(new SigmoidLayer(1));
		
		try {
			// Initialize Network
			net.initialize();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		// Set weights and biases manually
		net.getLayer(1).getNeuron(0).setWeightVector(new double[]{0.1,0.9});
		net.getLayer(1).getNeuron(0).setBias(0);
		net.getLayer(1).getNeuron(1).setWeightVector(new double[]{0.5,0.8});
		net.getLayer(1).getNeuron(1).setBias(0);
		
		net.getLayer(2).getNeuron(0).setWeightVector(new double[]{0.7,0.5});
		net.getLayer(2).getNeuron(0).setBias(0);
		
		double out[] = null;
		try {
			// Run network against input vector
			out=net.run(new double[]{0.2,0.4});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		// Print output layer to console
		for (int i = 0; i < out.length; i++) {
			System.out.println(out[i]);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testRunNN();
	}

}
