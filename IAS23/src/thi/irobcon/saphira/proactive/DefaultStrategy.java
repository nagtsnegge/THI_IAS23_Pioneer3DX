package thi.irobcon.saphira.proactive;

import thi.irobcon.saphira.reactive.BehGroup;

public class DefaultStrategy extends Strategy {

	protected BehGroup defaultBehGroup;
	
	public DefaultStrategy(BehGroup defaultBehGroup) {
		super();
		this.defaultBehGroup = defaultBehGroup;
		defaultBehGroup.activateExclusive();
	}

	public void plan() {
		if(defaultBehGroup.isSuccess() || defaultBehGroup.isError()) 
			stopRunning();
		else {			
			try{sleep(1000);}
			catch(InterruptedException ign) {}
		}
	}
}

