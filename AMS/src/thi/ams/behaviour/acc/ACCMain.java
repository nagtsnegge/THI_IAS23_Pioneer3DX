package thi.ams.behaviour.acc;

import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.saphira.SaphiraRobot;
import thi.irobcon.saphira.behaviour.BehCamInit;
import thi.irobcon.saphira.behaviour.BehConstTransVel;
import thi.irobcon.saphira.behaviour.BehLimFor;
import thi.irobcon.saphira.reactive.BehGroup;


public class ACCMain {

	public static void main(java.lang.String[] args) {

		// SaphiraRobot robot = new SaphiraRobot(ECarDefines.DX3);
		SaphiraRobot robot = new SaphiraRobot(ECarDefines.JSIM_DX3);
//		robot.addHalcon("");
//		robot.addPTZCamWithACTS("/robotics/...actsconfig", "1");
		
		// start
		BehGroup start = new BehGroup("Start");
		BehCamInit ci = new BehCamInit("CamInit", 0, 0, -10);
		start.add(ci, 50);
		robot.add(start);

	  
		// drive
		BehGroup drive = new BehGroup("Drive");
		BehConstTransVel cv = new BehConstTransVel("ConstVel", 10);
		BehLimFor lf = new BehLimFor("LimFor", 600, 1000, 100); 
		BehLaneDepartureControl ldc = new BehLaneDepartureControl( "LaneDepartureControl", 1);
		BehLaneChangeAssist lca = new BehLaneChangeAssist( "LaneChangeAssist");
		drive.add(cv, 60);
		drive.add(lf, 80);
		drive.add(ldc, 80);
		drive.add(lca, 60);
		robot.add(drive);
		
		// change
		BehGroup change = new BehGroup("Change");
		BehLaneChangeControl lcc = new BehLaneChangeControl( "LaneChangeControl", 1);
		change.add(cv, 60);
		change.add(lf, 80);
		change.add(lcc, 80);
		robot.add(change);
		
		// strategy
		robot.add(new ACCStrategy(start, drive, change));
		
		robot.run();		  
		  
		return;
	}
}
