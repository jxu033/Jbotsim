package jbotsim;

/**
 * Created by JiaqiXu
 */
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RobotDiscrete extends Robot {
    //this is the second commit, test for git 
	public int arrivedport;
     public Node mylocation=null;
	
     
     
     public  RobotDiscrete(Node currentlocation) {
		// TODO Auto-generated constructor stub
       this.mylocation = currentlocation;
       //it is important to  disable wireless for robot
       this.disableWireless();
       this.color=Color.red;
       this.size=25;
       this.setLocation(mylocation.getX(),mylocation.getY());
	}
     
     @Override
	    public void onPreClock() {
	        
	        snap=this.look();
	    }

	    @Override
	    public void onClock() {
	        
	        dest=this.compute(snap);
	        this.moving=true;
	    }

	    @Override
	    public void onPostClock() {
	       
	        this.move(dest);
	    }
     
     
     //return all the labeledLinks that this robot can go from current location
     public List<LabeledLink> getLabeledLinks()
     {
    	 List<LabeledLink> labeledLinks =new ArrayList<LabeledLink>();
//    	 for (LabeledLink l : topo.labeledLinkeds) {
//		      if(l.labeledendpoint1 == this.mylocation || l.labeledendpoint2 ==this.mylocation)
//		    	  labeledLinks.add(l);
//		} 
    	 List<Link> links = new ArrayList<Link>();
    	links = this.mylocation.getLinks();
    	for(Link l : links)
    	{
    		labeledLinks.add((LabeledLink)l);
    	}
    	 return labeledLinks;
     }
     
     /**
      * return the link used by this robot when given an outport
      * @param outport
      * @return
      */
     public LabeledLink getLink(int outport)
     {
    	 for(LabeledLink l : this.getLabeledLinks())
    	 {
    		 if((l.getLabeledendpoint1() ==this.mylocation && l.getLabel1() ==outport) ||
    				 (l.getLabeledendpoint2()==this.mylocation && l.getLabel2() ==outport))
    			 return l;
    	 }
		return null;
     }
     
	
	/**
	 * Return the snapshot of robot in discrete environment
	 * In the discrete environment, the snapshot is all the labeled ports associated with the location of robot
	 */
	@Override
	public SnapShot look() {
		if(this.mylocation ==null)
			return null;
		
	  SnapShotDiscrete snapDiscret = new SnapShotDiscrete();
	  for(LabeledLink l : this.getLabeledLinks())
	  {
		  if(l.getLabeledendpoint1() ==this.mylocation) snapDiscret.outports.add(l.getLabel1());
		  if(l.getLabeledendpoint2() ==this.mylocation) snapDiscret.outports.add(l.getLabel2());
	  }
	  
	  return snapDiscret;
		
	}

	@Override
	public Destination compute(SnapShot snap) {
	     SnapShotDiscrete snapShotDiscrete = (SnapShotDiscrete)snap;
	     DestinationDiscrete destinationDiscrete  = new DestinationDiscrete();
	     int index = new Random().nextInt(snapShotDiscrete.outports.size());
	     destinationDiscrete.outport = snapShotDiscrete.outports.get(index);
	     return destinationDiscrete;
	}
	
	@Override
	public void move(Destination a)
	{
		//for robotDiscrete, if destination.outport = -1, it means robot will stay on site. 
		DestinationDiscrete destinationDiscrete = (DestinationDiscrete)a;
		if(destinationDiscrete.outport  != -1)
		{
	     move(destinationDiscrete.outport);
		}
	}
	
	
	public void move(int outport) {
		LabeledLink l = this.getLink(outport);
		if(l.getLabel1() ==outport)
		{
		  this.setLocation(l.getLabeledendpoint2().getX(),l.getLabeledendpoint2().getY());
		  this.mylocation = l.getLabeledendpoint2();
		  this.arrivedport = l.getLabel2();
		}
		if(l.getLabel2() ==outport)
		{
			this.setLocation(l.getLabeledendpoint1().getX(),l.getLabeledendpoint1().getY());
			this.mylocation =l.getLabeledendpoint1();
			this.arrivedport =l.getLabel1();
		}
				
	}
	
	

	/**
	 * Return the number of robots at the same location as this robot(contains itself)
	 * @return
	 */
	public int getNumberOfRobotsAtMyLocation()
	{
		List<Robot> robotsAtMyLocation = new ArrayList<>();
		for(Robot robot : this.topo.robots)
		{
			if(((RobotDiscrete)robot).mylocation == this.mylocation)
				robotsAtMyLocation.add(robot);
		}
		
		return robotsAtMyLocation.size();
	}
	
}
