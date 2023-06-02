package thi.irobcon.saphira.reactive;

public class PriorityBehaviour {

	protected Behaviour behaviour;
	protected int priority;

public PriorityBehaviour(Behaviour behaviour, int priority) {
	this.behaviour = behaviour;
	this.priority = priority;
}

public int getPriority() {
	return priority;
}

public Behaviour getBehaviour() {
	return behaviour;
}

}

