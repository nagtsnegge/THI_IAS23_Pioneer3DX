package thi.ams.behaviour.gripblock;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.lps.Blob;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehCenterObject extends Behaviour {

	protected int channel;

public BehCenterObject(String actionName, int channel) {
	super(actionName);
	this.channel = channel;
}

public void fire() {
	  Blob blob;

	  if( robot.getNumBlobs(channel) > 0 ) 
	  {
		  blob = robot.getBlob(channel, 1);
		  int x = blob.getXCG() - 80; // Bildgroesse 160 x 120			
		  addOutput("x = " +  x + "\n");
		  
		  if( x < -10 )
		  {
			  
			  addOutput("turning left\n");
			  addDesire(new DesRotVel(5, 0.8));
		  }
		  else if ( x > 10 )
		  {
			  addOutput("turning right\n");
			  addDesire(new DesRotVel(-5, 0.8));
		  }
	  }
}
}

