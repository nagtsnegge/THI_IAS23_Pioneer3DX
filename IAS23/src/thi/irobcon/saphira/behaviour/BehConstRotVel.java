package thi.irobcon.saphira.behaviour;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehConstRotVel extends Behaviour {

	protected int vel;
	
public BehConstRotVel(String actionName, int vel) {
	super(actionName);
	this.vel = vel;
}

public void fire() {
	addDesire(new DesRotVel(vel,1.0));
}
}

