package jbotsim;


/**
 * Created by acasteig on 10/19/15.
 */
public class DefaultNodeScheduler implements NodeScheduler {

    @Override
    public void onClock(Topology tp) {
        for (Node node : tp.getNodes())
            node.onPreClock();
        for (Node node : tp.getNodes())
            node.onClock();        
        for (Node node : tp.getNodes())
            node.onPostClock();
    }
}
