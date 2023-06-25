package thi.irobcon.app.behaviour.rightangledock;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.desire.DesTransVel;
import thi.irobcon.saphira.lps.Pose;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehSafeDock extends Behaviour {

	protected int tolerance;
	
	public BehSafeDock(String behName) {
		super(behName);
	}
	
	public void fire() {
		  int leftObst = 0; int rightObst = 0;
	 
		  
		  leftObst = robot.getSonarRange(0);
		  rightObst = robot.getSonarRange(7);
		  
		  if(leftObst < 1500 || rightObst < 1500){
			  addDesire(new DesTransVel(0, 0.7));
			  addDesire(new DesRotVel(0, 1.0));
			  System.out.println("SafeDock -- Stop!!!");
		  }
		  
		  // Kurskorrektur
		  System.out.println("Left = " + leftObst + " right = " + rightObst);
	
	}
}

