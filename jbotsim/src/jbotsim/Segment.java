package jbotsim;

import java.awt.geom.Point2D;
import java.time.chrono.MinguoChronology;

public class Segment {
     Point2D left;
     Point2D right;
     
     Segment()
     {	 
    	 
     }
     
     Segment(Point2D end1, Point2D end2)
     {
    	 this.left  = end1;
    	 this.right = end2;
     }
     
     
     
     @Override
	public String toString() {
		return "Segment [left=" + left + ", right=" + right + "]";
	}

	/**
      * Given a segment
      * @return a intersection point with the given segment
      *  only use this function to get intersection point. if two segments in the same line, still return null. so be careful 
      */
     public Point2D.Double intersectionWithSegment(Segment segment)
     {
    	 double x1 = this.left.getX();
    	 double y1 = this.left.getY();
    	 double x2 = this.right.getX();
    	 double y2 = this.right.getY();
    	 double x3 = segment.left.getX();
    	 double y3 = segment.left.getY();
    	 double x4 = segment.right.getX();
    	 double y4 = segment.right.getY();
    	 
    	 double d = (x1-x2)*(y3-y4)-(y1-y2)*(x3-x4);
     	 if(d ==0) return null;
     	
     	 double xi =  ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
     	 double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
     	 
     	 xi=(double)Math.round(xi*10000000d)/10000000d;
    	 yi=(double)Math.round(yi*10000000d)/10000000d;	 
   
     	 Point2D.Double p = new Point2D.Double(xi,yi); 
     	 
     	 if (yi < Math.min(y1,y2) || yi > Math.max(y1,y2)) return null; 
     	 
     	 if (yi < Math.min(y3,y4) || yi > Math.max(y3,y4)) return null;
     	 
     	 if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return null;
     	 
     	 if (xi < Math.min(x3,x4) || xi > Math.max(x3,x4)) return null;
     	 
     	 return p;
     }
     
     
     public static void main(String[] args)
     {
    	 Point2D p1 = new Point2D.Double(0,	0);
    	 Point2D p2 = new Point2D.Double(2,2);
    	
    	 Segment s1 = new Segment(p1, p2);
        
    	 Point2D p3 = new Point2D.Double(2,	2);
    	 Point2D p4 = new Point2D.Double(2,3);
    	 
    	 Segment s2 = new Segment(p3, p4);
    	 
    	Point2D resultPoint2d = s1.intersectionWithSegment(s2);
    	System.out.println(resultPoint2d);
     }
     
     /**
      * 
      * @param segment
      * @return true if there exist a intersection point, otherwise false.
      */
     public boolean HasIntersectionWithSegment(Segment segment)
     {
    		 double x1 = this.left.getX();
    	     double y1 = this.left.getY();
    	     double x2 = this.right.getX();
    	     double y2 = this.right.getY();
    	     double x3 = segment.left.getX();
    	     double y3 = segment.left.getY();
    	     double x4 = segment.right.getX();
    	     double y4 = segment.right.getY();
    	     
    	     double d = (x1-x2)*(y3-y4)-(y1-y2)*(x3-x4);
    	     double x_Min_1=Math.min(x1, x2);
    	     double x_Max_1=Math.max(x1,x2);
    	     double x_Min_2=Math.min(x3, x4);
    	     double x_Max_2=Math.max(x3,x4);
    	     
         	 if(d==0)
         	 {
         		 if(this.AreInSameLine(segment))
         		 {
         			 if(   ( x3 <=x_Max_1 && x3 >= x_Min_1) || (x4 <=x_Max_1 && x4 >= x_Min_1)
         				|| ( x1 <=x_Max_2 && x1 >= x_Min_2) || (x2 <=x_Max_1 && x2 >= x_Min_1) )
         				 return true;    
         			 else {
						return false;
					}
         		 }
         		 else return false;
         	 }
         	 else	
         	 {
         		double xi =  ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
            	double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
            	xi=(double)Math.round(xi*10000000d)/10000000d;
           	    yi=(double)Math.round(yi*10000000d)/10000000d;	 
            	 if (yi < Math.min(y1,y2) || yi > Math.max(y1,y2)) return false; 
            	 
            	 if (yi < Math.min(y3,y4) || yi > Math.max(y3,y4)) return false;
            	 
            	 if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return false;
            	 
            	 if (xi < Math.min(x3,x4) || xi > Math.max(x3,x4)) return false;         
            	 
            	 return true;
         	 }
     }
    
