package video;

import java.awt.image.BufferedImage;

import riff.Chunk;
import riff.List;
import video.loader.Loader;

public class AviVideo {

	// avih

	public int DEFAULT_MICRO_SEC_PER_FRAME = 40000;
	public int DEFAULT_MAX_BYTE_PER_SEC = 0;
	public int DEFAULT_PADDING_GRANULARITY = 0;
	public int DEFAULT_FLAGS = 16;
	public int DEFAULT_INITIAL_FRAMES = 0;
	public int DEFAULT_STREAMS = 1;
	public int DEFAULT_SUGGESTED_BUFFER_SIZE = 0;

	// strh

	public String DEFAULT_TYPE = "vids";
	public String DEFAULT_HANDLER = "DIB ";
	public int DEFAULT_STREAM_FLAGS = 0;
	public int DEFAULT_PRIORITY = 0;
	public int DEFAULT_LANGUAGE = 0;
	public int DEFAULT_STREAM_INITIAL_FRAMES = 0;
	public int DEFAULT_SCALE = 1;
	public int DEFAULT_RATE = 25;
	public int DEFAULT_START = 0;
	public int DEFAULT_STREAM_SUGGESTED_BUFFER_SIZE = -1;
	public int DEFAULT_QUALITY = 0;
	public int DEFAULT_SAMPLE_SIZE = 0;
	public int DEFAULT_FRAME = 0;
	
	//strf
	
	public int DEFAULT_BITMAP_SIZE = 40;
	public int DEFAULT_BITMAP_Planes = 1;
	public int DEFAULT_BITMAP_BIT_COUNT = 24;
	public int DEFAULT_BITMAP_COMPRESSION = 0;
	public int DEFAULT_BITMAP_X_PELS_PER_METER = 0;
	public int DEFAULT_BITMAP_Y_PELS_PER_METER = 0;
	public int DEFAULT_BITMAP_CLR_USED = 0;
	public int DEFAULT_BITMAP_CLR_IMPORTANT = 0;
	
	//idx1
	
	public int DEFAULT_IDX_FLAGS = 18;
	

	public BufferedImage[] images;

	// avih

	private int microSecPerFrame;
	private int maxBytesPerSec;
	private int paddingGranularity;
	private int flags;
	private int totalFrames;
	private int initialFrames;
	private int streams;
	private int suggestedBufferSize;
	private int width;
	private int height;

	// strh

	private String type;
	private String handler;
	private int streamFlags;
	private int priority;
	private int language;
	private int streamInitialFrames;
	private int scale;
	private int rate;
	private int start;
	private int length;
	private int streamSuggestedBufferSize;
	private int quality;
	private int sampleSize;
	private int frame;
	
	//strf
	
	private int bitmapSize;
	private int bitmapWidth;
	private int bitmapHeight;
	private int bitmapPlanes;
	private int bitmapBitCount;
	private int bitmapCompression;
	private int bitmapSizeImage;
	private int bitmapXPelsPerMeter;
	private int bitmapYPelsPerMeter;
	private int bitmapClrUsed;
	private int bitmapClrImportant;

