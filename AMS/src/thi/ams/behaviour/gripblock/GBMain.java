package thi.ams.behaviour.gripblock;

import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.saphira.SaphiraRobot;
import thi.irobcon.saphira.behaviour.BehCamInit;
import thi.irobcon.saphira.behaviour.BehConstTransVel;
import thi.irobcon.saphira.behaviour.BehLimFor;
import thi.irobcon.saphira.reactive.BehGroup;

public class GBMain {

	public static void main(java.lang.String[] args) {

		// SaphiraRobot robot = new SaphiraRobot(ECarDefines.DX3);
		SaphiraRobot robot = new SaphiraRobot(ECarDefines.JSIM_DX3);

//		robot.addHalcon("");
//		robot.addPTZCamWithACTS("/robotics/...actsconfig", "1");
		
		// start
		BehGroup start = new BehGroup("Start");
		BehCamInit ci = new BehCamInit("CamInit", 0, 0, -10);
		BehGripInit gi = new BehGripInit("GripInit"); 
		start.add(ci, 50);
		start.add(gi, 50);
		robot.add(start);

		// dock
		BehGroup dock = new BehGroup("Dock");
		BehConstTransVel cv = new BehConstTransVel("ConstVel", 10);
		BehLimFor lf = new BehLimFor("LimFor", 600, 1000, 100); 
		BehCenterObject co = new BehCenterObject("CenterObject", 1);
		BehStopForwards sf = new BehStopForwards("StopForwards", 20 );
		dock.add(cv, 50);
		dock.add(lf, 80);
		dock.add(co, 50);
		dock.add(sf, 90);
		robot.add(dock);
		
		// grip
		BehGroup grip = new BehGroup("Grip");
		BehConstTransVel gv = new BehConstTransVel("GripVel", 10);
		BehGrip gr = new BehGrip("Grip"); 
		grip.add(gv, 50);
		grip.add(gr, 50);
		robot.add(grip);
		
		// strategy
		robot.add(new GBStrategy(start, dock, grip));
		
		robot.run();		  
	}
}
