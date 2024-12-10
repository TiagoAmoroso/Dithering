package dithering;

import java.awt.Color;

public class Pixel {
	int x;
	int y;
	Color colour;
	int r;
	int g;
	int b;
	

	//Constructor
	public Pixel(int xVal, int yVal, Color colour) {
		this.x = xVal;
		this.y = yVal;
		this.colour = colour;
		this.r = colour.getRed();
		this.g = colour.getGreen();
		this.b = colour.getBlue();
	}
	

	/**
	 * This function calculates the new RGB values of the pixel if it were to be constrained to a set number of allowed RGB values
	 * This constraint is signified by the colourCount parameter
	 * 
	 * @param colourCount is the number of possible values for each of RGB values
	 * @return newColour is the Color object of the compressed pixel
	 * 
	 */
	public Pixel compressPixel(int colourCount) {
		int newR = Math.round(colourCount * this.r / 255) * (255 / colourCount);
		int newG = Math.round(colourCount * this.g / 255) * (255 / colourCount);
		int newB = Math.round(colourCount * this.b / 255) * (255 / colourCount);

		this.colour = new Color(newR,newG,newB);
		Pixel newPixel = new Pixel(this.x, this.y, this.colour);

		return newPixel;
	}
	
	
	/**
	 * Gives the RGB values for any given pixel within the image
	 * 
	 * @return int[] The RGB values contianed within an array.
	 */
	public int[] getRGBArray() {

		int[] RGB = {this.r,this.g,this.b};

		return RGB;
	}
	
	/**
	 * Ensures that the RGB values of the pixel are within the valid range
	 */
	public void clipRGBOutliers() {

		if (this.r > 255) {
			this.r = 255;
		}
		if (this.r < 0) {
			this.r = 0;
		}
		
		if (this.g > 255) {
			this.g = 255;
		}
		if (this.g < 0) {
			this.g = 0;
		}
		
		if (this.b > 255) {
			this.b = 255;
		}
		if (this.b < 0) {
			this.b = 0;
		}
	}

}
