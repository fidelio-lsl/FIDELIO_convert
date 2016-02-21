import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import video.AviVideo;
import video.loader.AviLoader;
import video.loader.BWVLoader;


public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	BufferedImage[] images = null;

	long start;
	long end;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String name = "\\video20150929_225307";
		AviVideo video = null;
		try {
//			video = BWVLoader.loadBWV(args[0]);
			video = BWVLoader.loadBWV("C:\\Users\\Jakob\\Desktop\\Test"+name+".bwv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out
				.println((System.currentTimeMillis() - start) / 1000 + " sec");
		start = System.currentTimeMillis();
		try {
//			AviLoader.saveRiff(video.toRiff(), args[1]);
			AviLoader.saveRiff(video.toRiff(), "C:\\Users\\Jakob\\Desktop\\Test"+name+".avi");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out
		.println((System.currentTimeMillis() - start) / 1000 + " sec");
	}

//	public MainPanel() {
//		super();
//
//		AviVideo video = null;
//		try {
//			video = BWVLoader.loadBWV("H:\\vid.bwv");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// images = video.images;
//
//		try {
//			AviLoader.saveRiff(video.toRiff(),
//					"C:\\Users\\martin\\Desktop\\test2.avi");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	private void computeFrames(BufferedImage[] images) {
//		BufferedImage sub = new BufferedImage(images[0].getWidth(),
//				images[0].getWidth(), images[0].getType());
//
//		for (int i = 0; i < images.length - 1; i++) {
//			for (int x = 0; x < images[i].getWidth(); x++) {
//				for (int y = 0; x < images[i].getHeight(); y++) {
//					sub.setRGB(x, y, images[i].getRGB(x, y));
//				}
//			}
//		}
//	}
//
//	private BufferedImage subImage(BufferedImage i1, BufferedImage i2) {
//		BufferedImage sub = new BufferedImage(i1.getWidth(), i1.getHeight(),
//				i1.getType());
//		int b;
//
//		for (int x = 0; x < i1.getWidth(); x++) {
//			for (int y = 0; y < i1.getHeight(); y++) {
//				b = Math.abs(((i2.getRGB(x, y) & 0xff0000) >> 16)
//						- ((i1.getRGB(x, y) & 0xff0000) >> 16));
//
//				sub.setRGB(x, y, (b << 16) | (b << 8) | (b));
//
//			}
//		}
//
//		return sub;
//	}
//
//	int i = 0;
//
//	public void paintComponent(Graphics g) {
//		super.paintComponents(g);
//
//		if (i == images.length) {
//			i = 0;
//		}
//
//		g.drawImage(images[i], 20, 20, this);
//		i++;
//
//	}

}
