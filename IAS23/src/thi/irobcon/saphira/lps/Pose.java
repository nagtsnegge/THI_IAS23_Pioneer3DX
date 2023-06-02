package thi.irobcon.saphira.lps;

public class Pose {

	protected double x,y,th;

public Pose() {
		this.x = 0;
		this.y = 0;
		this.th = 0;
	}

public Pose(double[] position) {
	x = position[0];
	y = position[1];
	th = position[2];
}

public Pose(double x, double y, double th) {
	this.x = x;
	this.y = y;
	this.th = th;
}

public Pose(int x, int y, int th) {
	this.x = x;
	this.y = y;
	this.th = th;
}

public Pose(double d, double th) {
	this.x = d * Math.cos(Math.toRadians(th));
	this.y = d * Math.sin(Math.toRadians(th));
	this.th = th;
}

public void setTo(Pose newPose) {
	this.x = newPose.getX();
	this.y = newPose.getY();
	this.th = newPose.getTh();
}

public double getTh() {
	return th;
}


public void setTh(double th) {
	this.th = th;
}

public double getX() {
	return x;
}

public void setX(double x) {
	this.x = x;
}

public double getY() {
	return y;
}

public void setY(double y) {
	this.y = y;
}

public double findDistanceTo(Pose pos)
{
	double res = Math.sqrt( Math.pow(pos.getX() - getX(), 2) + Math.pow(pos.getY() - getY(),2));
	return res;
} 

public double findAngleTo(Pose pos)
{
	double res = 0;
	double dy = pos.getY() - getY();
	double dx = pos.getX() - getX();
	
	if (dx >= 0) {
		if (dy >= 0) res = Math.toDegrees(Math.atan( dy / dx));
		else res = Math.toDegrees(Math.atan( dy / dx));
	} else {
		if (dy >= 0) res = Math.toDegrees(Math.atan( dy / dx)) + 180;
		else res = Math.toDegrees(Math.atan( dy / dx)) - 180;
	}
	return res;
	
} 

public double diffAngle(Pose endPose, boolean clockwise)
{
	double res;

	if(clockwise)
	{
		if(getTh() >= endPose.getTh()) res = endPose.getTh() - getTh();
		else res = endPose.getTh() - 360 - getTh();
	}
	else
	{
		if(getTh() <= endPose.getTh()) res = endPose.getTh() - getTh();
		else res = endPose.getTh() + 360 - getTh();
	}
	return res;
} 

public double diffAngle(Pose endPose)
{
	double startAngle = getTh() + 180;
	double endAngle = endPose.getTh() + 180;
	double res = endAngle - startAngle;
//	if (res > 360) res -= 360;
//	if (res < -360) res += 360;
	return res;
} 


}