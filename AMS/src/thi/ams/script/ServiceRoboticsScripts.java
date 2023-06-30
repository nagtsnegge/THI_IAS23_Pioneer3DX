package thi.ams.script;

import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.script.IRobConScript;

public class ServiceRoboticsScripts extends IRobConScript {

	public void script() {
		patrol();
//	    slalom1();
//		slalom2();
//		clean();
		//motionTest();
	}

	protected void patrol() {
		
//		useRobot(ECarDefines.JSIM_DX1);
//		useRobot(ECarDefines.AL2);
//		useRobot(ECarDefines.DX5);
		useRobot(ECarDefines.SIM_DX1);
		for (int i=0; i < 10; i++) {
			move(1000);
			if (i%2==0) gripClose();
			else gripOpen();
			turn(180);
		}
	}

	protected void slalom1() {
		
		useRobot(ECarDefines.JSIM_DX1);
		setRobPose(-2000,0,0);
		
		turn(22);
		move(2154);
		turn(-55);
		move(2968);
		turn(66);
		move(2968);
		turn(-55);
		move(2154);
		turn(-147);
	}
		
	protected void slalom2() {
		
		useRobot(ECarDefines.JSIM_DX1);
		setRobPose(-2000,0,0);
		
		speed(200);
		rotate(7);
		wait(50);
		rotate(-6);
		wait(130);
		rotate(6);
		wait(150);
		rotate(-5);
		wait(180);
	
		turn(-135);
	}
	
	protected void clean() {
		
		useRobot(ECarDefines.JSIM_DX1);
		setRobPose(0,2000,90);
//		setRobPose(2500,0,180);
//		System.out.println(getSonarRange(2) + " " + getSonarRange(3) + " " + getSonarRange(4) + " " + getSonarRange(5) + " ");
		
	//	wait(50);
		addOutput("Greife Besen\n");
		gripOpen();
		wait(50);
		liftUp();
		wait(50);
	  
		move(100);
		gripClose();
		wait(30);

		addOutput("Reinige\n");
		int clearpath=5;
		turn(-100);
		speed(200);

		  while (clearpath > 0)
		  {
		     if (getSonarRange(2) < 700 ||
		         getSonarRange(3) < 700 ||
		         getSonarRange(4) < 700 ||
		         getSonarRange(5) < 700 )
		     {
		        addOutput("Umdrehen\n");
		        System.out.println("Turning");
		        speed(0);
		         if ( clearpath % 2 == 0 )
		         {
		              turn(90);
		              move(400);
		              turn(90);
		        }
		       else
		         {
		              turn(-90);
		              move(400);
		              turn(-90);
		        }
		        clearpath=clearpath-1;
		        speed(200);
		     }
		  }
	  speed(0);
	  addOutput("Alles sauber!\n");
	}

	protected void motionTest() {
		
		useRobot(ECarDefines.JSIM_DX1);
		
		gripClose();
		move(1000);
		gripOpen();
		liftUp();
		move(-1000);
		liftDown();
		wait(100);
		
		gripClose();
		turn(90);
		gripOpen();
		liftUp();
		turn(-90);
		liftDown();
		wait(100);
		
		turn(45);
		turn(-45);
		wait(100);

		turn(10);
		turn(-10);
		wait(100);

		turn(5);
		turn(-5);
		wait(100);

		move(1000);
		move(-1000);
		wait(100);
		
		move(500);
		move(-500);
		wait(100);
		
		move(200);
		move(-200);
		wait(100);
		
	}

}
