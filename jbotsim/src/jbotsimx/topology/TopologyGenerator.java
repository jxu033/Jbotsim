package jbotsimx.topology;

import jbotsim.LabeledLink;
import jbotsim.Link;
import jbotsim.Node;
import jbotsim.Topology;

import java.util.List;

import com.sun.javafx.scene.control.skin.LabeledImpl;

public class TopologyGenerator {
	public static void generateLine(Topology tp, int order){
		int scale=(tp.getDimensions().width-50)/order;
		tp.setCommunicationRange(scale+1);
		for (int i=0; i<order; i++)
			tp.addNode(50+i*scale,100,tp.newInstanceOfModel("default"));
	}
	public static void generateRing(Topology topology, int nbNodes) {
		generateRing(topology, nbNodes, false);
	}
	public static void generateRing(Topology topology, int nbNodes, boolean directed){
		topology.setCommunicationRange(0);
		double angle=Math.PI*2.0/nbNodes;
		int scale=100;
		for (int i=0; i<nbNodes; i++)
			topology.addNode(50 + scale + Math.cos(angle*i)*scale,
					50 + scale + Math.sin(angle*i)*scale,topology.newInstanceOfModel("default"));

		List<Node> nodes = topology.getNodes();
		Link.Type type = directed?Link.Type.DIRECTED:Link.Type.UNDIRECTED;
		for (int i=0; i<nbNodes-1; i++)
			topology.addLink(new Link(nodes.get(i), nodes.get(i+1), type));
		topology.addLink(new Link(nodes.get(nbNodes - 1), nodes.get(0), type));
	}
	
	/**
	 * Created by JiaqiXu
	 * @param topology
	 * @param nbNodes
	 */
	public static void generateDiscreteRing(Topology topology, int nbNodes)
	{
		topology.setCommunicationRange(0);
		double angle=Math.PI*2.0/nbNodes;
		int scale=100;
		for (int i=0; i<nbNodes; i++)
			topology.addNode(50 + scale + Math.cos(angle*i)*scale,
					50 + scale + Math.sin(angle*i)*scale,topology.newInstanceOfModel("default"));

		List<Node> nodes = topology.getNodes();
		for (int i=0; i<nbNodes-1; i++)
			topology.addLabeledLink(new LabeledLink(nodes.get(i), nodes.get(i+1), 1, 2));
		topology.addLabeledLink(new LabeledLink(nodes.get(nbNodes - 1), nodes.get(0), 1,2));
		
	}
	
	public static void generateGrid(Topology tp, int order){
		generateGrid(tp, order, order);
	}
	public static void generateGrid(Topology tp, int orderX, int orderY){
		int scale=(tp.getDimensions().width-50)/orderX;
		tp.setCommunicationRange(scale+1);
		for (int i=0; i<orderX; i++)
			for (int j=0; j<orderY; j++)
				tp.addNode(50+i*scale,50+j*scale,tp.newInstanceOfModel("default"));
	}
	
	/**
	 * Created by JiaqiXu
	 * @param tp
	 * @param orderX
	 * @param orderY
	 */
	public static void generateDiscreteGrid(Topology tp, int orderX, int orderY){
		Node[][] matrix = new Node[orderX][orderY];
		int scale=(tp.getDimensions().width-50)/orderX;
		for (int i=0; i<orderX; i++)
			for (int j=0; j<orderY; j++){
				Node a=tp.newInstanceOfModel("default");
				a.disableWireless();
				matrix[i][j] = a ;
				tp.addNode(50+i*scale,50+j*scale,a);			
			}
		
		for(int i =0; i<orderX; i++)
			for(int j =0; j< orderY; j++){
				if(i-1>=0){
					tp.addLabeledLink(new LabeledLink(matrix[i][j], matrix[i-1][j], 4, 2));
				}
				
				if(j-1>=0){
					tp.addLabeledLink(new LabeledLink(matrix[i][j], matrix[i][j-1], 1, 3));
				}		
			}
	}
	
	public static void generateTorus(Topology tp, int order){
		int scale=(tp.getDimensions().width-50)/order;
		tp.setCommunicationRange(scale+1);
		Node[][] matrix = new Node[order][order];
		for (int i=0; i<order; i++)
			for (int j=0; j<order; j++){
				Node node = tp.newInstanceOfModel("default");
				tp.addNode(50+i*scale, 50+j*scale, node);
				matrix[i][j]=node;
			}
		for (int i=0; i<order; i++)
			tp.addLink(new Link(matrix[i][0], matrix[i][order-1]));

		for (int j=0; j<order; j++)
			tp.addLink(new Link(matrix[0][j], matrix[order-1][j]));
	}
	public static void generateKN(Topology topology, int nbNodes){
		double angle=Math.PI*2.0/nbNodes;
		int scale=100;
		for (int i=0; i<nbNodes; i++)
			topology.addNode(50 + scale + Math.cos(angle*i)*scale,
					50 + scale + Math.sin(angle*i)*scale,topology.newInstanceOfModel("default"));
		topology.setCommunicationRange(250);
	}
}
