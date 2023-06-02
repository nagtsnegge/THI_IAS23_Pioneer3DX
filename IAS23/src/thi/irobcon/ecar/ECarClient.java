package thi.irobcon.ecar;

import java.net.*;

import thi.irobcon.ecar.sim.*;

import java.io.*;

public class ECarClient {

	protected static final boolean MANSTART = false;
	
	protected String eCarName;
	protected boolean hasLaser, hasACTS;
	protected Socket sock;
	protected PrintWriter socketSenden; 
	protected BufferedReader socketEmpfangen;
	protected boolean connected;
	protected Process server;
	
	public ECarClient(String eCarName) {
		this.eCarName = eCarName;
		connected = false;
		hasLaser = false;
		hasACTS = ECarDefines.hasACTS(eCarName);
	}

	public void addLaser() {
		hasLaser = true;
	}
	
	public void addACTS() {
		hasACTS = true;
	}
	
	public void startServer() {
		try {
			if (ECarDefines.getServerName(eCarName).equals("localhost")) {
				if (eCarName.startsWith(ECarDefines.JSIM)) {
					new SimThread(eCarName);
				}
				else {
					if (!MANSTART) {
						hasACTS  = false;
						String cmd = "cmd /c " + "start /MIN c:\\THIProg\\eCar\\bin\\ECServer.exe " + eCarName + " " + hasLaser + " " + hasACTS + 
								" localhost " + ECarDefines.JPTZ_SERVER_PORT + ECarDefines.SEP + ECarDefines.MIN_BLOBSIZE;
//						String cmd = "cmd /c " + "start /MIN c:\\THIProg\\MobileSimServer\\ECServer.exe " + eCarName + " " + hasLaser + " " + hasACTS + 
//								" localhost " + ECarDefines.JPTZ_SERVER_PORT + ECarDefines.SEP + ECarDefines.MIN_BLOBSIZE;
						server = Runtime.getRuntime().exec(cmd);
					}
				}
			}
			else {
				System.out.println("Connection to server control on host " + ECarDefines.getServerName(eCarName) + " at port " + ECarDefines.SERVER_CONTROL_PORT);
				sock = new Socket (ECarDefines.getServerName(eCarName), ECarDefines.SERVER_CONTROL_PORT);
			 	socketSenden = new PrintWriter( sock.getOutputStream(), true);
				socketEmpfangen = new BufferedReader ( new java.io.InputStreamReader ( sock.getInputStream ()));
				connected = true;
				String cmd = eCarName + ECarDefines.SEP;
				if (hasLaser) cmd += true + ECarDefines.SEP;
				else cmd += false + ECarDefines.SEP;
				if (hasACTS) cmd += true;
				else cmd += false;
				cmd += ECarDefines.SEP + ECarDefines.ACTS + eCarName;
				cmd += ECarDefines.SEP + ECarDefines.JPTZ_SERVER_PORT;
				cmd += ECarDefines.SEP + ECarDefines.MIN_BLOBSIZE;
				socketSenden.println(cmd);
			}
				
		} 
		catch (Exception e) {
			connected = false;
			System.out.println("Error starting server for " + eCarName + ":" + ECarDefines.getPort(eCarName));
		}
	}
	
	public boolean connect() {
		try {
			disconnect();
			System.out.println("Connection to robot " + ECarDefines.getServerName(eCarName) + " at port " + ECarDefines.getPort(eCarName));
			sock = new Socket (ECarDefines.getServerName(eCarName), ECarDefines.getPort(eCarName));
//			sock = new Socket ("192.168.178.27", 9221);
		    socketSenden = new PrintWriter( sock.getOutputStream(), true);
		    socketEmpfangen = new BufferedReader ( new InputStreamReader ( sock.getInputStream ()));			    
			connected = true;
		}
		catch (Exception e) {
			connected = false;
			System.out.println("Error connecting eCar " + eCarName + ":" + ECarDefines.getPort(eCarName));
		}
		return connected;
	}
	
	public void disconnect() {
		if (connected) {
			try {
				sock.close();
			}
			catch (Exception ign) {}
			connected = false;
		}
	}
	
	public synchronized String exec(String cmd) {
//		System.out.println("Execute cmd: " + cmd);
		if (connected) {
			try {
				socketSenden.println(cmd);
				String res = "";
				do {
					res = socketEmpfangen.readLine();
				}
				while (res.length() == 0);
				return res;
			}
			catch (Exception e) {
				System.out.println("Error executing command: " + cmd + " on: " + e);
				disconnect();
				connected = false;
				return null;
			}
		}
		else {
			System.out.println("Error executing command: " + cmd);
			return null;
		}
	}

	public String getECarName() {
		return eCarName;
	}

	public boolean isConnected() {
		return connected;
	}
	
	
}
