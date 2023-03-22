package ulb.algo2.node;

import ulb.algo2.MBR;

import java.util.List;


public class Node extends AbstractNode {

	private List<AbstractNode> children;

	// Constructor
	public Node(Node father, MBR data) {
		super(father, data);
	}

	public Node(Node father, MBR data, List<AbstractNode> children) {
		this(father, data);
		this.children = children;
	}

	// Modifiers
	public void addChild(AbstractNode child) { children.add(child); }
	public void removeChild(AbstractNode child) { children.remove(child); }

	public void swapChildren(Node other) {
		List<AbstractNode> temp = children;
		children = other.children;
		other.children = temp;
	}

	// Verifiers
	public boolean isChild(AbstractNode child) { return children.contains(child); }

	@Override
	public boolean isLeaf() { return false; }

	// Getters
	public List<AbstractNode> getChildren() { return children; }
	public AbstractNode getChild(int x) { return children.get(x); }
	public int getChildrenNb() { return children.size(); }


}
