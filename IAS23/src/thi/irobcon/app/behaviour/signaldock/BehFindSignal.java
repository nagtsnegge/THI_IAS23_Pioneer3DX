package thi.irobcon.app.behaviour.signaldock;

import thi.irobcon.saphira.lps.Blob;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehFindSignal extends Behaviour {

	protected int channel, timeout;
	
public BehFindSignal(String actionName, int channel, int timeout) {
	super(actionName);
	this.channel = channel;
	this.timeout = timeout;
}

public void fire() {
	if (timeout == 0) error();
	else timeout--;

	int numBlobs = robot.getNumBlobs(channel);
	System.out.println("NumBlobs: " + numBlobs);

	if (timeout == 10) success();
	
	if ( numBlobs > 0 ) {
		Blob blob = robot.getBlob(channel, 0);
		if (blob.getXCG() > 70 && blob.getXCG() < 90) 
			success();
	}
}
}

