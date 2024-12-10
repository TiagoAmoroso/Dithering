package dithering;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

//https://en.wikipedia.org/wiki/Dither
//https://en.wikipedia.org/wiki/Floyd%E2%80%93Steinberg_dithering

//Dithering is used in file compression. Dithering allows for a higher perceived quality of digital media without changing file size
//Dithering uses the error offset from the data compression and pushes this offset into other regions of the data. This mitigates
//the noticeability of the compression when compared to the un-dithered result. 
//There are many forms of dithering which can be used across many forms of data but here I will be implementing Floyd Steinberg
//dithering on digital images.

/*THINGS TO ADD
 * Menu where user can drag and drop image file into the application
 * Image scaling to ensure uniformly sized images
 * Create a pixel class to contain all pixel related information and methods for cleaner code
 * Allow image variants to be viewed side by side
 */

public class Dithering {
	
	public static void main(String[] args) throws InterruptedException {
		try {
			//Image image = new Image("C:\\Java\\Dithering\\Images\\Sunset(540x360).jpg");
			//Image image = new Image("C:\\Java\\Dithering\\Images\\Dogs(1000x750).jpg");
			//Image image = new Image("C:\\Java\\Dithering\\Images\\Kitten(1500x1000).jpg");
			Image image = new Image("C:\\Java\\Dithering\\Images\\Dithering_example_undithered.png");
			Image compressedImage = new Image("C:\\Java\\Dithering\\Images\\Dithering_example_undithered.png");
			Image ditheredImage = new Image("C:\\Java\\Dithering\\Images\\Dithering_example_undithered.png");
			
			image.displayImage("Plain");
			
			TimeUnit.MILLISECONDS.sleep(1000);
			
			compressedImage.compressImage(7);
			
			compressedImage.displayImage("Compressed");
			
			TimeUnit.MILLISECONDS.sleep(1000);
			
			ditheredImage.ditherImage(7);
			
			ditheredImage.displayImage("Dithered");
		
			
		} 
		catch (IOException e) {
			System.out.print("Error: ");
			e.printStackTrace();
		}
	} 
}
