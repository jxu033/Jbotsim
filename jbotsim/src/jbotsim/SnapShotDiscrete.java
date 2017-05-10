package jbotsim;

import java.util.ArrayList;
import java.util.List;



public class SnapShotDiscrete extends SnapShot{
      List<Integer> outports = new ArrayList<Integer>();
      
      public List<Integer> getOutPort()
      {
    	  return outports;
      }
}
