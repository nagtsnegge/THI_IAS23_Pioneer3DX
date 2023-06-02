package thi.irobcon.app.behaviour.dock;

import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.saphira.SaphiraRobot;
import thi.irobcon.saphira.behaviour.BehConstTransVel;
import thi.irobcon.saphira.behaviour.BehLimFor;
import thi.irobcon.saphira.reactive.BehGroup;

public class DOMain {

	public static void main(java.lang.String[] args) {

		// SaphiraRobot robot = new SaphiraRobot(ECarDefines.DX3);
		SaphiraRobot robot = new SaphiraRobot(ECarDefines.JSIM_DX3);
		//robot.addLaser();
		
		BehGroup dock = new BehGroup("Dock");
		BehConstTransVel cv = new BehConstTransVel("ConstTransVel", 200);
		BehLimFor lf = new BehLimFor("LimFor", 1000, 2000, 100); 
		dock.add(lf, 80);
		dock.add(cv, 50);
		robot.add(dock);
		
		robot.run();		  
	}
}
