package mnistDataset;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import main.OtsuThresholding;
import neuralNetworkLibrary.TrainingDataItem;
import neuralNetworkLibrary.TrainingDataSet;

public class MNISTDatasetLoader {
	private static OnDataitemLoadListener onDataitemLoadListener;

	public static TrainingDataSet loadMNISTDataset(String trainingSetFileName, String labelFileName){
		File trainingFile = new File(trainingSetFileName);
		File labelFile = new File(labelFileName);
		
		FileInputStream trainFis;
		DataInputStream trainDis = null;
		
		FileInputStream labelFis;
		DataInputStream labelDis = null;
		try {
			trainFis = new FileInputStream(trainingFile);
			trainDis = new DataInputStream(trainFis);
			
			labelFis = new FileInputStream(labelFile);
			labelDis = new DataInputStream(labelFis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		TrainingDataSet trainingDataSet = new TrainingDataSet();
		
		try {
			@SuppressWarnings("unused")
			int trainingMagicNumber = trainDis.readInt();
			@SuppressWarnings("unused")
			int labelMagicNumber = labelDis.readInt();
			
			int count = trainDis.readInt();
			labelDis.readInt();
			
			int rows = trainDis.readInt();
			int cols = trainDis.readInt();

			//tmpPixels to hold pixels temporarily before thresholding
			double[] tmpPixels = new double[rows*cols];
			
			for (int i = 0; i < count; i++) { // debugging note: change count to 300 for testing
				//read pixels into tmpPixels
				for (int j = 0; j < rows*cols; j++) {
					tmpPixels[j] = trainDis.readUnsignedByte();
				}
				
				// Threshold pixels to 0,1
				ArrayList<Byte> pixels = OtsuThresholding.convertToBinary(tmpPixels);
				
				//Debugging: view Thresholded pixels
				/*for (int j = 0; j < rows; j++) {
					for (int k = 0; k < cols; k++) {
						System.out.print((pixels.get(j*rows+k)==0)?".":"X");
					}
					System.out.println();
				}*/
				
				// Set expected output array according to label 
				ArrayList<Double>out = new ArrayList<Double>(10);
				int label = labelDis.readUnsignedByte();
				for (int j = 0; j < 10; j++) {
					out.add(0.0);
				}
				out.set(label,1.0);
				//System.out.println(label);
				
				
				trainingDataSet.addTrainingDataItem(new TrainingDataItem(pixels,out));
				
				if(onDataitemLoadListener!=null)onDataitemLoadListener.onDataitemLoadListener(i);
			}
			
			trainDis.close();
			labelDis.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return trainingDataSet;
	}
	
	public static void setOnDataitemLoadListener(
			OnDataitemLoadListener _onDataitemLoadListener) {
		onDataitemLoadListener = _onDataitemLoadListener;
	}
}
