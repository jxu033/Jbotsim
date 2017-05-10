package jbotsim.ui.painting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import jbotsim.Node;
import jbotsim.Obstacle;
import jbotsim.Robot;
import jbotsim.ui.JNode;

public class RobotPainter  implements NodePainter{

	@Override
	public void paintNode(Graphics2D g2d, Node node) {
		if(node instanceof Robot)
	       {
	    	   JNode jn=(JNode)node.getProperty("jnode");
	    	   int drawSize = jn.getWidth()/2;
	    	   if( node.getName() != null)
	    	   {
	    		   String name = node.getName();
	    		   g2d.setStroke(new BasicStroke(2));
	    		   g2d.setColor(Color.red);
	    	      // g2d.drawString(name, (float)(node.getLocation().getX()-100), (float)(node.getLocation().getY()-100));
	    	    //   g2d.drawString(name, 300, 400);
	    	   }
	    	   
	    	   if (node.getColor() != null){
	    		   g2d.setColor(node.getColor());
	    		   g2d.fillOval(drawSize/2, drawSize/2, drawSize, drawSize);
	        	}
	       }
	}
  
}
