package jbotsim;

import java.awt.geom.Point2D;
import java.util.List;

import jbotsim.ui.JViewer;
import jbotsim.ui.painting.ObstaclePainter;
import jbotsim.ui.painting.RobotPainter;

public class ObstacleTesting {
	public static void main(String[] args){
		 Topology tp = new Topology(false);;
		 double[] xpoints = { 100, 150, 150, 100 };
	     double[] ypoints = { 100, 100, 200 , 200 };
	     Obstacle obstacle = new Obstacle(xpoints, ypoints, 4);
	     
	     
	     //if a point p is in the boundary of the obstacle, which value will be returned 
	    boolean a = obstacle.contains(new Point2D.Double(90, 150));
	    System.out.println(a);
	     
	     double[] xpointsStar = {200,300,400,400};
	     double[] ypointsStar = {100,200,100,400};
	     Obstacle obstacle1 = new Obstacle(xpointsStar, ypointsStar, 4);
	   //  obstacle.intersections(50, 50, 120, 120);
	    // Point2D point2d = new Point2D.Double(200,200);
	   // boolean y = obstacle.contains(point2d);
	   // System.out.println(y);
	     tp.addObstacle(obstacle);
	     tp.addObstacle(obstacle1);
	     FullVisRobot r1 = new FullVisRobot(new Point2D.Double(50, 50));
	     tp.addRobot(r1);
	     FullVisRobot r2 = new FullVisRobot(new Point2D.Double(100, 50));
	     tp.addRobot(r2);
	     FullVisRobot r3 = new FullVisRobot(new Point2D.Double(100, 310));
	     tp.addRobot(r3);
	     FullVisRobot r4 = new FullVisRobot(new Point2D.Double(300, 300));
	     tp.addRobot(r4);
	     FullVisRobot r5 = new FullVisRobot(new Point2D.Double(550, 200));
	     tp.addRobot(r5);
	  
	     
	     System.out.println("My location is "+ r1.getLocation());
	    
	     System.out.println("There are "+tp.robots.size() + " robots in topology ");
	    List<Robot> visibleRobots = r1.getVisibleRobots();
	    
	    System.out.println("There are "+visibleRobots.size() + " robots can be seen by r1, they are: ");
	    System.out.println("There are "+tp.robots.size() + " robots in topology ");
	    for(int i =0 ; i<visibleRobots.size(); i++)
	    {
	     System.out.println("robot at: " + visibleRobots.get(i).getLocation());
	    }
	     JViewer jv = new JViewer(tp);
	     jv.getJTopology().addNodePainter(new RobotPainter());
	     jv.getJTopology().addNodePainter(new ObstaclePainter()) ;
		}
		
}
