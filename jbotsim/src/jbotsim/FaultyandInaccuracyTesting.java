package jbotsim;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jbotsim.ui.JViewer;
import jbotsim.ui.painting.RobotPainter;

public class FaultyandInaccuracyTesting {
	public static void main(String[] args) {
		Topology tp = new Topology(false);
		tp.setNodeScheduler(new DefaultRobotScheduler());
		tp.disableWireless();

		tp.setClockSpeed(100);
		for (int i = 0; i < 5; i++) {
			Point2D p = new Point2D.Double(new Random().nextInt(tp.getWidth()),
					new Random().nextInt(tp.getHeight()));
			TestRobot robot = new TestRobot(p);
			robot.name = Integer.toString(i + 1);
			tp.addRobot(robot);
		}

		 tp.setCrashFaulty(5);
		// tp.setByzantineFaulty(5);
		// tp.setSelfStabilization(5);
		//tp.setInaccuracies(5, Math.PI, 5);

		JViewer jv = new JViewer(tp);

		jv.getJTopology().addNodePainter(new RobotPainter());
		tp.start();
	}
}

class TestRobot extends FullVisRobot {

	public TestRobot(Point2D currentLocation) {
		this.sensingRange = Double.POSITIVE_INFINITY;
		// it is important to disable wireless for robot
		this.disableWireless();
		this.color = Color.red;
		this.setLocation(currentLocation.getX(), currentLocation.getY());
	}

	public void move(Destination dest) {
		this.dest = (Destination2D) dest;
		setDirection(((Destination2D) dest).dest);
		move(Math.min(1, this.distance(this.dest)));

	}

	public SnapShot look() {
		SnapShot snap = new SnapShot();
		snap.getSnapShot(this);
		return snap;
	}

	public Destination compute(SnapShot snap) {

		List<Point2D> a = new ArrayList<Point2D>();
		for (Robot robot : snap.visRobots) {
			a.add(robot.getLocation());
		}
		Destination2D destination = new Destination2D();
		destination.dest = a.get((int) (Math.random() * a.size()));

		return destination;

	}
}
