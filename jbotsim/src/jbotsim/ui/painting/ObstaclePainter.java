package jbotsim.ui.painting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.nio.file.Path;

import sun.security.provider.certpath.Vertex;
import jbotsim.Link;
import jbotsim.Node;
import jbotsim.Obstacle;
import jbotsim.ui.JNode;

public class ObstaclePainter implements NodePainter  {

	@Override
	public void paintNode(Graphics2D g2d, Node node) {
		 if(node instanceof Obstacle)
		 {
			 g2d.setColor(Color.DARK_GRAY);
		     g2d.setStroke(new BasicStroke(1));
		     g2d.draw(((Obstacle) node).polygon2d);	     
		 
		 }    
	}
	
}

