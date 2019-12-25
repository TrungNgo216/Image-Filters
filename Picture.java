import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from
 * SimplePicture and add functionality to the Picture class.
 *
 * Copyright Georgia Institute of Technology 2004-2005
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture {

  /**
   * Constructor that takes no arguments
   */
  public Picture() {
  }

  /**
   * Constructor that takes a file name and creates the picture
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    super(fileName);
  }

  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height) {
    super(width,height);
  }

  /**
   * Constructor that takes a picture and creates a
   * copy of that picture
   */
  public Picture(Picture copyPicture) {
    super(copyPicture);
  }

  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }

  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() +
      " height " + getHeight()
      + " width " + getWidth();
    return output;
  }

  /**
  *  Ensure the value is between 0 and 255.  If it is, return it.
  *  If it is less than 0, return 0.  If it is greater than 255,
  *  return 255.
  */
  public int putInColorRange(int value) {
     value = Math.max(value, 0);  // get the max of 0 and the value
     value = Math.min(value, 255);  // get the min of value and 255
     return value;
  }

   /*
    * Returns nothing because of void and name of method is scaleColor
    * This method scales the rgb values of all the pixels in a picture
    * The parameters scale rgb values by a double
    */
    public void scaleColor(double rScale, double gScale, double bScale) {
		int value = 0;
		//Setting pixelArray to get all the pixels
		Pixel[] pixelArray = this.getPixels();
		//Pixel picture is all of the pixels in pixelArray
        for (Pixel picture:pixelArray){
			//int value gets the red value, scales it, and casts it
			//as an int
			value = picture.getRed();
			value = (int) (value * rScale);
			//int scaled is set to be value
			int scaled = value;
			//Pixel picture now has a new red value for all the pixels
			picture.setRed(value);
			//scaled keeps it within 0-255
			scaled = this.putInColorRange(scaled);
			
			//Repeat 2 more times for green and blue
			value = picture.getGreen();
			value = (int) (value * gScale);
			scaled = value;
			picture.setGreen(value);
			scaled = this.putInColorRange(scaled);
			
			value = picture.getBlue();
			value = (int) (value * bScale);
			scaled = value;
			picture.setBlue(value);
			scaled = this.putInColorRange(scaled);
		}
			
			
    }


    /*
     * Returns nothing because of void and name of method is negative
     * This method inverts rgb values of any selected pixels
     * Parameters are int values that control the beginning and end
     * of inverting rgb values
     */
    public void inverted(int start, int end) {
		//Grabs all the pixels
		Pixel [] pixelArray = this.getPixels();	
		//Loop for inclusive start pixel and exclusive end pixel
		for (int i = start; i < end; i++){
			//Pixel is integer value i of pixelArray
			Pixel picture = pixelArray[i];
			//RGB values are set at 255 - original value
			int value = picture.getRed();
			value = 255 - value;
			picture.setRed(value);
		
			value = picture.getGreen();
			value = 255 - value;
			picture.setGreen(value);
			
			value = picture.getBlue();
			value = 255 - value;
			picture.setBlue(value);
		}
    }

    /*
     * Returns nothing because of void and name of method is grayscale
     * This method converts a region of pixels into a gray color
     * The parameters control the range of the pixels selected
     */
    public void grayscale(int start, int end) {
		//Grabs all the pixels
        Pixel [] pixelArray = this.getPixels();	
        
		//Loop for inclusive start pixel and exclusive end pixel
		for (int i = start; i < end; i++){
			//Pixel is integer value i of pixelArray
			Pixel picture = pixelArray[i];
			
			//Setting rgb value to be the average of their sums 
			//to create a shade of gray
			int value = (picture.getRed() + picture.getGreen() + 
			picture.getBlue()) / 3;
			
			picture.setRed(value);
			picture.setGreen(value);
			picture.setBlue(value);
		}

    }


    /*
     * Returns nothing because of void and name of method is myFilter
     * This method converts a region of pixels with 2 rotating values
     * and one random value for the colors 
     * The parameters control the range of the pixels selected
     */
    public void myFilter(int start, int end) {
        //Grabs all the pixels
        Pixel [] pixelArray = this.getPixels();	
		//Loop for inclusive start pixel and exclusive end pixel
		for (int i = start; i < end; i++)
		{
			//Pixel is integer value i of pixelArray
			Pixel picture = pixelArray[i];
			
			//Making a random number generator between 0 and 255 for 
			//the blue value
			Random generator = new Random();
			int randomValue = generator.nextInt(256);
			
			//Setting red value to have green's original value, setting
			//green value to have blue's original value, and blue to a
			//random value generated
			picture.setRed(picture.getGreen());
			picture.setGreen(picture.getBlue());
			picture.setBlue(randomValue);

		}
    }
    
    public static void main (String[] args) {
		//Loading a picture and copying it
		Picture original = new Picture("Original.png");
        Picture copy = new Picture(original);
        //Grabbing all of the pixels in the picture
        Pixel [] picture = copy.getPixels();
        
        //Setting 3 values to top, middle, and bottom sections of 
        //TriEffect
        int topThird = (int)(0.33*picture.length);
        int middleThird = (int)(0.66*picture.length);
        int botThird = (int)(picture.length);
        
        //Calling the three methods made from Picture.java
        copy.inverted(0, topThird);
        copy.grayscale(topThird, middleThird);
        copy.myFilter(middleThird, botThird);
        
        //Opens the pictures
        original.explore();
        copy.explore();
    }

}
