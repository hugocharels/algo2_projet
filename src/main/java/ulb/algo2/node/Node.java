package ulb.algo2.node;

import ulb.algo2.MBR;

import java.util.List;


public class Node extends AbstractNode {

	private List<AbstractNode> children;

	// Constructor
	public Node(MBR<Double> data, Node father) {
		super(data, father);
	}

	public Node(MBR<Double> data, Node father, List<AbstractNode> children) {
		this(data, father);
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

}