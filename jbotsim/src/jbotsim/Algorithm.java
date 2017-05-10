package jbotsim;

import jbotsim.ui.JViewer;
import jbotsim.ui.painting.RobotPainter;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Algorithm {
	public static void main(String args[]) {

            System.out.println("Starting a new simulation");
            Topology tp = new Topology(1500, 1500);
            
            tp.setDefaultNodeModel(MyRobot.class);

            tp.setNodeScheduler(new AsynchrRobotScheduler());
            tp.setClockSpeed(100);
            tp.disableWireless();

//            for (int i = 0; i < 10; ) {
//                Point2D point2D =
//                        new Point2D.Double(new Random().nextInt(tp.getWidth() / 20),
//                                new Random().nextInt(tp.getHeight()) / 20);
//                MyRobot robot =
//                        new MyRobot(point2D, Double.POSITIVE_INFINITY, null, false);
//
//                if (!isRepeated(robot, tp)) {
//                    tp.addRobot(robot);
//                    i++;
//                }
//            }


        MyRobot robot1 =
                new MyRobot(new Point2D.Double(100,100), Double.POSITIVE_INFINITY, null, false);
        MyRobot robot2 =
                new MyRobot(new Point2D.Double(100,300), Double.POSITIVE_INFINITY, null, false);
        MyRobot robot3 =
                new MyRobot(new Point2D.Double(100,500), Double.POSITIVE_INFINITY, null, false);
        MyRobot robot4 =
                new MyRobot(new Point2D.Double(100,200), Double.POSITIVE_INFINITY, null, false);
        MyRobot robot5 =
                new MyRobot(new Point2D.Double(100,400), Double.POSITIVE_INFINITY, null, false);
        tp.addRobot(robot1);
        tp.addRobot(robot2);
        tp.addRobot(robot3);
        tp.addRobot(robot4);
        tp.addRobot(robot5);
         //new JViewer(tp);
         JViewer jv = new JViewer(tp);
 		jv.getJTopology().addNodePainter(new RobotPainter());
            tp.start();
//            boolean nterminate = true;
//            do {
//                nterminate = true;
//                for (Robot robot : tp.robots) {
//                    nterminate = nterminate && ((MyRobot) robot).algorithmMoved;
//                }
//            } while (!nterminate);
//            tp.pause();
//            System.out.println("Terminate...............");
//
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//
//            }
//
//            for (Robot robot : tp.robots) {
//                if (robot.snap.visRobots.size() != tp.robots.size() - 1) {
//                    System.exit(-1);
//                }
//            }

        }


    private static boolean isRepeated(MyRobot myRobot, Topology tp) {
        for (Robot robot1 : tp.robots) {
            if (myRobot.coords.getX() == robot1.coords.getX()
                    && myRobot.coords.getY() == robot1.coords.getY()) {
                return true;
            }
        }
        return false;
    }
}

class MyRobot extends ObstrucVisRobot {
    private static int numberOfRobotsWithFullVis = 0;
    private boolean canSeeAll;
    private boolean fullVis;
    private boolean algorithmMoving;
    boolean algorithmMoved;
    Point2D initialPosition;

    public MyRobot(Point2D currentLocation, Double sensingRange, Volume volume, boolean rigidMove) {
        super(currentLocation, sensingRange, volume, rigidMove);
        this.color=Color.red;
        this.initialPosition = currentLocation;
    }

    @Override
    public Destination compute(SnapShot snap) {

        if (!this.algorithmMoved) {
            return this.algorithm();
        }

        if (!this.fullVis && this.snap.visRobots.size() == this.getSensedRobots().size()) {
            this.canSeeAll = true;
            numberOfRobotsWithFullVis++;
            this.fullVis = true;
            System.out.println("numberOfRobotsWithFullVis now is " + numberOfRobotsWithFullVis + " !");
        }

        if (numberOfRobotsWithFullVis == topo.robots.size()) {
            System.out.println("Everyone can see each other!!!!!!!!");
        }

        return new Destination2D(this.getLocation());
    }

    // The algorithm
    private Destination algorithm() {

        if (this.algorithmMoving == true) {
            this.algorithmMoved = true;
            this.algorithmMoving = false;
        }
        if (!this.algorithmMoved && this.noRobotsInSnapMoving()) {

            if (this.isTernimal()) {
                this.algorithmMoving = true;
                return this.getDestination();
            } else if (this.allCollinearNeighborsMoved()) {
                this.algorithmMoving = true;
                return this.getDestination();
            }
        }

        return new Destination2D(this.getLocation());
        	
    }

