package thi.irobcon.ecar.app;

import thi.irobcon.ecar.ECar;
import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.saphira.lps.Blob;

public class ECarAppMain {

	public static void main(String[] args) {

//		ECar ecar = new ECar(ECarDefines.JSIM_DX1);
		ECar ecar = new ECar(ECarDefines.DX4);
		
		ecar.panTilt(30, -20);
		ecar.wait(10);
		
		ecar.speed(200);
		ecar.rotate(10);
		ecar.wait(100);
		ecar.speed(0);
		ecar.rotate(0);
		
		ecar.panTilt(0, 20);
		ecar.move(500);
		ecar.panTilt(0, -20);
		ecar.turn(90);
		
	//	ecar.move(1000);

		int d3 = ecar.getSonarRange(3);
		int d4 = ecar.getSonarRange(4);
		System.out.println("Abstand nach vorne = " + d3 + " und " + d4);
		
		int[] laserRanges = ecar.getLaserRanges();
		System.out.print("Laser ranges:");
		for(int i=0; i<laserRanges.length; i++)
			System.out.print(" " + laserRanges[i]);
		System.out.println("\n");
		
		System.out.println("Blobs Channel 1:" + ecar.getNumBlobs(1));
		System.out.println("Blobs Channel 2:" + ecar.getNumBlobs(2));
		
		Blob b = ecar.getBlob(1, 0);
		System.out.println("Blobschwerpunkt: " + b.getXCG());
		
		ecar.disconnect();
		
	}

}
