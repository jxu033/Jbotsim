package jbotsim;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by HaochuanRan on 2017-02-20.
 */
public class Volume {
    private double radius;
    private double square;
    private Point2D center;

    public Volume(double radius, Point2D center) {
        this.radius = radius;
        this.center = center;
        this.square = Math.PI * radius * radius;
    }

    /**
     * Determine whether the robot is completely in the area of the two lines
     */
    public boolean isInLinesArea(ArrayList<Line2D> lines, Volume volume) {
        double distance1 = lines.get(0).ptLineDist(this.center);
        double distance2 = lines.get(1).ptLineDist(this.center);

        int[] positionOfVolume = getPosition(lines, volume); //The potential obstructing robot's position related to Lines
        int[] positionOfThis = getPosition(lines, this);

        if (positionOfThis[0] == positionOfVolume[0]
                && positionOfThis[1] == positionOfVolume[1]) {
            if (distance1 >= this.radius && distance2 >= this.radius) {
                return true;
            }
        }
        return false;
    }

    public int[] getPosition(ArrayList<Line2D> lines, Volume volume) {
        int[] positons = new int[2];
        positons[0] = pointPositionToLine(lines.get(0), volume.center);
        positons[1] = pointPositionToLine(lines.get(1), volume.center);
        return positons;
    }

    private int pointPositionToLine(Line2D line2D, Point2D point) {

        double slope = (line2D.getY2() - line2D.getY1()) / (line2D.getX2() - line2D.getX1());
        double offSet = line2D.getY1() - slope * line2D.getX1();

        double result = point.getX() * slope - point.getY() + offSet;
        if (result > 0) {
            return -1;
        }
        return 1;
    }

    //To calculate common tangent lines for two circles
    public ArrayList<Line2D> commonTanLines(Volume volume) {
        ArrayList<Line2D> lines = new ArrayList<>();

        //To find the cross node of the common tangent lines
        double x = (this.getRadius() * volume.center.getX() - volume.getRadius() * this.center.getX())
                / (this.getRadius() - volume.getRadius());
        double y = (this.getRadius() * volume.center.getY() - volume.getRadius() * this.center.getY())
                / (this.getRadius() - volume.getRadius());

        Point2D crossNode = new Point();
        crossNode.setLocation(x, y);

        lines = this.pointTanLines(crossNode, volume);
        return lines;
    }

