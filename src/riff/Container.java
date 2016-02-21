package riff;

public class Container {
	int cSize;
	String cType;
	
	public Container(int cSize, String cType) {
		this.cType = cType;
		this.cSize = cSize;
	}
	
	public List getAsList() {
		return (List) this;
	}
	
	public Chunk getAsChunk() {
		return (Chunk) this;
	}

	public int getcSize() {
		return cSize;
	}

	public void setcSize(int cSize) {
		this.cSize = cSize;
	}

	public String getcType() {
		return cType;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}
}
