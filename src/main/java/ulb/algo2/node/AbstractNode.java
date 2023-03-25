package ulb.algo2.node;

import ulb.algo2.MBR;

public abstract class AbstractNode {

	private MBR mbr;
	private Node father;

	// Constructor
	public AbstractNode(Node father, MBR mbr) {
		this.mbr = mbr;
		this.father = father;
	}

	// Modifiers
	public void swapData(AbstractNode other) {
		MBR temp = this.mbr;
		this.mbr = other.mbr;
		other.mbr = temp;
	}

	public void swapFather(AbstractNode other) {
		Node temp = this.father;
		this.father = other.father;
		other.father = temp;
	}

	public void expandMBR(MBR other) {
		this.mbr.expand(other);
	}

	// Verifiers
	public boolean isFather(Node father) { return this.father == father; }
	public abstract boolean isLeaf();
	public boolean isRoot() { return father == null; }

	// Setters
	public void setFather(Node father) { this.father = father; }
	public void setMBR(MBR mbr) { this.mbr = mbr; }

	// Getters
	public Node getFather() { return father; }
	public MBR getMBR() { return mbr; }

	public String toString() {
		return "Node" ;
	}

}
