package thi.ams.behaviour.park;

import thi.irobcon.saphira.proactive.Strategy;
import thi.irobcon.saphira.reactive.BehGroup;

public class PAStrategy extends Strategy {

	protected BehGroup start, drive, turn, dock;
	
	public PAStrategy(BehGroup start, BehGroup drive, BehGroup turn, BehGroup dock) {
		super();
		this.start = start;
		this.drive = drive;
		this.turn = turn;
		this.dock = dock;
		addOutput("Try to start ...");
		start.activateExclusive();
	}

	public void plan() {
		if (start.isSuccess()) {
			addOutput("done\nTry to drive ...");
			drive.activateExclusive();
		}
		if (drive.isSuccess()) {
			addOutput("done\nTry to turn ...");
			turn.activateExclusive();
		}
		if (turn.isSuccess()) {
			addOutput("done\nTry to dock ...");
			dock.activateExclusive();
		}
		if (dock.isSuccess()) {
			addOutput("done\nSucceed ... stop");
			stopRunning();
		}
	}
}

