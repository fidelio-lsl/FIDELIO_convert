package video.loader;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import video.Datatypes;

public class BitmapLoader {

	private static int width, height;

	final private static int dataOffset = 54;

	public static BufferedImage loadBmp(String name)
			throws FileNotFoundException {

		byte[] data = loadByteArray(new FileInputStream(name));

		loadHeader(data);

		BufferedImage image = loadImage(data, dataOffset);

		return image;
	}

	private static void loadHeader(byte[] data) {
		width = Datatypes.readInt(data, 18);
		height = Datatypes.readInt(data, 22);
	}

	private static BufferedImage loadImage(byte[] data, int offset) {

		int x = 0, y = height-1;

		short[] rgb = new short[3];
		BufferedImage image = new BufferedImage(width, height, 1);

		for (int i = offset; i < 3*width*height + offset; i += 3) {
			rgb[2] = Datatypes.readUSByte(data, i);
			rgb[1] = Datatypes.readUSByte(data, i + 1);
			rgb[0] = Datatypes.readUSByte(data, i + 2);

			image.setRGB(x, y, rgb[0] << 16 | rgb[1] << 8 | rgb[2]);

			x++;
			if (x == width) {
				x = 0;
				y--;
			}
		}



		return image;
	}

	public static byte[] loadByteArray(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];

		int readBytes = 0;
		try {
			while ((readBytes = in.read(buffer)) > 0) {
				out.write(buffer, 0, readBytes);
			}

			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out.toByteArray();

	}

}
