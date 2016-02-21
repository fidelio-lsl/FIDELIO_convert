package video.loader;

import java.awt.image.BufferedImage;
import java.io.IOException;

import video.AviVideo;
import video.Datatypes;

public class BWVLoader extends Loader {

	public static AviVideo loadBWV(String name) throws IOException {

		byte[] data = loadByteArray(name);
		System.out.println("Loading BWV");

//		int totalFrames = Datatypes.readInt(data, data.length - 3 * 4);
//		int width = Datatypes.readInt(data, data.length - 2 * 4);
//		int height = Datatypes.readInt(data, data.length - 1 * 4);
		
		int totalFrames = data.length/(720*480);
		int width = 720;
		int height = 480;

		BufferedImage[] images = new BufferedImage[totalFrames];

		for (int i = 0; i < images.length; i++) {
			images[i] = bytearrToBWImg(
					splitByteArray(data, i * width * height, (i + 1) * width
							* height), width, height);
//			images[i] = bytearrToClrImg(data, width, height);
		}

		return new AviVideo(images);
	}
}
