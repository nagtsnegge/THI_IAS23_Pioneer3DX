package thi.irobcon.app.behaviour.constantmotion;

import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.saphira.SaphiraRobot;
import thi.irobcon.saphira.behaviour.BehConstTransVel;
import thi.irobcon.saphira.reactive.BehGroup;

public class CMMain {

	public static void main(java.lang.String[] args) {

		// SaphiraRobot robot = new SaphiraRobot(ECarDefines.DX3);
		SaphiraRobot robot = new SaphiraRobot(ECarDefines.JSIM_DX3);

		BehGroup dock = new BehGroup("Dock");
		BehConstTransVel cv = new BehConstTransVel("ConstVel", 400);
		dock.add(cv, 50);
		robot.add(dock);
		
		robot.run();		  
	}
}
