package thi.irobcon.saphira.reactive;

public class ResolverState {
	
	public static final int RUNNING = 0;
	public static final int SUCCESS = 1;
	public static final int ERROR = 2;
	public static final int STOP = 3;
	
	protected int state;
	protected String behaviourName;
	protected String behGroupName;
	
	public ResolverState(String behGroupName, String behaviourName, int state) {
		this.behGroupName = behGroupName;
		this.behaviourName = behaviourName;
		this.state = state;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getBehaviourName() {
		return behaviourName;
	}

	public String getBehGroupName() {
		return behGroupName;
	}

	
}
