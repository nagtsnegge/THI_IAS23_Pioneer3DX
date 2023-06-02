package thi.irobcon.app.behaviour.rightangledock;

import thi.irobcon.saphira.proactive.Strategy;
import thi.irobcon.saphira.reactive.BehGroup;

public class RDStrategy extends Strategy {

	protected BehGroup dock;
	
	public RDStrategy(BehGroup dock) {
		super();
		this.dock = dock;
		addOutput("Try to dock ...");
		dock.activateExclusive();
	}

	public void plan() {
		if (dock.isSuccess()) {
			addOutput("done\nSucceed ... stop");
			stopRunning();
		}
	}
}

