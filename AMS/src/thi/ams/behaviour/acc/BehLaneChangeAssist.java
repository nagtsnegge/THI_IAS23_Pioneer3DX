package thi.ams.behaviour.acc;

import thi.irobcon.saphira.reactive.Behaviour;

public class BehLaneChangeAssist extends Behaviour {

	protected boolean rightLane, passObst;
	
public BehLaneChangeAssist(String actionName) {
	super(actionName);
	rightLane = true;
	passObst = false;
}

public void fire() {
	int obstFront, obstSide;

	if ( rightLane )
	{
		obstFront = (int) robot.checkBox(2800, 300, 300, -300, null );
		obstSide = (int) robot.checkBox( 300, 1100, -2000, 300, null );

		// if ( myRobot.getVel() < 200 && obstFront <= 2800 && obstSide > 2000 ) 
		if ( obstFront <= 2800 && obstSide > 2000 ) 
		{
			success();
			rightLane = false;
			passObst = false;
		}
	}
	else
	{
		obstSide = (int)robot.checkBox( 1000, -1100, -2800, -300, null );

		if ( passObst && obstSide > 1100 ) 
		{
			success();
			rightLane = true;
		}
		else if ( !passObst && obstSide < 1100 )
		{
			passObst = true;
		}
	}
}
}

