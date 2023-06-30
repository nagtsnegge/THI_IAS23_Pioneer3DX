package thi.ams.behaviour.savedock;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.desire.DesTransVel;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehSave extends Behaviour {

	protected int stopDistance;
	
public BehSave(String behName, int stopDistance) {
	super(behName);
	this.stopDistance = stopDistance;
}

public void fire() {
	  int leftDist = (int)robot.checkPolar(80, 180, null) - robot.getRadius();
	  int rightDist = (int)robot.checkPolar(-180, -80, null) - robot.getRadius();

	  if (leftDist <= stopDistance || rightDist <= stopDistance) {
		  addOutput("SaveStop");
		  addDesire(new DesTransVel(0, 1.0));
		  addDesire(new DesRotVel(0, 1.0));
	  }
}
}

