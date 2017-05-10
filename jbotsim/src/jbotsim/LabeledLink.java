package jbotsim;


/**
 * 
 * @author JiaqiXu
 *
 */
public class LabeledLink extends Link {
	private  Node labeledendpoint1;
	private  Node labeledendpoint2;
	private  int label1;
	private  int label2;
    
	public LabeledLink(Node n1, Node n2,  int label1, int label2) {
		// TODO Auto-generated constructor stub
    	 this.source = n1;
         this.destination = n2;       
         this.mode = Mode.WIRED;
    	 this.labeledendpoint1 = n1;
    	 this.labeledendpoint2 = n2;
    	 this.label1 = label1;
    	 this.label2 = label2;
    	 this.type = Type.UNDIRECTED;
	}
     
    
    public Node getLabeledendpoint1() {
		return labeledendpoint1;
	}

	public void setLabeledendpoint1(Node labeledendpoint1) {
		this.labeledendpoint1 = labeledendpoint1;
	}

	public Node getLabeledendpoint2() {
		return labeledendpoint2;
	}

	public void setLabeledendpoint2(Node labeledendpoint2) {
		this.labeledendpoint2 = labeledendpoint2;
	}

	
	public int getLabel1() {
		return label1;
	}

	public void setLabel1(int label1) {
		this.label1 = label1;
	}
	
	public int getLabel2() {
		return label2;
	}

	public void setLabel2(int label2) {
		this.label2 = label2;
	}
	
	

	
     
     /**
      * Return the link's labeled port which is associated with this node; 
      * otherwise, return -1 if there is no the node is not the end points  of this link.
      * @param node
      * @return
      */
     public int getPort(Node node)
     {
    	 if(labeledendpoint1 ==node)
    		 return label1;
    	 if(labeledendpoint2 ==node)
    		 return label2;
    	 
    	 return -1;
     }
     
     /**
      * Return the associated Node of this link in terms of labeled port
      * @param port
      * @return
      */
     public Node getNode(int port)
     {
    	 if(label1 == port)
    		 return labeledendpoint1;
    	 if(label2 == port)
    		 return labeledendpoint2;
    	 
    	 return null;
     }
	
}
