package thi.irobcon.saphira.behaviour;

import thi.irobcon.saphira.desire.DesCamPan;
import thi.irobcon.saphira.desire.DesCamTilt;
import thi.irobcon.saphira.desire.DesCamZoom;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehCamInit extends Behaviour {

	private int timer, pan, tilt, zoom;
	private static final int CYCLESWAIT = 15;
	
public BehCamInit(String actionName, int pan, int tilt, int zoom) {
	super(actionName);
	this.pan = pan;
	this.tilt = tilt;	
	this.zoom = zoom;
	timer = CYCLESWAIT;
}

public void fire() {
	if (timer == CYCLESWAIT) {
		addDesire(new DesCamPan(pan,1.0));
		addDesire(new DesCamTilt(tilt,1.0));
		addDesire(new DesCamZoom(zoom,1.0));
		timer--;
	}
	else if (timer == 0) 
		success();
	else timer--;
}
}

