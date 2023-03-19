package ulb.algo2.node;


public abstract class AbstractNode<T> {

	private T data;
	private Node<T> father;

	// Constructor
	public AbstractNode(T data, Node<T> father) {
		this.data = data;
		this.father = father;
	}

	// Verifiers
	public boolean isFather(Node<T> father) { return this.father == father; }
	public abstract boolean isLeaf();
	public boolean isRoot() { return father == null; }

	// Setters
	public void setFather(Node<T> father) { this.father = father; }
	public void setData(T data) { this.data = data; }

	// Getters
	public AbstractNode<T> getFather() { return father; }
	public T getData() { return data; }

}