	public AviVideo(BufferedImage[] images) {
		this.images = images;

		microSecPerFrame = DEFAULT_MICRO_SEC_PER_FRAME;
		maxBytesPerSec = images[0].getWidth()*images[0].getHeight()*15;
		paddingGranularity = DEFAULT_PADDING_GRANULARITY;
		flags = DEFAULT_FLAGS;
		totalFrames = images.length;
		initialFrames = DEFAULT_INITIAL_FRAMES;
		streams = DEFAULT_STREAMS;
		suggestedBufferSize = images[0].getWidth()*images[0].getHeight()*3;
		width = images[0].getWidth();
		height = images[0].getHeight();

		type = DEFAULT_TYPE;
		handler = DEFAULT_HANDLER;
		streamFlags = DEFAULT_STREAM_FLAGS;
		priority = DEFAULT_PRIORITY;
		language = DEFAULT_LANGUAGE;
		streamInitialFrames = DEFAULT_STREAM_INITIAL_FRAMES;
		scale = DEFAULT_SCALE;
		rate = DEFAULT_RATE;
		start = DEFAULT_START;
		length = width * height * 3;
		streamSuggestedBufferSize = DEFAULT_STREAM_SUGGESTED_BUFFER_SIZE;
		quality = DEFAULT_QUALITY;
		sampleSize = DEFAULT_SAMPLE_SIZE;
		frame = DEFAULT_FRAME;
		
		bitmapSize = DEFAULT_BITMAP_SIZE;
		bitmapWidth = width;
		bitmapHeight = height;
		bitmapPlanes = DEFAULT_BITMAP_Planes;
		bitmapBitCount = DEFAULT_BITMAP_BIT_COUNT;
		bitmapCompression = DEFAULT_BITMAP_COMPRESSION;
		bitmapSizeImage = width * height * 3;
		bitmapXPelsPerMeter = DEFAULT_BITMAP_X_PELS_PER_METER;
		bitmapYPelsPerMeter = DEFAULT_BITMAP_Y_PELS_PER_METER;
		bitmapClrUsed = DEFAULT_BITMAP_CLR_USED;
		bitmapClrImportant = DEFAULT_BITMAP_CLR_IMPORTANT;
	}

	public AviVideo(List aviRiff) {
		Chunk chunk = aviRiff.getAsList().getContainer("hdrl").getAsList()
				.getContainer("avih").getAsChunk();
		byte[] data = chunk.getData();

		microSecPerFrame = Datatypes.readInt(data, 0);
		maxBytesPerSec = Datatypes.readInt(data, 4);
		paddingGranularity = Datatypes.readInt(data, 8);
		flags = Datatypes.readInt(data, 12);
		totalFrames = Datatypes.readInt(data, 16);
		initialFrames = Datatypes.readInt(data, 20);
		streams = Datatypes.readInt(data, 24);
		suggestedBufferSize = Datatypes.readInt(data, 28);
		width = Datatypes.readInt(data, 32);
		height = Datatypes.readInt(data, 36);

		chunk = aviRiff.getAsList().getContainer("hdrl").getAsList()
				.getContainer("strl").getAsList().getContainer("strh")
				.getAsChunk();
		data = chunk.getData();
		
		type = Datatypes.readChars(data, 0, 4);
		handler = Datatypes.readChars(data, 4, 4);
		streamFlags = Datatypes.readInt(data, 8);
		priority = Datatypes.readInt(data, 12);
		language = Datatypes.readInt(data, 14);
		streamInitialFrames = Datatypes.readInt(data, 16);
		scale = Datatypes.readInt(data, 20);
		rate = Datatypes.readInt(data, 24);
		start = Datatypes.readInt(data, 28);
		length = Datatypes.readInt(data, 32);
		streamSuggestedBufferSize = Datatypes.readInt(data, 36);
		quality = Datatypes.readInt(data, 40);
		sampleSize = Datatypes.readInt(data, 44);
//		frame = Datatypes.readInt(data, 48);
		
		chunk = aviRiff.getAsList().getContainer("hdrl").getAsList()
				.getContainer("strl").getAsList().getContainer("strf")
				.getAsChunk();
		data = chunk.getData();
		
		bitmapSize = Datatypes.readInt(data, 0);
		bitmapWidth = Datatypes.readInt(data, 4);
		bitmapHeight = Datatypes.readInt(data, 8);
		bitmapPlanes = Datatypes.readInt(data, 12);
		bitmapBitCount = Datatypes.readInt(data, 14);
		bitmapCompression = Datatypes.readInt(data, 16);
		bitmapSizeImage = Datatypes.readInt(data, 20);
		bitmapXPelsPerMeter = Datatypes.readInt(data, 24);
		bitmapYPelsPerMeter = Datatypes.readInt(data, 28);
		bitmapClrUsed = Datatypes.readInt(data, 32);
		bitmapClrImportant = Datatypes.readInt(data, 34);
		

		images = new BufferedImage[totalFrames];

		for (int i = 0; i < totalFrames; i++) {
			chunk = (Chunk) ((List) aviRiff.getContainer("movi")).getContainer(
					"00db", i);
			data = chunk.getData();

			images[i] = Loader.bytearrToClrImg(data, width, height);
		}
	}
	
