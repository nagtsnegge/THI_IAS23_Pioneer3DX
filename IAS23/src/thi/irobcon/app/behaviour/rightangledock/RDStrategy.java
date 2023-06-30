package thi.irobcon.app.behaviour.rightangledock;

import thi.irobcon.saphira.proactive.Strategy;
import thi.irobcon.saphira.reactive.BehGroup;

public class RDStrategy extends Strategy {

	protected BehGroup dock, turn, parDrive;
	private boolean turned;
	
	public RDStrategy(BehGroup dock, BehGroup turn, BehGroup parDrive) {
		super();
		this.dock = dock;
		this.turn = turn;
		addOutput("Try to dock ...");
		turned = false;
		parDrive.activateExclusive();
	}

	public void plan() {
//		if (dock.isSuccess()) {
//			//Found wall one!
//			addOutput("done\nSucceed ... stop");
//			//stopRunning();
//			if (turned){
//				stopRunning();
//			}
//			dock.deactivate();
//			addOutput("Turning....");
//			turn.activateExclusive();
//		}
//		if(turn.isSuccess() && !turned){
//			addOutput("done\nTurned Successfully");
//			turned = true;
//			turn.deactivate();
//			dock.activateExclusive();
//		}
//		/*if(turned == true && dock.isSuccess()){
//			addOutput("Docked and Turned Sucessfully");
//			stopRunning();
//		}*/
		
		if(parDrive.isSuccess()) stopRunning();
		
	
		
	}
}

