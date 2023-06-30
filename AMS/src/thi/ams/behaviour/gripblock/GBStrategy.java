package thi.ams.behaviour.gripblock;

import thi.irobcon.saphira.proactive.Strategy;
import thi.irobcon.saphira.reactive.BehGroup;

public class GBStrategy extends Strategy {

	protected BehGroup start, dock, grip;
	
	public GBStrategy(BehGroup start, BehGroup dock, BehGroup grip) {
		super();
		this.start = start;
		this.dock = dock;
		this.grip = grip;
		addOutput("Try to start ...");
		start.activateExclusive();
	}

	public void plan() {
		if (start.isSuccess()) {
			addOutput("done\nTry to dock ...");
			dock.activateExclusive();
		}
		if (dock.isSuccess()) {
			addOutput("done\nTry to grip ...");
			grip.activateExclusive();
		}
		if (grip.isSuccess()) {
			addOutput("done\nSucceed ... stop");
			stopRunning();
		}
	}
}

