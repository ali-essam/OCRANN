package imageProcessor;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import main.*;

public class ImageSegmenter 
{
	BufferedImage image;
	int connectedComponentNum;
	double[] pixels1D;
	double[] grayScalePixels1D;
	double[] binaryPixels1D;
	double[][] pixels2D;
	int width;
	int height;
	int startX, startY;
	int[] dx;
	int[] dy;
	
	public ImageSegmenter(BufferedImage _img) {

		this.image = _img;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.connectedComponentNum = 0;
		this.pixels1D = null;
		this.pixels2D = null;
		dx = new int[] { 1, 1, 1, -1, -1, -1, 0, 0 };
		dy = new int[] { 0, 1, -1, 0, 1, -1, 1, -1 };
		initialize();
	}
	
	private void initialize() {
		startX = 0;
		startY = 0;
		pixels1D = new double [width*height];
		int counter=0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pixels1D[counter]=(double)image.getRGB(j, i);
				counter++;
			}
		}
	}

	private void convertTo2D() {
		pixels2D = convertTo2D(binaryPixels1D, width, height);
	}
	
	public double[][] convertTo2D(double[] pixels1D, int width, int height) {
		double[][] newPixels2D = new double [height][width];
		int counter=0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				newPixels2D[i][j]=pixels1D[counter++];
			}
		}
		
		return newPixels2D;
	}
	
	private void convertToGrayScale() {
		grayScalePixels1D = convertToGrayScale(pixels1D);
	}
	
	public double[] convertToGrayScale(double[] pixels1D) {
		int red, blue, green, median;
		int rgb;
		double[] grayScalePixles1D = new double[pixels1D.length];
		int counter=0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = (int)pixels1D[counter];
				red = new Color(rgb).getRed();
				green = new Color(rgb).getGreen();
				blue = new Color(rgb).getBlue();
				median = (red + green + blue) / 3;
				grayScalePixles1D[counter++] = new Color(median, median, median).getRGB();
			}
		}
		
		return grayScalePixles1D;
	}
	
	private void convertToBinary() {
		binaryPixels1D = convertToBinary(grayScalePixels1D);
	}

	public double[] convertToBinary (double[] grayScalePixels1D) {
		double[] redColor = new double[grayScalePixels1D.length];
		
		for (int i = 0; i < redColor.length; i++) {
			redColor[i] = new Color((int)grayScalePixels1D[i]).getRed();
		}
		
		ArrayList <Byte> newPixels =OtsuThresholding.convertToBinary(redColor);
	    double[] binaryPixels1D = new double[newPixels.size()];
		for (int i = 0; i < height*width; i++) {
				binaryPixels1D[i] = (double)newPixels.get(i);
		}
		
		return binaryPixels1D;
	}
	private boolean valid(double[][] pixels2D ,int x, int y, boolean[][] visited) {
		if (x >= 0 && x < width && y >= 0 && y < height
				&& visited[y][x] == false && pixels2D[y][x] == 1)
			return true;
		return false;
	}

	private ImageBoundries getConnectedComponent(double[][] pixels2D, int x, int y, boolean[][] visited) {
		ImageBoundries imageBoundries = new ImageBoundries(0, 0, 0, 0);
		Stack <Point> S = new Stack<Point>();
		S.push(new Point(x, y));
		while (!S.empty()) {
			Point cur = S.pop();
			Point next = new Point();

			imageBoundries.Max_X = Math.max(imageBoundries.Max_X, cur.x);
			imageBoundries.Min_X = Math.min(imageBoundries.Min_X, cur.x);

			imageBoundries.Max_Y = Math.max(imageBoundries.Max_Y, cur.y);
			imageBoundries.Min_Y = Math.min(imageBoundries.Min_Y, cur.y);

			for (int i = 0; i < 8; i++) {
				next.x = cur.x + dx[i];
				next.y = cur.y + dy[i];
				if (valid(pixels2D, next.x, next.y, visited) == true) {
					visited[next.y][next.x] = true;
					S.push(next);
				}
			}
		}
		
		return imageBoundries;
	}

	private ImageBoundries getSubImageBoundries(double[][] pixels2D, boolean[][] visited) {
		for (; startX < width; startX++) {
			for (; startY < height; startY++) {
				if ((visited[startY][startX] == false) && ((int)pixels2D[startY][startX] == 1)) {
					connectedComponentNum++;
					return getConnectedComponent(pixels2D, startX, startY, visited);
				}
			}
		}
		
		return null;
	}
	
	public ImageSet segmentImage() {
		boolean[][] visited = new boolean[height][width];
		for (int i = 0; i < visited.length; i++) {
			Arrays.fill(visited[i], false);
		}
		convertToGrayScale();
		convertToBinary();
		convertTo2D();
		//print(pixels2D, width, height);
		ImageBoundries imageBoundrais = getSubImageBoundries(pixels2D, visited);
		ImageSet imageSet = new ImageSet();
		
		while (imageBoundrais != null)
		{
			int width = Math.abs(imageBoundrais.Max_X - imageBoundrais.Min_X);
			int height = Math.abs(imageBoundrais.Max_Y - imageBoundrais.Min_Y);
			double[][] rectImage = cropImage(imageBoundrais, width, height);
			print(rectImage, width, height);
			//double[][] centralizedImage = centralizeImage(rectImage, width, height);
			double[][] squareImage = scaleImage(rectImage, width, height, 28, 28);
			print(squareImage, width, height);
			imageSet.addImage(squareImage);
			imageBoundrais = getSubImageBoundries(pixels2D, visited);
		}
		
		return imageSet;
	}
	
	private double[][] centralizeImage(double[][] image, int width, int height) {
		int length = Math.max(width, height);
		double[][] squarePixels2D = new double[length][length];
		Arrays.fill(squarePixels2D, 0.0);
		
		int iOffset = (length - height) / 2;
		int jOffset = (length - width) / 2;
		
		for (int i = iOffset; i < iOffset + height; i++) {
			for (int j = jOffset; j < jOffset + width; j++) {
				squarePixels2D[i][j] = image[i - iOffset][j - jOffset];
			}
		}
		
		return squarePixels2D;
	}
	private double[][] cropImage(ImageBoundries imageBoundries, int width, int height) {
		double[][] newPixels2D = new double[height][width];
		
		for (int i = imageBoundries.Min_Y; i <= imageBoundries.Max_Y; i++){
			for (int j = imageBoundries.Min_X; j <= imageBoundries.Max_X; j++) {
				newPixels2D[i - imageBoundries.Min_Y][j - imageBoundries.Min_X] = pixels2D[i][j];
			}
		}
		
		return newPixels2D;
	}
	
	public double[][] scaleImage(double[][] image, int width, int height, int newWidth, int newHeight) {
		BufferedImage tempImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				tempImage.setRGB(j, i, (int)image[i][j]);
			}
		}
		
		int length = Math.max(width, height);
		BufferedImage squareImage = null;
		AffineTransform transform = AffineTransform.getScaleInstance((double)newWidth / width, (double)newHeight / height);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		squareImage = op.filter(tempImage, null);
		
		double[][] squarePixels2D = new double[length][length];
		
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				squarePixels2D[i][j] = squareImage.getRGB(j, i);
			}
		}
		
		return squarePixels2D;
	}
	
	private void print(double[][] image, int width, int height) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print((int)image[i][j]);
			}
			
			System.out.println();
		}
	}
	
	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * set the image and initializes local variables to new values
	 */
	
	public void setImage(BufferedImage image) {
		this.image = image;
		initialize();
	}
	
	/**
	 * @return the pixels1D
	 */
	public double[] getPixels1D() {
		return pixels1D;
	}

	/**
	 * @return the grayScalePixels1D
	 */
	public double[] getGrayScalePixels1D() {
		return grayScalePixels1D;
	}

	/**
	 * @return the binaryPixels1D
	 */
	public double[] getBinaryPixels1D() {
		return binaryPixels1D;
	}

	/**
	 * @return the pixels2D
	 */
	public double[][] getPixels2D() {
		return pixels2D;
	}

	public int getConnectedComponentNum() {
		return connectedComponentNum;
	}
}

