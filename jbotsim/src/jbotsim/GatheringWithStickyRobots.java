package jbotsim;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jbotsim.event.ClockListener;
import jbotsim.ui.JViewer;
import jbotsim.ui.painting.ObstaclePainter;
import jbotsim.ui.painting.RobotPainter;

public class GatheringWithStickyRobots extends Robot {
	public GatheringWithStickyRobots(Point2D currentLocation)
	{
		   this.sensingRange = Double.POSITIVE_INFINITY; 		  
	       this.disableWireless();
	       this.color=Color.red;
	       this.setLocation(currentLocation.getX(),currentLocation.getY());
	}
	
	public SnapShot look(){
		//System.out.println(this.topo.robots.size());
		//System.out.println("Robot " + this.name + " at location: "+this.getLocation());
		SnapShot snap = new SnapShot();
		snap.getSnapShot(this);
		return snap;
	}
	
	public Destination compute(SnapShot snap) {
		/*System.out.println("The visible robots for Robot " + this.name
				+ " are:" + snap.visRobots);
		System.out.println("There are " + snap.visSegments.size()
				+ " can be see by this robot:");*/
		
		/*List<Segment> visArea = snap.visSegments;
		for (Segment s : visArea) {
			System.out.println("segment from " + s.left + " to " + s.right);
		}*/
		List<Point2D> a = new ArrayList<Point2D>();
	
		for (Robot robot : snap.visRobots) {
			a.add(robot.getLocation());
		}

		Destination2D destination = new Destination2D();
		if (!a.isEmpty()) {
            if(a.size() == 1) // if there is only one robot in its snapshot, go to the mid position between the visible robot and itself 
            {                 // such that it could be able to meet at the same position; otherwise, they keep switching position, 
            	              // never meet at the same position
            	double mid_x = (a.get(0).getX() +this.getLocation().getX())/2; 
                double mid_y = (a.get(0).getY() +this.getLocation().getY())/2; 
            	destination.dest = new Point2D.Double(mid_x, mid_y);
            }else {          
            	destination.dest = a.get((int) (Math.random() * a.size()));
			}			
		} else {// move to a position around one of the visible vertexes
			destination.dest = this.goToPointAroundVisibleVertexes();
		}

		return destination;
	}
	
	
	public void move(Destination dest) {
		this.dest = (Destination2D) dest;
		
		// if there is no obstacle between the robot and its destination, the
		// robot will move towards the destination
		if (this.HasObstalceBetween(((Destination2D) (dest)).dest) == false) {
			setDirection(((Destination2D) dest).dest);
			//move(Math.min(1, this.distance(this.dest)));
			move(this.distance(this.dest));
			
		} else if (this.HasObstalceBetween(((Destination2D) (dest)).dest) == true) {
			Point2D new_dest;
			do {			   
				new_dest = this.goToPointAroundVisibleVertexes();
			} while (this.HasObstalceBetween(new_dest) == true);
			// set a new destination
			this.setLocation(new_dest);

		}

	}
	
	
	/**
	 * 
	 * @param dest 
	 * @return  true if there is an obstacle between robot and its destination; otherwise return false.
	 */
	public boolean HasObstalceBetween(Point2D dest)
	{
		Segment robot_dest = new Segment(dest, this.getLocation());
		boolean hasObstalceBetween = false;
		for(Obstacle obstacle : this.topo.obstacles)
		{
			for(Segment segment : obstacle.segmentsOfObstalce)
			{
				
				if(robot_dest.HasIntersectionWithSegment(segment))
				{
					hasObstalceBetween = true;
					break;
				}
			}
			
			if(hasObstalceBetween == true)
				break;
		}
		
		return hasObstalceBetween;
	}
	
	/**
	 * 
	 * @param p  
	 * @param tp
	 * @return true if current point is not inside any obstacle in this topology; otherwise return false.
	 */
	public static boolean IsStartPointInObstalce(Point2D p, Topology tp)
	{
		boolean isStartPointInObstalce =false;
	
		for(Obstacle obstacle : tp.obstacles)
		{
			for(Segment s : obstacle.segmentsOfObstalce)
			   {
				   if(s.isPointOnSegment(p))
				   {
					   return true;		   
				   }
			   }
			
			if(obstacle.polygon2d.IsPointContained(p))
			{
			  isStartPointInObstalce = true;
			  break;
			}
		}
		
		return isStartPointInObstalce;
	}
	
