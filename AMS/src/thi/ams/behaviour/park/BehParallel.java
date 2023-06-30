package thi.ams.behaviour.park;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.lps.Pose;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehParallel extends Behaviour {

	protected int distance, tolerance, timer, counter;
	protected double verstaerkung;

	public BehParallel(String behName, int distance, int tolerance, int timer) {
		super(behName);
		this.distance = distance;
		this.tolerance = tolerance;
		this.timer = timer;
		verstaerkung = 3;
	}

	public void fire() {
		  int aktDistance, resFront, resRear;
		  Pose frontObstacle = new Pose();
		  Pose rearObstacle = new Pose();
		  double wallTh = 0;

		  aktDistance = (int) robot.checkBox(300, -2900, -300, -300, null);

		  if (counter == 0)
		  {
			  resFront = (int) robot.checkBox(800, -2900, 0, -300, frontObstacle);
			  resRear = (int) robot.checkBox(0, -2900, -800, -300, rearObstacle);

			  wallTh = Math.toDegrees(Math.atan2(frontObstacle.getY() - rearObstacle.getY(),
										frontObstacle.getX() - rearObstacle.getX()));
			  
//			  System.out.println("Front " + resFront + " Rear " + resRear);
			  System.out.println("Angle to wall: " + wallTh + " in distance " + aktDistance);
			  
			  if( resFront >= 3000 || resRear >= 3000 )
			  {
				  addOutput( "No valid sensor data\n" );
				  addDesire(new DesRotVel(0, 1.0));
			  }
		  	  else if ( Math.abs( wallTh ) > 5 )
			  {
		  		  double rotVel = wallTh * verstaerkung;
				  addOutput( "Not parallel --> Turning " + rotVel + "\n");
				  addDesire(new DesRotVel(rotVel, 1.0));
			  }
			  else
			  {
				  addDesire(new DesRotVel( 0, 1.0));
				  if (Math.abs(aktDistance - distance ) > tolerance)  counter = timer;
			  }
		  }
		  else {
			  counter--;
			  if ( aktDistance > distance && Math.abs( aktDistance - distance ) > tolerance )
			  {
				  addOutput( "Too far away --> Turning right\n\n" );
				  addDesire(new DesRotVel( -10, 1.0));
			  }
			  else if ( aktDistance < distance && Math.abs( aktDistance - distance ) > tolerance )
			  {
				  addOutput( "Too near --> Turning left\n\n" );
				  addDesire(new DesRotVel( 10, 1.0));
			  }

		  }
	}
}
