package thi.irobcon.ecar;

import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

import thi.irobcon.saphira.lps.Blob;

public class ECar {

	private static final String GRAB_IMAGE_CMD = "C:\\programme\\irobcon\\bin\\iRobConVideoServer ";		
	private static final String CAM_IMAGE = "C:\\programme\\ecar\\images\\cameraImage.jpg";		

	protected ECarClient eCarClient;
	protected Process simulator;
	
	public ECar(String eCarName) {
		this(eCarName, null);
	}
	
	public ECar(String eCarName, String world) {
		eCarClient = new ECarClient(eCarName);
		if (world != null) {
			String cmd = "c:\\Programme\\ActivMedia Robotics\\Aria\\bin\\SRISim.exe -p " + ECarDefines.getRobSimPort(eCarName) + " -w " + world;
			try {
				simulator = Runtime.getRuntime().exec(cmd);
			}
			catch (Exception e) {
				System.out.println("Could not start simulator");
			}
		}
		connect();
	}
	
	public boolean connect() {
		eCarClient.startServer();
		return eCarClient.connect();
	}
	
	public void disconnect() {
		if (simulator != null)
			simulator.destroy();
		eCarClient.disconnect();
	}
	
	public void addLaser() {
		eCarClient.addLaser();
	}
	
	public void setRobPose(int x, int y, int th) {
		String cmd = "" + ECarDefines.SETROBPOSE + ECarDefines.SEP + x + ECarDefines.SEP + y + ECarDefines.SEP + th;
		eCarClient.exec(cmd);
	}
	
	public void move(int dist) {
		String cmd = "" + ECarDefines.MOVE + ECarDefines.SEP + dist;
		eCarClient.exec(cmd);
	}
	
	public void turn(int deg) {
		String cmd = "" + ECarDefines.TURN + ECarDefines.SEP + deg;
		eCarClient.exec(cmd);
	}

	public void speed(int transVel) {
		String cmd = "" + ECarDefines.SPEED + ECarDefines.SEP + transVel;
//		System.out.println("Execute cmd: " + cmd);
		eCarClient.exec(cmd);
	}

	public void shift(int shiftVel) {
		String cmd = "" + ECarDefines.SHIFT + ECarDefines.SEP + shiftVel;
		eCarClient.exec(cmd);
	}

	public void rotate(int rotVel) {
		String cmd = "" + ECarDefines.ROTATE + ECarDefines.SEP + rotVel;
		eCarClient.exec(cmd);
	}

	public int getSonarRange(int sonNum) {
		String cmd = "" + ECarDefines.SONAR + ECarDefines.SEP + sonNum;
		String answer = eCarClient.exec(cmd);
		try {
			int res = Integer.parseInt(answer);
			return res;
		}
		catch (Exception e) {
			System.out.println("Error executing command: " + cmd);
			disconnect();
			return 0;
		}
	}

	public int getSonarAngle(int sonNum) {
		switch (sonNum) {
			case 0:		return 90;
			case 1: 	return 50;
			case 2:		return 30;
			case 3: 	return 10;
			case 4:		return -10;
			case 5: 	return -30;
			case 6:		return -50;
			case 7: 	return -90;
			case 8:		return -90;
			case 9: 	return -130;
			case 10:	return -150;
			case 11: 	return -170;
			case 12:	return 170;
			case 13: 	return 150;
			case 14:	return 130;
			case 15: 	return 90;
		}

		return 360;
	}

	public int[] getLaserRanges() {
		String cmd = "" + ECarDefines.LASER;
		String answer = eCarClient.exec(cmd);
		int[] res = new int[180];
		try {
			StringTokenizer strtok = new StringTokenizer(answer, ECarDefines.SEP);
			for(int i=0; i < res.length; i++) {
				String value = strtok.nextToken();
				res[i] = Integer.parseInt(value);
			}
		}
		catch (Exception e) {
			System.out.println("Error executing command: " + cmd);
			disconnect();
		}
		return res;
	}
	
	public int[] getSonarRanges() {
		String cmd = "" + ECarDefines.ALLSONAR;
		String answer = eCarClient.exec(cmd);
		int[] res = new int[16];
		try {
			StringTokenizer strtok = new StringTokenizer(answer, ECarDefines.SEP);
			for(int i=0; i < res.length; i++) {
				String value = strtok.nextToken();
				res[i] = Integer.parseInt(value);
			}
		}
		catch (Exception e) {
			System.out.println("Error executing command: " + cmd);
			disconnect();
		}
		return res;
	}
	
