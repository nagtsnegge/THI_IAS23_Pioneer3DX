package thi.irobcon.app.behaviour.rightangledock;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.desire.DesTransVel;
import thi.irobcon.saphira.lps.Pose;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehEdgeDock extends Behaviour {

	protected int tolerance;
	private boolean foundWall = false;
	
	public BehEdgeDock(String behName) {
		super(behName);
	}
	
	public void fire() {
		
		int dist = robot.getSonarRange(3);
			
		if(!foundWall){
			if ( dist <= 1000 )
		  	{
				System.out.println( "Wand 1 Found" );
				
				
				foundWall = true;
				addDesire(new DesTransVel(0, 0.8));
			}
		}else {
			if (dist <= 1000){
				System.out.println("Wand 2 Found");

				success();
			}
		}
		
		  
	}
}

