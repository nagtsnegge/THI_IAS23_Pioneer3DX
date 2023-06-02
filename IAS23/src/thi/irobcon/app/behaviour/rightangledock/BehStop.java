package thi.irobcon.app.behaviour.rightangledock;

import thi.irobcon.saphira.reactive.Behaviour;

public class BehStop extends Behaviour {

	protected int stopDistance;
	
public BehStop(String behName, int stopDistance) {
	super(behName);
	this.stopDistance = stopDistance;
}

public void fire() {
	  int dist = robot.getSonarRange(3);

	  if ( dist <= stopDistance )
	  {
		  System.out.println( "Stop" );
		  success();
	  }
}
}

