package video.loader;

import java.io.IOException;

import riff.Chunk;
import riff.Container;
import riff.List;
import video.Datatypes;

public class AviLoader extends Loader {

	final private static int lOffset = 12;
	final private static int cOffset = 8;

	final private static int lsOffset = 4;
	final private static int ltOffset = 8;
	final private static int ctOffset = 0;
	final private static int csOffset = 4;

	final private static int hSize = 8;

	public static List loadRiff(String name) throws IOException {
		byte[] data = loadByteArray(name);
		System.out.println("Loading AVI");

		List riffFile = new List(Datatypes.readInt(data, lsOffset),
				Datatypes.readChars(data, ltOffset, 4));

		genRiff(data, riffFile);

		return riffFile;
	}

	public static void saveRiff(List riffFile, String name) throws IOException {
		System.out.println("Saving AVI");
		byte[] data = genByteArray(riffFile);
		saveByteArray(name, data);
	}

	private static byte[] genByteArray(List list) {
		byte[] data = new byte[list.getcSize() + hSize];
		int index = lOffset;

		if (list.getcType().equals("AVI ")) {
			Datatypes.writeChars(data, 0, "RIFF");
		} else {
			Datatypes.writeChars(data, 0, "LIST");
		}

		Datatypes.writeInt(data, lsOffset, list.getcSize());
		Datatypes.writeChars(data, ltOffset, list.getcType());

		for (int i = 0; i < list.getNumContainers(); i++) {
			Container c = list.getContainer(i);

			if (c instanceof List) {
				System.arraycopy(genByteArray(c.getAsList()), 0, data, index,
						c.getcSize());
			} else {
				Datatypes.writeInt(data, index + csOffset, c.getcSize());
				Datatypes.writeChars(data, index + ctOffset, c.getcType());
				System.arraycopy(c.getAsChunk().getData(), 0, data, index
						+ cOffset, c.getcSize());
				if (c.getcType().equals("00db") & ((i%20) == 0))
					System.out.println((i*100)/(list.getNumContainers())+" %");
			}

			index += (c.getcSize() + hSize);

		}

		return data;
	}

	private static void genRiff(byte[] data, List list) {

		int index = 0;
		int length = 0;
		byte[] tmp = data;

		if (Datatypes.readChars(tmp, 0, 4).equals("LIST")
				| Datatypes.readChars(tmp, 0, 4).equals("RIFF")) {
			length = Datatypes.readInt(tmp, lsOffset) - hSize;
			tmp = splitByteArray(tmp, lOffset, tmp.length);

			while (index < length) {

				if (Datatypes.readChars(tmp, 0, 4).equals("LIST")) {

					// System.out.println("\n"+Datatypes.readChars(tmp,
					// ltOffset, 4));
					// System.out.println(Datatypes.readInt(tmp,
					// lsOffset)+"\n");

					index += Datatypes.readInt(tmp, lsOffset) + hSize;
					// System.out.println("Index: "+index+" (" + length + ")");

					// System.out.println("start");
					List newList = new List(Datatypes.readInt(tmp, lsOffset),
							Datatypes.readChars(tmp, ltOffset, 4));
					list.addContainer(newList,
							Datatypes.readChars(tmp, ltOffset, 4));
					genRiff(tmp, newList);
					// System.out.println("end");
					tmp = splitByteArray(tmp, Datatypes.readInt(tmp, lsOffset)
							+ hSize, tmp.length);

				} else {
					// tmp = splitByteArray(tmp, 0, Datatypes.readInt(tmp,
					// csOffset));

					// System.out.println("\n"+Datatypes.readChars(tmp,
					// ctOffset, 4));
					// System.out.println(Datatypes.readInt(tmp,
					// csOffset)+"\n");

					Chunk newChunk = new Chunk(Datatypes.readChars(tmp,
							ctOffset, 4), Datatypes.readInt(tmp, csOffset));
					newChunk.setData(splitByteArray(tmp, hSize,
							Datatypes.readInt(tmp, csOffset) + hSize));
					list.addContainer(newChunk,
							Datatypes.readChars(tmp, ctOffset, 4));

					index += Datatypes.readInt(tmp, csOffset) + hSize;
					// System.out.println("Index: "+index+" (" + length + ")");
					tmp = splitByteArray(tmp, Datatypes.readInt(tmp, csOffset)
							+ hSize, tmp.length);
				}

			}
		}
	}

}
