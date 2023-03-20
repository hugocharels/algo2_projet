package ulb.algo2.node;

import ulb.algo2.MBR;

public abstract class AbstractNode {

	private MBR<Double> data;
	private Node father;

	// Constructor
	public AbstractNode(MBR<Double> data, Node father) {
		this.data = data;
		this.father = father;
	}

	// Modifiers
	public void swapData(AbstractNode other) {
		MBR<Double> temp = this.data;
		this.data = other.data;
		other.data = temp;
	}

	public void swapFather(AbstractNode other) {
		Node temp = this.father;
		this.father = other.father;
		other.father = temp;
	}


	// Verifiers
	public boolean isFather(Node father) { return this.father == father; }
	public abstract boolean isLeaf();
	public boolean isRoot() { return father == null; }

	// Setters
	public void setFather(Node father) { this.father = father; }
	public void setData(MBR<Double> data) { this.data = data; }

	// Getters
	public AbstractNode getFather() { return father; }
	public MBR<Double> getData() { return data; }

}