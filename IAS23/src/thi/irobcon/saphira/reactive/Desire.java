package thi.irobcon.saphira.reactive;

public class Desire {
	
	protected double strength;
	protected double value;
	protected int priority;
	
	public Desire(double value, double strength) {
		super();
		this.value = value;
		this.strength = strength;
	}	
	
	public Desire() {
		super();
	}

	public double getStrength() {
		return strength;
	}

	public double getValue() {
		return value;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}


}