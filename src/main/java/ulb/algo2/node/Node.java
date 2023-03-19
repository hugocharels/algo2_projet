package ulb.algo2.node;

import java.util.List;


public class Node<T> extends AbstractNode<T> {

	private List<AbstractNode<T>> children;

	// Constructor
	public Node(T data, Node<T> father) {
		super(data, father);
	}

	public Node(T data, Node<T> father, List<AbstractNode<T>> children) {
		this(data, father);
		this.children = children;
	}

	// Modifiers
	public void addChild(AbstractNode<T> child) { children.add(child); }
	public void removeChild(AbstractNode<T> child) { children.remove(child); }

	// Verifiers
	public boolean isChild(AbstractNode<T> child) { return children.contains(child); }

	@Override
	public boolean isLeaf() { return false; }

	// Getters
	public List<AbstractNode<T>> getChildren() { return children; }

}