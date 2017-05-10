package jbotsim;

import java.awt.geom.Point2D;

/**
 * 
 * @author JiaqiXu
 *
 */
public class Destination2D extends Destination {
   public Point2D dest =null;

   public Destination2D(){}

   public Destination2D(Point2D dest) {
	   System.out.println("a"+dest);
      this.dest = dest;
   }
}
