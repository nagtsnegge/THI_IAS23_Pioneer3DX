package thi.ams.behaviour.gripblock;

import thi.irobcon.saphira.desire.DesTransVel;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehStopForwards extends Behaviour {

	protected int stopDist;
	protected boolean isStopped;	
	
public BehStopForwards(String actionName, int stopDist) {
	super(actionName);
	this.stopDist = stopDist;
	isStopped = false;
}

public void fire() {
	if( isStopped )
	{
		success();
	}
	else if (  robot.checkBox(2500, 300, 300, -300, null) - 
		       robot.getRadius() <= stopDist )
	{
		addDesire(new DesTransVel(0, 1.0));
		isStopped = true;
	}
}
}

