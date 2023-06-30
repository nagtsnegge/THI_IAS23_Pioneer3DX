package thi.ams.behaviour.acc;

import thi.irobcon.saphira.proactive.Strategy;
import thi.irobcon.saphira.reactive.BehGroup;

public class ACCStrategy extends Strategy {

	protected BehGroup start, drive, change;
	
public ACCStrategy(BehGroup start, BehGroup drive, BehGroup change) {
	super();
	this.start = start;
	this.drive = drive;
	this.change = change;
	addOutput("Starting ...");
	start.activateExclusive();
}

public void plan() {
	if (start.isSuccess()) {
		addOutput("done\nDriving ...");
		drive.activateExclusive();
	}
	if (drive.isSuccess()) {
			addOutput("done\nChanging ...");
			change.activateExclusive();
	}
	if (change.isSuccess()) {
		addOutput("done\nDriving ...");
		drive.activateExclusive();
	}
}
}

