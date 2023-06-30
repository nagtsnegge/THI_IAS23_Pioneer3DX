package thi.ams.behaviour.gripblock;

import thi.irobcon.saphira.desire.DesGrip;
import thi.irobcon.saphira.desire.DesLift;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehGripInit extends Behaviour {

	protected boolean init;	
	
public BehGripInit(String actionName) {
	super(actionName);
	init = true;
}

public void fire() {

	if (init) {
		addDesire(new DesGrip(false, 1.0));
		addDesire(new DesLift(false, 1.0));
		init = false;
	}
}
}

