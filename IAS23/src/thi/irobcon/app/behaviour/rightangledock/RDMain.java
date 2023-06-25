package thi.irobcon.app.behaviour.rightangledock;

import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.saphira.SaphiraRobot;
import thi.irobcon.saphira.behaviour.BehConstTransVel;
import thi.irobcon.saphira.behaviour.BehLimFor;
import thi.irobcon.saphira.behaviour.BehTurn;
import thi.irobcon.saphira.lps.Pose;
import thi.irobcon.saphira.reactive.BehGroup;
import thi.irobcon.app.behaviour.rightangledock.BehSafeDock;

public class RDMain {

	public static void main(java.lang.String[] args) {

		SaphiraRobot robot = new SaphiraRobot(ECarDefines.DX4);
//		SaphiraRobot robot = new SaphiraRobot(ECarDefines.JSIM_DX3);

//		robot.addLaser();
		robot.setRobPose(new Pose(0,0,20));
		
		BehGroup dock = new BehGroup("Dock");
		BehGroup turn = new BehGroup("Turn");
		
		//BehGroup Dock
		BehConstTransVel cv = new BehConstTransVel("ConstTransVel", 300);
		BehLimFor lf = new BehLimFor("LimFor", 500, 800, 100);
  	    BehAlign al = new BehAlign("Align", 30, 5);
  	    BehStop st = new BehStop("Stop", 1000);
  	    BehSafeDock sd = new BehSafeDock("ObstDetect");
  	    
  	    //BehGroup Turn
  	    BehTurn bt = new BehTurn("Turn", 90);

		dock.add(lf, 80);
		dock.add(cv, 50);
		dock.add(al, 75);
		dock.add(st, 80);
//		dock.add(sd, 90);
		
		robot.add(dock);
		
		turn.add(bt, 90);
		robot.add(turn);
		
		robot.add(new RDStrategy(dock,turn));
		
		robot.run();
		
		
		
		
	}
}
