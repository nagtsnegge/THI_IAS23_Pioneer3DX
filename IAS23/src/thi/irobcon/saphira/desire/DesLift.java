package thi.irobcon.saphira.desire;

public class DesLift extends DesManipulator {
	
	public DesLift(boolean up, double strength) {
		super(0, strength);
		
		if (up) value = 1;
		else value = 0;
	}
	
	public boolean isUp() {
		return value == 1;
	}

}