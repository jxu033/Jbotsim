package jbotsim.ui.painting;

import jbotsim.Node;
import jbotsim.Topology;

import java.awt.*;

/**
 * Created by acasteig on 10/19/15.
 */
public class DefaultBackgroundPainter implements BackgroundPainter {
    @Override
    public void paintBackground(Graphics2D g2d, Topology tp) {
        g2d.setStroke(new BasicStroke(1));
        for(Node n : tp.getNodes()){
        	if(n.getName() !=null)
        		 g2d.drawString(n.getName(), (float)n.getLocation().getX()-10, (float)n.getLocation().getY()-10);
        	
            double sR=n.getSensingRange();
            if(sR>0){
                g2d.setColor(Color.gray);
                g2d.drawOval((int)n.getX()-(int)sR, (int)n.getY()-(int)sR, 2*(int)sR, 2*(int)sR);
            }
        }
    }
}
