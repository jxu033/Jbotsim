package jbotsim;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;



public class Obstacle extends Node {
   	
	public Polygon2D polygon2d;
	
	public List<Point2D>  vertexesOfObstacle = new ArrayList<Point2D>();
	public List<Segment>  segmentsOfObstalce = new ArrayList<Segment>();
	    
	double vectorX ;
	double vectorY ;
	
	/**
	 * Constructor of obstacle
	 * @param xpoints 
	 * @param ypoints
	 * @param npoints
	 */
    public Obstacle(double[] xpoints, double[] ypoints, int npoints)
    {
    	this.coords = new Point3D(xpoints[0], ypoints[0],0);
    	this.disableWireless();
    	
    	//obtain all the vertexes of this obstacle
    	for(int i = 0; i < npoints; i++)
    	{
    		vertexesOfObstacle.add(new Point2D.Double(xpoints[i], ypoints[i]));
    	}
    	
    	//obtain all the segments of this obstacle
    	for(int i =0; i< npoints; i++)
    	{
    		if(i!=npoints-1)
    		{
    			Point2D end1 = new Point2D.Double(xpoints[i], ypoints[i]);
    			Point2D end2 = new Point2D.Double(xpoints[i+1], ypoints[i+1]);
    			segmentsOfObstalce.add(new Segment(end1, end2));
    		}
    		else {
    			Point2D end1 = new Point2D.Double(xpoints[npoints-1], ypoints[npoints-1]);
    			Point2D end2 = new Point2D.Double(xpoints[0], ypoints[0]);
    			segmentsOfObstalce.add(new Segment(end1,end2));
			}
    		
    		
    	}
    	
    	
    	
       //obtain the vector 
       this.vectorX = this.coords.getX() - 0;
       this.vectorY = this.coords.getY() - 0;
       
       
       // initialize the polygon2d object according to vertices of the obstacle 
       polygon2d = new Polygon2D(xpoints, ypoints, npoints);      
    }
    
    //test contains function
    public static void main(String[] args)
    {
    	double[] xpoints = { 100, 150, 150, 100 };  //convex polygon: rectangle
		double[] ypoints = { 100, 100, 200, 200 };
		Polygon2D polygon2d = new Polygon2D(xpoints, ypoints, 4);
		Point2D p = new Point2D.Double(100, 200);
		boolean a =polygon2d.IsPointContained(p);
		System.out.println("If the point is at the vertex, return " + a);
    	
		p = new Point2D.Double(100,150);
		a=polygon2d.IsPointContained(p);
		System.out.println("If the point is on the boundary(exclude vertexes), return " + a);
		
		p=new Point2D.Double(50,50);
		a= polygon2d.IsPointContained(p);
		System.out.println("If the point is outside the polygon, return " + a);
		
		p=new Point2D.Double(120, 120);
		a=polygon2d.IsPointContained(p);
		System.out.println("If the point is inside the polygon, return " + a);
    	
    	
     
		
		/*double[] xpoints = { 200, 300, 400, 400 }; //concave polygon
		double[] ypoints = { 100, 200, 100, 400 };
		Polygon2D polygon2d = new Polygon2D(xpoints, ypoints, 4);
		Point2D p = new Point2D.Double(400, 400);
		boolean a =polygon2d.IsPointContained(p);
		System.out.println("If the point is at the vertex, return " + a);
    	
		p = new Point2D.Double(400,150); //p(400,150) is on the boundary, but it return false
		a=polygon2d.IsPointContained(p);
		System.out.println("If the point is on the boundary(exclude vertexes), return " + a);
		
		p=new Point2D.Double(150,25);
		a= polygon2d.IsPointContained(p);
		System.out.println("If the point is outside the polygon, return " + a);
		
		p=new Point2D.Double(250,170);
		a=polygon2d.IsPointContained(p);
		System.out.println("If the point is inside the polygon, return " + a);*/
    	
    	
    	/*double[] xpoints = { 50, 100, 100}; //convex polygon : triangle
		double[] ypoints = { 50, 50,  100}; 
		Polygon2D polygon2d = new Polygon2D(xpoints, ypoints, 3);
		Point2D p = new Point2D.Double(100, 100);
		boolean a =polygon2d.IsPointContained(p);
		System.out.println("If the point is at the vertex, return " + a);
    	
		p = new Point2D.Double(75,75); 
		a=polygon2d.IsPointContained(p);
		System.out.println("If the point is on the boundary(exclude vertexes), return " + a);
		
		p=new Point2D.Double(200,0);
		a= polygon2d.IsPointContained(p);
		System.out.println("If the point is outside the polygon, return " + a);
		
		p=new Point2D.Double(75, 60);
		a=polygon2d.IsPointContained(p);
		System.out.println("If the point is inside the polygon, return " + a);*/
    }
    
    /**
     * check if a point is inside the obstacle
     * @param p
     * @return true if p is inside the obstacle, otherwise, return false.
     */
    public boolean contains(Point2D p)
    {
    	return this.polygon2d.IsPointContained(p);
    }
    
    /**
     * check if a node is inside the obstacle
     * @param n 
     * @return true if n is inside the obstacle, otherwise, return false.
     */
    public boolean contains(Node n)
    {
    	return this.polygon2d.contains(n.getX(),n.getY());
    }
    
    /**
     * 
     * @param segment
     * @return true if this obstacle intersects with given segment, otherwise return false
     */
    public boolean intersectsWithSegment(Segment segment)
    {
    	for(Segment s : this.segmentsOfObstalce)
    	{
    		if(s.HasIntersectionWithSegment(segment))
    		{
    		     return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Given a segment
     * @param segment
     * @return all the intersections if the number of intersections is finite
     */
    public List<Point2D> intersections(Segment segment)
    {
    	List<Point2D> intersections =  new ArrayList<Point2D>();
    	for(Segment s : this.segmentsOfObstalce)
    	{
    		Point2D intersection = segment.intersectionWithSegment(s);
    		if(intersection !=null)
    		intersections.add(intersection);
    	}
    	return intersections;
    }
      
}
