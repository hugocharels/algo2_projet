package ulb.algo2.node;

public abstract class AbstractNode {

	private MBR mbr;
	private Node father;

	// Constructor
	public AbstractNode(Node father, MBR mbr) {
		this.mbr = mbr;
		this.father = father;
	}

	// Modifiers
	public void expandMBR(MBR other) {
		this.mbr.expand(other);
	}

	// Verifiers
	public boolean isFather(Node father) { return this.father == father; }
	public boolean isRoot() { return father == null; }
	public abstract boolean isLeaf();

	// Setters
	public void setFather(Node father) { this.father = father; }
	public void setMBR(MBR mbr) { this.mbr = mbr; }

	// Getters
	public Node getFather() { return father; }
	public MBR getMBR() { return mbr; }

}
