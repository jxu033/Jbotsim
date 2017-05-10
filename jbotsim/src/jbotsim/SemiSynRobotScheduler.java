package jbotsim;

import java.util.ArrayList;
import java.util.Random;

import jbotsim.event.ClockListener;

/**
 *
 * @author Haochuan Ran
 */


 public class SemiSynRobotScheduler extends DefaultRobotScheduler{


    @Override
    public void onClock(Topology tp) {

        ArrayList<Robot> robotList = new ArrayList<Robot>();
        Random random = new Random();

        robotList.addAll(tp.robots);
        int robots = robotList.size();
        
		if (robots > 0) {
			for (int num = random.nextInt(robots); num > 0; num--) {
				int index = random.nextInt(num);
				robotList.remove(index);
			}
		}


        if ( robotList != null) {
            for (Robot robot :  robotList) {
                System.out.println("Activating:" + robot.ID);
                DestMeasurements.total_Activation++;
                robot.onPreClock(); //Look
            }
            
            for (Robot robot :  robotList)
            {
                robot.onClock(); //Compute
                if(robot instanceof Robot)
                {
                	if(((Destination2D)(robot.dest)).dest!=robot.getLocation())
                	{
                		DestMeasurements.total_Moves ++ ;
                		DestMeasurements.total_Distance += ((Destination2D)(robot.dest)).dest.distance(robot.getLocation());
                	}          	
                }
            }
            
            if(postClockListener!=null){
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
    		
    		for (Robot robot : robotList)
            	if(robot.rigidMove ==false)
            		robot.notRigidMove();// Not rigid moving
            	else
                robot.onPostClock();//move
        }
    }
    }

