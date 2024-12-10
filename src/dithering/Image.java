package dithering;

import java.awt.image.BufferedImage;
import java.awt.Color;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Image {

	File imageFile;
	BufferedImage image;

	int imageWidth;
	int imageHeight;
	Pixel[] pixels;


	//Constructor
	public Image(String imagePath) throws IOException {

		this.imageFile = new File(imagePath);
		this.image = ImageIO.read(imageFile);

		this.imageWidth = image.getWidth();
		this.imageHeight = image.getHeight();
		this.pixels = getPixels();
	}


	public void displayImage() {
		JLabel imageLabel = new JLabel(new ImageIcon(image));

		JPanel jPanel = new JPanel();
		jPanel.add(imageLabel);

		JFrame frame = new JFrame("Dithering");
		frame.setSize(imageWidth + 10,imageHeight + 40); //40 is the size of the header
		frame.add(jPanel);
		frame.setVisible(true);
	}

	public void displayImage(String title) {
		JLabel imageLabel = new JLabel(new ImageIcon(image));

		JPanel jPanel = new JPanel();
		jPanel.add(imageLabel);

		JFrame frame = new JFrame(title);
		frame.setSize(imageWidth + 10,imageHeight + 40); //40 is the size of the header
		frame.add(jPanel);
		frame.setVisible(true);
	}


	/**
	 * This method iterates over every pixel in the image and finds their RGB value using the color class
	 * 
	 * @return a Color array full of pixel data from the image
	 */
	public Pixel[] getPixels() {
		Pixel[] pixels = new Pixel[imageHeight * imageWidth];

		int pixelIndex = 0;

		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++) {
				Color pixelRGB = new Color(image.getRGB(x,y));
				pixels[pixelIndex] = new Pixel(x, y, pixelRGB);
				pixelIndex++;
			}
		}
		return pixels;
	}


	/**
	 * This method psuedo compresses the image object. It changes the image to what it would display in its compressed state without actually compressing the image
	 * 
	 * @param colourCount is the number of possible values for each of the RGB values
	 */
	public void compressImage(int colourCount) {

		for (int x = 0; x < imageWidth; x++) {
			for (int y = 0; y < imageHeight; y++) {

				/*
				System.out.println(Arrays.toString(getRGB(x,y)));
				System.out.println(newR);
				System.out.println(newG);
				System.out.println(newB);
				 */

				//Find pixel position
				int pixelPosition = (imageWidth * y) + x;

				//Get the data of a compressed version of the pixel
				Pixel compressedPixel = pixels[pixelPosition].compressPixel(colourCount);

				//Update image to represent this pixel
				int pixelRGB = compressedPixel.colour.getRGB();
				image.setRGB(x, y, pixelRGB);
			}		
		}
	}


	/**
	 * This method psuedo dithers the image object using Floyd Steinberg dithering.
	 * 
	 * @param colourCount is the number of possible values for each of the RGB values
	 */
	public void ditherImage(int colourCount) {

		//Altered range to avoid errors, alter this to a better approach. I'm just tired and cba rn... Fix this whole method tbh 
		for (int y = 0; y < imageHeight - 1; y++) {
			for (int x = 1; x < imageWidth - 1; x++) {

				//Finds current pixel
				int pixelPos = (y * imageWidth) + x;
				Pixel currentPixel = pixels[pixelPos];

				//Gets the data of a compressed version of the pixel
				Pixel compressedPixel = currentPixel.compressPixel(colourCount);

				//Updates the image to represent compressedPixel
				image.setRGB(x,y, compressedPixel.colour.getRGB());

				int quantisationErrorR = currentPixel.r - compressedPixel.r;
				int quantisationErrorG = currentPixel.g - compressedPixel.g;
				int quantisationErrorB = currentPixel.b - compressedPixel.b;

				//These are vector additions for neighbouring pixels if the pixels were represented as a grid starting at 0,0 top left
				int[] xAdditions = {1, -1, 0, 1};
				int[] yAdditions = {0,  1, 1, 1};
				int[] errorPushFactors = {7, 3, 5, 1};

				int neigbouringPixelCount = xAdditions.length;

				int pixelPosition;
				Pixel pixel;

				for (int i = 0; i < neigbouringPixelCount; i++) {
					//Getting pixel data
					pixelPosition = ((y + yAdditions[i]) * imageWidth) + x + xAdditions[i];
					pixel = pixels[pixelPosition];

					//Changing RGB values in relation to the quantisationError
					pixel.r += quantisationErrorR * errorPushFactors[i]/16;
					pixel.g += quantisationErrorG * errorPushFactors[i]/16;
					pixel.b += quantisationErrorB * errorPushFactors[i]/16;

					//Ensures RGB values are not out of valid range (0-255)
					pixel.clipRGBOutliers();

					//Updating colour value of the pixel
					pixels[pixelPosition].colour = new Color(pixel.r, pixel.g, pixel.b);

				}
			}
		}
	}
}

