package thi.irobcon.saphira.desire;

import thi.irobcon.saphira.reactive.Desire;

public class DesRotVel extends Desire {
	
	public DesRotVel() {
		super(0,0);
	}
	
	public DesRotVel(double rotVel, double strength) {
		super(rotVel, strength);		
	}	
	
}