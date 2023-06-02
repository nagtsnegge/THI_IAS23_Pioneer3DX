package thi.irobcon.saphira;

import java.util.Vector;

import thi.irobcon.ecar.ECar;
import thi.irobcon.saphira.lps.Blob;
import thi.irobcon.saphira.lps.Pose;
import thi.irobcon.saphira.proactive.DefaultStrategy;
import thi.irobcon.saphira.proactive.Strategy;
import thi.irobcon.saphira.reactive.BehGroup;
import thi.irobcon.saphira.reactive.PriorityResolver;

public class SaphiraRobot {

	protected static final int RADIUS = 250;
	protected static final int SONAR_MAXRANGE = 3000;
	
	protected String robotName;
	protected String worldName;

	protected PriorityResolver resolver;
	protected Strategy strategy;
	
	protected ECar ecar;
	protected Pose pose;

	public SaphiraRobot(String robotName) {
		this(robotName, null);
	}

	public SaphiraRobot(String robotName, String worldName) {
		this.robotName = robotName;
		resolver = new PriorityResolver();
		pose = new Pose();
	}

	public void add(BehGroup behGroup) {
		behGroup.setRobot(this);
		behGroup.setResolver(resolver);
		resolver.add(behGroup);
	}

	public void add(Strategy strategy) {
		this.strategy = strategy;
		this.strategy.setRobot(this);
	}

	public void run() {
		try {
			if (resolver.getBehaviourGroups().size() == 0) {
				addOutput("No behaviours specified");
				return;
			}
				
			if (strategy == null)
				add(new DefaultStrategy(resolver.getBehaviourGroups().firstElement()));

			if (connect()) {
				System.out.println("Robot running resolver cycle");
				resolver.start();
				strategy.start();
				strategy.join();
			} else
				addOutput("Connection error\n");
		} catch (InterruptedException ign) {
		}
	}

	public boolean connect() {
		addOutput("Connecting AGV ... ");
		ecar = new ECar(robotName);
		ecar.setRobPose((int)pose.getX(), (int)pose.getY(), (int)pose.getTh());
		resolver.setEcar(ecar);
		addOutputLn("done");

		return true;
	}

	public boolean isRunning() {
		return resolver.isAlive();
	}

	public int getRadius() {
		return RADIUS;
	}

	public Pose getPose() {
		pose = new Pose(ecar.getPosition());
		return pose;
	}

	public double checkPolar(double startAngle, double endAngle, Pose pos) {
		int minDistance = SONAR_MAXRANGE;
		Pose minObstaclePose = null;
		double s1, e1, s2, e2;
		if (startAngle <= endAngle) {
			s1 = startAngle;
			e1 = endAngle;
			s2 = 0;
			e2 = -1;
		} else {
			s1 = startAngle;
			e1 = 180;
			s2 = -180;
			e2 = endAngle;
		}
		int[] ranges = ecar.getSonarRanges();
		for (int i = 0; i <= ranges.length; i++) {
			int angle = ecar.getSonarAngle(i);
			if ((angle >= s1 && angle <= e1) || (angle >= s2 && angle <= e2)) {
				int distance = getSonarRange(i);
				Pose obstaclePose = new Pose(distance, angle);
				// System.out.println("Obstacle found: " + obstaclePose.getX() + "," +
				// obstaclePose.getY());
				if (distance < minDistance) {
					minObstaclePose = obstaclePose;
					minDistance = distance;
				}
			}
		}

		if (minObstaclePose != null) {
			if (pos != null)
				pos.setTo(minObstaclePose);
			return minDistance;
		} else
			return SONAR_MAXRANGE;
	}

	public double checkBox(double x1, double y1, double x2, double y2, Pose pos) {

		double sx1, sy1, sx2, sy2;
		if (x1 < x2) {
			sx1 = x1;
			sx2 = x2;
		} else {
			sx1 = x2;
			sx2 = x1;
		}
		if (y1 < y2) {
			sy1 = y1;
			sy2 = y2;
		} else {
			sy1 = y2;
			sy2 = y1;
		}

		int minDistance = SONAR_MAXRANGE;
		
		try {
			int[] ranges = ecar.getSonarRanges();
			Pose minObstaclePose = null;
			for (int i = 0; i < ranges.length; i++) {
				int angle = ecar.getSonarAngle(i);
				int distance = ranges[i];
				Pose obstaclePose = new Pose(distance, angle);
				if (distance < minDistance && obstaclePose.getX() >= sx1 && obstaclePose.getX() <= sx2
						&& obstaclePose.getY() >= sy1 && obstaclePose.getY() <= sy2) {
					minObstaclePose = obstaclePose;
					minDistance = (int) distance;
				}
			}

			if (minObstaclePose != null) {
				if (pos != null)
					pos.setTo(minObstaclePose);
				// System.out.println("Obstacle detected in distance " + minDistance);
				return minDistance;
			} else
				return SONAR_MAXRANGE;
		} catch (Exception ign) {
			return minDistance;
		}
	}

	public int getSonarRange(int num) {
		return ecar.getSonarRange(num);
	}

	public int getNumBlobs(int channel) {
		return ecar.getNumBlobs(channel);
	}

	public Blob getBlob(int channel, int blobNumber) {
		return ecar.getBlob(channel, blobNumber);
	}

	public void panTilt(int pan, int tilt) {
		ecar.panTilt(pan, tilt);
	}

	public double getTransVel() {
		return ecar.getVelocities()[0];
	}

	public double getRotVel() {
		return ecar.getVelocities()[2];
	}

	public String getRobotName() {
		return robotName;
	}

	public double getVoltage() {
		return ecar.getVoltage();
	}

	public Vector<BehGroup> getBehaviourGroups() {
		return resolver.getBehaviourGroups();
	}

	public PriorityResolver getResolver() {
		return resolver;
	}

	public void addOutput(String line) {
		System.out.print(line);
	}
	
	public void addOutputLn(String line) {
		System.out.print(line + "\n");
	}
	
	public void exit() {
		ecar.disconnect();
		System.exit(0);
	}

	public void setRobPose(Pose pose) {
		this.pose = pose;
	}

}