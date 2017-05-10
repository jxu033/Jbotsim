package jbotsim;


import jbotsim.event.ClockListener;


public class DefaultRobotScheduler implements NodeScheduler{
   
	ClockListener postClockListener=null;
	
	ClockListener stickyRobotsListener = null;
	
	ClockListener collisionDetection = null;
	
	public void addPostComputeListener(ClockListener clockListener){
		this.postClockListener=clockListener;	
	}
	
	public void addStickyRobotsListener(ClockListener clockListener){
		this.stickyRobotsListener=clockListener;	
	}
	
	public void addCollisionDetection(ClockListener clockListener){
		this.collisionDetection = clockListener;
	}
	
	
    @Override
    public void onClock(Topology tp) {
          
    	//robots on the same position become 
    	if(stickyRobotsListener !=null)
    	{
    	    stickyRobotsListener.onClock();
    	    
    	    synchronized (tp) {	
    	    for(Robot r: tp.robotsToMove)
        	{
        		tp.robots.remove(r);
        		tp.removeNode((Node)r);
        	}
    	    tp.robotsToMove.clear();
    	    }
    	}
    	
         
        //compute total times that the robots are activated
        DestMeasurements.total_Activation +=tp.robots.size();

        for (Robot robot : tp.robots)
            robot.onPreClock();//look
        

        for (Robot robot : tp.robots)
        {
            robot.onClock();//compute
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

        for (Robot robot : tp.robots)
        	if(robot.rigidMove ==false)
        		robot.notRigidMove();// Not rigid moving
        	else
            robot.onPostClock();//move
    }
}