    /**
     * Calculate the destination
     *
     * @return
     */
    private Destination getDestination() {
        double angle = this.getAngleOfMovement();
        double distance = this.getDistance();
        double x = this.getX() + distance * Math.cos(angle);
        double y = this.getY() + distance * Math.sin(angle);
        Point2D dest = new Point2D.Double(x, y);
        
        return new Destination2D(dest);
    }

    /**
     * Pick a random direction not collinear
     *
     * @return
     */
    private double getAngleOfMovement() {
        double randomAngle = Math.random() * Math.PI;
        ArrayList<Double> angles = new ArrayList<>();

        if (this.getMyCollinearNeighbors() == null) {
            return randomAngle;
        }

        for (Robot neighber : this.getMyCollinearNeighbors()) {
            angles.add(Math.atan2(neighber.getY() - this.getY(), (neighber.getX() - this.getX())));
        }

        while (angles.contains(randomAngle)) {
            randomAngle = Math.random() * Math.PI;
        }

        return randomAngle;
    }

    /**
     * Calculate the distance the robot will move which should be the minimum distance from this robot
     * to any other lines divided by 3 power of 4
     *
     * @return
     */
    private double getDistance() {
        double distance = 0;
        double temp = 0;
        if (this.getSegments().size() == 0) {
            distance = 10;
            return distance;
        }
        for (Line2D segment : this.getSegments()) {
            distance = segment.ptLineDist(this.getLocation());
            if (temp == 0 || temp > distance) {
                temp = distance;
            }
        }
        distance = Math.min(distance, temp) / Math.pow(3, 4);
        return distance;
    }

    /**
     * Get all the segments which are formed by the robots in its snapshot
     */
    private ArrayList<Line2D> getSegments() {
        ArrayList<Line2D> segments = new ArrayList<>();
        for (Robot robot1 : this.snap.visRobots) {
            for (Robot robot2 : this.snap.visRobots) {
                if (robot1 != robot2) {
                    Line2D segment =
                            new Line2D.Double(robot1.getLocation(), robot2.getLocation());
                    segments.add(segment);
                }
            }
        }
        return segments;
    }

    /**
     * Calculate all the collinear neighbors of this robot
     *
     * @return
     */
    private ArrayList<Robot> getMyCollinearNeighbors() {
        ArrayList<Robot> myCollinearNeighbors = new ArrayList<>();
        List<Robot> robots1 = this.snap.visRobots;    	
        List<Robot> robots2 = this.snap.visRobots;

        if (this.snap.visRobots.size() == 1) {
            return null;
        }

        Iterator<Robot> iterator1 = robots1.iterator();
        Iterator<Robot> iterator2 = robots2.iterator();

        while (iterator1.hasNext()) {
            Robot robot1 = iterator1.next();
            while (iterator2.hasNext()) {
                Robot robot2 = iterator2.next();
                if (this.isCollinearWith(robot1, robot2)
                        && !myCollinearNeighbors.contains(robot1)
                        && !myCollinearNeighbors.contains(robot2)) {
                    myCollinearNeighbors.add(robot1);
                    myCollinearNeighbors.add(robot2);
                }
            }
        }
        return myCollinearNeighbors;
    }

    /**
     * Determine whether two robots and this robot are collinear
     */
    private boolean isCollinearWith(Robot robot1, Robot robot2) {
        if (robot1 != robot2) {
            if (this.getX() - robot1.getX() == 0 && this.getX() - robot2.getX() == 0) {
                return true;
            }
            double slope1 = (this.getY() - robot1.getY()) / (this.getX() - robot1.getX());
            double slope2 = (this.getY() - robot2.getY()) / (this.getX() - robot2.getX());
            if (slope1 != slope2) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine whether the robot is a terminal point in any lines
     *
     * @return
     */
    private boolean isTernimal() {
        if (this.snap.visRobots.size() == 1) {
            return true;
        }
        for (Robot robot1 : this.snap.visRobots) {
            for (Robot robot2 : this.snap.visRobots) {
                if (robot1 != robot2 && !this.isCollinearWith(robot1, robot2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determine whether the all the collinear neighbors are not moving
     *
     * @return
     */
    private boolean allCollinearNeighborsMoved() {
        for (Robot collinearNeighbor : this.getMyCollinearNeighbors()) {
            if (!((MyRobot) collinearNeighbor).algorithmMoved) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine whether all the robots in the snapshot are not moving
     *
     * @return
     */
    private boolean noRobotsInSnapMoving() {
    	
    	System.out.println(this.snap);
        for (Robot robot : this.snap.visRobots) {
            if (((MyRobot) robot).algorithmMoving) {
                return false;
            }
        }
        return true;
    }
}
