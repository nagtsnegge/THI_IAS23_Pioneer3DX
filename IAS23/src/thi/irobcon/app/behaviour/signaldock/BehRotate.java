package thi.irobcon.app.behaviour.signaldock;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehRotate extends Behaviour {

	protected int rotVel;
	
public BehRotate(String actionName, int rotVel) {
	super(actionName);
	this.rotVel = rotVel;
}

public void fire() {
	addDesire(new DesRotVel(rotVel, 1.0));
}
}

