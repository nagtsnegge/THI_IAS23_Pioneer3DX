package thi.ams.behaviour.savedock;

import thi.irobcon.app.behaviour.rightangledock.BehAlign;
import thi.irobcon.app.behaviour.rightangledock.BehStop;
import thi.irobcon.app.behaviour.rightangledock.RDStrategy;
import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.saphira.SaphiraRobot;
import thi.irobcon.saphira.behaviour.BehConstTransVel;
import thi.irobcon.saphira.behaviour.BehLimFor;
import thi.irobcon.saphira.reactive.BehGroup;

public class SADMain {

	public static void main(java.lang.String[] args) {

		// SaphiraRobot robot = new SaphiraRobot(ECarDefines.DX3);
		SaphiraRobot robot = new SaphiraRobot(ECarDefines.JSIM_DX3);

//		robot.addLaser();
//		robot.addHalcon("");
		
		BehGroup dock = new BehGroup("Dock");
		BehConstTransVel cv = new BehConstTransVel("ConstTransVel", 200);
		BehLimFor lf = new BehLimFor("LimFor", 600, 2000, 100); 
  	    BehAlign al = new BehAlign("Align", 30, 5);
  	    BehStop st = new BehStop("Stop", 800);
  	    BehSave sa = new BehSave("Save", 800);

		dock.add(lf, 80);
		dock.add(cv, 50);
		dock.add(al, 75);
		dock.add(st, 80);
		dock.add(sa, 80);
		robot.add(dock);
		
		robot.add(new RDStrategy(dock));
		
		robot.run();		  
	}
}
