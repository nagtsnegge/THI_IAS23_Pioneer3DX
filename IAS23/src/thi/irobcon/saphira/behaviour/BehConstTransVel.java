package thi.irobcon.saphira.behaviour;

import thi.irobcon.saphira.desire.DesTransVel;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehConstTransVel extends Behaviour {

	protected int vel;
	
public BehConstTransVel(String actionName, int vel) {
	super(actionName);
	this.vel = vel;
}

public void fire() {
	//System.out.println("Fire BehConstTransVel");
	//robot.getDialog().addOutput("Fire BehConstTransVel");
	addDesire(new DesTransVel(vel,0.2));
}
}

