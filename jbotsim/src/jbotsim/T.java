package jbotsim;



public class T {
	public static void main(String[]args)
	{
		Topology tp = new Topology(false);

		// add two obstacles
		double[] xpoints = { 100, 150, 150, 100 };
		double[] ypoints = { 100, 100, 200 , 200 };
		Obstacle obstacle1 = new Obstacle(xpoints, ypoints, 4);

		double[] xpointsStar = { 200, 300, 400,400};
		double[] ypointsStar = { 100, 200, 100,400 };
		Obstacle obstacle2 = new Obstacle(xpointsStar, ypointsStar, 4);
	}
}
