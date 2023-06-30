package thi.ams.behaviour.park;

import thi.irobcon.app.behaviour.rightangledock.BehAlign;
import thi.irobcon.app.behaviour.rightangledock.BehStop;
import thi.irobcon.app.behaviour.signaldock.BehFindSignal;
import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.saphira.SaphiraRobot;
import thi.irobcon.saphira.behaviour.BehCamInit;
import thi.irobcon.saphira.behaviour.BehConstTransVel;
import thi.irobcon.saphira.behaviour.BehLimFor;
import thi.irobcon.saphira.behaviour.BehTurn;
import thi.irobcon.saphira.lps.Pose;
import thi.irobcon.saphira.reactive.BehGroup;

public class PAMain {

	public static void main(java.lang.String[] args) {

		// SaphiraRobot robot = new SaphiraRobot(ECarDefines.DX3);
		SaphiraRobot robot = new SaphiraRobot(ECarDefines.JSIM_DX3);
		robot.setRobPose(new Pose(-2000, -2500, 15));
		
//		robot.addLaser();
//		robot.addHalcon("");
//		robot.addPTZCamWithACTS("/robotics/...actsconfig", "1");
		
		// start
		BehGroup start = new BehGroup("Start");
		BehCamInit ci = new BehCamInit("CamInit", -90, -10, 0);
		start.add(ci, 50);
		robot.add(start);

		BehGroup drive = new BehGroup("Drive");
		BehConstTransVel cv = new BehConstTransVel("ConstTransVel", 200);
		BehFindSignal fs = new BehFindSignal("FindSignal", 1, 50);
		BehParallel pa = new BehParallel("Parallel", 1000, 50, 10);
		drive.add(cv, 50);
//		drive.add(fs, 80);
		drive.add(pa, 50);
		robot.add(drive);
		
		BehGroup turn = new BehGroup("Turn");
		BehTurn tu = new BehTurn("Turn parallel", -90);
		turn.add(tu, 80);
		robot.add(turn);
		
		BehGroup dock = new BehGroup("Dock");
		BehLimFor lf = new BehLimFor("LimFor", 500, 2000, 100); 
  	    BehAlign al = new BehAlign("Align", 30, 5);
  	    BehStop st = new BehStop("Stop", 800);
		dock.add(lf, 80);
		dock.add(cv, 50);
		dock.add(al, 75);
		dock.add(st, 80);
		robot.add(dock);

		robot.add(new PAStrategy(start, drive, turn, dock));
		
		robot.run();		  
	}
}
