package thi.irobcon.app.script;

import thi.irobcon.ecar.ECarDefines;
import thi.irobcon.script.IRobConScript;

public class ServiceRoboticsScripts extends IRobConScript {

	public void script() {
//		autp();
//		patrol();
//	    slalom1();
//		slalom2();
//		clean();
//		transport1();
//		transport2();
		transport3();
	}

	protected void autp() {
		
//		useRobot(ECarDefines.JSIM_DX1);
		useRobot(ECarDefines.DX4);
		turn(180);
	}

	protected void patrol() {
		
//		useRobot(ECarDefines.JSIM_DX1);
		useRobot(ECarDefines.DX4);
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
		

	}
		
	protected void slalom2() {
		
		useRobot(ECarDefines.JSIM_DX1);
		setRobPose(-2000,0,0);
		
	}
	
	protected void clean() {
		
		useRobot(ECarDefines.JSIM_DX1);
		setRobPose(0,2000,90);
	}
	
	protected void transport1()
	{
		useRobot(ECarDefines.SIM_DX1);
		setRobPose(-2000,0,0);
		liftDown();
		gripClose();
		int distToBlock = (getSonarRange(3) + getSonarRange(4)) / 2;
		while(getSonarRange(3) > 300 && getSonarRange(4) > 300)
		{
			speed(200);
		}
		gripOpen();
		while(getSonarRange(3) > 260 && getSonarRange(4) < 260)
		{
			speed(50);
		}
		speed(0);
		wait(10);
		gripClose();
		liftUp();
		move(-(distToBlock));
		turn(90);
		move(100);
		
		
	}
	
	protected void transport2()
	{
//		useRobot(ECarDefines.SIM_DX1);
		useRobot(ECarDefines.DX4);
		setRobPose(-2000,0,0);
		liftDown();
		gripClose();
		wait(50);
		int distToBlock = (getSonarRange(3) + getSonarRange(4)) / 2;
		while(getSonarRange(3) > 500 && getSonarRange(4) > 500)
		{
			speed(200);
		}
		speed(0);
		gripOpen();
		wait(50);
		while(getSonarRange(3) > 260 && getSonarRange(4) > 260)
		{
			speed(50);
		}
		speed(0);
		wait(10);
		gripClose();
		liftUp();
		wait(50);
		move(-(distToBlock));
		turn(90);
		panTilt(-90, 0);
		while(getBlobX(2) != 195)
		{
			speed(150);
		}
		speed(0);
		turn(-90);
		panTilt(0,0);
		move(distToBlock);
		wait(10);
		liftDown();
		gripOpen();
		wait(50);
		move(-100);
		
		
	}
	protected void transport3()
	{
//		useRobot(ECarDefines.SIM_DX1);
		useRobot(ECarDefines.DX4);
		setRobPose(-2000,0,0);
		liftDown();
		gripClose();
		panTilt(0, -20);
		wait(100);
		// Searches for Colorchannel 1
		int myX = getBlobX(1);
		// Turns right until aligned with Blob
		while(myX < 185 || myX > 205)
		{
			rotate(-7);
			myX = getBlobX(1);
		}
		
		rotate(0);
		wait(10);
		
		//get median to Block from 2 Sensors
		int distToBlock = (getSonarRange(3) + getSonarRange(4)) / 2;
		// Medium range medium speed
		while(getSonarRange(3) > 800 && getSonarRange(4) > 800)
		{
			speed(130);
			myX = getBlobX(1);
			// turn left
			if(myX < 196)
			{
				rotate(5);
			}
			// turn right
			if(myX > 194)
			{
				rotate(-5);
			}
			if(myX == 195)
			{
				rotate(0);
			}
		}
		
		//Close range slow speed
		while(getSonarRange(3) > 500 && getSonarRange(4) > 500)
		{
			speed(70);
			myX = getBlobX(1);
			// turn left
			if(myX < 195)
			{
				rotate(3);
			}
			// turn right
			if(myX > 195)
			{
				rotate(-3);
			}
			if(myX == 195)
			{
				rotate(0);
			}
		}
		
		//Stop and open Claw
		speed(0);
		rotate(0);
		gripOpen();
		wait(50);
		
		//Move closer to Block using Sonar
		while(getSonarRange(3) > 178 && getSonarRange(4) > 178)
		{
			speed(50);
		}
		
		speed(0);
		wait(10);
		
		//Close Grip and lift block
		gripClose();
		liftUp();
		wait(50);
		
		// Move backwards to start.
		move(-(distToBlock));
		turn(90);
		
		//set camera to look right
		panTilt(-90, -5);
		// 
		myX = getBlobX(2);
		while(myX < 195)
		{
			myX = getBlobX(2);
			speed(200);
		}
		
		speed(0);
		turn(-90);
		panTilt(0,0);
		
		move(distToBlock);
		wait(10);
		liftDown();
		gripOpen();
		wait(50);
		
		move(-100);
	}
}
