package thi.irobcon.app.behaviour.signaldock;

import thi.irobcon.saphira.proactive.Strategy;
import thi.irobcon.saphira.reactive.BehGroup;

public class SDStrategy extends Strategy {

	protected BehGroup start, find, dock;
	
	public SDStrategy(BehGroup start, BehGroup find, BehGroup dock) {
		super();
		this.start = start;
		this.find = find;
		this.dock = dock;
		addOutput("Try to start ...");
		start.activateExclusive();
	}

	public void plan() {
		if (start.isSuccess()) {
			addOutput("done\nTry to find ...");
			find.activateExclusive();
		}
		if (find.isError()) {
			addOutput("done\nNot found ... stop");
			stopRunning();
		}
		if (find.isSuccess()) {
			addOutput("done\nTry to dock ...");
			dock.activateExclusive();
		}
		if (dock.isSuccess()) {
			addOutput("done\nSucceed ... stop");
			stopRunning();
		}
	}
}

