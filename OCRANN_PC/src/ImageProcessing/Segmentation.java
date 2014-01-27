package ImageProcessing;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import main.*;;
public class Segmentation {

	BufferedImage img;
	int connectedComponentNum;
	double [][] Pixels2D;
	double [] Pixels1D;
	int width;
	int height;
	int Start_x, Start_y;
	Boolean[][] Visited;
	int[] dx;
	int []dy;
	public Segmentation(BufferedImage _img) {
		img = _img;
		width = img.getWidth();
		height = img.getHeight();
		Start_x = 0;
		Start_y = 0;
		Visited = new Boolean[width][height];
		Arrays.fill(Visited, 0);
		connectedComponentNum = 0;
		Pixels1D = null;
		Pixels2D=null;
		getPixels1D();
		dx = new int[] { 1, 1, 1, -1 - 1, -1, 0, 0 };
		dy = new int[] { 0, 1, -1, 0, 1, -1, 1, -1 };
	}

	private void getPixels1D() {
		Pixels1D = new double [width*height];
		int counter=0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Pixels1D[counter++]=img.getRGB(i, j);
			}
		}
	}
	
	private void getPixels2D() {
		Pixels2D = new double [width][height];
		int counter=0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Pixels2D[i][j]=Pixels1D[counter++];
			}
		}
	}
	
	
	private void ConvertToGrayScale() {
		int red, blue, green, median;
		int rgb;
		int counter=0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				rgb = (int)Pixels1D[counter++];
				red = new Color(rgb).getRed();
				green = new Color(rgb).getGreen();
				blue = new Color(rgb).getBlue();
				median = (red + green + blue) / 3;
				Pixels1D[counter++] = new Color(median, median, median).getRGB();
			}
		}
	}

	private void ConvertToBinary ()
	{
		ArrayList <Byte> newPixels =OtsuThresholding.convertToBinary(Pixels1D);
	    int counter=0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Pixels1D[counter++] = (double)newPixels.get(i);
			}
		}
	}
	private Boolean Valid(int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height
				&& Visited[x][y].equals(false) && Pixels2D[x][y] == 1)
			return true;
		return false;
	}

	private void getConnectedComponent(int x, int y, ImageBoundary Bound) {
		Stack <Point> S = new Stack<Point>();
		S.push(new Point(x, y));
		while (!S.empty()) {
			Point cur = S.pop();
			Point next = null;

			Bound.Max_X = Math.max(Bound.Max_X, cur.x);
			Bound.Min_X = Math.min(Bound.Min_X, cur.x);

			Bound.Max_Y = Math.max(Bound.Max_Y, cur.y);
			Bound.Min_Y = Math.min(Bound.Min_Y, cur.y);

			for (int i = 0; i < 8; i++) {
				next.x = cur.x + dx[i];
				next.y = cur.y + dy[i];
				if (Valid(next.x, next.y).equals(true)) {
					Visited[next.x][next.y] = true;
					S.push(next);
				}
			}
		}
	}

	private Boolean GetImageBoundres(ImageBoundary Bound) {
		for (; Start_x < width; Start_x++) {
			for (; Start_y < height; Start_y++) {
				if (Visited[Start_x][Start_y].equals(false)
						&& Pixels2D[Start_x][Start_y] == 1) {
					getConnectedComponent(Start_x, Start_y, Bound);
					connectedComponentNum++;
					return true;
				}
			}
		}
		return false;
	}

	public Boolean Segmenet(ImageBoundary Bound)
	{
		ConvertToGrayScale();
		ConvertToBinary();
		getPixels2D();
	    if (GetImageBoundres(Bound).equals(0)) return false;
	    // To Do
	    
		return true;
	}
	
	
	public int getConnectedComponentNum() {
		return connectedComponentNum;
	}
}

