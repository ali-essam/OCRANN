package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class DateSetLoader {

	public static double[][] pixels;
	public static double[][] result;

	DateSetLoader()
	{
		pixels=new double[5000][400] ;
		result=new double [5000][10];
	}
	
	public static void intialize() throws IOException {
		int counter = 0;
		BufferedImage img = null;
		Path dir = FileSystems.getDefault().getPath("src/OCR_Images");
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
		for (Path file : stream) 
		{
			int ind=file.getFileName().toString().lastIndexOf('.');
			String extension = file.getFileName().toString().substring(ind+1);
			if(extension!="png") continue;
			img = ImageIO.read(file.toFile());
			int[] rgb = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null,
					0, img.getWidth());
			for (int i = 0; i < rgb.length; i++) {
				pixels[counter][i] = new Color(rgb[i]).getRed() / 255.0;
			}
			String fileName=file.getFileName().toString();
			char digit=fileName.charAt(fileName.length()-1);
			for(char i='0';i<='9';i++)
			{
				if(digit==i)
				    result[counter][i]=1;
				else 
					result[counter][i]=0;
			}
			counter++;
		}
	}
	
	
}
