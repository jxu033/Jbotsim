package jbotsim.ui.painting;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;

import jbotsim.LabeledLink;
import jbotsim.Link;
import jbotsim.Topology;

/**
 * 
 * @author JiaqiXu
 *
 */
public class LabeledLinkPainter extends LinkPainter {

	
	public void paintLink(Graphics2D g2d, Link link) {
		LabeledLink llink=(LabeledLink)link;
        Integer width=llink.getWidth();
        if (width==0)
            return;
        g2d.setColor(llink.getColor());
        g2d.setStroke(new BasicStroke(width));
        int srcX=(int)llink.source.getX(), srcY=(int)llink.source.getY();
        int destX=(int)llink.destination.getX(), destY=(int)llink.destination.getY();
        g2d.drawLine(srcX, srcY, destX, destY);
        
       //draw label1 of link
        double px1 = 0.25*(destX -srcX)+srcX;
        double py1 = 0.25*(destY -srcY)+srcY;
        int l1= llink.getPort(llink.source);
        String lable1 = String.valueOf(l1);
        g2d.drawString(lable1, (float)px1, (float)py1);
        
        //draw label2 of link 
        double px2 = 0.75*(destX-srcX) +srcX;
        double py2=0.75*(destY-srcY)+srcY;
        int l2 = llink.getPort(llink.destination);
        String lable2 = String.valueOf(l2);	
        g2d.drawString(lable2, (float)px2, (float)py2);
       
       	
        Topology topology = llink.source.getTopology();
        if (topology.hasDirectedLinks()) { // FIXME sometimes topology is null here
            int x=srcX+4*(destX-srcX)/5-2;
            int y=srcY+4*(destY-srcY)/5-2;
            g2d.drawOval(x,y,4,4);
        }
    }
}
