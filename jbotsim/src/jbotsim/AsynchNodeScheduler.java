/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jbotsim;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author giuseppe
 */
public class AsynchNodeScheduler implements NodeScheduler{

    
    
    @Override
    public void onClock(Topology tp) {
        ArrayList<Node> alist=new ArrayList<Node>();
        Random r=new Random();
        alist.addAll(tp.getNodes());
        int x=r.nextInt(tp.getNodes().size());
        for(int y=x; y >0; y--){
            alist.remove(r.nextInt(alist.size()));
            
        }
        
        for (Node node : alist)
            {System.out.println("Activating:"+node.ID);
            node.onPreClock();
            }
        for (Node node : alist)
            node.onClock();
        for (Node node : alist)
            node.onPostClock();
    
    }
    
}
