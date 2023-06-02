package thi.irobcon.umfeldmodell;

public class Messwert {

	protected int nummer;
	protected double distanz, winkel;
	
	public Messwert(int nummer, double distanz, double winkel) {
		super();
		this.nummer = nummer;
		this.distanz = distanz;
		this.winkel = winkel;
	}

	public int getNummer() {
		return nummer;
	}

	public double getDistanz() {
		return distanz;
	}

	public double getWinkel() {
		return winkel;
	}
	
}
