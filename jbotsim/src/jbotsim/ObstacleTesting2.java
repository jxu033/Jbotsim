package jbotsim;

import java.awt.geom.Point2D;
import java.util.List;

import jbotsim.ui.JViewer;
import jbotsim.ui.painting.ObstaclePainter;
import jbotsim.ui.painting.RobotPainter;

public class ObstacleTesting2 {
	public static void main(String[] args)
	  {
			Topology tp = new Topology(false);
	   
			//first obstacle
			double[] xpoints = {100,150,150,100};
			double[] ypoints = {100,100,200,200};
			Obstacle obstacle1 = new Obstacle(xpoints, ypoints, 4);
	   
			//second obstacle
			double[] xpointsStar = {200,300,400,400};
			double[] ypointsStar = {100,200,100,400};
			Obstacle obstacle2 = new Obstacle(xpointsStar, ypointsStar, 4);
			
			tp.addObstacle(obstacle1);
			tp.addObstacle(obstacle2);
			
			//add robot
			 FullVisRobot r1 = new FullVisRobot(new Point2D.Double(50, 50));
			 r1.name = "A";
		     tp.addRobot(r1);
		     
		     
		    List<Segment> visArea =  r1.getVisibleSegments();
		    Point2D left;
		    Point2D right;
		    System.out.println("there are "+visArea.size()+" can be see by this robot:");
		    for(Segment s:visArea)
		    {
		    	left = s.left;
		    	right=s.right;
		    	System.out.println("segment from " +s.left+ " to "+s.right);
		    }
		    
		    List<Point2D> visVertexes = r1.getVisibleVertexesOfObstacles();
		    System.out.println(visVertexes);
			
		    JViewer jv = new JViewer(tp);
		    jv.getJTopology().addNodePainter(new RobotPainter());
			jv.getJTopology().addNodePainter(new ObstaclePainter());
			
	  }
}
