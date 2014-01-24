package mnistDataset;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
			//System.out.println(trainingMagicNumber +","+labelMagicNumber);
			
			for (int i = 0; i < count; i++) {
				ArrayList pixels = new ArrayList<>(rows*cols);
				ArrayList<Double>out = new ArrayList<Double>(10);
				for (int j = 0; j < rows*cols; j++) {
					pixels.add(trainDis.readByte());
					
				}
				int label = labelDis.readByte();
				for (int j = 0; j < 10; j++) {
					out.add(-1.0);
				}
				out.set(label,1.0);
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
