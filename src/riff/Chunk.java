package riff;

public class Chunk extends Container {

	byte[] data;

	public Chunk(String ckID, int ckSize) {
		super(ckSize, ckID);
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public byte[] getData() {
		return data;
	}
}
