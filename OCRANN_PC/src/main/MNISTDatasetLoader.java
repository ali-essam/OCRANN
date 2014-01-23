package main;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import neuralNetworkLibrary.TrainingDataItem;
import neuralNetworkLibrary.TrainingDataSet;

public class MNISTDatasetLoader {
	static TrainingDataSet getMNISTDataset(String trainingSetFileName, String labelFileName){
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
			int trainingMagicNumber = trainDis.readInt();
			int labelMagicNumber = labelDis.readInt();
			
			int count = trainDis.readInt();
			labelDis.readInt();
			
			int rows = trainDis.readInt();
			int cols = trainDis.readInt();
			//System.out.println(trainingMagicNumber +","+labelMagicNumber);
			
			for (int i = 0; i < 10; i++) {
				double[] pixels = new double[rows*cols];
				double[] out = new double[10];
				for (int j = 0; j < rows*cols; j++) {
					pixels[i] = trainDis.readByte();
				}
				int label = labelDis.readByte();
				Arrays.fill(out, -1);
				out[label] = 1;
				trainingDataSet.addTrainingDataItem(new TrainingDataItem(pixels,out));
				System.out.println(label);
			}
			
			trainDis.close();
			labelDis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return trainingDataSet;
	}
}
