package neuralNetworkLibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TrainingDataSet implements Iterable<TrainingDataItem>{
	
	ArrayList<TrainingDataItem> trainingDataItems;
	
	public TrainingDataSet() {
		this.trainingDataItems = new ArrayList<TrainingDataItem>();
	}
	
	public TrainingDataSet(List<TrainingDataItem> trainingDataItems) {
		this.trainingDataItems = new ArrayList<TrainingDataItem>(trainingDataItems);
	}
	
	public void addTrainingDataItem(TrainingDataItem trainingDataItem){
		trainingDataItems.add(trainingDataItem);
	}
	
	public TrainingDataItem getTrainingDataItem(int index){
		return trainingDataItems.get(index);
	}
	
	public void shuffle(){
		Collections.shuffle(trainingDataItems, new Random());
	}
	
	public TrainingDataSet subDataSet(int fromIndex, int toIndex){
		return new TrainingDataSet(trainingDataItems.subList(fromIndex, toIndex));
	}

	public int size(){
		return trainingDataItems.size();
	}
	
	@Override
	public Iterator<TrainingDataItem> iterator() {
		return trainingDataItems.iterator();
	}
}
