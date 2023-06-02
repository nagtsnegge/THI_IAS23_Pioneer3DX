package thi.irobcon.app.behaviour.rightangledock;

import thi.irobcon.saphira.desire.DesRotVel;
import thi.irobcon.saphira.lps.Pose;
import thi.irobcon.saphira.reactive.Behaviour;

public class BehAlign extends Behaviour {

	protected int tolerance;
	protected int rotVel;
	
public BehAlign(String behName, int tolerance, int rotVel) {
	super(behName);
	this.tolerance = tolerance;
	this.rotVel = rotVel;
}

public void fire() {
	  int leftDist, rightDist;
	  Pose relObstaclePose = new Pose();

/*
	  // Mit Sonaren explizit
	  leftDist = robot.getSonarRange(3);
	  rightDist = robot.getSonarRange(4); 
*/
/*
	  // Mit Kegel
	  leftDist = (int)  (robot.checkPolar(2, 10, relObstaclePose) - 
		      robot.getRadius());
	  System.out.println( "Position linkes Hindernis: (" + relObstaclePose.getX() + "," +
			  relObstaclePose.getY() + ")");
	  rightDist = (int) (robot.checkPolar(-10, -2, null) - 
		      robot.getRadius()); 
	
*/
	  // Mit Box
	  leftDist = (int)  (robot.checkBox(2500, 500, 100, 100,
	                     	relObstaclePose) - robot.getRadius());

	  System.out.println("Left obstacle at relative position (" + relObstaclePose.getX() + 
				"," + relObstaclePose.getY() + 
				"," + relObstaclePose.getTh() + ") in distance " + 
				(leftDist + robot.getRadius()));

	  rightDist = (int)  (robot.checkBox(2500, -500, 100, -100, null) 
							- robot.getRadius());

	  // Mit Positionsauswertung
	  Pose robPose = robot.getPose();
	  System.out.println("Robot at position (" + robPose.getX() + 
				"," + robPose.getY() + 
				"," + robPose.getTh() + ")");

	  Pose absObstaclePose = new Pose(robPose.getX() +  relObstaclePose.getX(), 
	  			robPose.getY() +  relObstaclePose.getY(), 
				robPose.getTh() +  relObstaclePose.getTh() );

	  System.out.println("Left obstacle at absolute position (" + absObstaclePose.getX() + 
				"," + absObstaclePose.getY() + 
				"," + absObstaclePose.getTh() + ")");

	  double distLeft = robPose.findDistanceTo( absObstaclePose );
	  double angleLeft = robPose.findAngleTo( absObstaclePose );

	  System.out.println("Validating distance = " + distLeft + " and angle " + angleLeft );
	  
	  // Kurskorrektur
	  System.out.println("Left = " + leftDist + " right = " + rightDist);

	  if ( leftDist - rightDist > tolerance)
	  {
		  addDesire(new DesRotVel(-rotVel, 1.0));
		  System.out.println( "Turn right" );
	  }
	  else if ( rightDist - leftDist > tolerance)
	  {
		  addDesire(new DesRotVel(rotVel, 1.0));
		  System.out.println( "Turn left" );
	  }
	  else addDesire(new DesRotVel(0, 0.5));
}
}

