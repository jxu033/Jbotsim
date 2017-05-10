package jbotsim;

import java.util.ArrayList;
import java.util.Random;

public class AsynchrRobotScheduler extends DefaultRobotScheduler {


	@Override
	public void onClock(Topology tp) {

		ArrayList<Robot> robotList = new ArrayList<Robot>();
		robotList.addAll(tp.robots);
		Random random = new Random();
		int robots = robotList.size();
		
		if (robots > 0) {
			for (int num = random.nextInt(robots); num > 0; num--) {
				int index = random.nextInt(num);
				robotList.remove(index);
			}
		}
		
		for (Robot robot : robotList) {
			if (robot.moving == false) {
				System.out.println("Activating:" + robot.ID);
				DestMeasurements.total_Activation++;
				robot.onPreClock(); // Look
			}
		}
		
		for (Robot robot : robotList) {
			if (robot.moving == false) {
				robot.onClock(); // Compute

				if (robot instanceof Robot) {
					System.out.println("B");
					System.out.println("C"+((Destination2D) (robot.dest)).dest);
					if (((Destination2D) (robot.dest)).dest != robot
							.getLocation()) {
						DestMeasurements.total_Moves++;
						DestMeasurements.total_Distance += ((Destination2D) (robot.dest)).dest
								.distance(robot.getLocation());
					}
				}
			}
		}
		
		if (postClockListener != null) {
			System.out.println("Collisi");
			postClockListener.onClock();

		}
		
		 /**
         * Determine whether robots may collide with each other on the way to its destinations
         */
		if (collisionDetection != null) {
			try {
				tp.postComputeHandler();
			} catch (Exception ex) {
				if (ex.getMessage() != null) {
					System.out.println(ex.getMessage());
					System.exit(-1);
				}
			}
		}
		
		for (Robot robot : robotList){
			
        	if(robot.rigidMove ==false)
        		robot.notRigidMove();// Not rigid moving
        	else
        	    robot.asynchMove();//move
		}
	}
}