     /**
      * 
      * @param segment
      * @return intersection point with segment as a straight line
      */
     public Point2D.Double intersectionWithSegmentAsLine(Segment segment)
     {
    	 double x1 = this.left.getX();
    	 double y1 = this.left.getY();
    	 double x2 = this.right.getX();
    	 double y2 = this.right.getY();
    	 double x3 = segment.left.getX();
    	 double y3 = segment.left.getY();
    	 double x4 = segment.right.getX();
    	 double y4 = segment.right.getY();
    	 double x_max = Math.max(x3, x4);
    	 double x_min = Math.min(x3, x4);
    	 
    	 
    	 double d = (x1-x2)*(y3-y4)-(y1-y2)*(x3-x4);
     	 if(d ==0) return null;
     	
     	 double xi =  ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
     	 double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
     	 xi=(double)Math.round(xi*10000000d)/10000000d;
   	     yi=(double)Math.round(yi*10000000d)/10000000d;	
   	     
   	     if(xi >x_max || xi<x_min) return null;
   	     
     	 Point2D.Double p = new Point2D.Double(xi,yi); 
     	 return p;
     }
     
     /**
      * 
      * @return Slope of this segment
      */
     public double getSlope()
     {
    	 double x1 = this.left.getX();
    	 double y1 = this.left.getY();
    	 double x2 = this.right.getX();
    	 double y2 = this.right.getY();
         if(x1!=x2)
        	 return (y2-y1)/(x2-x1);
         else
         {
        	 throw new ArithmeticException("slope does not exsit");
         }
     }
     
     
     
     
     /**
      * 
      * @param p Given a point
      * @return true if this point on the segment, otherwise return false
      */
     public boolean isPointOnSegment(Point2D p)
     {
    	 Segment segment  = new Segment(this.left,p);
    	 
    	 double x1 = this.left.getX();
    	 double y1 = this.left.getY();
    	 double x2 = this.right.getX();
    	 double y2 = this.right.getY();
    	 
    	 double xi = p.getX();
    	 double yi = p.getY();
    	 
    	 if(p.equals(this.left)||p.equals(this.right)) return true;
    	 
         if (yi < Math.min(y1,y2) || yi > Math.max(y1,y2)) return false; 
     	 
     	 if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return false;
     	 
     	 return AreInSameLine(segment);
     }
     
     /**
      * Given a segment
      * @param segment
      * @return true if this segment and segment are in the same line, otherwise, return false
      */
     public boolean AreInSameLine(Segment segment)
     {
    	 double x1 = this.left.getX();
    	 double y1 = this.left.getY();
    	 double x2 = this.right.getX();
    	 double y2 = this.right.getY();
    	 double x3 = segment.left.getX();
    	 double y3 = segment.left.getY();
    	 double x4 = segment.right.getX();
    	 double y4 = segment.right.getY();
    	 
    	 double k1;
    	 double b1;
    	 double k2;
    	 double b2;
    	 
    	 //y= kx +b
         if(x1 != x2)
         {
    	   k1 = (y1-y2)/(x1-x2);
    	   b1 = y1-k1*x1;
         }
         else if(x1 != x3)
        	 return false;
         else if(x3==x4)
        	 return true;
         else 
        	 return false;
			
    	//x1 ! = x2
		if(x3 != x4)
		{
			 k2 = (y3-y4)/(x3-x4);
		     b2 = y3-k2*x3;
		}
		else //x3 =x4 
			return false;
    	 
		k1=(double)Math.round(k1*10000000d)/10000000d;
    	k2=(double)Math.round(k2*10000000d)/10000000d;
    	b1=(double)Math.round(b1*10000000d)/10000000d;
    	b2=(double)Math.round(b2*10000000d)/10000000d;
		if(Math.pow(k1-k2, 2) <0.00000001 &&Math.pow(b1-b2, 2)<0.00000001)
		{
			return true;
		}
		else {
			return false;
		} 	 
     }
}
