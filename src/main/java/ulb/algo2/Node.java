package ulb.algo2;

import java.util.List;

public class Node<T> {

	private final T data;
	private Node<T> father;
	private List<Node<T>> children;


	// Constructors
	public Node(T data, Node<T> father) {
		this.data = data;
		this.father = father;
	}

	public Node(T data, Node<T> father, List<Node<T>> children) {
		this(data, father);
		this.children = children;
	}

	// Modifiers
	public void addChild(Node<T> child) {
		children.add(child);
	}

	public void removeChild(Node<T> child) {
		children.remove(child);
	}

	// Verifiers
	public boolean isChild(Node<T> child) {
		return children.contains(child);
	}

	public boolean isFather(Node<T> father) {
		return this.father == father;
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public boolean isRoot() {
		return father == null;
	}

	// Setters
	public void setFather(Node<T> father) {
		this.father = father;
	}

	// Getters
	public T getData() {
		return data;
	}

	public Node<T> getFather() {
		return father;
	}

}
