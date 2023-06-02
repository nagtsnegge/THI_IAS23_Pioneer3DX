package thi.irobcon.script;

import thi.irobcon.ecar.ECar;
import thi.irobcon.saphira.lps.Blob;

public abstract class IRobConScript {

	protected ECar ecar;
	
	public void useRobot(String name) {
		ecar = new ECar(name);
	}
	
	public abstract void script();
	
	protected void setRobPose(int x, int y, int th) {
		ecar.setRobPose(x, y, th);
	}
	
	protected void move(int dist) {
		ecar.move(dist);
	}
	
	protected void turn(int deg) {
		ecar.turn(deg);
	}
	
	protected void speed(int transVel) {
		ecar.speed(transVel);
	}
	
	protected void rotate(int rotVel) {
		ecar.rotate(rotVel);
	}
	
	protected void gripOpen() {
		ecar.gripOpen();
	}
	
	protected void gripClose() {
		ecar.gripClose();
	}
	
	protected void liftUp() {
		ecar.liftUp();
	}
	
	protected void liftDown() {
		ecar.liftDown();
	}
	
	protected int getSonarRange(int sonNum) {
		return ecar.getSonarRange(sonNum);
	}
	
	protected int[] getSonarRanges() {
		return ecar.getSonarRanges();
	}

	protected int[] getLaserRanges() {
		return ecar.getLaserRanges();
	}
	
	protected void addOutput(String s) {
		System.out.print(s);
	}
	
	protected void addOutputLn(String s) {
		System.out.println(s);
	}
	
	protected void wait(int cycles) {
		ecar.wait(cycles);
	}

	protected int getBlobX(int channel) {
		if (ecar.getNumBlobs(channel) > 0) {
//			Blob blob = ecar.getBlob(channel, 0);  // new version acts und jptzcam -> wird in acts vorher incrementiert, jptzcam bleibt so
			Blob blob = ecar.getBlob(channel, 1);  // old version acts, wird direkt verwendet
			return blob.getXCG();
		}
		else return -1;
	}
	
	protected void panTilt(int pan, int tilt) {
		ecar.panTilt(pan, tilt);
	}
}
