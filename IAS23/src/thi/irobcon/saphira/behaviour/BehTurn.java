package thi.irobcon.saphira.behaviour;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.lps.Pose;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehTurn extends Behaviour {

	protected final double MAX_ROTVEL = 30;
	protected final double DIFF_MAX_ROTVEL = 50;
	protected final double MIN_ROTVEL = 5;
	protected final double STOP_ROTVEL = 2;

	protected Pose startPose;
	protected double destAngle;
	boolean init;

	public BehTurn(String name, double destAngle) {
		super(name);
		this.destAngle = destAngle;
		init = true;
	}
	
	public void fire() {
		if (init) {
			startPose = robot.getPose();
			init = false;
		}
		
		Pose curPose = robot.getPose();
		double restAngle = 0;
		double rotVel = 0;
		if (destAngle >= 0) {
			restAngle = destAngle + curPose.diffAngle(startPose, true);
			// System.out.println("Restwinkel Linksdrehung " + restAngle);
			if (Math.abs(restAngle) > DIFF_MAX_ROTVEL) rotVel = MAX_ROTVEL;
			else rotVel = MAX_ROTVEL * restAngle / DIFF_MAX_ROTVEL;
			if (rotVel > STOP_ROTVEL && rotVel < MIN_ROTVEL) rotVel = MIN_ROTVEL;
			if (rotVel <= STOP_ROTVEL) {
				rotVel = 0;
				// System.out.println("Succeed turning");
				success();
			}
		}
		else {
			restAngle = destAngle + curPose.diffAngle(startPose, false);
			// System.out.println("Restwinkel Rechtsdrehung " + restAngle);
			if (-(Math.abs(restAngle)) < -DIFF_MAX_ROTVEL) rotVel = -MAX_ROTVEL;
			else rotVel = MAX_ROTVEL * restAngle / DIFF_MAX_ROTVEL;
			if (rotVel < -STOP_ROTVEL && rotVel > -MIN_ROTVEL) rotVel = -MIN_ROTVEL;
			if (rotVel >= -STOP_ROTVEL) {
				rotVel = 0;
				// System.out.println("Succeed turning");
				success();
			}
		}
//		System.out.println("RestAngle=" + restAngle + " RotVel=" + rotVel);
		addDesire(new DesRotVel(rotVel,1.0));
	}


	public void setDestAngle(double destAngle) {
		this.destAngle = destAngle;
	}

	
	public double getDestAngle() {
		return destAngle;
	}

	public void setInit(boolean init) {
		this.init = init;
	}
	
}

