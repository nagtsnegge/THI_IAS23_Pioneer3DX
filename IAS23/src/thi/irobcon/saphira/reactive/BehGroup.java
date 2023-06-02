package thi.irobcon.saphira.reactive;

import java.util.Vector;

import thi.irobcon.saphira.SaphiraRobot;

public class BehGroup {

public static String DEFAULT_NAME = "Default";

protected String name;
protected PriorityResolver resolver;
protected Vector<PriorityBehaviour> priorityBehaviours;
protected boolean active;

public BehGroup() {
	this.name = DEFAULT_NAME;
	priorityBehaviours = new Vector<PriorityBehaviour>();
}

public BehGroup(String name) {
	this.name = name;
	priorityBehaviours = new Vector<PriorityBehaviour>();
}

public void setRobot(SaphiraRobot robot) {
	for (int i=0; i<priorityBehaviours.size(); i++) {
		priorityBehaviours.elementAt(i).getBehaviour().setRobot(robot);
	}

}
public void add(Behaviour behaviour, int priority) {
	PriorityBehaviour priorityBehaviour = new PriorityBehaviour(behaviour, priority);
	for (int i=0; i<priorityBehaviours.size(); i++) {
		PriorityBehaviour b = (PriorityBehaviour) priorityBehaviours.elementAt(i);
		if (b.getPriority() <= priorityBehaviour.getPriority()) {
			priorityBehaviours.insertElementAt(priorityBehaviour,i);
			return;
		}
	}
	priorityBehaviours.add(priorityBehaviour);
}

public void activateExclusive() {
	Vector<BehGroup> robotBehaviourGroups = resolver.getBehaviourGroups();
	for (int i=0; i<robotBehaviourGroups.size();i++) {
		BehGroup tmp = robotBehaviourGroups.elementAt(i);
		tmp.deactivate();
	}
	activate();
}

public void activate() {
	active = true;
	resolver.setActiveBehGroup(this);
}

public void deactivate() {
	active = false;
}

public boolean isActive() {
	return active;
}

public Vector<PriorityBehaviour> getPrioriyBehaviours() {
	return priorityBehaviours;
}

public PriorityResolver getResolver() {
	return resolver;
}

public void setResolver(PriorityResolver resolver) {
	this.resolver = resolver;
}

public boolean isSuccess() {
	ResolverState resolverState = (ResolverState) resolver.getResolverState();
	if (resolverState == null) return false;
	else return 	resolverState.getBehGroupName() != null &&
					resolverState.getBehGroupName().equals(name) && 
					resolverState.getState() == ResolverState.SUCCESS;
}

public boolean isError() {
	ResolverState resolverState = (ResolverState) resolver.getResolverState();
	if (resolverState == null) return false;
	else return 	resolverState.getBehGroupName() != null &&
					resolverState.getBehGroupName().equals(name) && 
					resolverState.getState() == ResolverState.ERROR;
}

public String getName() {
	return name;
}

}