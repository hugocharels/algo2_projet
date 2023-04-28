package ulb.algo2.node;

import java.util.List;


public class Node extends AbstractNode {

	private List<AbstractNode> children;

	// Constructor
	public Node(Node father, MBR data) {
		super(father, data);
		this.children = new java.util.ArrayList<>();
	}

	public Node(Node father, MBR data, List<AbstractNode> children) {
		this(father, data);
		this.children = children;
	}

	// Modifiers
	public void addChild(AbstractNode child) { children.add(child); }
	public void removeChild(AbstractNode child) { children.remove(child); }
	public void removeChildren() { children.clear(); }

	// Verifiers
	public boolean isChild(AbstractNode child) { return children.contains(child); }
	public boolean hasLeaf() { return children.size() > 0 && children.get(0).isLeaf(); }
	public boolean hasNoChildren() { return children.size() == 0; }
	public boolean maxChildrenReach(int N) { return children.size() >= N; }
	@Override
	public boolean isLeaf() { return false; }

	// Getters
	public List<AbstractNode> getChildren() { return children; }
	public AbstractNode getChild(int x) { return children.get(x); }

}
