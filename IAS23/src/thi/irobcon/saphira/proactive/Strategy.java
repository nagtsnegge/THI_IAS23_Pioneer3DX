package thi.irobcon.saphira.proactive;

import thi.irobcon.saphira.SaphiraRobot;
import thi.irobcon.saphira.reactive.ResolverState;

public abstract class Strategy extends Thread {

	protected SaphiraRobot robot;
	protected boolean stopRunning;
	protected String pendingOutput;

public Strategy() {
	super();
	stopRunning = false;
}

public abstract void plan();

public void run () {
	if (pendingOutput != null) addOutput(pendingOutput);
	while (!stopRunning) {
		if (robot.isRunning()) {
			plan();
			ResolverState resolverState = robot.getResolver().getResolverState();
			if (resolverState != null) stopRunning = resolverState.getState() == ResolverState.STOP;
		}
		try { sleep(100); }
		catch (InterruptedException ign){}
	}
	robot.exit();
}

protected void stopRunning() {
	robot.getResolver().setResolverState(new ResolverState("Strategy", "Strategy", ResolverState.STOP));
	stopRunning = true;
}

public SaphiraRobot getRobot() {
	return robot;
}

public void setRobot(SaphiraRobot robot) {
	this.robot = robot;
}

public void addOutput(String line) {
	if (robot != null)	robot.addOutput(line);
	else System.out.println(line);
}
}

