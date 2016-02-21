package video;

public class Datatypes {

	public static char readChar(byte[] data, int offset) {
		return (char) (data[offset] & 0xff);
	}

	public static void writeChar(byte[] data, int offset, char character) {
		data[offset] = (byte) character;
	}

	public static String readChars(byte[] data, int offset, int num) {
		String string = "";

		for (int i = 0; i < num; i++) {
			string += readChar(data, offset + i);
		}
		
		return string;
	}

	public static void writeChars(byte[] data, int offset, String string) {
		char[] characters = string.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			writeChar(data, offset + i, characters[i]);
		}
	}

	public static short readUSByte(byte[] data, int offset) {
		return (short) (data[offset] & 0xff);
	}

	public static void writeUSByte(byte[] data, int offset, short b) {
		data[offset] = (byte) (b & 0xff);
	}

	public static short readShort(byte[] data, int offset) {
		return (short) (((data[offset + 1] & 0xff) << 8) | ((data[offset] & 0xff)));
	}

	public static int readInt(byte[] data, int offset) {
		return ((data[offset + 3] & 0xff) << 24)
				| ((data[offset + 2] & 0xff) << 16)
				| ((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff);
	}

	public static void writeInt(byte[] data, int offset, int integer) {
		data[offset] = (byte) (integer & 0xff);
		data[offset + 1] = (byte) ((integer & 0xff00) >> 8);
		data[offset + 2] = (byte) ((integer & 0xff0000) >> 16);
		data[offset + 3] = (byte) ((integer & 0xff000000) >> 24);
	}

	public static int readWord(byte[] data, int offset) {
		return ((data[offset + 3] & 0xff) << 24)
				| ((data[offset + 2] & 0xff) << 16)
				| ((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff);
	}

	public static float readFloat(byte[] data, int offset) {
		return Float.intBitsToFloat(readInt(data, offset));
	}

}
