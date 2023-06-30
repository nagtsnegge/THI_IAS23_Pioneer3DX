package thi.irobcon.app.behaviour.rightangledock;

import thi.irobcon.saphira.desire.DesTransVel;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehCamStop extends Behaviour {

	protected int colorChannel;
	
public BehCamStop(String behName, int colorChannel) {
	super(behName);
	this.colorChannel = colorChannel;
}

public void fire() {
	  int dist = robot.getSonarRange(3);

//	  if ( dist <= stopDistance )
//	  {
//		  System.out.println( "Stop" );
//		  addDesire(new DesTransVel(0, 1.0));
//		  success();
//	  }
}
}

