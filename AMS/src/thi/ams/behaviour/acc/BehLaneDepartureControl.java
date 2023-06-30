package thi.ams.behaviour.acc;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.desire.DesTransVel;
import thi.irobcon.saphira.lps.Blob;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehLaneDepartureControl extends Behaviour {

	protected int channel;
	protected final int AREAWIDTH = 60;
	protected final int ROTVEL = 10;
	protected final int FASTROTVEL = 10;
	
public BehLaneDepartureControl(String actionName, int channel) {
	super(actionName);
	this.channel = channel;
}

public void fire() {
	int i;
	int numBlobs = 0;
	int xLeft = 0;
	int xRight = 0;
	Blob leftBlob, rightBlob, blob;
	boolean leftFound,rightFound;

	numBlobs = robot.getNumBlobs(channel);
	
	if ( numBlobs == 0 ) 
	{
		addDesire(new DesTransVel(100, 1.0));
		addDesire(new DesRotVel(0, 1.0));
		System.out.println("No blob");
	}
	else 
	{
		for ( i=1, leftFound=rightFound=false; i <= numBlobs; i++ )
		{
			blob = robot.getBlob(channel, i);
			// System.out.println("Blob: (" +  blob.getXCG() + "," + blob.getYCG() + ")");

			if( !leftFound && blob.getXCG() < AREAWIDTH && blob.getYCG() > 120 - AREAWIDTH )
			{
				leftFound = true;
				leftBlob = blob;
				xLeft = leftBlob.getXCG();
				System.out.println("Blob left: " + leftBlob.getXCG() + "," + leftBlob.getYCG());
			}
			else if( !rightFound && blob.getXCG() > 160 - AREAWIDTH && blob.getYCG() > 120 - AREAWIDTH )
//			else if( !rightFound && blob.getXCG() > BANDBREITE && blob.getYCG() > 120 - BANDBREITE )
			{
				rightFound = true;
				rightBlob = blob;
				xRight = 160 - rightBlob.getXCG();
				System.out.println("Blob right: " + rightBlob.getXCG() + "," + rightBlob.getYCG());
			}
		}

		if ( leftFound && rightFound )
		{
			if ( xLeft > AREAWIDTH / 2) 
			{
				addDesire(new DesRotVel(-ROTVEL, 1.0));
				System.out.println("Turn right\n");
			}
			else if ( xRight > AREAWIDTH / 2) 
			{
				addDesire(new DesRotVel(ROTVEL, 1.0));
				System.out.println("Turn left\n");
			}
			else addDesire(new DesRotVel(0, 1.0));

		}
		else if (leftFound)
		{
			if (xLeft > AREAWIDTH / 2) 
			{
				addDesire(new DesRotVel(-FASTROTVEL, 1.0));
				System.out.println("Fast turn left right\n");
			}
			else 
			{
				addDesire(new DesRotVel(-ROTVEL, 1.0));
				System.out.println("Turn right\n");
			}
		}
		else if (rightFound)
		{
			if (xRight > AREAWIDTH / 2) 
			{
				addDesire(new DesRotVel(FASTROTVEL, 1.0));
				System.out.println("Fast turn left\n");
			}
			else 
			{
				addDesire(new DesRotVel(ROTVEL, 1.0));
				System.out.println("Turn left\n");
			}
		}
		else 
		{
			addDesire(new DesTransVel(100, 1.0));
			addDesire(new DesRotVel(0, 1.0));
		}
	}
}
}

