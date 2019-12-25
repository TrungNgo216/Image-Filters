import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.*;
import java.util.*;

public class Filters {
	static void grayscale(BufferedImage image, BufferedImage newImage, 
		int width, int height)
	{
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				//Get RGB Color on each pixel
				Color c = new Color(image.getRGB(i,j));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
						 
				int gr = (r + g + b)/3;
						   
				//Create graycolor
				Color gray = new Color (gr, gr, gr);
				newImage.setRGB(i, j, gray.getRGB());   
			}
		}
	}
	
	static void inverted(BufferedImage image, BufferedImage newImage, 
		int width, int height)
	{
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				//Get RGB Color on each pixel
				Color c = new Color(image.getRGB(i,j));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
						 
				//Creates inverted color
				Color inversion = new Color 
					(255 - r, 255 - g, 255 - b);
				newImage.setRGB(i, j, inversion.getRGB());   
			}
		}
	}
	
	static void rotated(BufferedImage image, BufferedImage newImage, 
		int width, int height)
	{
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				//Get RGB Color on each pixel
				Color c = new Color(image.getRGB(i,j));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
						 
				//Creates rotated RGB value color
				Color rotated = new Color (g, b, r);
				newImage.setRGB(i, j, rotated.getRGB());   
			}
		}
	}
	
	static void blur(BufferedImage image, BufferedImage newImage, 
		int width, int height)
	{
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				int redAmount = 0;
				int greenAmount = 0;
				int blueAmount = 0;

				//Takes the average of 9 pixel RGB values
				int pixel1 = image.getRGB(x - 1, y - 1);
				redAmount += (pixel1 >> 16) & 0xff;
				greenAmount += (pixel1 >> 8) & 0xff;
				blueAmount += (pixel1 >> 0) & 0xff;

				int pixel2 = image.getRGB(x, y - 1);
				redAmount += (pixel2 >> 16) & 0xff;
				greenAmount += (pixel2 >> 8) & 0xff;
				blueAmount += (pixel2 >> 0) & 0xff;

				int pixel3 = image.getRGB(x + 1, y - 1);
				redAmount += (pixel3 >> 16) & 0xff;
				greenAmount += (pixel3 >> 8) & 0xff;
				blueAmount += (pixel3 >> 0) & 0xff;

				int pixel4 = image.getRGB(x - 1, y);
				redAmount += (pixel4 >> 16) & 0xff;
				greenAmount += (pixel4 >> 8) & 0xff;
				blueAmount += (pixel4 >> 0) & 0xff;

				int pixel5 = image.getRGB(x, y);
				redAmount += (pixel5 >> 16) & 0xff;
				greenAmount += (pixel5 >> 8) & 0xff;
				blueAmount += (pixel5 >> 0) & 0xff;

				int pixel6 = image.getRGB(x + 1, y);
				redAmount += (pixel6 >> 16) & 0xff;
				greenAmount += (pixel6 >> 8) & 0xff;
				blueAmount += (pixel6 >> 0) & 0xff;

				int pixel7 = image.getRGB(x - 1, y + 1);
				redAmount += (pixel7 >> 16) & 0xff;
				greenAmount += (pixel7 >> 8) & 0xff;
				blueAmount += (pixel7 >> 0) & 0xff;

				int pixel8 = image.getRGB(x, y + 1);
				redAmount += (pixel8 >> 16) & 0xff;
				greenAmount += (pixel8 >> 8) & 0xff;
				blueAmount += (pixel8 >> 0) & 0xff;

				int pixel9 = image.getRGB(x + 1, y + 1);
				redAmount += (pixel9 >> 16) & 0xff;
				greenAmount += (pixel9 >> 8) & 0xff;
				blueAmount += (pixel9 >> 0) & 0xff;

				redAmount /= 9;
				greenAmount /= 9;
				blueAmount /= 9;

				//Creates blur
				int newPixel = (redAmount << 16) | (greenAmount << 8) | blueAmount;

				newImage.setRGB(x, y, newPixel);    
			}
		}
	}
	
	static void randomValue(BufferedImage image, BufferedImage newImage, 
		int width, int height, String input)
	{
		Random generator = new Random();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int random = generator.nextInt(256);
				Color c = new Color(image.getRGB(i,j));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				
				if (input.contains("random red"))
				{
					//Creates random color
					Color randomR = new Color 
						(random, g, b);
					newImage.setRGB(i, j, randomR.getRGB());
				}
				else if (input.contains("random green"))
				{	
					Color randomG = new Color 
						(r, random, b);
					newImage.setRGB(i, j, randomG.getRGB());
				}
				else
				{
					Color randomB = new Color 
						(r, g, random);
					newImage.setRGB(i, j, randomB.getRGB());
				} 
			}
		}
	}
	public static void main(String[] args)throws IOException {
        File original = new File("Original.png");
		BufferedImage image = null;
        		
		try {
			image = ImageIO.read(original);
			int width = image.getWidth();
			int height = image.getHeight();
			
			BufferedImage newImage = new BufferedImage
				(width, height, BufferedImage.TYPE_INT_RGB);
			
			System.out.println("What filter would you like?");
			Scanner scan = new Scanner(System.in);			
			String input = scan.nextLine();
			
			//Checks Command line input
			if (input.contains("grayscale"))
			{
				grayscale(image, newImage, width, height);
			}
			else if (input.contains("inverted"))
			{
				inverted(image, newImage, width, height);
			}
			else if (input.contains("rotated"))
			{
				rotated(image, newImage, width, height);
			}
			else if (input.contains("blur"))
			{
				blur(image, newImage, width, height);
			}
			else if (input.contains("random red") || 
				input.contains("random green") ||
				input.contains("random blue"))
			{
				randomValue(image, newImage, width, height, input);
			}
			else
			{
				System.out.println("Possible commands that are " 
				+ "case sensitive:\ngrayscale\ninverted\nrotated\n"
				+ "random red\nrandom green\nrandom blue\nblur");
			}
			scan.close();	
			ImageIO.write(newImage, "png", new File("Filtered.png"));
		} catch (IOException e) {
		 //TODO Auto-generated catch block
		 e.printStackTrace();
		}
 }
}
