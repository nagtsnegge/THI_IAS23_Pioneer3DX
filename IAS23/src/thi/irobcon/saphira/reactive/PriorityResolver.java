package thi.irobcon.saphira.reactive;

import java.util.Date;
import java.util.Vector;

import thi.irobcon.ecar.ECar;
import thi.irobcon.saphira.desire.DesCamPan;
import thi.irobcon.saphira.desire.DesCamTilt;
import thi.irobcon.saphira.desire.DesGrip;
import thi.irobcon.saphira.desire.DesLift;
import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.desire.DesTransVel;


public class PriorityResolver extends Thread {

	public static String THREAD_NAME = "Resolver";
	protected static final int CYLCETIME = 100;
	
	protected ECar ecar;
	protected Vector<BehGroup> behaviourGroups;
	protected BehGroup activeBehGroup;
	protected Vector<Desire> desiresToResolve;
	protected Vector<Desire> desires;
	protected ResolverState resolverState;
	
	protected int pan, tilt;

public PriorityResolver() {
	super();
	setName(THREAD_NAME);
	behaviourGroups = new Vector<BehGroup>();

	desiresToResolve = new Vector<Desire>();
	desiresToResolve.add(new DesTransVel());
//	desiresToResolve.add(new DesShiftVel());
	desiresToResolve.add(new DesRotVel());
	
	desires = new Vector<Desire>();
}

public void exec(Vector<Desire> actions)  {
	boolean isPanTilt = false;
	for(int i=0; i<actions.size(); i++) {
		if (actions.elementAt(i) instanceof DesTransVel)	ecar.speed((int)actions.elementAt(i).getValue());
//		if (actions.elementAt(i) instanceof DesShiftVel)	ecar.shift((int)actions.elementAt(i).getValue());
		if (actions.elementAt(i) instanceof DesRotVel)		ecar.rotate((int)actions.elementAt(i).getValue()); 
		if (actions.elementAt(i) instanceof DesGrip)	{
			DesGrip dg = (DesGrip)actions.elementAt(i);
			if (dg.isOpen()) ecar.gripOpen();
			else ecar.gripClose();
		}
		if (actions.elementAt(i) instanceof DesLift)	{
			DesLift dl = (DesLift)actions.elementAt(i);
			if (dl.isUp()) ecar.liftUp();
			else ecar.liftDown();
		}
		if (actions.elementAt(i) instanceof DesCamPan)	{
			pan =	(int)actions.elementAt(i).getValue();
			isPanTilt = true;
		}
		if (actions.elementAt(i) instanceof DesCamTilt)	{
			tilt =	(int)actions.elementAt(i).getValue();
			isPanTilt = true;
		}
	}
	if (isPanTilt) ecar.panTilt(pan, tilt);
}

public void add(BehGroup behGroup) {
	behGroup.setResolver(this);
	behaviourGroups.add(behGroup);
}

public void run() {
	try {
		while (true) {
			Date startTime = new Date();
			if (activeBehGroup != null) {
				calcDesires();
				resolve();
				clear();
			}
			Date endTime = new Date();
			long timeToSleep = CYLCETIME - (endTime.getTime() - startTime.getTime());
			try {
				if (timeToSleep > 0) {
//					System.out.println("Sleeping " + timeToSleep);
					sleep(timeToSleep);
				}
			} 
			catch (InterruptedException e) {
			}
		}
	}
	catch (Exception e) {
		System.out.println("Stopping resolver on Exception " + e);
	}
}

public void calcDesires() {
	PriorityBehaviour priorityBehaviour = null;

	try {
		for (int i=0; i < activeBehGroup.getPrioriyBehaviours().size(); i++) {
			priorityBehaviour = activeBehGroup.getPrioriyBehaviours().elementAt(i);
//			priorityBehaviour.getBehaviour().setProdSys(prodSys);
			priorityBehaviour.getBehaviour().setGroup(activeBehGroup);
			priorityBehaviour.getBehaviour().setPriority(priorityBehaviour.getPriority());
			priorityBehaviour.getBehaviour().fire();
		}
	} 
	catch (NullPointerException npe) {
		if (activeBehGroup == null) return;
	}
}

protected void resolve() throws InstantiationException, IllegalAccessException {
	Vector<Desire> actions = new Vector<Desire>();
	for(int i=0; i<desiresToResolve.size(); i++) {
		Desire resResove = resolve(desiresToResolve.elementAt(i));
		if (resResove != null) actions.add(resResove);
	}
	exec(actions);
	
}

public Desire resolve(Desire desireToResolve) throws InstantiationException, IllegalAccessException {
	Desire result = desireToResolve.getClass().newInstance();
	if (desires.size() == 0) return null;
	
	Vector<Desire> sortedDesires = new Vector<Desire>();
	for(int i=0; i<desires.size(); i++) {
		Desire desire = desires.elementAt(i);
		if (desire.getClass() == result.getClass()) {
			int insertIndex = sortedDesires.size();
			for(int j=0; j<sortedDesires.size(); j++)
				if (sortedDesires.elementAt(j).getPriority() < desire.getPriority()) {
					insertIndex = j;
					break;
				}
			sortedDesires.insertElementAt(desire, insertIndex);
		}
	}
	
	for(int i=0; i<sortedDesires.size() && result.getStrength() < 1; ) {
		Vector<Desire> blockDesires = new Vector<Desire>();
		blockDesires.add(sortedDesires.elementAt(i));
		i++;
		for(; i<sortedDesires.size() && sortedDesires.elementAt(i).getPriority() == blockDesires.firstElement().getPriority(); i++)
			blockDesires.add(sortedDesires.elementAt(i));
		Desire blockResult = new Desire(0,0);
		for(int j=0; j<blockDesires.size(); j++) {
			blockResult.setValue(blockResult.getValue() + blockDesires.elementAt(j).getValue() * blockDesires.elementAt(j).getStrength());
			blockResult.setStrength(blockResult.getStrength() + blockDesires.elementAt(j).getStrength());
		}
		blockResult.setStrength(blockResult.getStrength() / blockDesires.size());
		result.setValue(result.getValue() + blockResult.getValue());
		result.setStrength(result.getStrength() + blockResult.getStrength());
	}
	

	if (result.getStrength() > 0) {
		result.setValue(result.getValue() * 1.0 / result.getStrength());
	}	
	result.setStrength(1.0);
	
	return result;
}

protected void clear() {
	desires = new Vector<Desire>();
}

public void insert(Desire desire) {
	desires.add(desire);
}

public Vector<BehGroup> getBehaviourGroups() {
	return behaviourGroups;
}

public void setActiveBehGroup(BehGroup activeBehGroup) {
	this.activeBehGroup = activeBehGroup;
	resolverState = new ResolverState(activeBehGroup.getName(), "none", ResolverState.RUNNING);
}

public ResolverState getResolverState() {
	return resolverState;
}

public void setResolverState(ResolverState resolverState) {
	this.resolverState = resolverState;
}

public void setEcar(ECar ecar) {
	this.ecar = ecar;
}



}
