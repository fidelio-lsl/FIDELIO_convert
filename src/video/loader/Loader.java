package video.loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import video.Datatypes;

public abstract class Loader {

	protected static byte[] splitByteArray(byte[] array, int i1, int i2) {
		byte[] tmp = new byte[i2 - i1];

		System.arraycopy(array, i1, tmp, 0, i2 - i1);

		return tmp;
	}

	public static byte[] imgToBytearr(BufferedImage image) {
		byte[] data = new byte[image.getWidth() * image.getHeight() * 3];

		int r = 0;
		int g = 0;
		int b = 0;

		int x = 0, y = image.getHeight() - 1;

		for (int i = 0; i < image.getWidth() * image.getHeight() * 3; i += 3) {

			r = (image.getRGB(x, y) & 0xff0000) >> 16;
			g = (image.getRGB(x, y) & 0xff00) >> 8;
			b = (image.getRGB(x, y) & 0xff);

			Datatypes.writeUSByte(data, i, (short) b);
			Datatypes.writeUSByte(data, i + 1, (short) g);
			Datatypes.writeUSByte(data, i + 2, (short) r);

			x++;
			if (x == image.getWidth()) {
				x = 0;
				y--;
			}
		}

		return data;
	}

	public static BufferedImage bytearrToClrImg(byte[] data, int width,
			int height) {

		int x = 0, y = height - 1;

		int r = 0;
		int g = 0;
		int b = 0;

		BufferedImage image = new BufferedImage(width, height, 1);

		for (int i = 0; i < width * height * 3; i += 3) {
			b = Datatypes.readUSByte(data, i);
			g = Datatypes.readUSByte(data, i + 1);
			r = Datatypes.readUSByte(data, i + 2);

			image.setRGB(x, y, r << 16 | g << 8 | b);

			x++;
			if (x == width) {
				x = 0;
				y--;
			}
		}

		return image;
	}

	public static BufferedImage bytearrToBWImg(byte[] data, int width,
			int height) {

		int x = 0, y = height - 1;

		int l = 0;

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);

		for (int i = 0; i < width * height; i++) {
			l = Datatypes.readUSByte(data, i);
			;

			image.setRGB(x, y, l << 16 | l << 8 | l);

			x++;
			if (x == width) {
				x = 0;
				y--;
			}
		}

		return image;
	}

	protected static byte[] loadByteArray(String name) throws IOException {
		File file = new File(name);
		int length = (int) file.length();

		FileInputStream in = new FileInputStream(file);
		byte data[] = new byte[length];
		in.read(data, 0, length);
		in.close();

		return data;
	}

	protected static void saveByteArray(String name, byte[] data)
			throws IOException {
		File file = new File(name);

		FileOutputStream out = new FileOutputStream(file);
		out.write(data);
		out.close();
	}
}
