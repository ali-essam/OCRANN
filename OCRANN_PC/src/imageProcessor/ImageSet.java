package imageProcessor;

import java.util.ArrayList;

public class ImageSet {
	ArrayList<double[][]> images;

	public ImageSet() {
		images = new ArrayList<>();
	}
	
	public void addImage(double[][] image){
		images.add(image);
	}
	
	public double[][] getImage(int index){
		return images.get(index);
	}
	
	public int size(){
		return images.size();
	}
}
