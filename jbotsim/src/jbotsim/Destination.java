package jbotsim;

import sun.security.krb5.internal.crypto.Des;

import java.awt.geom.Point2D;

/**
 * 
 * @author JiaqiXu
 *
 */
public class Destination {
    Destination dest = null;

    public Destination(){}

    public void setDest(Destination dest){
        this.dest = dest;
    }

    public Destination getDest(){
        return dest;
    }
}
