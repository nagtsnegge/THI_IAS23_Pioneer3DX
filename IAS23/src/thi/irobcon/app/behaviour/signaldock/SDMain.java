package thi.irobcon.app.behaviour.signaldock;

import thi.irobcon.app.behaviour.rightangledock.BehAlign;
import thi.irobcon.app.behaviour.rightangledock.BehStop;
import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.saphira.SaphiraRobot;
import thi.irobcon.saphira.behaviour.BehCamInit;
import thi.irobcon.saphira.behaviour.BehConstTransVel;
import thi.irobcon.saphira.behaviour.BehLimFor;
import thi.irobcon.saphira.reactive.BehGroup;

public class SDMain {

	public static void main(java.lang.String[] args) {

		// SaphiraRobot robot = new SaphiraRobot(ECarDefines.DX3);
		SaphiraRobot robot = new SaphiraRobot(ECarDefines.JSIM_DX3);

		// robot.addLaser();
//		robot.addHalcon("");
//		robot.addPTZCamWithACTS("/robotics/...actsconfig", "1");
				
		// start
		BehGroup start = new BehGroup("Start");
		BehCamInit ci = new BehCamInit("CamInit", 0, -10, 0);
		start.add(ci, 50);
		robot.add(start);

		// find
		BehGroup find = new BehGroup("Find");
		BehRotate cr = new BehRotate("Rotate", 20);
		BehFindSignal fs = new BehFindSignal("FindSignal", 1, 50);
		find.add(cr, 50);
		find.add(fs, 50);
		robot.add(find);
		
		// dock
		BehGroup dock = new BehGroup("Dock");
		BehConstTransVel cv = new BehConstTransVel("ConstVel", 400);
		BehLimFor lf = new BehLimFor("LimFor", 500, 2000, 100); 
  	    BehAlign al = new BehAlign("Align", 30, 5);
  	    BehStop st = new BehStop("Stop", 800);
		dock.add(lf, 80);
		dock.add(cv, 50);
		dock.add(al, 75);
		dock.add(st, 80);
		robot.add(dock);
		
		// strategy
		robot.add(new SDStrategy(start, find, dock));
		
		robot.run();		  
	}
}
