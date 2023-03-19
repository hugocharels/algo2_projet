package ulb.algo2.node;

public class Leaf<T> extends AbstractNode<T> {

	// TODO : Add attributes and methods

	// Constructor
	public Leaf(T data, Node<T> father) {
		super(data, father);
	}

	// Modifiers


	// Verifiers
	@Override
	public boolean isLeaf() { return true; }

	// Getters


}
