package thi.ams.behaviour.gripblock;

import thi.irobcon.saphira.desire.DesGrip;
import thi.irobcon.saphira.desire.DesLift;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehGrip extends Behaviour {

	public static final int CLOSE = 1;
	public static final int WAIT_CLOSE = 2;
	public static final int UP = 3;
	public static final int WAIT_UP = 4;

	public static final int WAIT_TIME = 20;
	
	protected int gripState, timer;	
	
public BehGrip(String actionName) {
	super(actionName);
	gripState = CLOSE;
}

public void fire() {
	
	switch( gripState )
	{
		case CLOSE:	
			addDesire(new DesGrip(true, 1.0));
			timer = WAIT_TIME;
			gripState = WAIT_CLOSE;
			break;

		case WAIT_CLOSE:	
			if( timer == 0 ) 
				gripState = UP;
			else timer--; 
			break;

		case UP:	
			addDesire(new DesLift(true, 1.0));
			timer = WAIT_TIME;
			gripState = WAIT_UP;
			break;
	
		case WAIT_UP:	
			if( timer == 0 ) success();
			else timer--; 
			break;
	}
}
}