	public void gripOpen() {
		String cmd = "" + ECarDefines.OPEN;
		eCarClient.exec(cmd);
	}
	
	public void gripClose() {
		String cmd = "" + ECarDefines.CLOSE;
		eCarClient.exec(cmd);
	}

	public void liftUp() {
		String cmd = "" + ECarDefines.UP;
		eCarClient.exec(cmd);
	}

	public void liftDown() {
		String cmd = "" + ECarDefines.DOWN;
		eCarClient.exec(cmd);
	}

	public void wait(int cycles) {
		Timer t = new Timer(cycles * 100);
		t.waitFor();
	}
	
	public BufferedImage getImage() {
		BufferedImage res = null;
		String grabImageCmd = GRAB_IMAGE_CMD + eCarClient.getECarName() + " " + CAM_IMAGE;
		try {
			Runtime.getRuntime().exec(grabImageCmd);
		}
		catch (Exception e) {
			System.out.println("Could not start video server");
		}		
		try {
	    	res = ImageIO.read(new File(CAM_IMAGE));
		}
		catch (Exception e) {
			System.out.println("Could not read camera image");
		}		
    	return res;
	}
	
	public double[] getPosition() {
		String cmd = "" + ECarDefines.POSE;
		String answer = eCarClient.exec(cmd);
		double[] res = new double[3];
		try {
			StringTokenizer strtok = new StringTokenizer(answer, ECarDefines.SEP);
			for(int i=0; i < res.length; i++) {
				String value = strtok.nextToken();
				res[i] = Double.parseDouble(value);
			}
		}
		catch (Exception e) {
			System.out.println("Error executing command: " + cmd);
			disconnect();
		}
		return res;
	}
	
	public double getVoltage() {
		String cmd = "" + ECarDefines.VOLT;
		String answer = eCarClient.exec(cmd);
		try{			
			return Double.parseDouble(answer);
		}
		catch (Exception e) {
			System.out.println("Error executing command: " + cmd);
			disconnect();
		}
		return 0;
	}

	public double[] getVelocities() {
		String cmd = "" + ECarDefines.VEL;
		String answer = eCarClient.exec(cmd);
		double[] res = new double[2];
		try {
			StringTokenizer strtok = new StringTokenizer(answer, ECarDefines.SEP);
			for(int i=0; i < res.length; i++) {
				String value = strtok.nextToken();
				res[i] = Double.parseDouble(value);
			}
		}
		catch (Exception e) {
			System.out.println("Error executing command: " + cmd);
			disconnect();
		}
		return res;
	}
	
	public String getName() {
		return eCarClient.getECarName();
	}
	
	public boolean isConnected() {
		return this.eCarClient.isConnected();
	}
	
	public int getImageServerPort() {
		return ECarDefines.getImageServerPort(eCarClient.getECarName());
	}
	
	public int getNumBlobs(int channel) {
		String cmd = "" + ECarDefines.GETNUMBLOBS +  ECarDefines.SEP + channel;
		String answer = eCarClient.exec(cmd);
		int res = -1;
		try {
			res = Integer.parseInt(answer);
		}
		catch (Exception e) {
			System.out.println("Error executing command: " + cmd);
			disconnect();
		}
		return res;
	}

	public Blob getBlob(int channel, int blobNumber) {
		String cmd = "" + ECarDefines.GETBLOB + ECarDefines.SEP + channel + ECarDefines.SEP + blobNumber;
		String answer = eCarClient.exec(cmd);
		Blob res = new Blob(channel, blobNumber, -1, -1);
		if (answer == null) return res;
		try {
			StringTokenizer strtok = new StringTokenizer(answer, ECarDefines.SEP);
			strtok.nextToken();
			strtok.nextToken();
			String value = strtok.nextToken();
			int val = Integer.parseInt(value);
			res.setxCG(val);
			value = strtok.nextToken();
			val = Integer.parseInt(value);
			res.setyCG(val);
		}
		catch (Exception e) {
			System.out.println("Error executing command: " + cmd);
			disconnect();
		}
		return res;
	}

	public void panTilt(int pan, int tilt) {
		String cmd = "" + ECarDefines.PANTILT + ECarDefines.SEP + pan + ECarDefines.SEP + tilt;
		eCarClient.exec(cmd);
	}
	
}
