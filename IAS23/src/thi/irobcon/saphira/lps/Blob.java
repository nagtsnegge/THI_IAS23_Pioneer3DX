package thi.irobcon.saphira.lps;

public class Blob {
	
	protected int channel, blobNumber, xCG, yCG, area, bottom, left, right, top;
	
	public Blob(int channel, int blobNumber, int xCG, int yCG) {
		this.channel = channel;
		this.blobNumber = blobNumber;
		this.xCG = xCG;
		this.yCG = yCG;
	}	
	
	public int getXCG() {
		return xCG;
	}

	public int getYCG() {
		return yCG;
	}
	
	public int getArea() {
		return area;
	}

	public int getBottom() {
		return bottom;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getTop() {
		return top;
	}

	public int getBlobNumber() {
		return blobNumber;
	}

	public int getChannel() {
		return channel;
	}

	public void setxCG(int xCG) {
		this.xCG = xCG;
	}

	public void setyCG(int yCG) {
		this.yCG = yCG;
	}
	
	
}