	public static void main(String[] args) {
		int activation_min=0;
		int activation_max = 0;
		int activation_avg=0;
		
		double distance_min=0;
		double distance_max = 0;
		double distance_avg=0;
		
		int  moves_min=0;
		int moves_max=0;
		int moves_avg=0; 
		
		int activation_sum =0;
		double distance_sum =0;
		int moves_sum = 0;
		

		for(int j=1; j <=100; j++){
	try{
		
		Topology tp = new Topology(false);

		//setting 1:
		/*double[] xpoints = { 200, 500, 500, 200 };
		double[] ypoints = { 100, 100, 300 , 300 };
		Obstacle obstacle1 = new Obstacle(xpoints, ypoints, 4);*/
		
		//setting 2:
		/*double[] xpoints1 = { 100, 250, 250, 100 };
		double[] ypoints1 = { 100, 100, 300 , 300 };
		Obstacle obstacle1 = new Obstacle(xpoints1, ypoints1, 4);
		
		double[] xpoints2 = { 350, 500, 500, 350 };
		double[] ypoints2 = { 100, 100, 300 , 300 };
		Obstacle obstacle2 = new Obstacle(xpoints2, ypoints2, 4);*/
		
		//setting 3:
		double[] xpoints1 = { 100, 150, 150, 100 };
		double[] ypoints1 = { 100, 100, 200 , 200 };
		Obstacle obstacle1 = new Obstacle(xpoints1, ypoints1, 4);

		double[] xpoints2 = { 200, 300, 400,400};
		double[] ypoints2 = { 100, 200, 100,400 };
		Obstacle obstacle2 = new Obstacle(xpoints2, ypoints2, 4);
   
		tp.addObstacle(obstacle1);
		tp.addObstacle(obstacle2);
		
		// add some robots
		Point2D p;
		for (int i = 0; i < 100; i++) {
			do {
				p = new Point2D.Double((int)(new Random().nextInt(tp.getWidth())),
						(int)(new Random().nextInt(tp.getHeight())));
			} while (IsStartPointInObstalce(p, tp) == true);
			GatheringWithStickyRobots robot = new GatheringWithStickyRobots(p);
			robot.name = Integer.toString(i + 1);
			tp.addRobot(robot);
		}

		tp.setNodeScheduler(new DefaultRobotScheduler());
		
		if(tp.nodeScheduler instanceof DefaultRobotScheduler)
		{
			((DefaultRobotScheduler)tp.nodeScheduler).addStickyRobotsListener(new ClockListener() {
				
				List<Robot> robotsOnSamePositions = new ArrayList<Robot>();
				@Override
				public void onClock() {
					for(Robot r : tp.robots)
					{
						if(!tp.robotsToMove.contains(r))
						{
							
					        robotsOnSamePositions = r.getRobotsOntheSameLocation();
					       //System.out.println(robotsOnSamePositions); 
					       for(Robot robot : robotsOnSamePositions)
					       {
					    	   tp.robotsToMove.add(robot);
					      }
					    }
					}
					
				}
			});
		}
		tp.disableWireless();
		tp.setClockSpeed(100);
		JViewer jv = new JViewer(tp);
		jv.getJTopology().addNodePainter(new RobotPainter());
		jv.getJTopology().addNodePainter(new ObstaclePainter());
		tp.start();
		boolean variable=false;
		do{
			  synchronized (tp) {
			    variable=(tp.robots.size()==1);
			  }
			
			
		}while(!variable);
		tp.pause();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Experiment number "+j+" :");
		//print the Metrics distances and activation
		System.out.println("total activations:" + DestMeasurements.total_Activation);
		System.out.println("total distances:" + DestMeasurements.total_Distance);
		System.out.println("total moves:" + DestMeasurements.total_Moves);	
		//compute the average, min and max
		if(j==1)
		{
			activation_min = DestMeasurements.total_Activation;
			distance_min =DestMeasurements.total_Distance;
			moves_min = DestMeasurements.total_Moves;
		}
		
		activation_min =Math.min(activation_min, DestMeasurements.total_Activation);
		distance_min =  Math.min(distance_min, DestMeasurements.total_Distance);
		moves_min = Math.min(moves_min, DestMeasurements.total_Moves);
		System.out.println("minimal activation: " + activation_min);
		System.out.println("minimal distance: " + distance_min);
		System.out.println("minimal moves: " + moves_min);
		
		activation_max = Math.max(activation_max, DestMeasurements.total_Activation);
		distance_max = Math.max(distance_max, DestMeasurements.total_Distance);
		moves_max =  Math.max(moves_max, DestMeasurements.total_Moves);
		System.out.println("maximal activation: " + activation_max);
		System.out.println("maximal distance: " + distance_max );
		System.out.println("maximal moves: " + moves_max ); 
		
		activation_sum = activation_sum + DestMeasurements.total_Activation;
		distance_sum = distance_sum + DestMeasurements.total_Distance;
		moves_sum = moves_sum + DestMeasurements.total_Moves;
		activation_avg = activation_sum/j;
		distance_avg = distance_sum/j;
		moves_avg = moves_sum/j;
		System.out.println("average activation: " + activation_avg);
		System.out.println("average distance: " + distance_avg );
		System.out.println("average moves: " + moves_avg ); 
		
		DestMeasurements.reset();
		}catch (Exception e) {
			System.out.println("Experiment number "+ j+" failed");
			DestMeasurements.reset();
			j--;
		}
	  }
	}
  }
