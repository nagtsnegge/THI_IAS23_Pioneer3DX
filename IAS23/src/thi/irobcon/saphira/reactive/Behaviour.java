package thi.irobcon.saphira.reactive;

import thi.irobcon.saphira.SaphiraRobot;

public abstract class Behaviour {

	protected String name;
	protected SaphiraRobot robot;
	protected BehGroup group;
	protected int priority;

public Behaviour(String name) {
	super();
	this.name = name.replace(' ', '_');
}

public abstract void fire();

public void success () {
	robot.getResolver().setResolverState(new ResolverState(group.getName(), this.getName(), ResolverState.SUCCESS));
}

public void error () {
	robot.getResolver().setResolverState(new ResolverState(group.getName(), this.getName(), ResolverState.ERROR));
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public void setRobot(SaphiraRobot robot) {
	this.robot = robot;
}

public void setPriority(int priority) {
	this.priority = priority;
}


public void setGroup(BehGroup group) {
	this.group = group;
}

public void addDesire(Desire desire) {
	desire.setPriority(priority);
	robot.getResolver().insert(desire);
}

public void addOutput(String line) {
	robot.addOutput(line);
}
}

