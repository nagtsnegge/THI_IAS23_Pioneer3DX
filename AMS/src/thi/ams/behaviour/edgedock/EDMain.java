package thi.ams.behaviour.edgedock;

import thi.irobcon.app.behaviour.rightangledock.BehAlign;
import thi.irobcon.app.behaviour.rightangledock.BehStop;
import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.saphira.SaphiraRobot;
import thi.irobcon.saphira.behaviour.BehConstTransVel;
import thi.irobcon.saphira.behaviour.BehLimFor;
import thi.irobcon.saphira.behaviour.BehTurn;
import thi.irobcon.saphira.reactive.BehGroup;

public class EDMain {

	public static void main(java.lang.String[] args) {

		// SaphiraRobot robot = new SaphiraRobot(ECarDefines.DX3);
		SaphiraRobot robot = new SaphiraRobot(ECarDefines.JSIM_DX3);

//		robot.addLaser();
//		robot.addHalcon("");
		
		BehGroup dock = new BehGroup("Dock");
		BehConstTransVel cv = new BehConstTransVel("ConstTransVel", 200);
		BehLimFor lf = new BehLimFor("LimFor", 400, 2000, 100); 
  	    BehAlign al = new BehAlign("Align", 30, 5);
  	    BehStop st = new BehStop("Stop", 800);
		dock.add(lf, 80);
		dock.add(cv, 50);
		dock.add(al, 75);
		dock.add(st, 80);
		robot.add(dock);

		BehGroup turn = new BehGroup("Turn");
		BehTurn tu = new BehTurn("Turn parallel", 90);
		turn.add(tu, 80);
		robot.add(turn);
		
		robot.add(new EDStrategy(dock, turn));
		
		robot.run();		  
	}
}