    //To calculate a point's two tangent lines of a circle
    public ArrayList<Line2D> pointTanLines(Point2D point, Volume volume) {
        try {
            ArrayList<Line2D> lines = new ArrayList<>();
            //Calculate nodes that the two tangent lines pass
            ArrayList<Point2D> points = caculateTanPoints(point, volume);
            lines.add(0, new Line2D.Double(point, points.get(0)));
            lines.add(1, new Line2D.Double(point, points.get(1)));
            return lines;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Calculate the two tangent points which are on the tangent lines and the circle of the robot volume
     */
     private ArrayList<Point2D> caculateTanPoints(Point2D point, Volume volume) throws Exception {
        ArrayList<Point2D> points = new ArrayList<>();
        Point2D point1 = new Point.Double();
        Point2D point2 = new Point.Double();
        double r = volume.radius;
        double x0 = volume.center.getX();
        double y0 = volume.center.getY();
        double x1 = point.getX();
        double y1 = point.getY();

        /**
         * Find solutions of
         * (y - y1)*(y - y0) + (x - x0)*(x - x1) ==0 && (x - x0)^2 + (y - y0)^2 == r^2
         */
        Double xp1 = ((-2) * r * r * x0 + 2 * x0 * x0 * x0 + 2 * r * r * x1 - 4 * x0 * x0 * x1 + 2 * x0 * x1 * x1
                + 2 * x0 * y0 * y0 - 4 * x0 * y0 * y1 + 2 * x0 * y1 * y1 - Math.sqrt((2 * r * r * x0 - 2 * x0 * x0 * x0 - 2 * r * r * x1 + 4 * x0 * x0 * x1 - 2 * x0 * x1 * x1 - 2 * x0 * y0 * y0
                + 4 * x0 * y0 * y1 - 2 * x0 * y1 * y1) * (2 * r * r * x0 - 2 * x0 * x0 * x0 - 2 * r * r * x1 + 4 * x0 * x0 * x1 - 2 * x0 * x1 * x1 - 2 * x0 * y0 * y0
                + 4 * x0 * y0 * y1 - 2 * x0 * y1 * y1) - 4 * (x0 * x0 - 2 * x0 * x1 + x1 * x1 + y0 * y0 - 2 * y0 * y1 + y1 * y1) * (r * r * r * r
                - 2 * r * r * x0 * x0 + x0 * x0 * x0 * x0 + 2 * r * r * x0 * x1 - 2 * x0 * x0 * x0 * x1 + x0 * x0 * x1 * x1 - r * r * y0 * y0 + x0 * x0 * y0 * y0
                + 2 * r * r * y0 * y1 - 2 * x0 * x0 * y0 * y1 - r * r * y1 * y1 + x0 * x0 * y1 * y1))) / (2 * (x0 * x0 - 2 * x0 * x1 + x1 * x1 + y0 * y0 - 2 * y0 * y1 + y1 * y1));

        Double xp2 = ((-2) * r * r * x0 + 2 * x0 * x0 * x0 + 2 * r * r * x1 - 4 * x0 * x0 * x1 + 2 * x0 * x1 * x1 + 2 * x0 * y0 * y0 - 4 * x0 * y0 * y1 + 2 * x0 * y1 * y1
                + Math.sqrt((2 * r * r * x0 - 2 * x0 * x0 * x0 - 2 * r * r * x1 + 4 * x0 * x0 * x1 - 2 * x0 * x1 * x1 - 2 * x0 * y0 * y0
                + 4 * x0 * y0 * y1 - 2 * x0 * y1 * y1) * (2 * r * r * x0 - 2 * x0 * x0 * x0 - 2 * r * r * x1 + 4 * x0 * x0 * x1 - 2 * x0 * x1 * x1 - 2 * x0 * y0 * y0
                + 4 * x0 * y0 * y1 - 2 * x0 * y1 * y1) - 4 * (x0 * x0 - 2 * x0 * x1 + x1 * x1 + y0 * y0 - 2 * y0 * y1 + y1 * y1) * (r * r * r * r
                - 2 * r * r * x0 * x0 + x0 * x0 * x0 * x0 + 2 * r * r * x0 * x1 - 2 * x0 * x0 * x0 * x1 + x0 * x0 * x1 * x1 - r * r * y0 * y0 + x0 * x0 * y0 * y0
                + 2 * r * r * y0 * y1 - 2 * x0 * x0 * y0 * y1 - r * r * y1 * y1 + x0 * x0 * y1 * y1))) / (2 * (x0 * x0 - 2 * x0 * x1 + x1 * x1 + y0 * y0 - 2 * y0 * y1 + y1 * y1));

        Double yp1, yp2;
        if (y0 == y1) {
            yp1 = Math.sqrt(r * r - (xp1 - x0) * (xp1 - x0)) + y0;
            yp2 = -yp1;
        } else {
            yp1 = (1 / (y0 - y1)) * (-r * r + x0 * x0 - x0 * x1 + y0 * y0 - y0 * y1 + (r * r * x0 * x0 - x0 * x0 * x0 * x0 - 2 * r * r * x0 * x1 + 3 * x0 * x0 * x0 * x1
                    + r * r * x1 * x1 - 3 * x0 * x0 * x1 * x1 + x0 * x1 * x1 * x1 - x0 * x0 * y0 * y0 + x0 * x1 * y0 * y0 + 2 * x0 * x0 * y0 * y1 - 2 * x0 * x1 * y0 * y1
                    - x0 * x0 * y1 * y1 + x0 * x1 * y1 * y1) / (x0 * x0 - 2 * x0 * x1 + x1 * x1 + y0 * y0 - 2 * y0 * y1 + y1 * y1) + (x0 - x1) * Math.sqrt((2 * r * r * x0 - 2 * x0 * x0 * x0 - 2 * r * r * x1 + 4 * x0 * x0 * x1 - 2 * x0 * x1 * x1 - 2 * x0 * y0 * y0
                    + 4 * x0 * y0 * y1 - 2 * x0 * y1 * y1) * (2 * r * r * x0 - 2 * x0 * x0 * x0 - 2 * r * r * x1 + 4 * x0 * x0 * x1 - 2 * x0 * x1 * x1 - 2 * x0 * y0 * y0
                    + 4 * x0 * y0 * y1 - 2 * x0 * y1 * y1) - 4 * (x0 * x0 - 2 * x0 * x1 + x1 * x1 + y0 * y0 - 2 * y0 * y1 + y1 * y1) * (r * r * r * r
                    - 2 * r * r * x0 * x0 + x0 * x0 * x0 * x0 + 2 * r * r * x0 * x1 - 2 * x0 * x0 * x0 * x1 + x0 * x0 * x1 * x1 - r * r * y0 * y0 + x0 * x0 * y0 * y0
                    + 2 * r * r * y0 * y1 - 2 * x0 * x0 * y0 * y1 - r * r * y1 * y1 + x0 * x0 * y1 * y1)) / (2 * (x0 * x0 - 2 * x0 * x1 + x1 * x1 + y0 * y0 - 2 * y0 * y1 + y1 * y1)));

            yp2 = (1 / (y0 - y1)) * (-r * r + x0 * x0 - x0 * x1 + y0 * y0 - y0 * y1 + (r * r * x0 * x0 - x0 * x0 * x0 * x0 - 2 * r * r * x0 * x1 + 3 * x0 * x0 * x0 * x1
                    + r * r * x1 * x1 - 3 * x0 * x0 * x1 * x1 + x0 * x1 * x1 * x1 - x0 * x0 * y0 * y0 + x0 * x1 * y0 * y0 + 2 * x0 * x0 * y0 * y1 - 2 * x0 * x1 * y0 * y1
                    - x0 * x0 * y1 * y1 + x0 * x1 * y1 * y1) / (x0 * x0 - 2 * x0 * x1 + x1 * x1 + y0 * y0 - 2 * y0 * y1 + y1 * y1) - (x0 - x1) * Math.sqrt((2 * r * r * x0 - 2 * x0 * x0 * x0 - 2 * r * r * x1 + 4 * x0 * x0 * x1 - 2 * x0 * x1 * x1 - 2 * x0 * y0 * y0
                    + 4 * x0 * y0 * y1 - 2 * x0 * y1 * y1) * (2 * r * r * x0 - 2 * x0 * x0 * x0 - 2 * r * r * x1 + 4 * x0 * x0 * x1 - 2 * x0 * x1 * x1 - 2 * x0 * y0 * y0
                    + 4 * x0 * y0 * y1 - 2 * x0 * y1 * y1) - 4 * (x0 * x0 - 2 * x0 * x1 + x1 * x1 + y0 * y0 - 2 * y0 * y1 + y1 * y1) * (r * r * r * r
                    - 2 * r * r * x0 * x0 + x0 * x0 * x0 * x0 + 2 * r * r * x0 * x1 - 2 * x0 * x0 * x0 * x1 + x0 * x0 * x1 * x1 - r * r * y0 * y0 + x0 * x0 * y0 * y0
                    + 2 * r * r * y0 * y1 - 2 * x0 * x0 * y0 * y1 - r * r * y1 * y1 + x0 * x0 * y1 * y1)) / (2 * (x0 * x0 - 2 * x0 * x1 + x1 * x1 + y0 * y0 - 2 * y0 * y1 + y1 * y1)));
        }
        if (xp1.isNaN() || yp1.isNaN() || xp2.isNaN() || yp2.isNaN()) {
            throw new Exception("Tangent points are not valid");
        }

        point1.setLocation(xp1, yp1);
        point2.setLocation(xp2, yp2);
        points.add(point1);
        points.add(point2);
        return points;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getSquare() {
        return this.square;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        this.square = radius * radius * Math.PI;
    }

    public void setSquare(double square) {
        this.square = square;
        this.radius = Math.sqrt(square / Math.PI);
    }

    public boolean isBiggerThan(Volume volume) {
        if (volume.radius < this.radius) {
            return true;
        }
        return false;
    }

    public boolean equalsTo(Volume volume) {
        if (volume.radius == this.radius) {
            return true;
        }
        return false;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }
}