	public List toRiff() {
		System.out.println("generating AVI");
		
			int l00dbi = 16;
		int lidx1 = l00dbi*totalFrames;
			int l00db = width*height*3;
		int lmovi = l00db*totalFrames+8*totalFrames+4;
			
				int lstrh = 56;
				int lstrf = 40;
			int lstrl = lstrh+lstrf+2*8+4;
			int lavih = 56;
		int lhdrl = lavih+lstrl+2*8+4;
				
		int lriff = lidx1+lmovi+lhdrl+3*8+4;
		
		List riff = new List(lriff, "AVI ");
		
		List hdrl = new List(lhdrl, "hdrl");
			Chunk avih = new Chunk("avih", lavih);
			avih.setData(genHeadData(microSecPerFrame,maxBytesPerSec,paddingGranularity,flags,totalFrames,initialFrames,streams,suggestedBufferSize,width,height,0,0,0,0));
			hdrl.addContainer(avih, "avih");
			List strl = new List(lstrl, "strl");
				Chunk strh = new Chunk("strh", lstrh);
				strh.setData(genHeadData(type,handler,streamFlags,(priority<<16|language),streamInitialFrames,scale,rate,start,length,streamSuggestedBufferSize,quality,sampleSize,frame,frame));
				strl.addContainer(strh, "strh");
				Chunk strf = new Chunk("strf", lstrf);
				strf.setData(genHeadData(bitmapSize,bitmapWidth,bitmapHeight,bitmapPlanes|(bitmapBitCount << 16),bitmapCompression,bitmapSizeImage,bitmapXPelsPerMeter,bitmapYPelsPerMeter,bitmapClrUsed,bitmapClrImportant));
				strl.addContainer(strf, "strf");
			hdrl.addContainer(strl, "strl");
				
		riff.addContainer(hdrl, "hdrl");
				
		List movi = new List(lmovi, "movi");
			for (int i = 0; i < totalFrames; i++) {
				Chunk db = new Chunk("00db", l00db);
				db.setData(Loader.imgToBytearr(images[i]));
				movi.addContainer(db, "00db");
				if ((i%20) == 0)
					System.out.println((i*100)/(totalFrames)+" %");
			}
			
		riff.addContainer(movi, "movi");
		
		Chunk idx1 = new Chunk("idx1", lidx1);
		byte[] data = new byte[lidx1];
		for (int i = 0; i < totalFrames; i++) {
			System.arraycopy(genHeadData("00db", DEFAULT_IDX_FLAGS, 4+i*(width*height*3+8), width*height*3), 0, data, i*16, 16);
		}
		idx1.setData(data);
		
		riff.addContainer(idx1, "idx1");
		
		return riff;
	}
	
	
	private byte[] genHeadData(Object... o) {
		byte[] data = new byte[o.length*4];

		for (int i = 0; i < o.length; i ++) {
			if (o[i] instanceof Integer) {
				Datatypes.writeInt(data, i*4, (Integer) o[i]);
			} else if (o[i] instanceof String) {
				Datatypes.writeChars(data, i*4, (String) o[i]);
			}
		}

		return data;
	}

	public void addImage(BufferedImage image, int index) {
		if ((index >= 0) & (index < images.length)) {
			// Imagearray
			BufferedImage[] tmpImages = new BufferedImage[images.length + 1];
			for (int i = 0; i < images.length; i++) {
				if (i < index) {
					tmpImages[i] = images[i];
				} else {
					tmpImages[i + 1] = images[i];
				}
			}

			tmpImages[index] = image;
			images = tmpImages;
			totalFrames++;
		}
	}

	public void deleteImage(int index) {
		if ((index > 0) & (index < images.length)) {
			BufferedImage[] tmpImages = new BufferedImage[images.length - 1];
			for (int i = 0; i < images.length; i++) {
				if (i <= index) {
					tmpImages[i] = images[i];
				} else {
					tmpImages[i - 1] = images[i];
				}
			}

			images = tmpImages;
			totalFrames--;
		}
	}

}
