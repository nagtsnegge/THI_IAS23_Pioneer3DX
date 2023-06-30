package thi.ams.behaviour.acc;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.lps.Blob;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehLaneChangeControl extends Behaviour {

	protected int channel;
	protected boolean init, leftChange, isTurned;
	protected double zielTh;
	
	protected final int ANGLE = 35;
	
public BehLaneChangeControl(String actionName, int channel) {
	super(actionName);
	this.channel = channel;
	this.init = true;    
	this.leftChange = true;
	this.isTurned = false;
}

public void fire() {
	  int numBlobs, i;
	  Blob blob;

	  if ( leftChange )
	  {
		if ( init )
	  	{
	  		zielTh = robot.getPose().getTh() + ANGLE;
	  		if( zielTh > 180 ) zielTh -= 360;
	  		init = false;
	  	}

	  	if ( !isTurned && robot.getPose().getTh() < zielTh ) 
			addDesire(new DesRotVel(8, 0.5));
		else isTurned = true;

		if ( isTurned )
		{
			numBlobs = robot.getNumBlobs(channel);
			if ( numBlobs > 0 )
			{
				for( i=1; i < numBlobs; i++ )
				{
					blob = robot.getBlob(channel, i);
					//System.out.println("Blob: " + blob.getXCG() + "," + blob.getYCG());
				
					if ( blob.getXCG() > 120 && blob.getYCG() > 60 ) return;
				}

	  			init = true;
				isTurned = false;
	  			leftChange = !leftChange;
	  			success();
			}
	  	}
	  }
	  else
	 {
		if ( init )
	  	{
		  	zielTh = robot.getPose().getTh() - ANGLE;
		  	if( zielTh < -180 ) zielTh += 360;
		  	init = false;
			System.out.println("Ziel = " + zielTh );
	  	}

	  	if ( !isTurned && robot.getPose().getTh() > zielTh ) 
			addDesire(new DesRotVel(-8, 0.5));
		else isTurned = true;

	  	if ( isTurned )
	  	{
			numBlobs = robot.getNumBlobs(channel);
			if ( numBlobs > 0 )
			{
				for( i=1; i < numBlobs; i++ )
				{
					blob = robot.getBlob(channel, i);
					//System.out.println("Blob: " + blob.getXCG() + "," + blob.getYCG());
			
					if ( blob.getXCG() < 60 && blob.getYCG() > 60 ) return;
				}

				init = true;
				isTurned = false;
	  			leftChange = !leftChange;
	  			success();
	  		}
		}

	  }
}
}

