package jbotsim;

import java.awt.geom.Point2D;

import com.sun.javafx.scene.paint.GradientUtils.Point;

public class methodTest {
	
	public  static void main(String[] args)
	{  
		/*Point2D p1 = new Point2D.Double(60,60);
		Point2D p2 = new Point2D.Double(90,90);
		Segment presentVisSeg = new Segment(p1,p2);
		
		Point2D p3 = new Point2D.Double(10,10);
		Point2D p4 = new Point2D.Double(40,40);
		Segment s = new Segment(p3, p4);
		
		Point2D p5 = new Point2D.Double(0, 0);
		
		 Segment r_left = new Segment(p5,presentVisSeg.left);
		 Segment r_right= new Segment(p5,presentVisSeg.right);
		 
		 double[] xpoints = {presentVisSeg.left.getX(),presentVisSeg.right.getX(), p5.getX()};
		 double[] ypoints = {presentVisSeg.left.getY(),presentVisSeg.right.getY(), p5.getY()};
		 Polygon2D polygon2d = new Polygon2D(xpoints, ypoints, xpoints.length);
		 
		 boolean a = polygon2d.contains(p4);
		  System.out.println(a);
		  
		 if(r_left.HasIntersectionWithSegment(s)
					&&r_right.HasIntersectionWithSegment(s))//case 1: segment is blocked entirely by segment s
		   System.out.println("true");*/
		   
		
		
		double[] xpoints = {50, 150, 150};
		double[] ypoints = {50,100,200};
	   Polygon2D aa = new Polygon2D(xpoints, ypoints, 3);
	  Point2D p1 = new Point2D.Double(100,125);
	  Point2D p2 = new Point2D.Double(150,100);
	  Point2D p3 = new Point2D.Double(100, 100);
	  
	  Obstacle o = new Obstacle(xpoints, ypoints, 3);
	  boolean a = o.polygon2d.IsPointContained(p1);
	  System.out.println("when point p on the boundary exclude vertexes, contains return " + a);
	  
	   a = o.polygon2d.IsPointContained(p2);
	   System.out.println("when point p on the vertexes, contains return " + a);
	   
	   a = o.polygon2d.IsPointContained(p3);
	   System.out.println("when point p inside polygon, contains return " + a);
		
//		double[] xpoints = {225, 200, 50};
//		double[] ypoints = {137.5,100,50};
//		 Polygon2D polygon2d = new Polygon2D(xpoints, ypoints, 3);
//		 
//		 Point2D sleft = new Point2D.Double(150,100);
//		 Point2D sright = new Point2D.Double(150,200);
//		 
//		if(polygon2d.IsPointContained(sleft))
//			System.out.println("aa");
	  
	}
}
