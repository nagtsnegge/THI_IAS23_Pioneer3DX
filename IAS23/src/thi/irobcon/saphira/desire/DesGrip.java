package thi.irobcon.saphira.desire;

public class DesGrip extends DesManipulator {
	
	public DesGrip(boolean open, double strength) {
		super(0, strength);
		
		if (open) value = 1;
		else value = 0;
	}		
	
	public boolean isOpen() {
		return value == 1;
	}
}