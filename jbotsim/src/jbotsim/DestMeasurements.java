package jbotsim;

public  class  DestMeasurements {
       static double total_Distance;
       static int total_Moves;
       static int total_Activation;
       
       public  static void reset()
       {
    	   total_Activation =0;
    	   total_Distance= 0;
           total_Moves = 0;
       }
       
}
