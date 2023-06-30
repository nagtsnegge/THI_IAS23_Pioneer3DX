package thi.ams.behaviour.edgedock;

import thi.irobcon.saphira.proactive.Strategy;
import thi.irobcon.saphira.reactive.BehGroup;

public class EDStrategy extends Strategy {

	protected BehGroup dock, turn;
	protected boolean hasTurned;
	
	public EDStrategy(BehGroup dock, BehGroup turn) {
		super();
		this.dock = dock;
		this.turn = turn;
		hasTurned = false;
		addOutput("Try to dock ...");
		dock.activateExclusive();
	}

	public void plan() {
		if (!hasTurned && dock.isSuccess()) {
			addOutput("done\nTry to turn ...");
			turn.activateExclusive();
		}
		else if (turn.isSuccess()) {
			addOutput("done\nTry to dock ...");
			hasTurned = true;
			dock.activateExclusive();
		}
		else if (hasTurned && dock.isSuccess()) {
			addOutput("done\nSucceed ... stop");
			stopRunning();
		}
	}
}

