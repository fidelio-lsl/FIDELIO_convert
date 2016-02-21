package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import video.Datatypes;
import video.loader.BitmapLoader;
import video.loader.Loader;

public class HuffyAnalyzer {

	public static void main(String[] args) {
		BufferedImage image = null;
		try {
			image = BitmapLoader
					.loadBmp("C:\\Users\\martin\\Desktop\\10022.bmp");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HuffyAnalyzer ha = new HuffyAnalyzer(image);

	}

	boolean[][] codes;
	Node main;

	public HuffyAnalyzer(BufferedImage image) {

		ArrayList<Node> nodes = new ArrayList<Node>();

		int[] num = new int[256];
		int value = 0;

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				value = (image.getRGB(x, y) & 0xff);
				// if (value > 50) {
				// value = 255;
				// }
				num[value]++;
			}
		}

		for (int i = 0; i < num.length; i++) {
			Node node = new Leaf(num[i], i);
			nodes.add(node);
		}

		genTree(nodes);
		main = nodes.get(0);

		// Code[] codes = new Code[256];
		codes = new boolean[256][];

		genCodes(codes, main, new ArrayList<Boolean>());
		int max_l = 0;
		;

		for (int i = 0; i < codes.length; i++) {
			// System.out.println(i + "	" + num[i] + "	" + codes[i].code.length
			// + "	" + codes[i].toString());
			// l += num[i] * codes[i].code.length;
			// if (num[i] != 0){
			System.out.println(i + "	" + num[i] + "	" + codes[i].length + "	"
					+ printCode(codes[i]));
			if (codes[i].length > max_l)
				max_l = codes[i].length;
		}

		System.out.println(max_l);

		// System.out.println(main.frequency);

		// byte[] hdata = huffy(bmpToArr(image));

		// System.out.println(hdata.length);
		//
		// File outputfile = new File("C:\\Users\\martin\\Desktop\\image.bmp");
		// try {
		// ImageIO.write(arrTobmp(bmpToArr(image)), "bmp", outputfile);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	private String printCode(boolean[] code) {
		String s = "";

		for (int i = 0; i < code.length; i++) {
			if (code[i]) {
				s += "1";
			} else {
				s += "0";
			}
		}

		return s;
	}

	private void saveHuffy(boolean[][] codes) throws IOException {
		File file = new File("C:\\Users\\martin\\Desktop\\meteor.hfy");
		FileOutputStream out = new FileOutputStream(file);

		int length = 256 * 8;

		for (int i = 0; i < codes.length; i++) {
			length += codes[i].length / 8 + 1;
		}

		byte[] data = new byte[length];

		int index = 0;

		for (int i = 0; i < codes.length; i++) {
			data[index] = (byte) i;
			index+=8;
		}

		out.write(data);
		out.close();
	}

	// private byte[] huffy(byte[] data) {
	// ArrayList<Boolean> hArr = new ArrayList<Boolean>();
	//
	// for (int i = 0; i < data.length; i++) {
	// int index = (int) (data[i] & 0xff);
	// // System.out.println(codes[index].length);
	// for (int j = codes[index].length - 1; j > -1; j--) {
	// // System.out.println(j);
	// hArr.add(((codes[data[index]].code >> j) & 0x1) == 1);
	// }
	// }
	//
	// byte[] hData = new byte[hArr.size() / 8 + 1];
	//
	// for (int i = 0; i < hData.length - 1; i++) {
	// // hData[i] = booleanArrToByte(new boolean[] { hArr.get(i * 8),
	// // hArr.get(i * 8 + 1), hArr.get(i * 8 + 2),
	// // hArr.get(i * 8 + 3), hArr.get(i * 8 + 4),
	// // hArr.get(i * 8 + 5), hArr.get(i * 8 + 6),
	// // hArr.get(i * 8 + 7) });
	// }
	//
	// return hData;
	// }

	private final Node MAX_NODE = new Node(null, null);

	private void genTree(ArrayList<Node> nodes) {

		Node n1 = MAX_NODE;
		Node n2 = MAX_NODE;

		for (int i = 0; i < nodes.size(); i++) {

			if (nodes.get(i).frequency < n1.frequency) {
				n2 = n1;
				n1 = nodes.get(i);
			} else if (nodes.get(i).frequency < n2.frequency) {
				n2 = nodes.get(i);
			}
		}

		// System.out.println(nodes.size());
		// System.out.println(nodes.remove(n1) + "	"+ nodes.remove(n2));
		// System.out.println(n1.frequency + "  " + n1.getType() + " | "
		// + n2.frequency + "  " + n2.getType());

		nodes.remove(n1);
		nodes.remove(n2);

		nodes.add(new Node(n1, n2));

		if (nodes.size() != 1) {
			genTree(nodes);
		}
	}

	@SuppressWarnings("unchecked")
	private void genCodes(boolean[][] codes, Node node, ArrayList<Boolean> code) {
		if (!(node instanceof Leaf)) {
			ArrayList<Boolean> tmpcode = (ArrayList<Boolean>) code.clone();
			tmpcode.add(false);
			genCodes(codes, node.nodes[0], tmpcode);
			tmpcode =  (ArrayList<Boolean>) code.clone();
			tmpcode.add(true);
			genCodes(codes, node.nodes[1], tmpcode);
		} else {
			boolean[] c = new boolean[code.toArray().length];
			for (int i = 0; i < code.toArray().length; i++) {
				c[i] = (boolean) code.toArray()[i];
			}
			codes[((Leaf) node).value] = c;
		}
	}

	class Node {
		Node[] nodes;
		int frequency;

		public Node(Node n1, Node n2) {
			if (n1 != null & n2 != null) {
				nodes = new Node[2];
				nodes[0] = n1;
				nodes[1] = n2;
				this.frequency = n1.frequency + n2.frequency;
			} else {
				this.frequency = Integer.MAX_VALUE;
			}
		}

		public int getType() {
			if (this instanceof Leaf) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	class Leaf extends Node {
		int value;

		public Leaf(int frequency, int value) {
			super(null, null);
			this.frequency = frequency;
			this.value = value;
		}
	}

	class Code {
		int code;
		int length;

		public Code(int code, int length) {
			this.code = code;
			this.length = length;
		}

		public Code(Code c) {
			this.code = c.code;
			this.length = c.length;
		}

		public Code() {
			this.code = 0;
		}

		public void add(int i) {
			code = (code << 1) | i;
			length++;
		}

		@Override
		public String toString() {
			String s = "";
			for (int i = 0; i < length - Integer.toBinaryString(code).length(); i++) {
				s += "0";
			}
			return "Code: " + s + Integer.toBinaryString(code);
		}

	}

	private byte[] bmpToArr(BufferedImage image) {
		byte[] data = new byte[image.getWidth() * image.getHeight()];
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				data[x + y * image.getWidth()] = (byte) (image.getRGB(x, y) & 0xff);
			}
		}
		return data;
	}

	private BufferedImage arrTobmp(byte[] data) {
		BufferedImage image = new BufferedImage(720, 480, 1);
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				image.setRGB(x, y, ((data[x + y * image.getWidth()]) & 0xff)
						| (((data[x + y * image.getWidth()]) & 0xff) << 8)
						| (((data[x + y * image.getWidth()]) & 0xff) << 16));
			}
		}

		return image;
	}
}
