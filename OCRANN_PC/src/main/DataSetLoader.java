package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class DataSetLoader {

	public static double[][] pixels;
	public static double[][] result;

	
	
	public static void intialize() throws IOException {
		pixels=new double[5000][400] ;
		result=new double [5000][10];

		int counter = 0;
		BufferedImage img = null;
		Path dir = FileSystems.getDefault().getPath("src/OCR_Images");
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
		for (Path file : stream) 
		{
			String fileName=file.getFileName().toString();
			int ind=fileName.lastIndexOf('.');
			String extension = fileName.substring(ind+1);
			if(!extension.equals("png")) continue;
			img = ImageIO.read(file.toFile());
			int[] rgb = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null,
					0, img.getWidth());
			for (int i = 0; i < rgb.length; i++) {
				pixels[counter][i] = new Color(rgb[i]).getRed() / 255.0;
			}
			char digit=fileName.charAt(ind-1);
			for(char i='0';i<='9';i++)
			{
				if(digit==i)
				    result[counter][i-'0']=1;
				else 
					result[counter][i-'0']=0;
			}
			counter++;
		}
	}
	
	
}
