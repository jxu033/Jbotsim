/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jbotsim;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import jbotsim.ui.JViewer;


/**
 *
 * @author giuseppe
 */
public class LCMNode extends Node{
    ArrayList<Point2D> locations;
    Point2D target;

    @Override
    public void onPreClock() {  // LOOK
        locations = new ArrayList<Point2D>();
        for (Node node : getSensedNodes())
            locations.add(node.getLocation());
    }
    @Override
    public void onClock(){      // COMPUTE
        System.out.println(locations.size());
        if(locations.size()!=0){
            target = locations.get((new Random()).nextInt(locations.size()));
        }else
        {target=new Point2D() {
            double x;
            double y;
            @Override
            public double getX() {
               return x;//To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double getY() {
                return y; //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setLocation(double x, double y) {
                this.x=x;this.y=y;
            
            }
        };
        target.setLocation(new Random().nextInt(600),new Random().nextInt(400));
        
        }
    }
    @Override
    public void onPostClock(){  // MOVE
        setDirection(target);
        move(Math.min(1, distance(target)));
    }
    
    
public static void main(String[] args) {
    Topology tp = new Topology(false);
    tp.setDefaultNodeModel(LCMNode.class);
    tp.setNodeScheduler(new AsynchNodeScheduler());
    tp.disableWireless();
    tp.setSensingRange(100);
    tp.setClockSpeed(100);
    for (int i = 0; i<20; i++)
        
        tp.addNode(new Random().nextInt(tp.getWidth()),new Random().nextInt(tp.getHeight()));
    new JViewer(tp);
    tp.start();
}        
            
            
}    