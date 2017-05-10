package jbotsim;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jiaqi Xu on 2017-03-023.
 */
public class SnapShot {

	
	//these three are used for presence of obstacles: Written by Jiaqi Xu
    List<Robot> visRobots =  new ArrayList<Robot>();
    List<Segment> visSegments = new ArrayList<Segment>(); 
    List<Point2D> visVertexesOfObstacles = new ArrayList<Point2D>();
    
  
    void addVisibleRobots(Robot robot)
    {
    	this.visRobots = robot.getVisibleRobots();
    }
       
    void addVisibleSegments(Robot robot){
        this.visSegments = robot.getVisibleSegments();
    }
    
    void addVisibleVertexes(Robot robot){
        this.visVertexesOfObstacles = robot.getVisibleVertexesOfObstacles();
    }
    
    void getSnapShot(Robot robot)
    {
       this.addVisibleRobots(robot);
       this.addVisibleSegments(robot);
       this.addVisibleVertexes(robot);
    }
    
  //used for obstructed visibility : written by Haochuan Ran
    public List<Robot> getRobots() {
        return visRobots;
    }

    public void setRobots(ArrayList<Robot> robots) {
        this.visRobots = robots;
    }

    
    
}