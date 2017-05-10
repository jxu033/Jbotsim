package jbotsim;

import java.util.List;
import java.util.Random;

import jbotsim.ui.JViewer;
import jbotsim.ui.painting.LabeledLinkPainter;
import jbotsim.ui.painting.RobotPainter;
import jbotsimx.topology.TopologyGenerator;

public class DiscreteEnvironmentTesting {
	public static void main(String[] args)
    {
    	Topology tp = new Topology(false);
    	
    	TopologyGenerator.generateDiscreteGrid(tp, 3, 4);
    	List<Node> points = tp.getNodes();
    	Node startPoint = points.get(new Random().nextInt(12));
    	RobotDiscrete robotDiscrete = new RobotDiscrete(startPoint);
    	tp.addRobot(robotDiscrete);
//    	RobotDiscrete robotDiscrete2  = new RobotDiscrete(startPoint);
// 		tp.addRobot(robotDiscrete2);
    
    	System.out.println("There are " +robotDiscrete.getNumberOfRobotsAtMyLocation()+ " robots at my location");
    	System.out.println("There are " + tp.robots.size() + " robots in the topology");
	    System.out.println("I am at position:" + robotDiscrete.mylocation.getX()+"," + robotDiscrete.mylocation.getY());
	    List<LabeledLink> links = robotDiscrete.getLabeledLinks();
	    System.out.println("There are " +links.size()+" links for robot to go from current location" );
	    List<Integer> Ports =  ((SnapShotDiscrete)robotDiscrete.look()).getOutPort();
	    //choose one port to go
	    int chosenPort = Ports.get(new Random().nextInt(Ports.size()));
    //    robotDiscrete.move(chosenPort);
	    System.out.println("My current location is: "+ robotDiscrete.mylocation.getX() +","+ robotDiscrete.mylocation.getY());
	    List<Node> a = tp.getNodes();
	  	System.out.println(a);
	  
	   
    
	  JViewer jv = new JViewer(tp);
	 //jv.getJTopology().setDefaultNodePainter(new RobotPainter());
	 jv.getJTopology().setLinkPainter(new LabeledLinkPainter());
	 jv.getJTopology().addNodePainter(new RobotPainter());	 
	  tp.setClockSpeed(500);
	 tp.start();
	  
    	
    }
}
